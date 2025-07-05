package com.minimarket.web_minimarket.repository;

import com.minimarket.web_minimarket.entity.OrderDetail;
import com.minimarket.web_minimarket.entity.OrderDetailsID;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailsID> {
    Optional<OrderDetail> getByOrder_orderIdAndProduct_productId(int orderId, int productId);
    List<OrderDetail> getByOrder_orderId(int orderId);
    List<OrderDetail> getByProduct_productId(int productId);
}
