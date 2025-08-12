package com.minimarket.web_minimarket.controller;

import com.minimarket.web_minimarket.dto.ProductResponseDTO;
import com.minimarket.web_minimarket.dto.SupplierRequestDTO;
import com.minimarket.web_minimarket.dto.SupplierResponseDTO;
import com.minimarket.web_minimarket.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/suppliers")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    @PostMapping
    public ResponseEntity<SupplierResponseDTO> createSupplier(@RequestBody SupplierRequestDTO supplierRequest) {
        SupplierResponseDTO createdSupplier = supplierService.createSupplier(supplierRequest);
        return ResponseEntity.ok(createdSupplier);
    }

    @GetMapping
    public ResponseEntity<List<SupplierResponseDTO>> getAllSuppliers() {
        List<SupplierResponseDTO> suppliers = supplierService.getAllSuppliers();
        return ResponseEntity.ok(suppliers);
    }

    @GetMapping("/{supplierId}")
    public ResponseEntity<SupplierResponseDTO> getSupplierById(@PathVariable("supplierId") int supplierId) {
        SupplierResponseDTO supplier = supplierService.getSupplierById(supplierId);
        return ResponseEntity.ok(supplier);
    }

    @GetMapping("/search")
    public ResponseEntity<SupplierResponseDTO> getSupplierByName(@RequestParam String supplierName) {
        SupplierResponseDTO supplier = supplierService.getSupplierByName(supplierName);
        return ResponseEntity.ok(supplier);
    }

    @GetMapping("/{supplierId}/products")
    public ResponseEntity<List<ProductResponseDTO>> getProductsBySupplierId(@PathVariable int supplierId) {
        List<ProductResponseDTO> products = supplierService.getProductsBySupplierId(supplierId);
        return ResponseEntity.ok(products);
    }

    @PutMapping("/{supplierId}")
    public ResponseEntity<SupplierResponseDTO> updateSupplier(@PathVariable("supplierId") int supplierId, @RequestBody SupplierRequestDTO supplierDetail) {
        SupplierResponseDTO updatedSupplier = supplierService.updateSupplier(supplierId, supplierDetail);
        return ResponseEntity.ok(updatedSupplier);
    }

    @DeleteMapping("/{supplierId}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable("supplierId") int supplierId) {
        supplierService.deleteSupplier(supplierId);
        return ResponseEntity.noContent().build();
    }
}
