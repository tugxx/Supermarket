package com.minimarket.web_minimarket.mapper;

import com.minimarket.web_minimarket.dto.ProductRequestDTO;
import com.minimarket.web_minimarket.dto.ProductResponseDTO;
import com.minimarket.web_minimarket.entity.Category;
import com.minimarket.web_minimarket.entity.Product;
import com.minimarket.web_minimarket.entity.Supplier;
import com.minimarket.web_minimarket.repository.CategoryRepository;
import com.minimarket.web_minimarket.repository.SupplierRepository;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, SupplierMapper.class})
public interface ProductMapper {

    @Mapping(target = "productId", source = "productId")
    @Mapping(target = "productName", source = "productName")
    @Mapping(target = "productPrice", source = "productPrice")
    @Mapping(target = "productQuantity", source = "productQuantity")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "supplier", source = "supplier")
    ProductResponseDTO productToProductResponseDTO(Product product);

    @Mapping(target = "productName", source = "productName")
    @Mapping(target = "productPrice", source = "productPrice")
    @Mapping(target = "productQuantity", source = "productQuantity")
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "supplier", ignore = true)
    Product productRequestDTOToProduct(ProductRequestDTO productDTO);

    @AfterMapping
    default void linkCategoryAndSupplier(ProductRequestDTO productDTO, @MappingTarget Product product, @Context CategoryRepository categoryRepository, @Context SupplierRepository supplierRepository) {
        // Load real Category
        Category category = categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(()->new RuntimeException(("Category not found: "+productDTO.getCategoryId())));
        product.setCategory(category);

        // Load real Supplier
        Supplier supplier = supplierRepository.findById(productDTO.getSupplierId()).orElseThrow(()->new RuntimeException("Supplier not found: "+productDTO.getSupplierId()));
        product.setSupplier(supplier);
    }
}
