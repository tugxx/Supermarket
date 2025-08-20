package com.minimarket.web_minimarket.mapper;

import com.minimarket.web_minimarket.dto.OrderDetailResponseDTO;
import com.minimarket.web_minimarket.dto.OrderRequestDTO;
import com.minimarket.web_minimarket.dto.OrderResponseDTO;
import com.minimarket.web_minimarket.entity.Customer;
import com.minimarket.web_minimarket.entity.Order;
import com.minimarket.web_minimarket.entity.OrderDetail;
import com.minimarket.web_minimarket.exception.CustomerNotFoundException;
import com.minimarket.web_minimarket.repository.CustomerRepository;
import com.minimarket.web_minimarket.repository.ProductRepository;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "orderId", source = "orderId")
    @Mapping(target = "orderTime", source = "orderTime")
    @Mapping(target = "orderTotal", source = "orderTotal")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "customerId", ignore = true)
    @Mapping(target = "orderDetails", ignore = true)
    OrderResponseDTO orderToOrderResponse(Order order, @Context OrderDetailMapper orderDetailMapper);

    @AfterMapping
    default void linkCustomerIdAndOrderDetails(Order order, @MappingTarget OrderResponseDTO orderResponse, @Context OrderDetailMapper orderDetailMapper) {
        // Load customerId
        if (order.getCustomer() != null) {
            orderResponse.setCustomerId(order.getCustomer().getCustomerId());
        }

        // Load orderDetails
        if (order.getOrderDetails() != null) {
            List<OrderDetailResponseDTO> orderDetailResponses = order.getOrderDetails().stream().map(orderDetailMapper::orderDetailToOrderDetailResponse).toList();
            orderResponse.setOrderDetails(orderDetailResponses);
        }
    }

    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "orderDetails", ignore = true)
    Order orderRequestToOrder(OrderRequestDTO orderRequest, @Context CustomerRepository customerRepository, @Context ProductRepository productRepository, @Context OrderDetailMapper orderDetailMapper);

    @AfterMapping
    default void linkCustomerIdAndOrderDetails(OrderRequestDTO orderRequest, @MappingTarget Order order, @Context CustomerRepository customerRepository, @Context ProductRepository productRepository, @Context OrderDetailMapper orderDetailMapper) {
        // Load real Customer
        Customer customer = customerRepository.findById(orderRequest.getCustomerId()).orElseThrow(()->new CustomerNotFoundException("Customer not found: "+orderRequest.getCustomerId()));
        order.setCustomer(customer);

        // Load real OrderDetail
        if (orderRequest.getOrderDetails() != null) {
            List<OrderDetail> orderDetails = orderRequest.getOrderDetails().stream().map(orderDetailRequest -> orderDetailMapper.orderDetailRequestToOrderDetail(orderDetailRequest, productRepository)).toList();
            order.setOrderDetails(orderDetails);
        }
    }
}
