package com.minimarket.web_minimarket.controller;

import com.minimarket.web_minimarket.dto.CategoryRequestDTO;
import com.minimarket.web_minimarket.dto.CategoryResponseDTO;
import com.minimarket.web_minimarket.dto.ProductResponseDTO;
import com.minimarket.web_minimarket.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//✅ @PathVariable → pulls ID from URL path
//✅ @RequestBody → pulls JSON body → Java object
//✅ @RequestParam → pulls query params → Java value
//✅ ResponseEntity → lets you control HTTP status codes

@RestController // This class handles HTTP requests
@RequestMapping("/api/categories") // All routes start with /api/categories
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    // Create a new category
    @PostMapping // POST means create
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody CategoryRequestDTO categoryRequest) { // @RequestBody means take JSON from the request body and convert it to a Category object
        CategoryResponseDTO createdCategory = categoryService.createCategory(categoryRequest);
        return ResponseEntity.ok(createdCategory);
    }

    // Get all categories
    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {
        List<CategoryResponseDTO> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    // Get a category by ID
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable("categoryId") int categoryId) {
        CategoryResponseDTO category = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(category);
    }

    // Get category by name
    @GetMapping("/search")
    public ResponseEntity<CategoryResponseDTO> getByCategoryName(@RequestParam String categoryName) {
        CategoryResponseDTO category = categoryService.getCategoryByName(categoryName);
        if (category != null) {
            return ResponseEntity.ok(category);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{categoryId}/products")
    public ResponseEntity<List<ProductResponseDTO>> getProductsByCategoryId(@PathVariable("categoryId") int categoryId) {
        List<ProductResponseDTO> products = categoryService.getProductsByCategoryId(categoryId);
        return ResponseEntity.ok(products);
    }

    // Update a category
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable("categoryId") int categoryId, @RequestBody CategoryRequestDTO categoryDetails) {
        CategoryResponseDTO updatedCategory = categoryService.updateCategory(categoryId, categoryDetails);
        return ResponseEntity.ok(updatedCategory);
    }

    // Delete a category
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("categoryId") int categoryId) {
        categoryService.deleteCategoryById(categoryId);
        return ResponseEntity.noContent().build();
    }
}
