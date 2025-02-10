package com.challenge.froneus.msvc_users.services;

import java.util.List;
import java.util.Optional;

import com.challenge.froneus.msvc_users.entities.User;

public interface UserSerivce {
    User createUser(User user);
    Optional<User> getUserById(Long id);
    List<User> getAllUsers();
    User updateUser(Long id, User user);
    void deleteUser(Long id);
    boolean existsById(Long id);
}
