package com.challenge.froneus.msvc_users.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.challenge.froneus.msvc_users.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findById(String id);

    void deleteById(String id);

    
} 