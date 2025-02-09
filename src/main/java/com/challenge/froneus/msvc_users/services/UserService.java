package com.challenge.froneus.msvc_users.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.challenge.froneus.msvc_users.entities.User;
import com.challenge.froneus.msvc_users.exception.ResourceNotFoundException;
import com.challenge.froneus.msvc_users.exception.ValidationException;
import com.challenge.froneus.msvc_users.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        validateUser(user);
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id)
                             .or(() -> {
                                throw new ResourceNotFoundException("El usuario con ID "+id+" no existe");
                             });
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(Long id, User user) {
        if(!userRepository.existsById(id)){
            throw new ResourceNotFoundException("El usuario con ID "+id+" no existe");
        }
        validateUser(user);
        user.setId(id);
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
     private void validateUser(User user) {
        if (user.getFirstName() == null || user.getSecondName().trim().isEmpty()) {
            throw new ValidationException("El nombre y apellido del usuario no pueden estar vacíos.");
        }
        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            throw new ValidationException("El email proporcionado no es válido.");
        }
    } 

    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }
}
