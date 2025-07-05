package com.minimarket.web_minimarket.service;

import com.minimarket.web_minimarket.entity.Category;
import com.minimarket.web_minimarket.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // This is a service class — use easily in other parts of the project
public class CategoryService { // Call function from CategoryRepository
    @Autowired // automatically give an instance of CategoryRepository
    private CategoryRepository categoryRepository;

    // Create a new category
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    // Get all categories
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Get a category by ID
    public Category getCategoryById(int categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(()->new EntityNotFoundException("Category not found"));
    }

    // Get category by name
    public Category getCategoryByName(String categoryName) {
        return categoryRepository.getByCategoryName(categoryName).orElseThrow(()->new EntityNotFoundException("Category not found"));
    }

    // Update a category
    public Category updateCategory(int categoryId, Category categoryDetails) {
        Category category = getCategoryById(categoryId); // load the existing Category from the database
        category.setCategoryName(categoryDetails.getCategoryName()); // update only the fields you want to change
        return categoryRepository.save(category); // Spring Data sees this is an existing entity, so it runs UPDATE on that row only
    }

    // Delete a category
    public void deleteCategoryById(int categoryId) {
        Category category = getCategoryById(categoryId);
        categoryRepository.delete(category);
    }
}
