package com.minimarket.web_minimarket.repository;

import com.minimarket.web_minimarket.entity.Employee;
import com.minimarket.web_minimarket.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Optional<Employee> getByEmployeeName(String employeeName);
    Optional<Employee> getByUserId(int userId);
    Optional<User> getByUser_userId(int employeeId);
}
