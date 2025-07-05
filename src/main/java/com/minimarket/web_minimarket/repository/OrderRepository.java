package com.minimarket.web_minimarket.repository;

import com.minimarket.web_minimarket.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Integer> {
    List<Order> getByCustomer_CustomerId(int customerId);
}
