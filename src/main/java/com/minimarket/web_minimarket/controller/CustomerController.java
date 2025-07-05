package com.minimarket.web_minimarket.controller;

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
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("customerId") int customerId) {
        Customer customer = customerService.getCustomerById(customerId);
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/search")
    public ResponseEntity<Customer> getCustomerByName(@RequestParam String customerName) {
        Customer customer = customerService.getCustomerByName(customerName);
        if (customer != null) {
            return ResponseEntity.ok(customer);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("customerId") int customerId, @RequestBody Customer customerDetails) {
        Customer updatedCustomer = customerService.updateCustomer(customerId, customerDetails);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("customerId") int customerId) {
        customerService.deleteCustomerById(customerId);
        return ResponseEntity.noContent().build();
    }
}
