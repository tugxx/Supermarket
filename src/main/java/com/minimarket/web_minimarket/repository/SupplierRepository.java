package com.minimarket.web_minimarket.repository;

import com.minimarket.web_minimarket.entity.Product;
import com.minimarket.web_minimarket.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
    Optional<Supplier> getBySupplierName(String supplierName);
    List<Product> getProductsBySupplierId(int supplierId);
}
