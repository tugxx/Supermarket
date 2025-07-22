package com.minimarket.web_minimarket.dto;

public class CategoryRequestDTO {
    private String categoryName;

    // Constructors
    public CategoryRequestDTO() {}

    public CategoryRequestDTO(String categoryName) {
        this.categoryName = categoryName;
    }

    // Getters, setters
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
