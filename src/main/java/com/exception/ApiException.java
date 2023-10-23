package com.exception;


public class ApiException extends RuntimeException {
    ApiException(String msg) {
        super(msg);
    }
}
