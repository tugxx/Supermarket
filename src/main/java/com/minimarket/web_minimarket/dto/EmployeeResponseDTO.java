package com.minimarket.web_minimarket.dto;

public class EmployeeResponseDTO {
    private int employeeId;
    private String name;
    private UserResponseDTO user;

    // Constructors
    public EmployeeResponseDTO() {}

    public EmployeeResponseDTO(int employeeId, String name, UserResponseDTO user) {
        this.employeeId = employeeId;
        this.name = name;
        this.user = user;
    }

    // Getters, no setters
    public int getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public UserResponseDTO getUser() {
        return user;
    }
}
