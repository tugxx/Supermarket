package com.minimarket.web_minimarket.dto;

public class EmployeeRequestDTO {
    private String name;
    private UserRequestDTO user;

    // Contructors
    public EmployeeRequestDTO() {}

    public EmployeeRequestDTO(String employeeName, UserRequestDTO user) {
        this.name = employeeName;
        this.user = user;
    }

    // Getters, setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserRequestDTO getUser() {
        return user;
    }

    public void setUser(UserRequestDTO user) {
        this.user = user;
    }
}
