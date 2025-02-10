package com.challenge.froneus.msvc_users.exception;

public class ResourceNotFoundException extends AppException {
    public ResourceNotFoundException(String message) {
        super(message, 404); // HTTP 404 Not Found
    }
}
