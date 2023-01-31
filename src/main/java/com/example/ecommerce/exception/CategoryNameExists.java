package com.example.ecommerce.exception;

public class CategoryNameExists extends RuntimeException {
    public CategoryNameExists(String msg) {
        super(msg);
    }
}
