package com.response;

import org.springframework.http.HttpStatus;

public class NotFoundResponse extends ApiResponse{
    private final HttpStatus code;

    public NotFoundResponse(String message) {
        super(message);
        this.code = HttpStatus.NOT_FOUND;
    }
}
