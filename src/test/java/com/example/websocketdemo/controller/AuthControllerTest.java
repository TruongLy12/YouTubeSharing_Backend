package com.example.websocketdemo.controller;

import com.example.websocketdemo.dto.RegisterDto;
import com.example.websocketdemo.dto.LoginDto;
import com.example.websocketdemo.entity.User;
import com.example.websocketdemo.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthController authController;

    @Test
    void testRegisterUser_Success() {
        // Arrange
        RegisterDto registerDto = new RegisterDto("testuser", "password");

        // Mock userService.findByUsername to return empty optional, indicating username is available
        when(userService.findByUsername(registerDto.getUsername())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> responseEntity = authController.registerUser(registerDto);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("User registered successfully!", responseEntity.getBody());
        verify(userService, times(1)).registerUser(registerDto.getUsername(), registerDto.getPassword());
    }

    @Test
    void testRegisterUser_Failure_UsernameTaken() {
        // Arrange
        RegisterDto registerDto = new RegisterDto("existinguser", "password");

        // Create a mock user object
        User existingUser = new User();
        existingUser.setUsername(registerDto.getUsername());

        // Mock userService.findByUsername to return non-empty optional, indicating username is taken
        when(userService.findByUsername(registerDto.getUsername())).thenReturn(Optional.of(existingUser));

        // Act
        ResponseEntity<?> responseEntity = authController.registerUser(registerDto);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Error: Email is already taken!", responseEntity.getBody());
        verify(userService, never()).registerUser(anyString(), anyString());
    }

    @Test
    void testLoginUser_Success() {
        // Arrange
        LoginDto loginDto = new LoginDto("testuser", "password");

        // Mock userService.authenticate to return true, indicating successful authentication
        when(userService.authenticate(loginDto.getUsername(), loginDto.getPassword())).thenReturn(true);

        // Act
        ResponseEntity<?> responseEntity = authController.loginUser(loginDto);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Login successful", responseEntity.getBody());
    }

    @Test
    void testLoginUser_Failure_UserNotFound() {
        // Arrange
        LoginDto loginDto = new LoginDto("nonexistentuser", "password");

        // Mock userService.authenticate to return false, indicating user not found
        when(userService.authenticate(loginDto.getUsername(), loginDto.getPassword())).thenReturn(false);

        // Act
        ResponseEntity<?> responseEntity = authController.loginUser(loginDto);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("User not found", responseEntity.getBody());
    }
}

