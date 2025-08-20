package com.minimarket.web_minimarket.dto;

import java.math.BigDecimal;

public class OrderDetailRequestDTO {
    private int orderId;
    private int productId;
    private int orderQuantity;
    private BigDecimal orderPrice;

    // Constructors
    public OrderDetailRequestDTO() {}

    public OrderDetailRequestDTO(int orderId, BigDecimal orderPrice, int orderQuantity, int productId) {
        this.orderId = orderId;
        this.orderPrice = orderPrice;
        this.orderQuantity = orderQuantity;
        this.productId = productId;
    }

    // Getters, setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }


    @Override
    public String toString() {
        return "OrderDetailRequestDTO{" +
                "orderId=" + orderId +
                ", productId=" + productId +
                ", orderQuantity=" + orderQuantity +
                ", orderPrice=" + orderPrice +
                '}';
    }
}
