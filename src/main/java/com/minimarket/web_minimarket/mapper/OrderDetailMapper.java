package com.minimarket.web_minimarket.mapper;

import com.minimarket.web_minimarket.dto.OrderDetailRequestDTO;
import com.minimarket.web_minimarket.dto.OrderDetailResponseDTO;
import com.minimarket.web_minimarket.entity.Order;
import com.minimarket.web_minimarket.entity.OrderDetail;
import com.minimarket.web_minimarket.entity.Product;
import com.minimarket.web_minimarket.repository.OrderRepository;
import com.minimarket.web_minimarket.repository.ProductRepository;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {

    @Mapping(target = "orderId", source = "orderDetailsID.orderId")
    @Mapping(target = "productId", source = "orderDetailsID.productId")
    @Mapping(target = "productName", source = "product.productName")
    @Mapping(target = "orderQuantity", source = "orderQuantity")
    @Mapping(target = "orderPrice", source = "orderPrice")
    OrderDetailResponseDTO orderDetailToOrderDetailResponse(OrderDetail orderDetail);

    @Mapping(target = "orderDetailsID.orderId", source = "orderId")
    @Mapping(target = "orderDetailsID.productId", source = "productId")
    @Mapping(target = "orderQuantity", source = "orderQuantity")
    @Mapping(target = "orderPrice", source = "orderPrice")
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "product", ignore = true)
    OrderDetail orderDetailRequestToOrderDetail(OrderDetailRequestDTO orderDetailDTO);

    @AfterMapping
    default void linkOrderAndProduct(OrderDetailRequestDTO orderDetailDTO, @MappingTarget OrderDetail orderDetail, @Context OrderRepository orderRepository, @Context ProductRepository productRepository) {
        Order order = orderRepository.findById(orderDetailDTO.getOrderId()).orElseThrow(()->new RuntimeException("Order not found: "+orderDetailDTO.getOrderId()));
        orderDetail.setOrder(order);

        Product product = productRepository.findById(orderDetailDTO.getProductId()).orElseThrow(()->new RuntimeException("Product not found: "+orderDetailDTO.getProductId()));
        orderDetail.setProduct(product);
    }
}
