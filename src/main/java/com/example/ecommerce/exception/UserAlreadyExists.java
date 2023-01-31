package com.example.ecommerce.exception;

public class UserAlreadyExists extends RuntimeException {
    public UserAlreadyExists(String msg) {
        super(msg);
    }
}
