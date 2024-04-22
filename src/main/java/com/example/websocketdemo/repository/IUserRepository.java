package com.example.websocketdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.websocketdemo.entity.User;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}