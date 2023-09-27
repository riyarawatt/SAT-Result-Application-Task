package com.task.ecommerce.service;

import com.task.ecommerce.dto.CategoryDTO;
import com.task.ecommerce.dto.ProductDTO;
import org.springframework.data.domain.Page;

public interface CatalogService {
    Page<CategoryDTO> getAllCategories(int page, int pageSize);
    Page<ProductDTO> getProductsByCategory(Integer categoryId, int page, int pageSize);
}
