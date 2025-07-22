package com.minimarket.web_minimarket.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private int employeeId;

    @Column(name = "employee_name")
    private String employeeName;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Constructors
    public Employee() {}

    public Employee(int employeeId, String employeeName, User user) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.user = user;
    }

    public Employee(String employeeName, User user) {
        this.employeeName = employeeName;
        this.user = user;
    }

    // Getters, setters
    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
