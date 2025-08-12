package com.minimarket.web_minimarket.service;

import com.minimarket.web_minimarket.dto.OrderRequestDTO;
import com.minimarket.web_minimarket.dto.OrderResponseDTO;
import com.minimarket.web_minimarket.entity.*;
import com.minimarket.web_minimarket.exception.CustomerNotFoundException;
import com.minimarket.web_minimarket.exception.InsufficientStockException;
import com.minimarket.web_minimarket.exception.OrderNotFoundException;
import com.minimarket.web_minimarket.exception.ProductNotFoundException;
import com.minimarket.web_minimarket.mapper.OrderDetailMapper;
import com.minimarket.web_minimarket.mapper.OrderMapper;
import com.minimarket.web_minimarket.repository.CustomerRepository;
import com.minimarket.web_minimarket.repository.OrderRepository;
import com.minimarket.web_minimarket.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final OrderMapper orderMapper;
    private final OrderDetailMapper orderDetailMapper;

    public OrderService(OrderMapper orderMapper, OrderRepository orderRepository, ProductRepository productRepository, CustomerRepository customerRepository, OrderDetailMapper orderDetailMapper) {
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.orderDetailMapper = orderDetailMapper;
    }

    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequest) {
        Order order = orderMapper.orderRequestToOrder(orderRequest);

        List<OrderDetail> orderDetails = order.getOrderDetails();
        if (orderDetails == null || orderDetails.isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least 1 item");
        }
        for (OrderDetail orderDetail : orderDetails) {
            Product product = productRepository.findById(orderDetail.getProduct().getProductId()).orElseThrow(() -> new ProductNotFoundException("Product not found: " + orderDetail.getProduct().getProductId()));

            if (product.getProductQuantity() < orderDetail.getOrderQuantity()) {
                throw new InsufficientStockException("Insufficient stock for product: " + product.getProductName());
            }

            product.setProductQuantity(product.getProductQuantity() - orderDetail.getOrderQuantity());
            orderDetail.setOrderPrice(product.getProductPrice().multiply(BigDecimal.valueOf(orderDetail.getOrderQuantity())));
            productRepository.save(product);
        }

        BigDecimal totalAmount = orderDetails.stream().map(OrderDetail::getOrderPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setOrderTotal(totalAmount);

        order.setOrderTime(LocalDateTime.now());

        Customer customer = customerRepository.findById(orderRequest.getCustomerId()).orElseThrow(() -> new CustomerNotFoundException("Customer not found: " + orderRequest.getCustomerId()));
        order.setCustomer(customer);

        order.setStatus(OrderStatus.PENDING);

        Order savedOrder = orderRepository.save(order);
        return orderMapper.orderToOrderResponse(savedOrder);
    }

    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll().stream().map(orderMapper::orderToOrderResponse).collect(Collectors.toList());
    }

    public List<OrderResponseDTO> getOrdersByCustomerId(Integer customerId) {
        List<Order> orders = orderRepository.getByCustomer_CustomerId(customerId);
        return orders.stream().map(orderMapper::orderToOrderResponse).collect(Collectors.toList());
    }

    public OrderResponseDTO getOrderById(int orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(()->new EntityNotFoundException("Order with id: "+orderId+" not found"));
        return orderMapper.orderToOrderResponse(order);
    }

    @Transactional
    public OrderResponseDTO updateOrder(int orderId, OrderRequestDTO orderRequest) {
        if (orderId <= 0) {
            throw new IllegalArgumentException("Order ID must be positive");
        }

        // Fetching existing order
        Order existingOrder = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order with ID: " + orderId + " not found"));

        // Check if update is allowed
        if (existingOrder.getStatus() != OrderStatus.PENDING) {
            throw new IllegalStateException("Cannot update order with status " + existingOrder.getStatus());
        }

        // Restore stock for existing OrderDetails
        for (OrderDetail orderDetail : existingOrder.getOrderDetails()) {
            Product product = productRepository.findById(orderDetail.getProduct().getProductId()).orElseThrow(() -> new ProductNotFoundException("Product not found: " + orderDetail.getProduct().getProductId()));
            product.setProductQuantity(product.getProductQuantity() + orderDetail.getOrderQuantity());
            productRepository.save(product);
        }

        // Map DTO to entity
        Order updatedOrder = orderMapper.orderRequestToOrder(orderRequest);
        updatedOrder.setOrderId(orderId);

        // Validate and update stock for new OrderDetails
        List<OrderDetail> newOrderDetails = updatedOrder.getOrderDetails();
        if (newOrderDetails == null || newOrderDetails.isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least 1 item");
        }

        for (OrderDetail orderdetail : newOrderDetails) {
            Product product = productRepository.findById(orderdetail.getProduct().getProductId()).orElseThrow(() -> new ProductNotFoundException("Product not found: " + orderdetail.getProduct().getProductId()));
            if (product.getProductQuantity() < orderdetail.getOrderQuantity()) {
                throw new InsufficientStockException("Insufficient stock for product: " + product.getProductName());
            }
            product.setProductQuantity(product.getProductQuantity() - orderdetail.getOrderQuantity());
            orderdetail.setOrderPrice(product.getProductPrice().multiply(BigDecimal.valueOf(orderdetail.getOrderQuantity())));
            productRepository.save(product);
        }

        // Calculate total amount
        BigDecimal totalAmount = newOrderDetails.stream().map(OrderDetail::getOrderPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        updatedOrder.setOrderTotal(totalAmount);

        Customer customer = customerRepository.findById(orderRequest.getCustomerId()).orElseThrow(() -> new CustomerNotFoundException("Customer not found: " + orderRequest.getCustomerId()));
        updatedOrder.setCustomer(customer);

        updatedOrder.setOrderTime(LocalDateTime.now());

        updatedOrder.setStatus(OrderStatus.PENDING);

        Order savedOrder = orderRepository.save(updatedOrder);
        return orderMapper.orderToOrderResponse(savedOrder);
    }

    @Transactional
    public void deleteOrderById(int orderId) {
        if (orderId <= 0) {
            throw new IllegalArgumentException("Order ID must be positive");
        }

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order with ID: " + orderId + " not found"));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new IllegalStateException("Cannot delete order with status: " + order.getStatus());
        }

        // Restore product stock for each OrderDetail
        List<OrderDetail> orderDetails = order.getOrderDetails();
        for (OrderDetail detail : orderDetails) {
            Product product = productRepository.findById(detail.getProduct().getProductId()).orElseThrow(() -> new ProductNotFoundException("Product not found: " + detail.getProduct().getProductId()));
            product.setProductQuantity(product.getProductQuantity() + detail.getOrderQuantity());
            productRepository.save(product);
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }
}
