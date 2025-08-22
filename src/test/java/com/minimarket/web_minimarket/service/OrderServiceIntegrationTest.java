package com.minimarket.web_minimarket.service;

import com.minimarket.web_minimarket.dto.OrderDetailRequestDTO;
import com.minimarket.web_minimarket.dto.OrderRequestDTO;
import com.minimarket.web_minimarket.dto.OrderResponseDTO;
import com.minimarket.web_minimarket.entity.*;
import com.minimarket.web_minimarket.exception.CustomerNotFoundException;
import com.minimarket.web_minimarket.exception.InsufficientStockException;
import com.minimarket.web_minimarket.exception.OrderNotFoundException;
import com.minimarket.web_minimarket.exception.ProductNotFoundException;
import com.minimarket.web_minimarket.repository.CustomerRepository;
import com.minimarket.web_minimarket.repository.OrderDetailRepository;
import com.minimarket.web_minimarket.repository.OrderRepository;
import com.minimarket.web_minimarket.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class OrderServiceIntegrationTest {

    @Autowired private OrderService orderService;
    @Autowired private OrderRepository orderRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private OrderDetailRepository orderDetailRepository;
    @Autowired private CustomerRepository customerRepository;

    private OrderRequestDTO orderRequest;

    @BeforeEach
    void setUp() {
        orderRequest = new OrderRequestDTO();
        orderRequest.setCustomerId(1);
        OrderDetailRequestDTO orderDetailRequest = new OrderDetailRequestDTO();
        orderDetailRequest.setProductId(50);
        orderDetailRequest.setOrderQuantity(2);
        orderRequest.setOrderDetails(List.of(orderDetailRequest));
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Test
    void createOrder_insufficientStock_integration() {
        // Arrange
        orderRequest.getOrderDetails().getFirst().setOrderQuantity(56);

        // Act & Assert
        assertThrows(InsufficientStockException.class, () -> orderService.createOrder(orderRequest));

        // Verify product quantity was not changed
        Product productAfterAttempt = productRepository.findById(50).orElseThrow();
        assertEquals(55, productAfterAttempt.getProductQuantity());
    }

    @Test
    void createOrder_productNotFound_integration() {
        // Arrange
        orderRequest.getOrderDetails().getFirst().setProductId(51);

        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> orderService.createOrder(orderRequest));
    }

    @Test
    void createOrder_customerNotFound_integration() {
        // Arrange
        orderRequest.setCustomerId(4);

        // Act & Assert
        assertThrows(CustomerNotFoundException.class, () -> orderService.createOrder(orderRequest));
    }

    @Test
    void createOrder_emptyOrder_integration() {
        // Arrange
        orderRequest.setOrderDetails(Collections.emptyList());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> orderService.createOrder(orderRequest));
    }

    @Test
    void createOrder_success_integration() {
        System.out.println(orderRequest);
        // Act
        OrderResponseDTO response = orderService.createOrder(orderRequest);

        // Assert
        assertNotNull(response);

        Order savedOrder = orderRepository.findAll().getLast();
        assertEquals(new BigDecimal("180000.00"), savedOrder.getOrderTotal());
        assertEquals(OrderStatus.PENDING, savedOrder.getStatus());

        Product updatedProduct = productRepository.findById(50).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        assertEquals(53, updatedProduct.getProductQuantity());
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Test
    void getAllOrders_whenEmpty_integration() {
        orderRepository.deleteAll();
        assertThatThrownBy(() -> orderService.getAllOrders()).isInstanceOf(EntityNotFoundException.class).hasMessage("No orders found");
    }

    @Test
    void getAllOrders_success_integration() {
        // Act
        List<OrderResponseDTO> orders = orderService.getAllOrders();

        // Assert
        assertNotNull(orders);
        assertEquals(6, orders.size());

        // Verify the details of the first order
        OrderResponseDTO firstOrder = orders.getFirst();
        assertEquals(1, firstOrder.getOrderId());
        assertEquals(BigDecimal.valueOf(120000), firstOrder.getOrderTotal());

        // Verify the details of the second order
        OrderResponseDTO secondOrder = orders.get(1);
        assertEquals(2, secondOrder.getOrderId());
        assertEquals(BigDecimal.valueOf(1800000), secondOrder.getOrderTotal());

        assertThat(orders.getFirst().getOrderTotal()).isEqualByComparingTo("120000");
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Test
    void getOrdersByCustomerId_customerNotFound_integration() {
        // Arrange
        orderRequest.setCustomerId(4);

        // Act & Assert
        assertThrows(CustomerNotFoundException.class, () -> orderService.createOrder(orderRequest));
    }

    @Test
    void getOrdersByCustomerId_whenEmpty_integration() {
        assertThatThrownBy(() -> orderService.getOrdersByCustomerId(3))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("No orders found for customer");
    }

    @Test
    void getOrdersByCustomerId_success_integration() {
        // Act
        List<OrderResponseDTO> orderResponses = orderService.getOrdersByCustomerId(1);

        // Assert
        assertNotNull(orderResponses);
        assertEquals(3, orderResponses.size());

        // Verify that the retrieved orders belong to the correct customer
        orderResponses.forEach(order4 -> assertEquals(1, order4.getCustomerId()));
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Test
    void getOrderById_orderNotFound_integration() {
        // Act & Assert
        assertThrows(OrderNotFoundException.class, () -> orderService.getOrderById(7));
    }

    @Test
    void getOrderById_success_integration() {
        // Act
        OrderResponseDTO response = orderService.getOrderById(1);

        // Assert
        assertNotNull(response);
        assertEquals(response.getOrderTotal(), BigDecimal.valueOf(120000));
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Test
    void updateOrder_invalidOrderId_integration() {
        OrderRequestDTO request = new OrderRequestDTO();
        request.setCustomerId(1);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> orderService.updateOrder(0, request));

        assertEquals("Order ID must be positive", exception.getMessage());
    }

    @Test
    void updateOrder_NonExistingOrder_integration() {
        OrderRequestDTO request = new OrderRequestDTO();
        request.setCustomerId(1);

        Exception exception = assertThrows(OrderNotFoundException.class, () -> orderService.updateOrder(7, request));

        assertEquals("Order with ID: 7 not found", exception.getMessage());
    }

    @Test
    void updateOrder_NonPendingOrder_integration() {
        Order order1 = orderRepository.findById(6).orElseThrow(() -> new OrderNotFoundException("Order not found: " + 6));
        order1.setStatus(OrderStatus.CONFIRMED);
        order1.setOrderId(1);
        orderRepository.save(order1);

        Exception exception = assertThrows(IllegalStateException.class, () -> orderService.updateOrder(6, orderRequest));

        assertEquals("Cannot update order with status CONFIRMED", exception.getMessage());
    }

    @Test
    void updateOrder_InsufficientStock_integration() {
        List<OrderDetailRequestDTO> orderDetailRequests = new ArrayList<>();
        OrderDetailRequestDTO orderDetailRequest = new OrderDetailRequestDTO();
        orderDetailRequest.setOrderId(1);
        orderDetailRequest.setProductId(2);
        orderDetailRequest.setOrderQuantity(62);
        orderDetailRequests.add(orderDetailRequest);
        orderRequest.setOrderDetails(orderDetailRequests);

        Exception exception = assertThrows(InsufficientStockException.class, () -> orderService.updateOrder(1, orderRequest));

        assertEquals("Insufficient stock for product: Son dưỡng môi tự nhiên", exception.getMessage());
    }

    @Test
    void updateOrder_NonExistingCustomer_integration() {
        orderRequest.setCustomerId(4);
        List<OrderDetailRequestDTO> orderDetailRequests = new ArrayList<>();
        OrderDetailRequestDTO OrderDetailRequest = new OrderDetailRequestDTO();
        OrderDetailRequest.setProductId(1);
        OrderDetailRequest.setOrderQuantity(2);
        orderDetailRequests.add(OrderDetailRequest);
        orderRequest.setOrderDetails(orderDetailRequests);

        Exception exception = assertThrows(CustomerNotFoundException.class, () -> orderService.updateOrder(1, orderRequest));

        assertEquals("Customer not found: 4", exception.getMessage());
    }

    @Test
    void updateOrder_SuccessfulUpdate_integration() {
        List<OrderDetailRequestDTO> orderDetailRequests = new ArrayList<>();
        OrderDetailRequestDTO orderDetailRequest = new OrderDetailRequestDTO();
        orderDetailRequest.setOrderId(1);
        orderDetailRequest.setProductId(1);
        orderDetailRequest.setOrderQuantity(3);
        orderDetailRequest.setOrderPrice(BigDecimal.valueOf(135000));
        orderDetailRequests.add(orderDetailRequest);
        orderRequest.setOrderDetails(orderDetailRequests);

        OrderResponseDTO response = orderService.updateOrder(1, orderRequest);

        assertNotNull(response);
        assertEquals(1, response.getOrderId());
        assertEquals(new BigDecimal("135000.00"), response.getOrderTotal());
        assertEquals(OrderStatus.PENDING, response.getStatus());

        Product updatedProduct = productRepository.findById(1).orElseThrow();
        assertEquals(79, updatedProduct.getProductQuantity());
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Test
    void deleteOrderById_InvalidOrderId_integration() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> orderService.deleteOrderById(0));

        assertEquals("Order ID must be positive", exception.getMessage());
    }

    @Test
    void deleteOrderById_NonExistingOrder_integration() {
        Exception exception = assertThrows(OrderNotFoundException.class, () -> orderService.deleteOrderById(7));

        assertEquals("Order with ID: 7 not found", exception.getMessage());
    }

    @Test
    void deleteOrderById_NonPendingOrder_integration() {
        Order order = new Order();
        order.setStatus(OrderStatus.CONFIRMED);
        Order order1 = orderRepository.save(order);

        Exception exception = assertThrows(IllegalStateException.class, () -> orderService.deleteOrderById(order1.getOrderId()));

        assertEquals("Cannot delete order with status: CONFIRMED", exception.getMessage());
    }

    @Test
    void deleteOrderById_SuccessfulDeletion_integration() {
        orderService.deleteOrderById(1);

        Optional<Order> deletedOrder = orderRepository.findById(1);
        assertFalse(deletedOrder.isPresent(), "Order should be deleted");

        List<OrderDetail> remainingOrderDetails = orderDetailRepository.findAll();
        assertEquals(5, remainingOrderDetails.size());

        List<Product> updatedProducts = productRepository.findAll();

        assertEquals(82, updatedProducts.getFirst().getProductQuantity());
        assertEquals(61, updatedProducts.get(1).getProductQuantity());
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Test
    void sortOrdersByTotalAsc_integration() {
        List<OrderResponseDTO> result = orderService.sortOrders(1, "orderTotal", "asc");

        assertThat(result).extracting(OrderResponseDTO::getOrderTotal).containsExactly(BigDecimal.valueOf(90000), BigDecimal.valueOf(120000), BigDecimal.valueOf(1800000));
    }

    @Test
    void sortOrdersByTimeDesc_integration() {
        List<OrderResponseDTO> result = orderService.sortOrders(2, "orderTime", "desc");

        assertThat(result).extracting(OrderResponseDTO::getOrderTime).isSortedAccordingTo(Comparator.reverseOrder());
    }

    @Test
    void throwWhenInvalidSortField_integration() {
        assertThatThrownBy(() -> orderService.sortOrders(1, "invalidField", "asc"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid sort field");
    }

    @Test
    void returnAllOrdersWhenCustomerIdNull_integration() {
        List<OrderResponseDTO> result = orderService.sortOrders(null, "orderId", "asc");
        assertThat(result).hasSize(6);
    }

    @Test
    void throwWhenCustomerHasNoOrders_integration() {
        Customer emptyCustomer = new Customer();
        emptyCustomer.setCustomerName("No Orders");
        emptyCustomer = customerRepository.save(emptyCustomer);

        Integer customerId = emptyCustomer.getCustomerId();
        assertThatThrownBy(() -> orderService.sortOrders(customerId, "orderId", "asc"))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("No orders found for customer with id");
    }

    @Test
    void throwWhenNoOrdersExistAtAll_integration() {
        orderRepository.deleteAll();
        assertThatThrownBy(() -> orderService.sortOrders(null, "orderId", "asc"))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("No orders found");
    }
}
