package com.challenge.froneus.msvc_users.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.challenge.froneus.msvc_users.entities.User;
import com.challenge.froneus.msvc_users.exception.ResourceNotFoundException;
import com.challenge.froneus.msvc_users.services.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        logger.info("Intentando crear un nuevo usuario con los datos: {}", user);
        User createdUser = userService.createUser(user);
        logger.info("Usuario creado exitosamente con ID: {}", createdUser.getId());
        return ResponseEntity.status(201).body(createdUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        logger.info("Buscando usuario con ID: {}", id);
        Optional<User> user = userService.getUserById(id);
        
        if (user.isPresent()) {
            logger.debug("Usuario encontrado: {}", user.get());
            return ResponseEntity.ok(user.get());
        } else {
            logger.warn("No se encontró el usuario con ID: {}", id);
            throw new ResourceNotFoundException("Usuario con ID " + id + " no encontrado");
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        logger.info("Obteniendo lista de todos los usuarios");
        List<User> users = userService.getAllUsers();
        logger.debug("Cantidad total de usuarios encontrados: {}", users.size());
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        logger.info("Actualizando usuario con ID: {}", id);
        Optional<User> existingUser = userService.getUserById(id);
        if (existingUser.isEmpty()) {
            logger.warn("Usuario con ID {} no encontrado para actualización", id);
            throw new ResourceNotFoundException("Usuario con ID " + id + " no encontrado");
        }
        user.setId(id);
        User updatedUser = userService.updateUser(id, user);
        logger.info("Usuario con ID {} actualizado exitosamente", id);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        logger.info("Intentando eliminar usuario con ID: {}", id);
        if (!userService.existsById(id)) {
            logger.warn("Usuario con ID {} no encontrado para eliminación", id);
            throw new ResourceNotFoundException("Usuario con ID " + id + " no encontrado");
        }
        userService.deleteUser(id);
        logger.info("Usuario con ID {} eliminado exitosamente", id);
        return ResponseEntity.noContent().build();
    }
}