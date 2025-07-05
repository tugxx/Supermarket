package com.minimarket.web_minimarket.controller;

import com.minimarket.web_minimarket.entity.User;
import com.minimarket.web_minimarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") int userId) {
        User user =  userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/email")
    public ResponseEntity<User> getUserByEmail(@RequestParam String userEmail) {
        User user = userService.getUserByEmail(userEmail);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/search")
    public ResponseEntity<User> getUserByName(@RequestParam String userName) {
        User user = userService.getUserByUsername(userName);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable("userId") int userId, @RequestBody User userDetail) {
        User updatedUser = userService.updateUserById(userId, userDetail);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") int userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
