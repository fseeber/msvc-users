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
        logger.info("Guardando un nuevo usuario: {}", user);
        validateUser(user);
        return userRepository.save(user);
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
        if (user.getSecondName() == null || user.getSecondName().trim().isEmpty()) {
            logger.warn("Validación fallida para el usuario: {} - El apellido está vacío.", user);
            throw new ValidationException("El apellido del usuario no puede estar vacío.");
        }
        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            logger.warn("Validación fallida para el usuario: {} - Email inválido.", user);
            throw new ValidationException("El email proporcionado no es válido.");
        }
    } 

    public boolean existsById(Long id) {
        logger.debug("Verificando si existe el usuario con ID: {}", id);
        return userRepository.existsById(id);
    }
}
