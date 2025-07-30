package com.minimarket.web_minimarket.dto;

import com.minimarket.web_minimarket.entity.Category;
import com.minimarket.web_minimarket.entity.Supplier;

import java.math.BigDecimal;

public class ProductResponseDTO {
    private int productId;
    private String productName;
    private BigDecimal productPrice;
    private int productQuantity;
    private CategoryResponseDTO category;
    private SupplierResponseDTO supplier;

    // Constructors
    public ProductResponseDTO() {}

    public ProductResponseDTO(CategoryResponseDTO category, int productId, String productName, BigDecimal productPrice, int productQuantity, SupplierResponseDTO supplier) {
        this.category = category;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.supplier = supplier;
    }

    // Getters, setters
    public CategoryResponseDTO getCategory() {
        return category;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public SupplierResponseDTO getSupplier() {
        return supplier;
    }

    public void setCategory(CategoryResponseDTO category) {
        this.category = category;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public void setSupplier(SupplierResponseDTO supplier) {
        this.supplier = supplier;
    }
}
