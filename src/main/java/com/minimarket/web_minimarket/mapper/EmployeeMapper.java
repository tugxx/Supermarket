package com.minimarket.web_minimarket.mapper;

import com.minimarket.web_minimarket.dto.EmployeeRequestDTO;
import com.minimarket.web_minimarket.dto.EmployeeResponseDTO;
import com.minimarket.web_minimarket.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface EmployeeMapper {

    @Mapping(target = "employeeId", source = "employeeId")
    @Mapping(target = "employeeName", source = "employeeName")
    @Mapping(target = "user", source = "user")
    EmployeeResponseDTO employeeToEmployeeResponseDTO(Employee employee);

    @Mapping(target = "employeeName", source = "employeeName")
    @Mapping(target = "user", source = "user")
    Employee employeeRequestDTOToEmployee(EmployeeRequestDTO employeeDTO);
}
