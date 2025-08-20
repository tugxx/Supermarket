package com.minimarket.web_minimarket.controller;

import com.minimarket.web_minimarket.dto.OrderRequestDTO;
import com.minimarket.web_minimarket.dto.OrderResponseDTO;
import com.minimarket.web_minimarket.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody OrderRequestDTO orderRequest) {
        OrderResponseDTO createdOrder = orderService.createOrder(orderRequest);
        // Return 201
        return ResponseEntity.created(URI.create("/api/orders/" + createdOrder.getOrderId())).body(createdOrder);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getOrders(@RequestParam(required = false) Integer customerId,
                                                            @RequestParam(required = false, defaultValue = "orderId") String sortBy,
                                                            @RequestParam(required = false, defaultValue = "asc") String direction) {
        List<OrderResponseDTO> orderResponses = orderService.sortOrders(customerId, sortBy, direction);
        return ResponseEntity.ok(orderResponses);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable("orderId") int orderId) {
        OrderResponseDTO orderResponse = orderService.getOrderById(orderId);
        return ResponseEntity.ok(orderResponse);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderResponseDTO> updateOrder(@PathVariable("orderId") int orderId, @Valid @RequestBody OrderRequestDTO orderRequest) {
        OrderResponseDTO updatedOrder = orderService.updateOrder(orderId, orderRequest);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("orderId") int orderId) {
        orderService.deleteOrderById(orderId);
        return ResponseEntity.noContent().build();
    }
}
