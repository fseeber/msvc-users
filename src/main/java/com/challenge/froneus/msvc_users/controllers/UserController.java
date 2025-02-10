package com.challenge.froneus.msvc_users.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.challenge.froneus.msvc_users.entities.User;
import com.challenge.froneus.msvc_users.exception.ResourceNotFoundException;
import com.challenge.froneus.msvc_users.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

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

    @Operation(summary = "Crear un nuevo usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400", description = "Datos inválidos proporcionados", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            logger.info("Intentando crear un nuevo usuario con los datos: {}", user);
            User createdUser = userService.createUser(user);
            logger.info("Usuario creado exitosamente con ID: {}", createdUser.getId());
            return ResponseEntity.status(201).body(createdUser);
        } catch (Exception e) {
            logger.error("Error al crear el usuario: ", e);
            return ResponseEntity.status(500).body(null);
        }
    }

    @Operation(summary = "Obtener un usuario por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    })
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

    @Operation(summary = "Obtener todos los usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente", content = {
                    @Content(mediaType = "application/json") })
    })
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        logger.info("Obteniendo lista de todos los usuarios");
        List<User> users = userService.getAllUsers();
        logger.debug("Cantidad total de usuarios encontrados: {}", users.size());
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Actualizar un usuario por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    })
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

    @Operation(summary = "Eliminar un usuario por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
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