package com.minimarket.web_minimarket.mapper;

import com.minimarket.web_minimarket.dto.EmployeeRequestDTO;
import com.minimarket.web_minimarket.dto.EmployeeResponseDTO;
import com.minimarket.web_minimarket.entity.Employee;
import com.minimarket.web_minimarket.entity.User;
import com.minimarket.web_minimarket.repository.UserRepository;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface EmployeeMapper {

    @Mapping(target = "employeeId", source = "employeeId")
    @Mapping(target = "employeeName", source = "employeeName")
    @Mapping(target = "user", source = "user")
    EmployeeResponseDTO employeeToEmployeeResponse(Employee employee);

    @Mapping(target = "employeeName", source = "employeeName")
    @Mapping(target = "user", ignore = true)
    Employee employeeRequestToEmployee(EmployeeRequestDTO employeeDTO);

    @AfterMapping
    default void linkUser(EmployeeRequestDTO employeeDTO, @MappingTarget Employee employee, @Context UserRepository userRepository) {
        // Load real User
        User user = userRepository.findById(employeeDTO.getUserId()).orElseThrow(()->new RuntimeException("User not found: "+employeeDTO.getUserId()));
        employee.setUser(user);
    }
}
