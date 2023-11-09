package com.controller;


import com.response.BadRequestResponse;
import com.response.NotFoundResponse;
import com.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<BadRequestResponse> constraintViolationException (ConstraintViolationException e) {
        log.error("[Error] {} ", e.getMessage());
        Map<String, String> errors = new HashMap<>();
        e.getConstraintViolations().forEach(
                err -> errors.put(err.getPropertyPath().toString(), err.getMessage())
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(BadRequestResponse.<String>builder()
                        .errors(errors)
                        .build());


    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<NotFoundResponse> apiException(ResponseStatusException e) {
        log.error("Error {} {}", e.getReason() , e.getMessage());
        return ResponseEntity.status(e.getStatus()).body(
                NotFoundResponse.builder()
                                .message(e.getReason())
                                .statusCode(e.getStatus().value()
                                ).build()
                );

    }
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<?> handleValidation(
//            MethodArgumentNotValidException ex
//    ) {
//        log.error(ex.getMessage());
//        Map<String, Object> res = new HashMap<>();
//        Map<String, Object> errors = new HashMap<>();
//        res.put("status", "fail");
//        ex.getBindingResult().getAllErrors().forEach(err -> {
//            String field = ((FieldError) err).getField();
//            errors.put(field, err.getDefaultMessage());
//        });
//        res.put("errors", errors );
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
//    }
}
