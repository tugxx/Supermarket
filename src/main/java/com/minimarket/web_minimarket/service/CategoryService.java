package com.minimarket.web_minimarket.service;

import com.minimarket.web_minimarket.dto.CategoryRequestDTO;
import com.minimarket.web_minimarket.dto.CategoryResponseDTO;
import com.minimarket.web_minimarket.dto.ProductResponseDTO;
import com.minimarket.web_minimarket.entity.Category;
import com.minimarket.web_minimarket.mapper.CategoryMapper;
import com.minimarket.web_minimarket.mapper.ProductMapper;
import com.minimarket.web_minimarket.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service // This is a service class — use easily in other parts of the project
public class CategoryService { // Call function from CategoryRepository
    @Autowired // automatically give an instance of CategoryRepository
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ProductMapper productMapper;

    // Create a new category
    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryDTO) {
        Category category = categoryMapper.categoryRequestToCategory(categoryDTO);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.categoryToCategoryResponse(savedCategory);
    }

    // Get all categories
    public List<CategoryResponseDTO> getAllCategories() {
        return categoryRepository.findAll().stream().map(categoryMapper::categoryToCategoryResponse).collect(Collectors.toList());
    }

    // Get a category by ID
    public CategoryResponseDTO getCategoryById(int categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->new EntityNotFoundException("Category not found"));
        return categoryMapper.categoryToCategoryResponse(category);
    }

    // Get category by name
    public CategoryResponseDTO getCategoryByName(String categoryName) {
        Category category = categoryRepository.getByCategoryName(categoryName).orElseThrow(()->new EntityNotFoundException("Category not found"));
        return categoryMapper.categoryToCategoryResponse(category);
    }

    public List<ProductResponseDTO> getProductsByCategoryId(int categoryId) {
        return categoryRepository.getProductsByCategoryId(categoryId).stream().map(productMapper::productToProductResponse).collect(Collectors.toList());
    }

    // Update a category
    public CategoryResponseDTO updateCategory(int categoryId, CategoryRequestDTO categoryDetails) {
        Category existingCategory = categoryRepository.findById(categoryId).orElseThrow(()->new EntityNotFoundException("Category not found"));
        existingCategory.setCategoryName(categoryDetails.getCategoryName()); // update only the fields you want to change
        Category updatedCategory = categoryRepository.save(existingCategory);
        return categoryMapper.categoryToCategoryResponse(updatedCategory);
    }

    // Delete a category
    public void deleteCategoryById(int categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
