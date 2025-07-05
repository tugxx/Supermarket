package com.minimarket.web_minimarket.service;

import com.minimarket.web_minimarket.entity.Customer;
import com.minimarket.web_minimarket.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(int customerId) {
        return customerRepository.findById(customerId).orElseThrow(()->new EntityNotFoundException("Customer not found"));
    }

    public Customer getCustomerByName(String customerName) {
        return customerRepository.getByCustomerName(customerName).orElseThrow(()->new EntityNotFoundException("Customer not found"));
    }

    public Customer updateCustomer(int customerId, Customer customerDetails) {
        Customer customer = getCustomerById(customerId);
        customer.setCustomerName(customerDetails.getCustomerName());
        return customerRepository.save(customer);
    }

    public void deleteCustomerById(int customerId) {
        customerRepository.deleteById(customerId);
    }
}
