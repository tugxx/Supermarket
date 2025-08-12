package com.minimarket.web_minimarket.service;

import com.minimarket.web_minimarket.dto.OrderRequestDTO;
import com.minimarket.web_minimarket.dto.OrderResponseDTO;
import com.minimarket.web_minimarket.entity.*;
import com.minimarket.web_minimarket.exception.CustomerNotFoundException;
import com.minimarket.web_minimarket.exception.InsufficientStockException;
import com.minimarket.web_minimarket.exception.ProductNotFoundException;
import com.minimarket.web_minimarket.mapper.OrderMapper;
import com.minimarket.web_minimarket.repository.CustomerRepository;
import com.minimarket.web_minimarket.repository.OrderRepository;
import com.minimarket.web_minimarket.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderService orderService;

    private OrderRequestDTO orderRequest;
    private Order order;
    private Product product;
    private Customer customer;
    private OrderDetail orderDetail;

    @BeforeEach
    void setUp() {
        // Initialize test data
        orderRequest = new OrderRequestDTO();
        orderRequest.setCustomerId(1);

        customer = new Customer();
        customer.setCustomerId(1);

        product = new Product();
        product.setProductId(1);
        product.setProductName("Laptop");
        product.setProductPrice(BigDecimal.valueOf(1000));
        product.setProductQuantity(10);

        orderDetail = new OrderDetail();
        orderDetail.setProduct(product);
        orderDetail.setOrderQuantity(2);

        List<OrderDetail> orderDetails = new ArrayList<>();
        orderDetails.add(orderDetail);

        order = new Order();
        order.setOrderDetails(orderDetails);
        order.setCustomer(customer);
        order.setOrderTime(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        order.setOrderTotal(BigDecimal.valueOf(2000));
    }

    @Test
    void createOrder_Success() {
        // Arrange
        when(orderMapper.orderRequestToOrder(orderRequest)).thenReturn(order);
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);
        when(orderRepository.save(order)).thenReturn(order);
        when(orderMapper.orderToOrderResponse(order)).thenReturn(new OrderResponseDTO());

        // Act
        OrderResponseDTO orderResponse = orderService.createOrder(orderRequest);

        // Assert
        assertNotNull(orderResponse);
        assertEquals(BigDecimal.valueOf(2000), order.getOrderTotal());
        assertEquals(8, product.getProductQuantity()); // 10 - 2
        assertEquals(OrderStatus.PENDING, order.getStatus());
        verify(productRepository, times(1)).save(product);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void createOrder_EmptyOrderDetails_ThrowsIllegalArgumentException() {
        // Arrange
        order.setOrderDetails(new ArrayList<>());
        when(orderMapper.orderRequestToOrder(orderRequest)).thenReturn(order);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> orderService.createOrder(orderRequest));
        assertEquals("Order must contain at least 1 item", exception.getMessage());
        verify(orderRepository, never()).save(any());
    }

    @Test
    void createOrder_ProductNotFound_ThrowsProductNotFoundException() {
        // Arrange
        when(orderMapper.orderRequestToOrder(orderRequest)).thenReturn(order);
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class,
                () -> orderService.createOrder(orderRequest));
        assertEquals("Product not found: 1", exception.getMessage());
        verify(orderRepository, never()).save(any());
    }

    @Test
    void createOrder_InsufficientStock_ThrowsInsufficientStockException() {
        // Arrange
        product.setProductQuantity(1); // Less than order quantity (2)
        when(orderMapper.orderRequestToOrder(orderRequest)).thenReturn(order);
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        // Act & Assert
        InsufficientStockException exception = assertThrows(InsufficientStockException.class,
                () -> orderService.createOrder(orderRequest));
        assertEquals("Insufficient stock for product: Laptop", exception.getMessage());
        verify(productRepository, never()).save(any());
        verify(orderRepository, never()).save(any());
    }

    @Test
    void createOrder_CustomerNotFound_ThrowsCustomerNotFoundException() {
        // Arrange
        when(orderMapper.orderRequestToOrder(orderRequest)).thenReturn(order);
        when(customerRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class,
                () -> orderService.createOrder(orderRequest));
        assertEquals("Customer not found: 1", exception.getMessage());
        verify(orderRepository, never()).save(any());
    }

    @Test
    void deleteOrderById() {
    }
}
