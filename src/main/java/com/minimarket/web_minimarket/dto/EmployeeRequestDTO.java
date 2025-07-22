package com.minimarket.web_minimarket.dto;

public class EmployeeRequestDTO {
    private String employeeName;
    private UserRequestDTO user;

    // Contructors
    public EmployeeRequestDTO() {}

    public EmployeeRequestDTO(String employeeName, UserRequestDTO user) {
        this.employeeName = employeeName;
        this.user = user;
    }

    // Getters, setters
    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public UserRequestDTO getUser() {
        return user;
    }

    public void setUser(UserRequestDTO user) {
        this.user = user;
    }
}
