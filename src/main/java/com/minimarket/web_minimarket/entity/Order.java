package com.minimarket.web_minimarket.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int orderId;

    @Column(name = "order_time")
    @NotNull(message = "Order time cannot be null")
    private LocalDateTime orderTime;

    @Column(name = "order_total")
    @PositiveOrZero(message = "Order total must be non-negative")
    private BigDecimal orderTotal;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @NotNull(message = "Customer cannot be null")
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @NotNull(message = "Order status cannot be null")
    private OrderStatus status;

    // Constructors
    public Order() {}

    public Order(int orderId, LocalDateTime orderTime, BigDecimal orderTotal, Customer customer, OrderStatus status) {
        this.orderId = orderId;
        this.orderTime = orderTime;
        this.orderTotal = orderTotal;
        this.customer = customer;
        this.status = status;
    }

    public Order(Customer customer, BigDecimal orderTotal, LocalDateTime orderTime, OrderStatus status) {
        this.customer = customer;
        this.orderTotal = orderTotal;
        this.orderTime = orderTime;
        this.status = status;
    }

    // Getters and setters
    public @NotNull(message = "Customer cannot be null") Customer getCustomer() {
        return customer;
    }

    public void setCustomer(@NotNull(message = "Customer cannot be null") Customer customer) {
        this.customer = customer;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public @NotNull(message = "Order time cannot be null") LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(@NotNull(message = "Order time cannot be null") LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public @PositiveOrZero(message = "Order total must be non-negative") BigDecimal getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(@PositiveOrZero(message = "Order total must be non-negative") BigDecimal orderTotal) {
        this.orderTotal = orderTotal;
    }

    public @NotNull(message = "Order status cannot be null") OrderStatus getStatus() {
        return status;
    }

    public void setStatus(@NotNull(message = "Order status cannot be null") OrderStatus status) {
        this.status = status;
    }
}
