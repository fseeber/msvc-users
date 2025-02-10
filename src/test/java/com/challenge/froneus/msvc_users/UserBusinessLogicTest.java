package com.challenge.froneus.msvc_users;

import com.challenge.froneus.msvc_users.entities.User;
import com.challenge.froneus.msvc_users.exception.ValidationException;
import com.challenge.froneus.msvc_users.repositories.UserRepository;
import com.challenge.froneus.msvc_users.services.UserServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

public class UserBusinessLogicTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFirstNameWithNumbers() {
        User user = new User(1L, "John123", "Doe", "john.doe@example.com", "password123", LocalDate.now());

        assertThrows(ValidationException.class, () -> {
            userService.createUser(user);
        });
    }

    @Test
    void testSecondNameWithNumbers() {
        User user = new User(1L, "John", "Doe123", "john.doe@example.com", "password123", LocalDate.now());

        assertThrows(ValidationException.class, () -> {
            userService.createUser(user);
        });
    }

    @Test
    void testEmailInvalidFormat() {
        User user = new User(1L, "John", "Doe", "john.doeatexample.com", "password123", LocalDate.now());

        assertThrows(ValidationException.class, () -> {
            userService.createUser(user);
        });
    }

    @Test
    void testValidUser() {
        User user = new User(1L, "John", "Doe", "john.doe@example.com", "password123", LocalDate.now());

        assertDoesNotThrow(() -> {
            userService.createUser(user);
        });
    }
}
