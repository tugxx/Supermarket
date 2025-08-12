package com.minimarket.web_minimarket.controller;

import com.minimarket.web_minimarket.dto.OrderDetailRequestDTO;
import com.minimarket.web_minimarket.dto.OrderDetailResponseDTO;
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
    public ResponseEntity<OrderDetailResponseDTO> createOrderDetail(@RequestBody OrderDetailRequestDTO orderDetailRequest) {
        OrderDetailResponseDTO createdOrderDetail = orderDetailService.createOrderDetail(orderDetailRequest);
        return ResponseEntity.ok(createdOrderDetail);
    }

    @GetMapping
    public ResponseEntity<List<OrderDetailResponseDTO>> getAllOrderDetails() {
        List<OrderDetailResponseDTO> orderDetails = orderDetailService.getAllOrderDetails();
        return ResponseEntity.ok(orderDetails);
    }

    @GetMapping("/{orderId}/{productId}")
    public ResponseEntity<OrderDetailResponseDTO> getOrderDetailById(@PathVariable("orderId") int orderId, @PathVariable("productId") int productId) {
        OrderDetailsID orderDetailsID = new OrderDetailsID(orderId, productId);
        OrderDetailResponseDTO orderDetail = orderDetailService.getOrderDetailById(orderDetailsID);
        return ResponseEntity.ok(orderDetail);
    }

    @GetMapping("/by-order-product")
    public ResponseEntity<OrderDetailResponseDTO> getByOrderAndProduct(@RequestParam int orderId, @RequestParam int productId) {
        OrderDetailResponseDTO orderDetail = orderDetailService.getOrderDetailByOrderIdAndProductId(orderId, productId);
        return ResponseEntity.ok(orderDetail);
    }

    @GetMapping("/by-order/{orderId}")
    public List<OrderDetailResponseDTO> getByOrder(@PathVariable("orderId") int orderId) {
        return orderDetailService.getOrderDetailByOrderId(orderId);
    }

    @GetMapping("/by-product/{productId}")
    public List<OrderDetailResponseDTO> getByProduct(@PathVariable("productId") int productId) {
        return orderDetailService.getOrderDetailByProductId(productId);
    }

    @PutMapping("/{orderId}/{productId}")
    public ResponseEntity<OrderDetailResponseDTO> updateOrderDetailByPath(@PathVariable("orderId") int orderId, @PathVariable("productId") int productId, @RequestBody OrderDetail orderDetailDetail) {
        OrderDetailResponseDTO updatedOrderDetail = orderDetailService.updateOrderDetail(orderId, productId, orderDetailDetail);
        return ResponseEntity.ok(updatedOrderDetail);
    }

    @PutMapping("/by-order-product")
    public ResponseEntity<OrderDetailResponseDTO> updateOrderDetailByQuery(@RequestParam int orderId, @RequestParam int productId, @RequestBody OrderDetail orderDetailDetail) {
        OrderDetailResponseDTO updatedOrderDetail = orderDetailService.updateOrderDetail(orderId, productId, orderDetailDetail);
        return ResponseEntity.ok(updatedOrderDetail);
    }

    @DeleteMapping("{orderId}/{productId}")
    public ResponseEntity<Void> deleteOrderDetail(@PathVariable("orderId") int orderId, @PathVariable("productId") int productId) {
        OrderDetailsID orderDetailsID = new OrderDetailsID(orderId,productId);
        orderDetailService.deleteOrderDetailById(orderDetailsID);
        return ResponseEntity.noContent().build();
    }
}
