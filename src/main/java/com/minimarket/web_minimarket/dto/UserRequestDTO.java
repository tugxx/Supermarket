package com.minimarket.web_minimarket.dto;

public class UserRequestDTO {
    private String userName;
    private String userEmail;
    private String password;
    private String role;

    // Constructors
    public UserRequestDTO() {}

    public UserRequestDTO(String password, String role, String userEmail, String userName) {
        this.password = password;
        this.role = role;
        this.userEmail = userEmail;
        this.userName = userName;
    }

    // Getters, setters
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
