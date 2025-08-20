package com.minimarket.web_minimarket.service;

import com.minimarket.web_minimarket.dto.OrderDetailRequestDTO;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
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

    @Mock
    private OrderDetailMapper orderDetailMapper;

    private OrderRequestDTO orderRequest;
    private Order order;
    private Product product;
    private Customer customer;

//    @BeforeEach
//    void setUp() {
//         Category category = new Category(1, "category");
//         System.out.println(category.toString());
//
//         User user = new User(1, "email", "hello123", "123", "user");
//         System.out.println(user.toString());
//
//         Customer customer1 = new Customer(1, "buyer", 24, user);
//         System.out.println(customer1.toString());
//
//        Employee employee = new Employee(1, "hehe", user);
//        System.out.println(employee.toString());
//
//        Supplier supplier = new Supplier("No", 1, "Nah");
//        System.out.println(supplier.toString());
//
//        Product product1 = new Product("TV", new BigDecimal("1000.00"), 1, category, 1, supplier);
//        System.out.println(product1);
//
//        OrderDetailsID orderDetailsID = new OrderDetailsID(1, 1);
//        System.out.println(orderDetailsID.toString());
//
//        Order order1 = new Order(1, null, new BigDecimal("1000.00"), customer1, OrderStatus.PENDING, new ArrayList<>());
//        LocalDateTime orderTime = LocalDateTime.parse("00:00:00 08/09/2018", DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy"));
//        order1.setOrderTime(orderTime);
//
//        OrderDetail orderDetail1 = new OrderDetail(orderDetailsID, 1, new BigDecimal("1000.00"), order1, product1);
//
//        order1.getOrderDetails().add(orderDetail1);
//        System.out.println(order1.toString());
//        System.out.println(orderDetail1.toString());
//    }

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

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProduct(product);
        orderDetail.setOrderQuantity(2);

        List<OrderDetail> orderDetails = new ArrayList<>();
        orderDetails.add(orderDetail);

        List<OrderDetailRequestDTO> orderDetailRequests = new ArrayList<>();
        OrderDetailRequestDTO orderDetailRequest = new OrderDetailRequestDTO(0, BigDecimal.valueOf(0), 2, 1);
        orderDetailRequests.add(orderDetailRequest);
        orderRequest.setOrderDetails(orderDetailRequests);

        order = new Order();
        order.setOrderId(1);
        order.setCustomer(customer);
        order.setOrderTime(LocalDateTime.now());
        order.setOrderDetails(orderDetails);
        order.setStatus(OrderStatus.PENDING);

        orderDetail.setOrder(order);

        /**/
        OrderDetail detail1 = new OrderDetail();
        detail1.setProduct(product);
        detail1.setOrderQuantity(1);

        Order order1 = new Order();
        order1.setOrderId(1);
        order1.setCustomer(customer);
        order1.setOrderTime(LocalDateTime.now().minusDays(2));
        order1.setStatus(OrderStatus.PENDING);
        order1.setOrderDetails(List.of(detail1));
        detail1.setOrder(order1);

        OrderDetail detail2 = new OrderDetail();
        detail2.setProduct(product);
        detail2.setOrderQuantity(3);

        Order order2 = new Order();
        order2.setOrderId(2);
        order2.setCustomer(customer);
        order2.setOrderTime(LocalDateTime.now().minusDays(1));
        order2.setStatus(OrderStatus.CONFIRMED);
        order2.setOrderDetails(List.of(detail2));
        detail2.setOrder(order2);

        OrderDetail detail3 = new OrderDetail();
        detail3.setProduct(product);
        detail3.setOrderQuantity(5);

        Order order3 = new Order();
        order3.setOrderId(3);
        order3.setCustomer(customer);
        order3.setOrderTime(LocalDateTime.now());
        order3.setStatus(OrderStatus.PENDING);
        order3.setOrderDetails(List.of(detail3));
        detail3.setOrder(order3);

        orderMapper = Mappers.getMapper(OrderMapper.class);
        orderDetailMapper = Mappers.getMapper(OrderDetailMapper.class);
        orderService = new OrderService(orderMapper, orderRepository, productRepository, customerRepository, orderDetailMapper);
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Test
    void createOrder_EmptyOrderDetails_ThrowsIllegalArgumentException() {
        // Arrange
        orderRequest.setOrderDetails(new ArrayList<>());
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> orderService.createOrder(orderRequest));
        assertEquals("Order must contain at least 1 item", exception.getMessage());
        verify(orderRepository, never()).save(any());
    }

    @Test
    void createOrder_ProductNotFound_ThrowsProductNotFoundException() {
        // Arrange
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
        when(customerRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class,
                () -> orderService.createOrder(orderRequest));
        assertEquals("Customer not found: 1", exception.getMessage());
        verify(orderRepository, never()).save(any());
    }

    @Test
    void createOrder_Success() {
        // Mock repository behavior
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order order1 = invocation.getArgument(0);
            Order persisted = new Order();
            persisted.setOrderId(2);
            persisted.setOrderDetails(order1.getOrderDetails());
            persisted.setCustomer(order1.getCustomer());
            persisted.setOrderTime(order1.getOrderTime());
            persisted.setStatus(order1.getStatus());
            persisted.setOrderTotal(order1.getOrderTotal());

            if (order1.getOrderDetails() != null) {
                for (OrderDetail orderDetail1 : order1.getOrderDetails()) {
                    orderDetail1.getOrderDetailsID().setOrderId(2);
                    order1.setOrderId(2);
                    orderDetail1.setOrder(order1);
                }
            }
            persisted.setOrderDetails(order1.getOrderDetails());

            return persisted;
        });

        // Act
        OrderResponseDTO orderResponse = orderService.createOrder(orderRequest);

        // Assert
        assertNotNull(orderResponse);
        assertEquals(BigDecimal.valueOf(2000), orderResponse.getOrderTotal());
        assertEquals(8, product.getProductQuantity()); // 10 - 2
        assertEquals(OrderStatus.PENDING, orderResponse.getStatus());
        verify(productRepository, times(1)).save(product);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Test
    void testGetAllOrders_NoOrdersFound() {
        // Arrange
        when(orderRepository.findAll()).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> orderService.getAllOrders());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void testGetAllOrders_Success() {
        // Arrange
        when(orderRepository.findAll()).thenReturn(List.of(order));

        // Act
        List<OrderResponseDTO> result = orderService.getAllOrders();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.getFirst().getOrderId());
        assertEquals("Laptop", result.getFirst().getOrderDetails().getFirst().getProductName());

        verify(orderRepository, times(1)).findAll();
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Test
    void testGetOrdersByCustomerId_CustomerNotFound() {
        // Arrange
        when(customerRepository.findById(99)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CustomerNotFoundException.class,
                () -> orderService.getOrdersByCustomerId(99));

        verify(customerRepository, times(1)).findById(99);
        verify(orderRepository, never()).getByCustomer_CustomerId(anyInt());
    }

    @Test
    void testGetOrdersByCustomerId_NoOrdersFound() {
        // Arrange
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(orderRepository.getByCustomer_CustomerId(1)).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(EntityNotFoundException.class,
                () -> orderService.getOrdersByCustomerId(1));

        verify(customerRepository, times(1)).findById(1);
        verify(orderRepository, times(1)).getByCustomer_CustomerId(1);
    }

    @Test
    void testGetOrdersByCustomerId_Success() {
        // Arrange
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(orderRepository.getByCustomer_CustomerId(1)).thenReturn(List.of(order));

        // Act
        List<OrderResponseDTO> result = orderService.getOrdersByCustomerId(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.getFirst().getOrderId());
        assertEquals("Laptop", result.getFirst().getOrderDetails().getFirst().getProductName());

        verify(customerRepository, times(1)).findById(1);
        verify(orderRepository, times(1)).getByCustomer_CustomerId(1);
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Test
    void testGetOrderById_NotFound() {
        // Arrange
        when(orderRepository.findById(99)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(OrderNotFoundException.class, () -> orderService.getOrderById(99));

        verify(orderRepository, times(1)).findById(99);
    }

    @Test
    void testGetOrderById_Success() {
        // Arrange
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));

        // Act
        OrderResponseDTO result = orderService.getOrderById(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getOrderId());
        assertEquals("Laptop", result.getOrderDetails().getFirst().getProductName());

        verify(orderRepository, times(1)).findById(1);
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Test
    void testUpdateOrder_InvalidOrderId() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> orderService.updateOrder(0, orderRequest));
        assertEquals("Order ID must be positive", exception.getMessage());
        verify(orderRepository, never()).findById(anyInt());
    }

    @Test
    void testUpdateOrder_OrderNotFound() {
        // Arrange
        when(orderRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        OrderNotFoundException exception = assertThrows(OrderNotFoundException.class, () -> orderService.updateOrder(1, orderRequest));
        assertEquals("Order with ID: 1 not found", exception.getMessage());
        verify(orderRepository, times(1)).findById(1);
    }

    @Test
    void testUpdateOrder_NonPendingOrder() {
        // Setup
        order.setStatus(OrderStatus.CONFIRMED);

        // Arrange
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> orderService.updateOrder(1, orderRequest));
        assertEquals("Cannot update order with status CONFIRMED", exception.getMessage());
        verify(orderRepository, times(1)).findById(1);
    }

    @Test
    void testUpdateOrder_ProductNotFound() {
        // Arrange
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> orderService.updateOrder(1, orderRequest));
        assertEquals("Product not found: 1", exception.getMessage());
        verify(productRepository, times(1)).findById(1);
    }

    @Test
    void testUpdateOrder_InsufficientStock() {
        // Setup
        orderRequest.getOrderDetails().getFirst().setOrderQuantity(100);

        // Arrange
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        // Act & Assert
        InsufficientStockException exception = assertThrows(InsufficientStockException.class, () -> orderService.updateOrder(1, orderRequest));
        assertEquals("Insufficient stock for product: Laptop", exception.getMessage());
        verify(productRepository, times(1)).save(product); // Stock restoration
        verify(productRepository, times(3)).findById(1);
    }

    @Test
    void testUpdateOrder_EmptyOrderDetails() {
        // Setup
        orderRequest.setOrderDetails(Collections.emptyList());

        // Arrange
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> orderService.updateOrder(1, orderRequest));
        assertEquals("Order must contain at least 1 item", exception.getMessage());
        verify(productRepository, times(1)).save(product); // Stock restoration
    }

    @Test
    void testUpdateOrder_CustomerNotFound() {
        // Arrange
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(customerRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () -> orderService.updateOrder(1, orderRequest));
        assertEquals("Customer not found: 1", exception.getMessage());
        verify(customerRepository, times(1)).findById(1);
    }

    @Test
    void testUpdateOrder_Success() {
        // Setup
        orderRequest.getOrderDetails().getFirst().setOrderQuantity(3);

        // Arrange
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order order1 = invocation.getArgument(0);
            Order persisted = new Order();
            persisted.setOrderId(order1.getOrderId());
            persisted.setOrderDetails(order1.getOrderDetails());
            persisted.setCustomer(order1.getCustomer());
            persisted.setOrderTime(order1.getOrderTime());
            persisted.setStatus(order1.getStatus());
            persisted.setOrderTotal(order1.getOrderTotal());

            if (order1.getOrderDetails() != null) {
                for (OrderDetail orderDetail1 : order1.getOrderDetails()) {
                    orderDetail1.getOrderDetailsID().setOrderId(order1.getOrderId());
                    order1.setOrderId(order1.getOrderId());
                    orderDetail1.setOrder(order1);
                }
            }
            persisted.setOrderDetails(order1.getOrderDetails());

            return persisted;
        });

        // Act
        OrderResponseDTO result = orderService.updateOrder(1, orderRequest);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getOrderId());
        assertEquals(BigDecimal.valueOf(3000), result.getOrderTotal());
        verify(productRepository, times(2)).save(product); // Verify stock restoration
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Test
    void testDeleteOrderById_InvalidId() {
        assertThrows(IllegalArgumentException.class, () -> orderService.deleteOrderById(0));
        assertThrows(IllegalArgumentException.class, () -> orderService.deleteOrderById(-5));
        verifyNoInteractions(orderRepository, productRepository);
    }

    @Test
    void testDeleteOrderById_OrderNotFound() {
        when(orderRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.deleteOrderById(1));

        verify(orderRepository, times(1)).findById(1);
        verifyNoInteractions(productRepository);
    }

    @Test
    void testDeleteOrderById_OrderNotPending() {
        order.setStatus(OrderStatus.CONFIRMED);
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));

        assertThrows(IllegalStateException.class, () -> orderService.deleteOrderById(1));

        verify(orderRepository, times(1)).findById(1);
        verify(orderRepository, never()).save(any());
        verifyNoInteractions(productRepository);
    }

    @Test
    void testDeleteOrderById_Success() {
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        orderService.deleteOrderById(1);

        // Product stock should be restored
        assertEquals(12, product.getProductQuantity());  // 10 + 2
        // Order status should be updated
        assertEquals(OrderStatus.CANCELLED, order.getStatus());

        verify(orderRepository, times(1)).findById(1);
        verify(productRepository, times(1)).findById(1);
        verify(productRepository, times(1)).save(product);
        verify(orderRepository, times(1)).save(order);
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Test
    void testSortOrders_InvalidSortField() {
        assertThrows(IllegalArgumentException.class,
                () -> orderService.sortOrders(null, "invalidField", "asc"));
        verifyNoInteractions(orderRepository, customerRepository);
    }

    @Test
    void testSortOrders_AllOrders_Success() {
        // Arrange
        Sort sort = Sort.by("orderId").ascending();
        when(orderRepository.findAll(sort)).thenReturn(List.of(order));

        // Act
        List<OrderResponseDTO> result = orderService.sortOrders(null, "orderId", "asc");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.getFirst().getOrderId());
        assertEquals("Laptop", result.getFirst().getOrderDetails().getFirst().getProductName());

        verify(orderRepository, times(1)).findAll(sort);
    }

    @Test
    void testSortOrders_ByCustomer_Success() {
        // Arrange
        Sort sort = Sort.by("orderId").descending();
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(orderRepository.findByCustomer(customer, sort)).thenReturn(List.of(order));

        // Act
        List<OrderResponseDTO> result = orderService.sortOrders(1, "orderId", "desc");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.getFirst().getOrderId());

        verify(customerRepository, times(1)).findById(1);
        verify(orderRepository, times(1)).findByCustomer(customer, sort);
    }

    @Test
    void testSortOrders_CustomerNotFound() {
        when(customerRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class,
                () -> orderService.sortOrders(99, "orderId", "asc"));

        verify(customerRepository, times(1)).findById(99);
        verify(orderRepository, never()).findByCustomer(any(), any());
    }

    @Test
    void testSortOrders_NoOrdersFound() {
        Sort sort = Sort.by("orderId").ascending();
        when(orderRepository.findAll(sort)).thenReturn(Collections.emptyList());

        assertThrows(EntityNotFoundException.class,
                () -> orderService.sortOrders(null, "orderId", "asc"));

        verify(orderRepository, times(1)).findAll(sort);
    }
}
