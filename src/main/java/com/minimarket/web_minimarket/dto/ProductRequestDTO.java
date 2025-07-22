package com.minimarket.web_minimarket.dto;

import java.math.BigDecimal;

public class ProductRequestDTO {
    private String productName;
    private BigDecimal productPrice;
    private int productQuantity;
    private int categoryId;
    private int supplierId;

    // Constructors
    public ProductRequestDTO() {}

    public ProductRequestDTO(int categoryId, String productName, BigDecimal productPrice, int productQuantity, int supplierId) {
        this.categoryId = categoryId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.supplierId = supplierId;
    }

    // Getters, setters
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }
}
