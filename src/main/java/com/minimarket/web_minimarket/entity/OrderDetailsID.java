package com.minimarket.web_minimarket.entity;

import jakarta.persistence.Embeddable;

import java.util.Objects;

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


    @Override
    public String toString() {
        return "OrderDetailsID{" +
                "orderId=" + orderId +
                ", productId=" + productId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetailsID that = (OrderDetailsID) o;
        return orderId == that.orderId && productId == that.productId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, productId);
    }
}
