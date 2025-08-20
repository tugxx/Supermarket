package com.minimarket.web_minimarket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minimarket.web_minimarket.dto.OrderRequestDTO;
import com.minimarket.web_minimarket.dto.OrderResponseDTO;
import com.minimarket.web_minimarket.entity.OrderStatus;
import com.minimarket.web_minimarket.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    private OrderResponseDTO orderResponse;

    @BeforeEach
    void setUp() {
        orderResponse = new OrderResponseDTO();
        orderResponse.setOrderId(1);
        orderResponse.setOrderTotal(BigDecimal.valueOf(100.50));
        orderResponse.setOrderTime(LocalDateTime.now());
        orderResponse.setStatus(OrderStatus.PENDING);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testCreateOrder() throws Exception {
        OrderRequestDTO orderRequest = new OrderRequestDTO();
        orderRequest.setCustomerId(1);

        Mockito.when(orderService.createOrder(any(OrderRequestDTO.class))).thenReturn(orderResponse);
        mockMvc.perform(post("/api/orders").with(csrf()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/orders/1"))
                .andExpect(jsonPath("$.orderId").value(1))
                .andExpect(jsonPath("$.orderTotal").value(100.50));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testGetOrders_all() throws Exception {
        Mockito.when(orderService.sortOrders(null, "orderId", "asc"))
                .thenReturn(List.of(orderResponse));

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].orderId").value(1));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testGetOrders_byCustomerId() throws Exception {
        Mockito.when(orderService.sortOrders(eq(2), eq("orderId"), eq("asc")))
                .thenReturn(List.of(orderResponse));

        mockMvc.perform(get("/api/orders")
                        .param("customerId", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].orderId").value(1));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testGetOrderById() throws Exception {
        Mockito.when(orderService.getOrderById(1))
                .thenReturn(orderResponse);

        mockMvc.perform(get("/api/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(1));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testUpdateOrder() throws Exception {
        OrderRequestDTO orderRequest = new OrderRequestDTO();
        orderRequest.setCustomerId(1);

        Mockito.when(orderService.updateOrder(eq(1), any(OrderRequestDTO.class)))
                .thenReturn(orderResponse);

        mockMvc.perform(put("/api/orders/1").with(csrf()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(1));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testDeleteOrder() throws Exception {
        Mockito.doNothing().when(orderService).deleteOrderById(1);

        mockMvc.perform(delete("/api/orders/1").with(csrf()))
                .andExpect(status().isNoContent());
    }
}
