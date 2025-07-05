package com.minimarket.web_minimarket.service;

import com.minimarket.web_minimarket.entity.OrderDetail;
import com.minimarket.web_minimarket.entity.OrderDetailsID;
import com.minimarket.web_minimarket.repository.OrderDetailRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public OrderDetail createOrderDetail(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    public List<OrderDetail> getAllOrderDetails() {
        return orderDetailRepository.findAll();
    }

    public OrderDetail getOrderDetailById(OrderDetailsID orderDetailsId) {
        return orderDetailRepository.findById(orderDetailsId).orElseThrow(()-> new EntityNotFoundException("OrderDetail Not Found"));
    }

    public OrderDetail getOrderDetailByOrderIdAndProductId(int orderId, int productId) {
        return orderDetailRepository.getByOrder_orderIdAndProduct_productId(orderId, productId).orElseThrow(()->new EntityNotFoundException("OrderDetail Not Found"));
    }

    public List<OrderDetail> getOrderDetailByOrderId(int orderId) {
        return orderDetailRepository.getByOrder_orderId(orderId);
    }

    public List<OrderDetail> getOrderDetailByProductId(int productId) {
        return orderDetailRepository.getByProduct_productId(productId);
    }

    public OrderDetail updateOrderDetail(int orderId, int productId, OrderDetail orderDetailDetail) {
        OrderDetail orderDetail = getOrderDetailByOrderIdAndProductId(orderId, productId);
        orderDetail.setOrderPrice(orderDetailDetail.getOrderPrice());
        return orderDetailRepository.save(orderDetail);
    }

    public void deleteOrderDetailById(OrderDetailsID orderDetailsId) {
        orderDetailRepository.deleteById(orderDetailsId);
    }
}
