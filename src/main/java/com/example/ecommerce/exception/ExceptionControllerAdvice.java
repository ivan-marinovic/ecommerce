package com.example.ecommerce.exception;

import com.example.ecommerce.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(value = CartItemNotFoundException.class)
    public final ResponseEntity<ApiResponse> cartItemNotFoundException(CartItemNotFoundException cartItemNotFoundException) {
        return new ResponseEntity<>(new ApiResponse(0, cartItemNotFoundException.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ProductNotFoundException.class)
    public final ResponseEntity<ApiResponse> handleProductNotExistsException(ProductNotFoundException productNotFoundException) {
        return new ResponseEntity<>(new ApiResponse(0, productNotFoundException.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = CategoryNotFoundException.class)
    public final ResponseEntity<ApiResponse> handleCategoryNotFoundException(CategoryNotFoundException categoryNotFoundException) {
        return new ResponseEntity<>(new ApiResponse(0, categoryNotFoundException.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = CategoryNameExists.class)
    public final ResponseEntity<ApiResponse> handleCategoryNameExists(CategoryNameExists categoryNameExists) {
        return new ResponseEntity<>(new ApiResponse(0, categoryNameExists.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UserAlreadyExists.class)
    public final ResponseEntity<ApiResponse> handleUserAlreadyExists(UserAlreadyExists userAlreadyExists) {
        return new ResponseEntity<>(new ApiResponse(0, userAlreadyExists.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = OrderNotFoundException.class)
    public final ResponseEntity<ApiResponse> handleOrderNotFoundException(OrderNotFoundException orderNotFoundException) {
        return new ResponseEntity<>(new ApiResponse(0, orderNotFoundException.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = OutOfStockException.class)
    public final ResponseEntity<ApiResponse> handleOutOfStockException(OutOfStockException outOfStockException) {
        return new ResponseEntity<>(new ApiResponse(0, outOfStockException.getMessage()), HttpStatus.NOT_FOUND);
    }
}
