package com.example.udemy.exceptions;

public class DataUploadException extends RuntimeException {
    public DataUploadException(final String message) {
        super(message);
    }
}
