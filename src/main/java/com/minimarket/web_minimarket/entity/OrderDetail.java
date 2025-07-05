package com.minimarket.web_minimarket.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "order_details")
public class OrderDetail {
    @EmbeddedId
    private OrderDetailsID orderDetailsID;

    private int orderQuantity;
    private double orderPrice;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    // Constructors
    public OrderDetail() {}

    public OrderDetail(OrderDetailsID orderDetailsID, int orderQuantity, double orderPrice, Order order, Product product) {
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

    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
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
}
