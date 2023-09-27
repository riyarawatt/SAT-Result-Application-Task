package com.task.ecommerce.controller;

import com.task.ecommerce.dto.CategoryDTO;
import com.task.ecommerce.dto.ProductDTO;
import com.task.ecommerce.service.CatalogSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/task")
public class CatalogSyncController {

    @Autowired
    private CatalogSyncService categoryService;

    @Autowired
    private CatalogSyncService productService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${external.api.key}") // Load the API key from application.properties or application.yml
    private String apiKey;

    @GetMapping("/categories")
    public ResponseEntity<String> fetchAndSaveCategories() {
        try {
            String apiUrl = "https://stageapi.monkcommerce.app/task/categories";

            // Create an HTTP client (RestTemplate)
            // Set the API key in the request headers
            HttpHeaders headers = new HttpHeaders();
            headers.set("x-api-key", apiKey);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            // Make an HTTP GET request to the external API
            ResponseEntity<CategoryDTO[]> response = restTemplate.exchange(
                    apiUrl, 
                    HttpMethod.GET,
                    entity,
                    CategoryDTO[].class
            );

            // Check if the request was successful (HTTP status code 200)
            if (response.getStatusCode() == HttpStatus.OK) {
                CategoryDTO[] categoryDTOList = response.getBody();

                if (categoryDTOList != null && categoryDTOList.length > 0) {
                    categoryService.saveCategories(List.of(categoryDTOList));
                    return new ResponseEntity<>("Categories fetched and saved successfully.", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("No categories found in the external API.", HttpStatus.NOT_FOUND);
                }
            } else {
                // Handle unsuccessful response (e.g., return an error response)
                return new ResponseEntity<>("Failed to fetch categories from the external API.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            // Handle exceptions (e.g., connection errors)
            return new ResponseEntity<>("Failed to fetch and save categories.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/products")
    public ResponseEntity<String> fetchAndSaveProducts() {
        try {
            String apiUrl = "https://stageapi.monkcommerce.app/task/products";

            // Set up HttpHeaders with x-api-key
            HttpHeaders headers = new HttpHeaders();
            headers.set("x-api-key", apiKey); // Replace with your actual API key

            // Create an HttpEntity with headers
            HttpEntity<String> entity = new HttpEntity<>(headers);

            // Make an HTTP GET request to the external API
            ResponseEntity<ProductDTO[]> response = new RestTemplate().exchange(
                    apiUrl, HttpMethod.GET, entity, ProductDTO[].class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                List<ProductDTO> productDTOList = List.of(response.getBody());
                productService.saveProducts(productDTOList);
                return new ResponseEntity<>("Products fetched and saved successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No products found in the external API.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to fetch and save products.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
