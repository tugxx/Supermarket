package com.minimarket.web_minimarket.service;

import com.minimarket.web_minimarket.dto.CategoryResponseDTO;
import com.minimarket.web_minimarket.dto.ProductRequestDTO;
import com.minimarket.web_minimarket.dto.ProductResponseDTO;
import com.minimarket.web_minimarket.dto.SupplierResponseDTO;
import com.minimarket.web_minimarket.entity.Category;
import com.minimarket.web_minimarket.entity.Product;
import com.minimarket.web_minimarket.entity.Supplier;
import com.minimarket.web_minimarket.mapper.CategoryMapper;
import com.minimarket.web_minimarket.mapper.ProductMapper;
import com.minimarket.web_minimarket.mapper.SupplierMapper;
import com.minimarket.web_minimarket.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private SupplierMapper supplierMapper;

    public ProductResponseDTO createProduct(ProductRequestDTO productDTO) {
        Product product = productMapper.productRequestToProduct(productDTO);
        Product savedProduct = productRepository.save(product);
        return productMapper.productToProductResponse(savedProduct);
    }

    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll().stream().map(productMapper::productToProductResponse).collect(Collectors.toList());
    }

    public ProductResponseDTO getProductById(int productId) {
        Product product = productRepository.findById(productId).orElseThrow(()->new EntityNotFoundException("Product with id: "+productId+" not found"));
        return productMapper.productToProductResponse(product);
    }

    public ProductResponseDTO getProductByName(String productName) {
        Product product = productRepository.getByProductName(productName).orElseThrow(()->new EntityNotFoundException("Product with name: "+productName+" not found"));
        return  productMapper.productToProductResponse(product);
    }

    public List<ProductResponseDTO> getProductsByCategoryId(int categoryId) {
        return productRepository.getProductsByCategory_categoryId(categoryId).stream().map(productMapper::productToProductResponse).collect(Collectors.toList());
    }

    public CategoryResponseDTO getCategoryByProductId(int productId) {
        Category category = productRepository.getByCategory_categoryId(productId).orElseThrow(()->new EntityNotFoundException("Category not found"));
        return categoryMapper.categoryToCategoryResponse(category);
    }

    public List<ProductResponseDTO> getProductsBySupplierId(int supplierId) {
        List<Product> products = productRepository.getProductsBySupplier_supplierId(supplierId);
        return products.stream().map(productMapper::productToProductResponse).collect(Collectors.toList());
    }

    public SupplierResponseDTO getSupplierByProductId(int productId) {
        Supplier supplier = productRepository.getBySupplier_supplierId(productId).orElseThrow(()->new EntityNotFoundException("Supplier not found"));
        return supplierMapper.supplierToSupplierResponse(supplier);
    }

    public ProductResponseDTO updateProduct(int productId, ProductRequestDTO productDetail) {
        Product existingProduct = productRepository.findById(productId).orElseThrow(()->new EntityNotFoundException("Product not found"));
        existingProduct.setProductName(productDetail.getProductName());
        existingProduct.setProductPrice(productDetail.getProductPrice());
        existingProduct.setProductQuantity(productDetail.getProductQuantity());
        Product updatedProduct = productRepository.save(existingProduct);
        return productMapper.productToProductResponse(updatedProduct);
    }

    public void deleteProductById(int productId) {
        productRepository.deleteById(productId);
    }
}
