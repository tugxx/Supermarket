package com.minimarket.web_minimarket.dto;

public class SupplierResponseDTO {
    private int supplierId;
    private String supplierName;
    private String supplierContact;

    // Constructors
    public SupplierResponseDTO() {}

    public SupplierResponseDTO(String supplierContact, int supplierId, String supplierName) {
        this.supplierContact = supplierContact;
        this.supplierId = supplierId;
        this.supplierName = supplierName;
    }

    // Getters, setters
    public String getSupplierContact() {
        return supplierContact;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierContact(String supplierContact) {
        this.supplierContact = supplierContact;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
}
