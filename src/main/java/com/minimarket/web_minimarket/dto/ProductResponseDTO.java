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

    // Getters, not setters
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
}
