package com.minimarket.web_minimarket.dto;

public class CustomerRequestDTO {
    private String customerName;
    private int customerAge;
    private int userId;

    // Constructors
    public CustomerRequestDTO() {}

    public CustomerRequestDTO(int customerAge, String customerName, int userId) {
        this.customerAge = customerAge;
        this.customerName = customerName;
        this.userId = userId;
    }

    // Getters, setters
    public int getCustomerAge() {
        return customerAge;
    }

    public void setCustomerAge(int customerAge) {
        this.customerAge = customerAge;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    @Override
    public String toString() {
        return "CustomerRequestDTO{" +
                "customerAge=" + customerAge +
                ", customerName='" + customerName + '\'' +
                ", userId=" + userId +
                '}';
    }
}
