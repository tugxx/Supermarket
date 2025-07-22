package com.minimarket.web_minimarket.dto;

public class SupplierRequestDTO {
    private String supplierName;
    private String supplierContact;

    // Constructors
    public SupplierRequestDTO() {}

    public SupplierRequestDTO(String supplierContact, String supplierName) {
        this.supplierContact = supplierContact;
        this.supplierName = supplierName;
    }

    // Getters, setters
    public String getSupplierContact() {
        return supplierContact;
    }

    public void setSupplierContact(String supplierContact) {
        this.supplierContact = supplierContact;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
}
