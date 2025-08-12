package com.minimarket.web_minimarket.dto;

import com.minimarket.web_minimarket.entity.Customer;
import com.minimarket.web_minimarket.entity.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderResponseDTO {
    private int orderId;
    private LocalDateTime orderTime;
    private BigDecimal orderTotal;
    private OrderStatus status;
    private int customerId;
    private List<OrderDetailResponseDTO> orderDetails;

    // Constructors
    public OrderResponseDTO() {}

    public OrderResponseDTO(int customerId, List<OrderDetailResponseDTO> orderDetails, int orderId, LocalDateTime orderTime, BigDecimal orderTotal, OrderStatus status) {
        this.customerId = customerId;
        this.orderDetails = orderDetails;
        this.orderId = orderId;
        this.orderTime = orderTime;
        this.orderTotal = orderTotal;
        this.status = status;
    }

    // Getters, setters
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public BigDecimal getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(BigDecimal orderTotal) {
        this.orderTotal = orderTotal;
    }

    public List<OrderDetailResponseDTO> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailResponseDTO> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
