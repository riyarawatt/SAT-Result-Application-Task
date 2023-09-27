let page = 1;
const pageSize = 10;
let data;

// Function to fetch and display categories
function fetchCategories(page, pageSize) {
    fetch(`http://localhost:8081/shop/categories?page=${page}&pageSize=${pageSize}`)
        .then(response => response.json())
        .then(responseData => {
            data = responseData;

            // Handle the categories data
            const categoryList = document.getElementById('category-list');
            categoryList.innerHTML = ''; // Clear previous data

            data.content.forEach(category => {
                const listItem = document.createElement('li');
                // Display both categoryId and categoryName in bold and on separate lines
                listItem.innerHTML = `
                    <strong>Category ID:</strong> ${category.categoryId}<br>
                    <strong>Category Name:</strong> ${category.categoryName}<br><br>
                `;
                listItem.setAttribute('data-category-id', category.categoryId);
                categoryList.appendChild(listItem);

                // Add click event listener to each category list item
                listItem.addEventListener('click', () => {
                    const categoryId = listItem.getAttribute('data-category-id');
                    openProductPage(categoryId);
                });
            });

            // Update pagination controls
            const pageInfo = document.getElementById('page-info');
            pageInfo.textContent = `Page ${page} of ${data.totalPages}`;

            const prevButton = document.getElementById('prev-button');
            prevButton.disabled = page === 1;

            const nextButton = document.getElementById('next-button');
            nextButton.disabled = page === data.totalPages;
        })
        .catch(error => {
            console.error('Error fetching categories:', error);
        });
}

function openProductPage(categoryId) {
    window.location.href = `products.html?categoryId=${categoryId}`;
}

fetchCategories(page, pageSize);

// Pagination controls event listeners
const prevButton = document.getElementById('prev-button');
const nextButton = document.getElementById('next-button');

prevButton.addEventListener('click', () => {
    if (page > 1) {
        page--; // Decrement page
        fetchCategories(page, pageSize);
    }
});

nextButton.addEventListener('click', () => {
    if (page < data.totalPages) {
        page++; // Increment page
        fetchCategories(page, pageSize);
    }
});
