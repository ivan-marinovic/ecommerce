package com.example.ecommerce.exception;

public class CartItemNotFoundException extends IllegalArgumentException {
    public CartItemNotFoundException(String msg) {
        super(msg);
    }
}
