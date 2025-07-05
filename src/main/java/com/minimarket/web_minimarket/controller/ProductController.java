package com.minimarket.web_minimarket.controller;

import com.minimarket.web_minimarket.entity.Category;
import com.minimarket.web_minimarket.entity.Product;
import com.minimarket.web_minimarket.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @GetMapping
    public List<Product> getProductsByCategoryId(@RequestParam(required = false) Integer categoryId) {
        if (categoryId != null) {
            return productService.getProductsByCategoryId(categoryId);
        }
        return productService.getAllProducts();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable("productId") int productId) {
        Product product = productService.getProductById(productId);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/search")
    public ResponseEntity<Product> getProductByName(@RequestParam String productName) {
        Product product = productService.getProductByName(productName);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/{productId}/category")
    public ResponseEntity<Category> getCategoryByProductId(@PathVariable("productId") int productId) {
        Category category = productService.getCategoryByProductId(productId);
        return ResponseEntity.ok(category);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable("productId") int productId, @RequestBody Product productDetail) {
        Product updatedProduct = productService.updateProduct(productId, productDetail);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProductById(@PathVariable("productId") int productId) {
        productService.deleteProductById(productId);
        return ResponseEntity.noContent().build();
    }
}
