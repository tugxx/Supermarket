package com.minimarket.web_minimarket.dto;

public class CategoryResponseDTO {
    private int categoryId;
    private String categoryName;

    // Constructors
    public CategoryResponseDTO() {}

    public CategoryResponseDTO(int categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    // Getters, setters
    public String getCategoryName() {
        return categoryName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
