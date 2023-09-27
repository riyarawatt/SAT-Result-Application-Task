package com.task.ecommerce.service;

import com.task.ecommerce.dto.CategoryDTO;
import com.task.ecommerce.dto.ProductDTO;
import com.task.ecommerce.model.Category;
import com.task.ecommerce.model.Product;
import com.task.ecommerce.repository.CategoryRepository;
import com.task.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatalogSyncService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<CategoryDTO> fetchCategoriesFromExternalAPI(String apiUrl) {
        // Use RestTemplate to fetch categories from the external API
        ResponseEntity<CategoryDTO[]> response = new RestTemplate().getForEntity(apiUrl, CategoryDTO[].class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return Arrays.asList(response.getBody());
        } else {
            return Collections.emptyList();
        }
    }

    @Transactional
    public void saveCategories(List<CategoryDTO> categoryDTOList) {
        // Convert CategoryDTO objects to Category entities and save to the database
        List<Category> categoryList = categoryDTOList.stream()
                .map(categoryDTO -> {
                    Category category = new Category();
                    category.setCategoryId(categoryDTO.getCategoryId());
                    category.setCategoryName(categoryDTO.getCategoryName());
                    return category;
                })
                .collect(Collectors.toList());

        categoryRepository.saveAll(categoryList);
    }

    public List<ProductDTO> fetchProductsFromExternalAPI(String apiUrl) {
        // Fetch products from the external API using restTemplate
        ProductDTO[] productDTOs = restTemplate.getForObject(apiUrl, ProductDTO[].class);

        if (productDTOs != null) {
            return List.of(productDTOs);
        }

        return List.of();
    }

    public void saveProducts(List<ProductDTO> productDTOList) {
        List<Product> productList = productDTOList.stream()
                .map(productDTO -> {
                    Product product = new Product();
                    product.setProductId(productDTO.getProductId());
                    product.setProductName(productDTO.getProductName());
                    product.setDescription(productDTO.getDescription());
                    product.setSalePrice(productDTO.getSalePrice());
                    product.setDigital(productDTO.getDigital());
                    product.setShippingCost(productDTO.getShippingCost());
                    product.setCustomer_review_count(productDTO.getCustomerReviewCount());
                    product.setCategoryId(productDTO.getCategoryId());
                    product.setImages(productDTO.getImages());
                    return product;
                })
                .collect(Collectors.toList());

        productRepository.saveAll(productList);
    }

}
