package com.minimarket.web_minimarket.controller;

import com.minimarket.web_minimarket.dto.EmployeeResponseDTO;
import com.minimarket.web_minimarket.dto.UserRequestDTO;
import com.minimarket.web_minimarket.dto.UserResponseDTO;
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
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequest) {
        UserResponseDTO createdUser = userService.createUser(userRequest);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/role")
    public ResponseEntity<List<UserResponseDTO>> getUserByRole(@RequestParam String userRole) {
        List<UserResponseDTO> users = userService.getUserByRole(userRole);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable("userId") int userId) {
        UserResponseDTO user =  userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/email")
    public ResponseEntity<UserResponseDTO> getUserByEmail(@RequestParam String userEmail) {
        UserResponseDTO user = userService.getUserByEmail(userEmail);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/search")
    public ResponseEntity<UserResponseDTO> getUserByName(@RequestParam String userName) {
        UserResponseDTO user = userService.getUserByUsername(userName);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{userId}/employee")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeByUserId(@PathVariable("userId") int userId) {
        EmployeeResponseDTO employee = userService.getEmployeeByUserId(userId);
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> updateUserById(@PathVariable("userId") int userId, @RequestBody UserRequestDTO userDetail) {
        UserResponseDTO updatedUser = userService.updateUserById(userId, userDetail);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") int userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
