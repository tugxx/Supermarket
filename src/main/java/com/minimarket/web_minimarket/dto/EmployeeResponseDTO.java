package com.minimarket.web_minimarket.dto;

public class EmployeeResponseDTO {
    private int id;
    private String name;
    private UserResponseDTO user;

    // Constructors
    public EmployeeResponseDTO() {}

    public EmployeeResponseDTO(int employeeId, String name, UserResponseDTO user) {
        this.id = employeeId;
        this.name = name;
        this.user = user;
    }

    // Getters, setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserResponseDTO getUser() {
        return user;
    }

    public void setUser(UserResponseDTO user) {
        this.user = user;
    }
}
