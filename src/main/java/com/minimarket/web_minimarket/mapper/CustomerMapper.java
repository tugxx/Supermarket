package com.minimarket.web_minimarket.mapper;

import com.minimarket.web_minimarket.dto.CustomerRequestDTO;
import com.minimarket.web_minimarket.dto.CustomerResponseDTO;
import com.minimarket.web_minimarket.entity.Customer;
import com.minimarket.web_minimarket.entity.User;
import com.minimarket.web_minimarket.repository.UserRepository;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(target = "customerId", source = "customerId")
    @Mapping(target = "customerName", source = "customerName")
    @Mapping(target = "customerAge", source = "customerAge")
    @Mapping(target = "user", source = "user")
    CustomerResponseDTO customerToCustomerResponse(Customer customer);

    @Mapping(target = "customerName", source = "customerName")
    @Mapping(target = "customerAge", source = "customerAge")
    @Mapping(target = "user", ignore = true)
    Customer customerRequestToCustomer(CustomerRequestDTO customerDTO);

    @AfterMapping
    default void linkUser(CustomerRequestDTO customerDTO, @MappingTarget Customer customer, @Context UserRepository userRepository) {
        // Load real User
        User user = userRepository.findById(customerDTO.getUserId()).orElseThrow(()->new RuntimeException("User not found: "+customerDTO.getUserId()));
        customer.setUser(user);
    }
}
