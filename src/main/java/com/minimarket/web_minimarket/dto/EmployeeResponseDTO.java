package com.minimarket.web_minimarket.dto;

public class EmployeeResponseDTO {
    private int employeeId;
    private String employeeName;
    private UserResponseDTO user;

    // Constructors
    public EmployeeResponseDTO() {}

    public EmployeeResponseDTO(int employeeId, String employeeName, UserResponseDTO user) {
        this.employeeId = employeeId;
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

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public UserResponseDTO getUser() {
        return user;
    }

    public void setUser(UserResponseDTO user) {
        this.user = user;
    }
}
