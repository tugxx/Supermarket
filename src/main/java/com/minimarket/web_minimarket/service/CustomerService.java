package com.minimarket.web_minimarket.service;

import com.minimarket.web_minimarket.dto.CustomerRequestDTO;
import com.minimarket.web_minimarket.dto.CustomerResponseDTO;
import com.minimarket.web_minimarket.entity.Customer;
import com.minimarket.web_minimarket.mapper.CustomerMapper;
import com.minimarket.web_minimarket.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    public CustomerResponseDTO createCustomer(CustomerRequestDTO customerDTO) {
        Customer customer = customerMapper.customerRequestToCustomer(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.customerToCustomerResponse(savedCustomer);
    }

    public List<CustomerResponseDTO> getAllCustomers() {
        return customerRepository.findAll().stream().map(customerMapper::customerToCustomerResponse).collect(Collectors.toList());
    }

    public CustomerResponseDTO getCustomerById(int customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(()->new EntityNotFoundException("Customer not found"));
        return customerMapper.customerToCustomerResponse(customer);
    }

    public CustomerResponseDTO getCustomerByName(String customerName) {
        Customer customer = customerRepository.getByCustomerName(customerName).orElseThrow(()->new EntityNotFoundException("Customer not found"));
        return customerMapper.customerToCustomerResponse(customer);
    }

    public CustomerResponseDTO updateCustomer(int customerId, Customer customerDetails) {
        Customer existingCustomer = customerRepository.findById(customerId).orElseThrow(()->new EntityNotFoundException("Customer not found"));
        existingCustomer.setCustomerAge(customerDetails.getCustomerAge());
        existingCustomer.setCustomerName(customerDetails.getCustomerName());
        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return customerMapper.customerToCustomerResponse(updatedCustomer);
    }

    public void deleteCustomerById(int customerId) {
        customerRepository.deleteById(customerId);
    }
}
