package com.minimarket.web_minimarket.service;

import com.minimarket.web_minimarket.dto.OrderDetailRequestDTO;
import com.minimarket.web_minimarket.dto.OrderDetailResponseDTO;
import com.minimarket.web_minimarket.entity.OrderDetail;
import com.minimarket.web_minimarket.entity.OrderDetailsID;
import com.minimarket.web_minimarket.mapper.OrderDetailMapper;
import com.minimarket.web_minimarket.repository.OrderDetailRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    public OrderDetailResponseDTO createOrderDetail(OrderDetailRequestDTO orderDetailDTO) {
        OrderDetail orderDetail = orderDetailMapper.orderDetailRequestToOrderDetail(orderDetailDTO);
        OrderDetail savedOrderDetail = orderDetailRepository.save(orderDetail);
        return orderDetailMapper.orderDetailToOrderDetailResponse(savedOrderDetail);
    }

    public List<OrderDetailResponseDTO> getAllOrderDetails() {
        return orderDetailRepository.findAll().stream().map(orderDetailMapper::orderDetailToOrderDetailResponse).collect(Collectors.toList());
    }

    public OrderDetailResponseDTO getOrderDetailById(OrderDetailsID orderDetailsId) {
        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailsId).orElseThrow(()-> new EntityNotFoundException("OrderDetail Not Found"));
        return orderDetailMapper.orderDetailToOrderDetailResponse(orderDetail);
    }

    public OrderDetailResponseDTO getOrderDetailByOrderIdAndProductId(int orderId, int productId) {
        OrderDetail orderDetail = orderDetailRepository.getByOrder_orderIdAndProduct_productId(orderId, productId).orElseThrow(()->new EntityNotFoundException("OrderDetail Not Found"));
        return orderDetailMapper.orderDetailToOrderDetailResponse(orderDetail);
    }

    public List<OrderDetailResponseDTO> getOrderDetailByOrderId(int orderId) {
        List<OrderDetail> orderDetails = orderDetailRepository.getByOrder_orderId(orderId);
        return orderDetails.stream().map(orderDetailMapper::orderDetailToOrderDetailResponse).collect(Collectors.toList());
    }

    public List<OrderDetailResponseDTO> getOrderDetailByProductId(int productId) {
        List<OrderDetail> orderDetails = orderDetailRepository.getByProduct_productId(productId);
        return orderDetails.stream().map(orderDetailMapper::orderDetailToOrderDetailResponse).collect(Collectors.toList());
    }

    public OrderDetailResponseDTO updateOrderDetail(int orderId, int productId, OrderDetail orderDetailDetail) {
        OrderDetail existingOrderDetail = orderDetailRepository.getByOrder_orderIdAndProduct_productId(orderId, productId).orElseThrow(()->new EntityNotFoundException("OrderDetail not found"));
        existingOrderDetail.setOrderPrice(orderDetailDetail.getOrderPrice());
        existingOrderDetail.setOrderQuantity(orderDetailDetail.getOrderQuantity());
        OrderDetail updatedOrderDetail = orderDetailRepository.save(existingOrderDetail);
        return orderDetailMapper.orderDetailToOrderDetailResponse(updatedOrderDetail);
    }

    public void deleteOrderDetailById(OrderDetailsID orderDetailsId) {
        orderDetailRepository.deleteById(orderDetailsId);
    }
}
