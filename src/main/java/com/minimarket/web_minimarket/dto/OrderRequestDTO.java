package com.minimarket.web_minimarket.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.util.List;

public class OrderRequestDTO {
    @Positive(message = "Customer ID must be positive")
    private int customerId;

    @NotEmpty(message = "Order details cannot be empty")
    private List<OrderDetailRequestDTO> orderDetails;

    // Constructors
    public OrderRequestDTO() {}

    public OrderRequestDTO(List<OrderDetailRequestDTO> orderDetails, int customerId) {
        this.orderDetails = orderDetails;
        this.customerId = customerId;
    }

    // Getters, setters
    public @Positive(message = "Customer ID must be positive") int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(@Positive(message = "Customer ID must be positive") int customerId) {
        this.customerId = customerId;
    }

    public @NotEmpty(message = "Order details cannot be empty") List<OrderDetailRequestDTO> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(@NotEmpty(message = "Order details cannot be empty") List<OrderDetailRequestDTO> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
