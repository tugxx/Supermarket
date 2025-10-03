package com.minimarket.web_minimarket.service;

import com.minimarket.web_minimarket.dto.ProductResponseDTO;
import com.minimarket.web_minimarket.dto.SupplierRequestDTO;
import com.minimarket.web_minimarket.dto.SupplierResponseDTO;
// import com.minimarket.web_minimarket.entity.Product;
import com.minimarket.web_minimarket.entity.Supplier;
import com.minimarket.web_minimarket.mapper.ProductMapper;
import com.minimarket.web_minimarket.mapper.SupplierMapper;
import com.minimarket.web_minimarket.repository.SupplierRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private SupplierMapper supplierMapper;

    @Autowired
    private ProductMapper productMapper;

    public SupplierResponseDTO createSupplier(SupplierRequestDTO supplierDTO) {
        Supplier supplier = supplierMapper.supplierRequestToSupplier(supplierDTO);
        Supplier savedSupplier = supplierRepository.save(supplier);
        return supplierMapper.supplierToSupplierResponse(savedSupplier);
    }

    public List<SupplierResponseDTO> getAllSuppliers() {
        return supplierRepository.findAll().stream().map(supplierMapper::supplierToSupplierResponse).collect(Collectors.toList());
    }

    public SupplierResponseDTO getSupplierById(int supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId).orElseThrow(()->new EntityNotFoundException("Supplier with id: "+supplierId+" not found"));
        return supplierMapper.supplierToSupplierResponse(supplier);
    }

    public SupplierResponseDTO getSupplierByName(String supplierName) {
        Supplier supplier = supplierRepository.getBySupplierName(supplierName).orElseThrow(()->new EntityNotFoundException("supplier with name: "+supplierName+" not found"));
        return supplierMapper.supplierToSupplierResponse(supplier);
    }

    public List<ProductResponseDTO> getProductsBySupplierId(int supplierId) {
        return supplierRepository.getProductsBySupplierId(supplierId).stream().map(productMapper::productToProductResponse).collect(Collectors.toList());
    }

    public SupplierResponseDTO updateSupplier(int supplierId, SupplierRequestDTO supplierDetail) {
        Supplier existingSupplier = supplierRepository.findById(supplierId).orElseThrow(()->new EntityNotFoundException("Supplier not found"));
        existingSupplier.setSupplierName(supplierDetail.getSupplierName());
        existingSupplier.setSupplierContact(supplierDetail.getSupplierContact());
        Supplier updatedSupplier = supplierRepository.save(existingSupplier);
        return supplierMapper.supplierToSupplierResponse(updatedSupplier);
    }

    public void deleteSupplier(int supplierId) {
        supplierRepository.deleteById(supplierId);
    }
}
