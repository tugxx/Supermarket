package com.minimarket.web_minimarket.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerId;

    private String customerName;
    private int customerAge;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Constructors
    public Customer() {}

    public Customer(int customerId, String customerName, int customerAge, User user) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAge = customerAge;
        this.user = user;
    }

    public Customer(String customerName, int customerAge, User user) {
        this.customerName = customerName;
        this.customerAge = customerAge;
        this.user = user;
    }

    // Getters and setters
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

    public int getCustomerAge() {
        return customerAge;
    }

    public void setCustomerAge(int customerAge) {
        this.customerAge = customerAge;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
