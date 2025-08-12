package com.minimarket.web_minimarket.dto;

public class CustomerResponseDTO {
    private int customerId;
    private String customerName;
    private int customerAge;
    private UserResponseDTO user;

    // Constructors
    public CustomerResponseDTO() {}

    public CustomerResponseDTO(int customerAge, int customerId, String customerName, UserResponseDTO user) {
        this.customerAge = customerAge;
        this.customerId = customerId;
        this.customerName = customerName;
        this.user = user;
    }

    // Getters, setters
    public int getCustomerAge() {
        return customerAge;
    }

    public void setCustomerAge(int customerAge) {
        this.customerAge = customerAge;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public UserResponseDTO getUser() {
        return user;
    }

    public void setUser(UserResponseDTO user) {
        this.user = user;
    }
}
