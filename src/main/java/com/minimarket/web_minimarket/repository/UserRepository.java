package com.minimarket.web_minimarket.repository;

import com.minimarket.web_minimarket.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> getByUserName(String userName);
    Optional<User> getByUserEmail(String email);
}
