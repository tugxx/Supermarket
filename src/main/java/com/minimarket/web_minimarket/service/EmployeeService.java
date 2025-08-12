package com.minimarket.web_minimarket.service;

import com.minimarket.web_minimarket.dto.EmployeeRequestDTO;
import com.minimarket.web_minimarket.dto.EmployeeResponseDTO;
import com.minimarket.web_minimarket.dto.UserResponseDTO;
import com.minimarket.web_minimarket.entity.Employee;
import com.minimarket.web_minimarket.entity.User;
import com.minimarket.web_minimarket.mapper.EmployeeMapper;
import com.minimarket.web_minimarket.mapper.UserMapper;
import com.minimarket.web_minimarket.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private UserMapper userMapper;

    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO employeeDTO) {
        Employee employee = employeeMapper.employeeRequestToEmployee(employeeDTO);
        Employee savedEmployee = employeeRepository.save(employee);
        return employeeMapper.employeeToEmployeeResponse(savedEmployee);
    }

    public List<EmployeeResponseDTO> getAllEmployees() {
        return employeeRepository.findAll().stream().map(employeeMapper::employeeToEmployeeResponse).collect(Collectors.toList());
    }

    public EmployeeResponseDTO getEmployeeById(int employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(()->new EntityNotFoundException("Employee with the id: "+employeeId+" not found"));
        return employeeMapper.employeeToEmployeeResponse(employee);
    }

    public EmployeeResponseDTO getEmployeeByName(String employeeName) {
        Employee employee =  employeeRepository.getByEmployeeName(employeeName).orElseThrow(()->new EntityNotFoundException("Employee with name: "+employeeName+" not found"));
        return employeeMapper.employeeToEmployeeResponse(employee);
    }

    public EmployeeResponseDTO getEmployeeByUserId(int userId) {
        Employee employee = employeeRepository.getByUser_UserId(userId).orElseThrow(()->new EntityNotFoundException("No employee with user id:"+userId));
        return employeeMapper.employeeToEmployeeResponse(employee);
    }

    public UserResponseDTO getUserByEmployeeId(int employeeId) {
        User user = employeeRepository.getByUser_userId(employeeId).orElseThrow(()->new EntityNotFoundException("User not found"));
        return userMapper.userToUserResponse(user);
    }

    public EmployeeResponseDTO updateEmployee(int employeeId, EmployeeRequestDTO employeeDetail) {
        Employee existingEmployee = employeeRepository.findById(employeeId).orElseThrow(()->new EntityNotFoundException("Employee not found"));
        existingEmployee.setEmployeeName(employeeDetail.getEmployeeName());
        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        return employeeMapper.employeeToEmployeeResponse(updatedEmployee);
    }

    public void deleteEmployee(int employeeId) {
        employeeRepository.deleteById(employeeId);
    }
}
