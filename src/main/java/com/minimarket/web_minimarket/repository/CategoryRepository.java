package com.minimarket.web_minimarket.repository;

import com.minimarket.web_minimarket.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> getByCategoryName(String categoryName);
}
