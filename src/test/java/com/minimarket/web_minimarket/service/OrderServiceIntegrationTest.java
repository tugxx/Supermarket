package com.minimarket.web_minimarket.service;

import com.minimarket.web_minimarket.dto.OrderRequestDTO;
import com.minimarket.web_minimarket.dto.OrderResponseDTO;
import com.minimarket.web_minimarket.entity.*;
import com.minimarket.web_minimarket.mapper.OrderDetailMapper;
import com.minimarket.web_minimarket.mapper.OrderMapper;
import com.minimarket.web_minimarket.repository.CustomerRepository;
import com.minimarket.web_minimarket.repository.OrderRepository;
import com.minimarket.web_minimarket.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class OrderServiceIntegrationTest {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private OrderService orderService;

    private OrderMapper orderMapper;

    @BeforeEach
    void setUp() {
        OrderMapper orderMapper = mock(OrderMapper.class);
        OrderDetailMapper orderDetailMapper = mock(OrderDetailMapper.class); // Use real mapper if available
        orderService = new OrderService(orderMapper, orderRepository, productRepository, customerRepository, orderDetailMapper);
    }

    @Test
    void createOrder_Success_Integration() {
        // Arrange
        Customer customer = new Customer();
        customer.setCustomerId(1);
        customerRepository.save(customer);

        Product product = new Product();
        product.setProductId(1);
        product.setProductName("Laptop");
        product.setProductPrice(BigDecimal.valueOf(1000));
        product.setProductQuantity(10);
        productRepository.save(product);

        OrderRequestDTO orderRequest = new OrderRequestDTO();
        orderRequest.setCustomerId(1);

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProduct(product);
        orderDetail.setOrderQuantity(2);

        List<OrderDetail> orderDetails = new ArrayList<>();
        orderDetails.add(orderDetail);

        Order order = new Order();
        order.setOrderDetails(orderDetails);
        order.setCustomer(customer);

        when(orderMapper.orderRequestToOrder(orderRequest)).thenReturn(order);
        when(orderMapper.orderToOrderResponse(any())).thenReturn(new OrderResponseDTO());

        // Act
        OrderResponseDTO response = orderService.createOrder(orderRequest);

        // Assert
        assertNotNull(response);
        Order savedOrder = orderRepository.findAll().getFirst();
        assertEquals(BigDecimal.valueOf(2000), savedOrder.getOrderTotal());
        assertEquals(OrderStatus.PENDING, savedOrder.getStatus());
        Product updatedProduct = productRepository.findById(1).get();
        assertEquals(8, updatedProduct.getProductQuantity()); // 10 - 2
    }
}
