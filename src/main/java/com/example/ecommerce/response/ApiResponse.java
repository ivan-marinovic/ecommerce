package com.example.ecommerce.response;

import java.time.LocalDateTime;

public class ApiResponse {
    private final Integer status;
    private final String message;

    public ApiResponse(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return LocalDateTime.now().toString();
    }
}
