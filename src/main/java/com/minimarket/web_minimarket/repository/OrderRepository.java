package com.minimarket.web_minimarket.repository;

import com.minimarket.web_minimarket.entity.Customer;
import com.minimarket.web_minimarket.entity.Order;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Integer> {
    // SELECT *
    // FROM Order o
    // WHERE o.customer_id = customerId
    List<Order> getByCustomer_CustomerId(int customerId);

    // SELECT *
    // FROM Order o
    // WHERE o.customer = customer
    // ORDER BY ...
//    SELECT *
//    FROM orders o
//    WHERE o.customer_id = customer.getCustomerId()
//    ORDER BY o.created_at DESC;

    List<Order> findByCustomer(Customer customer, Sort sort);
}
