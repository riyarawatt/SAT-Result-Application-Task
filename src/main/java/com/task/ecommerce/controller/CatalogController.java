package com.task.ecommerce.controller;

import com.task.ecommerce.dto.CategoryDTO;
import com.task.ecommerce.dto.ProductDTO;
import com.task.ecommerce.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/shop")
public class CatalogController {

    @Autowired
    private CatalogService catelogService;

    @CrossOrigin(origins = "http://localhost:8082")
    @GetMapping("/categories")
    public ResponseEntity<Page<CategoryDTO>> getAllCategories(@RequestParam(defaultValue = "1") int page,
                                                              @RequestParam(defaultValue = "10") int pageSize) {
        try {
            Page<CategoryDTO> categories = catelogService.getAllCategories(page,pageSize);
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "http://localhost:8082")
    @GetMapping("/products")
    public ResponseEntity<Page<ProductDTO>> getProductsByCategory(
            @RequestParam Integer categoryId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        try {
            Page<ProductDTO> products = catelogService.getProductsByCategory(categoryId, page, pageSize);
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
