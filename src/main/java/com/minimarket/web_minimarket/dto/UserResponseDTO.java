package com.minimarket.web_minimarket.dto;

public class UserResponseDTO {
    private int userId;
    private String userName;
    private String userEmail;
    private String role;

    // Constructors
    public UserResponseDTO() {}

    public UserResponseDTO(String role, String userEmail, int userId, String userName) {
        this.role = role;
        this.userEmail = userEmail;
        this.userId = userId;
        this.userName = userName;
    }

    // Getters, no setters
    public String getRole() {
        return role;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
