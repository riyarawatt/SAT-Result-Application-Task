package com.task.ecommerce.service;

import com.task.ecommerce.dto.CategoryDTO;
import com.task.ecommerce.dto.ProductDTO;
import com.task.ecommerce.model.Category;
import com.task.ecommerce.model.Product;
import com.task.ecommerce.repository.CategoryRepository;
import com.task.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatalogSyncService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

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
