package com.minimarket.web_minimarket.controller;

import com.minimarket.web_minimarket.dto.EmployeeRequestDTO;
import com.minimarket.web_minimarket.dto.EmployeeResponseDTO;
import com.minimarket.web_minimarket.dto.UserResponseDTO;
import com.minimarket.web_minimarket.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> createEmployee(@RequestBody EmployeeRequestDTO employeeRequest) {
        EmployeeResponseDTO createdEmployee = employeeService.createEmployee(employeeRequest);
        return ResponseEntity.ok(createdEmployee);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
        List<EmployeeResponseDTO> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable("employeeId") int employeeId) {
        EmployeeResponseDTO employee = employeeService.getEmployeeById(employeeId);
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/search")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeByName(@RequestParam String employeeName) {
        EmployeeResponseDTO employee = employeeService.getEmployeeByName(employeeName);
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/userId")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeByUserId(@RequestParam int userId) {
        EmployeeResponseDTO employee = employeeService.getEmployeeByUserId(userId);
        return ResponseEntity.ok(employee);
    }

    @GetMapping("{employeeId}/user")
    public ResponseEntity<UserResponseDTO> getUserByEmployeeId(@PathVariable("employeeId") int employeeId) {
        UserResponseDTO user = employeeService.getUserByEmployeeId(employeeId);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(@PathVariable("employeeId") int employeeId, @RequestBody EmployeeRequestDTO employeeDetail) {
        EmployeeResponseDTO updatedEmployee = employeeService.updateEmployee(employeeId, employeeDetail);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable("employeeId") int employeeId) {
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.noContent().build();
    }
}
