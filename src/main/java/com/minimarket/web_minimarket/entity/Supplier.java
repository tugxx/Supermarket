package com.minimarket.web_minimarket.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "suppliers")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplier_id")
    private int supplierId;

    @Column(name = "supplier_name")
    private String supplierName;
    @Column(name = "supplier_contactInfo")
    private String supplierContact;

    // Constructors
    public Supplier() {
    }

    public Supplier(String supplierContact, int supplierId, String supplierName) {
        this.supplierContact = supplierContact;
        this.supplierId = supplierId;
        this.supplierName = supplierName;
    }

    public Supplier(String supplierContact, String supplierName) {
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

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
}
