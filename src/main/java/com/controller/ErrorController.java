package com.controller;


import com.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Response<String>> constraintViolationException (ConstraintViolationException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Response.<String>builder()
                        .errors(exception.getMessage())
                        .build());


    }

    @ExceptionHandler
    public ResponseEntity<Response<String>> apiException(ResponseStatusException e) {
        return ResponseEntity.status(e.getStatus())
                .body(Response.<String>builder()
                        .errors(e.getReason())
                        .build());

    }
}
