package com.minimarket.web_minimarket.dto;

public class OrderDetailResponseDTO {
    private int orderId;
    private int productId;
    private String productName;
    private int orderQuantity;
    private double orderPrice;

    // Constructors
    public OrderDetailResponseDTO() {}

    public OrderDetailResponseDTO(int orderId, double orderPrice, int orderQuantity, String productName, int productId) {
        this.orderId = orderId;
        this.orderPrice = orderPrice;
        this.orderQuantity = orderQuantity;
        this.productName = productName;
        this.productId = productId;
    }

    // Getters, setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
