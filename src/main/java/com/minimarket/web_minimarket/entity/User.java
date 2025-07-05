package com.minimarket.web_minimarket.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    private String userEmail;
    private String userName;
    private String password;

    // Constructors
    public User() {}

    public User(int userId, String userEmail, String userName, String password) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userName = userName;
        this.password = password;
    }

    public User(String userEmail, String userName, String password) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.password = password;
    }

    // Getters and setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
