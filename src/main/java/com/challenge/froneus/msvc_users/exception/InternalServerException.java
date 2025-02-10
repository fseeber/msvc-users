package com.challenge.froneus.msvc_users.exception;

public class InternalServerException extends AppException {
    public InternalServerException(String message) {
        super(message, 500); // Código HTTP 500 Internal Server Error
    }
}
