package com.minimarket.web_minimarket.repository;

import com.minimarket.web_minimarket.entity.Category;
import com.minimarket.web_minimarket.entity.Product;
import com.minimarket.web_minimarket.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    Optional<Product> getByProductName(String productName);
    List<Product> getProductsByCategory_categoryId(int categoryCategoryId); // SELECT * FROM products WHERE category_id = ?;
    Optional<Category> getByCategory_categoryId(int productId);
    List<Product> getProductsBySupplier_supplierId(int supplierSupplierId); // SELECT * FROM products WHERE supplier_id = ?;

    //  SELECT s.*
    //  FROM suppliers s
    //  JOIN products p ON s.supplier_id = p.supplier_id
    //  WHERE p.product_id = ?;
    Optional<Supplier> getBySupplier_supplierId(int productId);

    List<Product> findByIdIn(Set<Integer> ids);
}
