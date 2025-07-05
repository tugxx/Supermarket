package com.minimarket.web_minimarket.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    private LocalDateTime orderTime;
    private BigDecimal orderTotal;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    // Constructors
    public Order() {}

    public Order(int orderId, LocalDateTime orderTime, BigDecimal orderTotal, Customer customer) {
        this.orderId = orderId;
        this.orderTime = orderTime;
        this.orderTotal = orderTotal;
        this.customer = customer;
    }

    public Order(Customer customer, BigDecimal orderTotal, LocalDateTime orderTime) {
        this.customer = customer;
        this.orderTotal = orderTotal;
        this.orderTime = orderTime;
    }

    // Getters and setters
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
