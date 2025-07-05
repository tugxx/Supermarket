package com.minimarket.web_minimarket.repository;

import com.minimarket.web_minimarket.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Optional<Customer> getByCustomerName(String customerName);
}
