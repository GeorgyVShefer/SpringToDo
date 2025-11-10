package com.emobile.springtodo.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
public class ErrorResponse {

    private int status;
    private String error;
    private String message;
    private Map<String, String> details;
    private LocalDateTime timestamp = LocalDateTime.now();


    public ErrorResponse(int status, String error, String message, Map<String, String> details) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.details = details;
    }

    public ErrorResponse(int status, String error, String message) {
        this(status, error, message, null);
    }
}
