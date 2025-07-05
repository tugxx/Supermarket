package com.minimarket.web_minimarket.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class OrderDetailsID {
    private int orderId;
    private int productId;

    // Constructors
    public OrderDetailsID() {}

    public OrderDetailsID(int orderId, int productId) {
        this.orderId = orderId;
        this.productId = productId;
    }

    // Getters and setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
