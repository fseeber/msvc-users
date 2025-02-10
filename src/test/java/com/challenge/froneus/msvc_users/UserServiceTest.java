package com.challenge.froneus.msvc_users;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import com.challenge.froneus.msvc_users.entities.User;
import com.challenge.froneus.msvc_users.exception.ResourceNotFoundException;
import com.challenge.froneus.msvc_users.repositories.UserRepository;
import com.challenge.froneus.msvc_users.services.UserService;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository);
    }

    @Test
    public void testUpdateUser_Success() {
        User existingUser = new User(1L, "John", "Doe", "john.doe@example.com", "password123", LocalDate.now());

        User updatedUser = new User(1L, "John", "Doe", "john.newemail@example.com", "newpassword123", LocalDate.now());

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        
        when(userRepository.existsById(1L)).thenReturn(true);
        
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.updateUser(1L, updatedUser);

        assertEquals("john.newemail@example.com", result.getEmail());
        assertEquals("newpassword123", result.getPassword());

        verify(userRepository, times(1)).save(updatedUser);
    }



    @Test
    public void testUpdateUser_NotFound() {
        User user = new User(1L, "John", "Doe", "john.doe@example.com", "password123", LocalDate.now());

        when(userRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> userService.updateUser(1L, user));
    }
    
    @Test
    public void testDeleteUser_Success() {
        when(userRepository.existsById(1L)).thenReturn(true);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteUser_NotFound() {
        when(userRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(1L));
    }
}
