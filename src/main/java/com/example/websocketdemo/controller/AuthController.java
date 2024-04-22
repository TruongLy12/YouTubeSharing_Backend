package com.example.websocketdemo.controller;

import com.example.websocketdemo.dto.RegisterDto;
import com.example.websocketdemo.dto.LoginDto;
import com.example.websocketdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDto registerDto) {
        if (userService.findByUsername(registerDto.getUsername()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already taken!");
        }
        userService.registerUser(registerDto.getUsername(), registerDto.getPassword());

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDto loginDto) {
        if (userService.authenticate(loginDto.getUsername(), loginDto.getPassword())) {
            return ResponseEntity.ok(("Login successful"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}

