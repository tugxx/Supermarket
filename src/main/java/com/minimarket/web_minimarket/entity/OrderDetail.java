package com.minimarket.web_minimarket.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Entity
@Table(name = "order_details")
public class OrderDetail {
    @EmbeddedId
    private OrderDetailsID orderDetailsID;

    @Column(name = "quantity")
    @NotNull(message = "Quantity cannot be null")
    @Positive(message = "Quantity must be positive")
    private int orderQuantity;

    @Column(name = "price")
    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be positive")
    private BigDecimal orderPrice;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    @NotNull(message = "Order cannot be null")
    private Order order;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    @NotNull(message = "Product cannot be null")
    private Product product;

    // Constructors
    public OrderDetail() {}

    public OrderDetail(OrderDetailsID orderDetailsID, int orderQuantity, BigDecimal orderPrice, Order order, Product product) {
        this.orderDetailsID = orderDetailsID;
        this.orderQuantity = orderQuantity;
        this.orderPrice = orderPrice;
        this.order = order;
        this.product = product;
    }

    // Getters and setters
    public OrderDetailsID getOrderDetailsID() {
        return orderDetailsID;
    }

    public void setOrderDetailsID(OrderDetailsID orderDetailsID) {
        this.orderDetailsID = orderDetailsID;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }


    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderDetailsID=" + orderDetailsID +
                ", orderQuantity=" + orderQuantity +
                ", orderPrice=" + orderPrice +
                ", orderId=" + (order != null ? order.getOrderId() : null) +
                ", \n\tproduct=" + product +
                "}";
    }
}
