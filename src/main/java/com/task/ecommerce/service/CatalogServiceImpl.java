package com.task.ecommerce.service;

import com.task.ecommerce.dto.CategoryDTO;
import com.task.ecommerce.dto.ProductDTO;
import com.task.ecommerce.model.Category;
import com.task.ecommerce.model.Product;
import com.task.ecommerce.repository.CategoryRepository;
import com.task.ecommerce.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CatalogServiceImpl implements CatalogService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Page<CategoryDTO> getAllCategories(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize); // Page numbers start from 0 in Spring Data
        Page<Category> categories = categoryRepository.findAll(pageable);

        return categories.map(this::convertToDTO);
    }

    private CategoryDTO convertToDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        BeanUtils.copyProperties(category, categoryDTO);
        return categoryDTO;
    }

    @Override
    public Page<ProductDTO> getProductsByCategory(Integer categoryId, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<Product> products = productRepository.findByCategoryId(categoryId,pageable);

        return products.map(this::convertToDTO);
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        BeanUtils.copyProperties(product, productDTO);
        return productDTO;
    }
}
