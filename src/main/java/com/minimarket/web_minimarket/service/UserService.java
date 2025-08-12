package com.minimarket.web_minimarket.service;

import com.minimarket.web_minimarket.dto.EmployeeResponseDTO;
import com.minimarket.web_minimarket.dto.UserRequestDTO;
import com.minimarket.web_minimarket.dto.UserResponseDTO;
import com.minimarket.web_minimarket.entity.Employee;
import com.minimarket.web_minimarket.entity.User;
import com.minimarket.web_minimarket.mapper.EmployeeMapper;
import com.minimarket.web_minimarket.mapper.UserMapper;
import com.minimarket.web_minimarket.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    public UserResponseDTO createUser(UserRequestDTO userDTO) {
        User user = userMapper.userRequestToUser(userDTO);
        User savedUser = userRepository.save(user);
        return userMapper.userToUserResponse(savedUser);
    }

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::userToUserResponse).collect(Collectors.toList());
    }

    public List<UserResponseDTO> getUserByRole(String userRole) {
        return userRepository.getByUserRole(userRole).stream().map(userMapper::userToUserResponse).collect(Collectors.toList());
    }

    public UserResponseDTO getUserById(int userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new EntityNotFoundException("User with id: "+userId+" not found"));
        return userMapper.userToUserResponse(user);
    }

    public UserResponseDTO getUserByEmail(String userEmail) {
        User user = userRepository.getByUserEmail(userEmail).orElseThrow(()->new EntityNotFoundException("User with email: "+userEmail+" not found"));
        return userMapper.userToUserResponse(user);
    }

    public UserResponseDTO getUserByUsername(String userName) {
        User user = userRepository.getByUserName(userName).orElseThrow(()->new EntityNotFoundException("User with name: "+userName+" not found"));
        return userMapper.userToUserResponse(user);
    }

    public EmployeeResponseDTO getEmployeeByUserId(int userId) {
        Employee employee = userRepository.getEmployeeByUserId(userId).orElseThrow(()->new EntityNotFoundException("Employee not found"));
        return employeeMapper.employeeToEmployeeResponse(employee);
    }

    public UserResponseDTO updateUserById(int userId, UserRequestDTO userDetail) {
        User existingUser = userRepository.findById(userId).orElseThrow(()->new EntityNotFoundException("User not found"));
        existingUser.setUserEmail(userDetail.getUserEmail());
        existingUser.setPassword(userDetail.getPassword());
        User updatedUser = userRepository.save(existingUser);
        return userMapper.userToUserResponse(updatedUser);
    }

    public void deleteUser(int userId) {
        userRepository.deleteById(userId);
    }
}
