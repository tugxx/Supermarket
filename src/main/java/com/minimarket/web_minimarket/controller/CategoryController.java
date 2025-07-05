package com.minimarket.web_minimarket.controller;

import com.minimarket.web_minimarket.entity.Category;
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
    public Category createCategory(@RequestBody Category category) { // @RequestBody means take JSON from the request body and convert it to a Category object
        return categoryService.createCategory(category);
    }

    // Get all categories
    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    // Get a category by ID
    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategoryById(@PathVariable("categoryId") int categoryId) {
        Category category = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(category);
    }

    // Get category by name
    @GetMapping("/search")
    public ResponseEntity<Category> getByCategoryName(@RequestParam String categoryName) {
        Category category = categoryService.getCategoryByName(categoryName);
        if (category != null) {
            return ResponseEntity.ok(category);
        }
        return ResponseEntity.notFound().build();
    }

    // Update a category
    @PutMapping("/{categoryId}")
    public ResponseEntity<Category> updateCategory(@PathVariable("categoryId") int categoryId, @RequestBody Category categoryDetails) {
        Category updatedCategory = categoryService.updateCategory(categoryId, categoryDetails);
        return ResponseEntity.ok(updatedCategory);
    }

    // Delete a category
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("categoryId") int categoryId) {
        categoryService.deleteCategoryById(categoryId);
        return ResponseEntity.noContent().build();
    }
}
