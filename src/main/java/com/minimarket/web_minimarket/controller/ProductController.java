package com.minimarket.web_minimarket.controller;

import com.minimarket.web_minimarket.dto.*;
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
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO productRequest) {
        ProductResponseDTO createdProduct = productService.createProduct(productRequest);
        return ResponseEntity.ok(createdProduct);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<ProductResponseDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponseDTO>> getProductsByCategoryId(@PathVariable("categoryId") int categoryId) {
        List<ProductResponseDTO> products = productService.getProductsByCategoryId(categoryId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<List<ProductResponseDTO>> getProductsBySupplierId(@PathVariable("supplierId") int supplierId) {
        List<ProductResponseDTO> products = productService.getProductsBySupplierId(supplierId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable("productId") int productId) {
        ProductResponseDTO product = productService.getProductById(productId);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/search")
    public ResponseEntity<ProductResponseDTO> getProductByName(@RequestParam String productName) {
        ProductResponseDTO product = productService.getProductByName(productName);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/{productId}/category")
    public ResponseEntity<CategoryResponseDTO> getCategoryByProductId(@PathVariable("productId") int productId) {
        CategoryResponseDTO category = productService.getCategoryByProductId(productId);
        return ResponseEntity.ok(category);
    }

    @GetMapping("{productId}/supplier")
    public ResponseEntity<SupplierResponseDTO> getSupplierByProductId(@PathVariable("productId") int productId) {
        SupplierResponseDTO supplier = productService.getSupplierByProductId(productId);
        return ResponseEntity.ok(supplier);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable("productId") int productId, @RequestBody ProductRequestDTO productDetail) {
        ProductResponseDTO updatedProduct = productService.updateProduct(productId, productDetail);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProductById(@PathVariable("productId") int productId) {
        productService.deleteProductById(productId);
        return ResponseEntity.noContent().build();
    }
}
