package com.minimarket.web_minimarket.controller;

import com.minimarket.web_minimarket.dto.CustomerRequestDTO;
import com.minimarket.web_minimarket.dto.CustomerResponseDTO;
import com.minimarket.web_minimarket.entity.Customer;
import com.minimarket.web_minimarket.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(@RequestBody CustomerRequestDTO customerRequest) {
        CustomerResponseDTO createdCustomer = customerService.createCustomer(customerRequest);
        return ResponseEntity.ok(createdCustomer);
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers() {
        List<CustomerResponseDTO> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponseDTO> getCustomerById(@PathVariable("customerId") int customerId) {
        CustomerResponseDTO customer = customerService.getCustomerById(customerId);
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/search")
    public ResponseEntity<CustomerResponseDTO> getCustomerByName(@RequestParam String customerName) {
        CustomerResponseDTO customer = customerService.getCustomerByName(customerName);
        if (customer != null) {
            return ResponseEntity.ok(customer);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerResponseDTO> updateCustomer(@PathVariable("customerId") int customerId, @RequestBody Customer customerDetails) {
        CustomerResponseDTO updatedCustomer = customerService.updateCustomer(customerId, customerDetails);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("customerId") int customerId) {
        customerService.deleteCustomerById(customerId);
        return ResponseEntity.noContent().build();
    }
}
