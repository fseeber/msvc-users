package com.challenge.froneus.msvc_users.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.challenge.froneus.msvc_users.entities.User;
import com.challenge.froneus.msvc_users.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(String id, User user) {
        user.setId(id);
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
