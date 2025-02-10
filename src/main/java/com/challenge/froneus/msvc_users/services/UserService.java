package com.challenge.froneus.msvc_users.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.challenge.froneus.msvc_users.entities.User;
import com.challenge.froneus.msvc_users.exception.ResourceNotFoundException;
import com.challenge.froneus.msvc_users.exception.ValidationException;
import com.challenge.froneus.msvc_users.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        try {
            logger.info("Guardando un nuevo usuario: {}", user);
            validateUser(user);
            return userRepository.save(user);
        } catch (Exception e) {
            logger.error("Error al crear el usuario: ", e);
            throw new ValidationException("Error al crear el usuario");
        }
    }

    public Optional<User> getUserById(Long id) {
        logger.info("Buscando usuario por ID: {}", id);
        return userRepository.findById(id)
                             .or(() -> {
                                throw new ResourceNotFoundException("El usuario con ID "+id+" no existe");
                             });
    }

    public List<User> getAllUsers() {
        logger.debug("Obteniendo todos los usuarios de la base de datos");
        return userRepository.findAll();
    }

    public User updateUser(Long id, User user) {
        logger.info("Actualizando usuario con ID: {}", id);
        if(!userRepository.existsById(id)){
            throw new ResourceNotFoundException("El usuario con ID "+id+" no existe");
        }
        validateUser(user);
        user.setId(id);
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        logger.info("Eliminando usuario con ID: {}", id);
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("El usuario con ID " + id + " no existe.");
        }
        userRepository.deleteById(id);
    }
    private void validateUser(User user) {
        if (user.getFirstName() == null || user.getFirstName().trim().isEmpty()) {
            logger.warn("Validación fallida para el usuario: {} - El nombre está vacío.", user);
            throw new ValidationException("El nombre del usuario no puede estar vacío.");
        }
        if (user.getFirstName().matches(".*\\d.*")) {  
            logger.warn("Validación fallida para el usuario: {} - El nombre no puede contener números.", user);
            throw new ValidationException("El primer nombre no puede contener números.");
        }
    
        if (user.getSecondName() == null || user.getSecondName().trim().isEmpty()) {
            logger.warn("Validación fallida para el usuario: {} - El apellido está vacío.", user);
            throw new ValidationException("El apellido del usuario no puede estar vacío.");
        }
        if (user.getSecondName().matches(".*\\d.*")) {  
            logger.warn("Validación fallida para el usuario: {} - El apellido no puede contener números.", user);
            throw new ValidationException("El apellido no puede contener números.");
        }
    
        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            logger.warn("Validación fallida para el usuario: {} - Email inválido.", user);
            throw new ValidationException("El email proporcionado no es válido.");
        }
        if (!user.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) { 
            logger.warn("Validación fallida para el usuario: {} - Email con formato incorrecto.", user);
            throw new ValidationException("El email proporcionado no tiene un formato válido.");
        }
    }

    public boolean existsById(Long id) {
        logger.debug("Verificando si existe el usuario con ID: {}", id);
        return userRepository.existsById(id);
    }
}
