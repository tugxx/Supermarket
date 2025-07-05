package com.minimarket.web_minimarket.controller;

import com.minimarket.web_minimarket.entity.OrderDetail;
import com.minimarket.web_minimarket.entity.OrderDetailsID;
import com.minimarket.web_minimarket.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orderdetails")
public class OrderDetailController {
    @Autowired
    private OrderDetailService orderDetailService;

    @PostMapping
    public OrderDetail createOrderDetail(@RequestBody OrderDetail orderDetail) {
        return orderDetailService.createOrderDetail(orderDetail);
    }

    @GetMapping
    public List<OrderDetail> getAllOrderDetails() {
        return orderDetailService.getAllOrderDetails();
    }

    @GetMapping("/{orderId}/{productId}")
    public ResponseEntity<OrderDetail> getOrderDetailById(@PathVariable("orderId") int orderId, @PathVariable("productId") int productId) {
        OrderDetailsID orderDetailsID = new OrderDetailsID(orderId, productId);
        OrderDetail orderDetail = orderDetailService.getOrderDetailById(orderDetailsID);
        return ResponseEntity.ok(orderDetail);
    }

    @GetMapping("/by-order-product")
    public ResponseEntity<OrderDetail> getByOrderAndProduct(@RequestParam int orderId, @RequestParam int productId) {
        OrderDetail orderDetail = orderDetailService.getOrderDetailByOrderIdAndProductId(orderId, productId);
        return ResponseEntity.ok(orderDetail);
    }

    @GetMapping("/by-order/{orderId}")
    public List<OrderDetail> getByOrder(@PathVariable("orderId") int orderId) {
        return orderDetailService.getOrderDetailByOrderId(orderId);
    }

    @GetMapping("/by-product/{productId}")
    public List<OrderDetail> getByProduct(@PathVariable("productId") int productId) {
        return orderDetailService.getOrderDetailByProductId(productId);
    }

    @PutMapping("/{orderId}/{productId}")
    public ResponseEntity<OrderDetail> updateOrderDetailByPath(@PathVariable("orderId") int orderId, @PathVariable("productId") int productId, @RequestBody OrderDetail orderDetailDetail) {
        OrderDetail updatedOrderDetail = orderDetailService.updateOrderDetail(orderId, productId, orderDetailDetail);
        return ResponseEntity.ok(updatedOrderDetail);
    }

    @PutMapping("/by-order-product")
    public ResponseEntity<OrderDetail> updateOrderDetailByQuery(@RequestParam int orderId, @RequestParam int productId, @RequestBody OrderDetail orderDetailDetail) {
        OrderDetail updatedOrderDetail = orderDetailService.updateOrderDetail(orderId, productId, orderDetailDetail);
        return ResponseEntity.ok(updatedOrderDetail);
    }

    @DeleteMapping("{orderId}/{productId}")
    public ResponseEntity<Void> deleteOrderDetail(@PathVariable("orderId") int orderId, @PathVariable("productId") int productId) {
        OrderDetailsID orderDetailsID = new OrderDetailsID(orderId,productId);
        orderDetailService.deleteOrderDetailById(orderDetailsID);
        return ResponseEntity.noContent().build();
    }
}
