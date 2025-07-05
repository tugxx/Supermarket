package com.minimarket.web_minimarket.controller;

import com.minimarket.web_minimarket.entity.Order;
import com.minimarket.web_minimarket.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @GetMapping
    public List<Order> getOrdersByCustomerId(@RequestParam(required = false) Integer customerId) {
        if (customerId != null) {
            return orderService.getOrdersByCustomerId(customerId);
        }
        return orderService.getAllOrders();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable("orderId") int orderId) {
        Order order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable("orderId") int orderId, @RequestBody Order orderDetail) {
        Order updatedOrder = orderService.updateOrder(orderId, orderDetail);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("orderId") int orderId) {
        orderService.deleteOrderById(orderId);
        return ResponseEntity.noContent().build();
    }
}
