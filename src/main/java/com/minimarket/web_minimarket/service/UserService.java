package com.minimarket.web_minimarket.service;

import com.minimarket.web_minimarket.entity.User;
import com.minimarket.web_minimarket.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(long userId) {
        return userRepository.findById(userId).orElseThrow(()->new EntityNotFoundException("User with id: "+userId+" not found"));
    }

    public User getUserByEmail(String userEmail) {
        return userRepository.getByUserEmail(userEmail).orElseThrow(()->new EntityNotFoundException("User with email: "+userEmail+" not found"));
    }

    public User getUserByUsername(String userName) {
        return userRepository.getByUserName(userName).orElseThrow(()->new EntityNotFoundException("User with name: "+userName+" not found"));
    }

    public User updateUser(User user) {
        User updatedUser = userRepository.save(user);
        updatedUser.setPassword(user.getPassword());
        return userRepository.save(updatedUser);
    }

    public User updateUserById(int userId, User userDetail) {
        User user = getUserById(userId);
        user.setUserEmail(userDetail.getUserEmail());
        return userRepository.save(user);
    }

    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
    }
}
