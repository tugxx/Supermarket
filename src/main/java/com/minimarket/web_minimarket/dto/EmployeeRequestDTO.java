package com.minimarket.web_minimarket.dto;

public class EmployeeRequestDTO {
    private String employeeName;
    private int userId;

    // Constructors
    public EmployeeRequestDTO() {}

    public EmployeeRequestDTO(String employeeName, int userId) {
        this.employeeName = employeeName;
        this.userId = userId;
    }

    // Getters, setters
    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
