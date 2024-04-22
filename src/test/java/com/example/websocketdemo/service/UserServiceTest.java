package com.example.websocketdemo.service;

import com.example.websocketdemo.entity.User;
import com.example.websocketdemo.repository.IUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void testRegisterUser() {
        // Arrange
        String username = "testuser";
        String password = "password";
        User user = new User();
        user.setUsername(username);
        user.setPassword("encodedPassword");

        // Mocking behavior of userRepository.save()
        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        User savedUser = userService.registerUser(username, password);

        // Assert
        assertNotNull(savedUser);
        assertEquals(username, savedUser.getUsername());
        assertEquals("encodedPassword", savedUser.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testFindByUsername() {
        // Arrange
        String username = "testuser";
        User user = new User();
        user.setUsername(username);

        // Mocking behavior of userRepository.findByUsername()
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // Act
        Optional<User> foundUser = userService.findByUsername(username);

        // Assert
        assertTrue(foundUser.isPresent());
        assertEquals(username, foundUser.get().getUsername());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void testFindByUsername_NotFound() {
        // Arrange
        String username = "nonexistentuser";

        // Mocking behavior of userRepository.findByUsername()
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act
        Optional<User> foundUser = userService.findByUsername(username);

        // Assert
        assertTrue(foundUser.isEmpty());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void testAuthenticate() {
        // Arrange
        String username = "testuser";
        String password = "password";
        String encodedPassword = "encodedPassword";
        User user = new User();
        user.setUsername(username);
        user.setPassword(encodedPassword);

        // Mocking behavior of userRepository.findByUsername()
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);

        // Act
        boolean isAuthenticated = userService.authenticate(username, password);

        // Assert
        assertTrue(isAuthenticated);
        verify(userRepository, times(1)).findByUsername(username);
        verify(passwordEncoder, times(1)).matches(password, encodedPassword);
    }

    @Test
    void testAuthenticate_UserNotFound() {
        // Arrange
        String username = "nonexistentuser";

        // Mocking behavior of userRepository.findByUsername()
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act
        boolean isAuthenticated = userService.authenticate(username, "password");

        // Assert
        assertFalse(isAuthenticated);
        verify(userRepository, times(1)).findByUsername(username);
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }

    @Test
    void testAuthenticate_WrongPassword() {
        // Arrange
        String username = "testuser";
        String password = "password";
        String encodedPassword = "encodedPassword";
        User user = new User();
        user.setUsername(username);
        user.setPassword(encodedPassword);

        // Mocking behavior of userRepository.findByUsername()
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(false);

        // Act
        boolean isAuthenticated = userService.authenticate(username, password);

        // Assert
        assertFalse(isAuthenticated);
        verify(userRepository, times(1)).findByUsername(username);
        verify(passwordEncoder, times(1)).matches(password, encodedPassword);
    }
}
