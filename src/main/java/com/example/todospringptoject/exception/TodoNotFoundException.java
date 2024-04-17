package com.example.todospringptoject.exception;

public class TodoNotFoundException extends Exception {
    public TodoNotFoundException(String message) {
        super(message);
    }
}
