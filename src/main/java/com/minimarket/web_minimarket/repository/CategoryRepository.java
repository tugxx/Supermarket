package com.minimarket.web_minimarket.repository;

import com.minimarket.web_minimarket.entity.Category;
import com.minimarket.web_minimarket.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
//    select *
//    from categories cate
//    where cate.category_name = category_name;
    Optional<Category> getByCategoryName(String categoryName);
    List<Product> getProductsByCategoryId(int categoryId);
}
