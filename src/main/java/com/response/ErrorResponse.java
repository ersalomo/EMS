package com.response;

import java.util.Map;

public class ErrorResponse extends RuntimeException {
    private Map<String, String> errors;
    private String status;

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

