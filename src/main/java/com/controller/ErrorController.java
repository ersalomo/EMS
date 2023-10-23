package com.controller;


import com.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Response<String>> constraintViolationException (ConstraintViolationException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Response.<String>builder()
                        .errors("Invalid")
                        .build());


    }

    @ExceptionHandler
    public ResponseEntity<Response<String>> apiException(ResponseStatusException e) {
        return ResponseEntity.status(e.getStatus())
                .body(Response.<String>builder()
                        .errors("Invalid")
                        .build());

    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(
            MethodArgumentNotValidException ex
    ) {
        Map<String, Object> res = new HashMap<>();
        Map<String, Object> errors = new HashMap<>();
        res.put("status", "fail");
        ex.getBindingResult().getAllErrors().forEach(err -> {
            String field = ((FieldError) err).getField();
            errors.put(field, err.getDefaultMessage());
        });
        res.put("errors", errors );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }
}
