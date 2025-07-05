package com.minimarket.web_minimarket.service;

import com.minimarket.web_minimarket.entity.Order;
import com.minimarket.web_minimarket.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByCustomerId(int customerId) {
        return orderRepository.getByCustomer_CustomerId(customerId);
    }

    public Order getOrderById(int orderId) {
        return orderRepository.findById(orderId).orElseThrow(()->new EntityNotFoundException("Order with id: "+orderId+" not found"));
    }

    public Order updateOrder(int orderId, Order orderDetail) {
        Order order = getOrderById(orderId);
        order.setOrderTotal(orderDetail.getOrderTotal());
        return orderRepository.save(order);
    }

    public void deleteOrderById(int orderId) {
        orderRepository.deleteById(orderId);
    }
}
