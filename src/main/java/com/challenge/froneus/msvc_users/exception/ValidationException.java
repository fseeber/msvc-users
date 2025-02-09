package com.challenge.froneus.msvc_users.exception;

public class ValidationException extends AppException {
    public ValidationException(String message) {
        super(message, 400); // HTTP 400 Bad Request
    }
}
