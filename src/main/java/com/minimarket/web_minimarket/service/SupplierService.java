package com.minimarket.web_minimarket.service;

import com.minimarket.web_minimarket.entity.Product;
import com.minimarket.web_minimarket.entity.Supplier;
import com.minimarket.web_minimarket.repository.SupplierRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;

    public Supplier createSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public Supplier getSupplierById(int supplierId) {
        return supplierRepository.findById(supplierId).orElseThrow(()->new EntityNotFoundException("Supplier with id: "+supplierId+" not found"));
    }

    public Supplier getSupplierByName(String supplierName) {
        return supplierRepository.getBySupplierName(supplierName).orElseThrow(()->new EntityNotFoundException("supplier with name: "+supplierName+" not found"));
    }

    public List<Product> getProductsBySupplierId(int supplierId) {
        return supplierRepository.getProductsBySupplierId(supplierId);
    }

    public Supplier updateSupplier(int supplierId, Supplier supplier) {
        Supplier updatedSupplier = getSupplierById(supplierId);
        updatedSupplier.setSupplierContact(supplier.getSupplierContact());
        return supplierRepository.save(updatedSupplier);
    }

    public void deleteSupplier(int supplierId) {
        supplierRepository.deleteById(supplierId);
    }
}
