package com.minimarket.web_minimarket.controller;

import com.minimarket.web_minimarket.entity.Product;
import com.minimarket.web_minimarket.entity.Supplier;
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
    public Supplier createSupplier(@RequestBody Supplier supplier) {
        return supplierService.createSupplier(supplier);
    }

    @GetMapping
    public List<Supplier> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }

    @GetMapping("/{supplierId}")
    public ResponseEntity<Supplier> getSupplierById(@PathVariable("supplierId") int supplierId) {
        Supplier supplier = supplierService.getSupplierById(supplierId);
        return ResponseEntity.ok(supplier);
    }

    @GetMapping("/search")
    public ResponseEntity<Supplier> getSupplierByName(@RequestParam String supplierName) {
        Supplier supplier = supplierService.getSupplierByName(supplierName);
        return ResponseEntity.ok(supplier);
    }

    @GetMapping("/{supplierId}/products")
    public List<Product> getProductsBySupplierId(@PathVariable int supplierId) {
        return supplierService.getProductsBySupplierId(supplierId);
    }

    @PutMapping("/{supplierId}")
    public ResponseEntity<Supplier> updateSupplier(@PathVariable("supplierId") int supplierId, @RequestParam Supplier supplierDetail) {
        Supplier updatedSupplier = supplierService.updateSupplier(supplierId, supplierDetail);
        return ResponseEntity.ok(updatedSupplier);
    }

    @DeleteMapping("/{supplierId}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable("supplierId") int supplierId) {
        supplierService.deleteSupplier(supplierId);
        return ResponseEntity.noContent().build();
    }
}
