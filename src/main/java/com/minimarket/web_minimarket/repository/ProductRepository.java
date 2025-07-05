package com.minimarket.web_minimarket.repository;

import com.minimarket.web_minimarket.entity.Category;
import com.minimarket.web_minimarket.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    List<Product> getProductsByCategory_categoryId(int categoryCategoryId);
    Optional<Product> getByProductName(String productName);
    Optional<Category> getByCategory_categoryId(int productId);
}
