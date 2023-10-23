package com.response;

import org.springframework.http.HttpStatus;

public abstract class ApiResponse {
    protected HttpStatus statusCode;
    protected String message;

     ApiResponse(HttpStatus status, String message) {
        this.statusCode = status;
        this.message = message;
    }

}
