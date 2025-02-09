package com.challenge.froneus.msvc_users.exception;

public abstract class AppException extends RuntimeException {
    private String message;
    private int statusCode;

    public AppException(String message, int statusCode) {
        super(message);
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
