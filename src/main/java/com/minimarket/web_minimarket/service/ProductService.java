package com.minimarket.web_minimarket.service;

import com.minimarket.web_minimarket.entity.Category;
import com.minimarket.web_minimarket.entity.Product;
import com.minimarket.web_minimarket.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(int productId) {
        return productRepository.findById(productId).orElseThrow(()->new EntityNotFoundException("Product with id: "+productId+" not found"));
    }

    public Product getProductByName(String productName) {
        return productRepository.getByProductName(productName).orElseThrow(()->new EntityNotFoundException("Product with name: "+productName+" not found"));
    }

    public List<Product> getProductsByCategoryId(int categoryId) {
        return productRepository.getProductsByCategory_categoryId(categoryId);
    }

    public Category getCategoryByProductId(int productId) {
        return productRepository.getByCategory_categoryId(productId).orElseThrow(()->new EntityNotFoundException("Category not found"));
    }

    public Product updateProduct(int productId, Product productDetail) {
        Product updatedProduct = getProductById(productId);
        updatedProduct.setProductName(productDetail.getProductName());
        return productRepository.save(updatedProduct);
    }

    public void deleteProductById(int productId) {
        productRepository.deleteById(productId);
    }
}
