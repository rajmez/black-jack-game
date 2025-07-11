package com.rajmez.blackjackgame.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ApiExceptionHandler {
    // Custom DTO for error response
    public static record ApiError(int status, String error, String message) {}

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiError> handleResponseStatus(ResponseStatusException ex) {
        String reasonPhrase = "";
        if (ex.getStatusCode() instanceof HttpStatus status) {
            reasonPhrase = status.getReasonPhrase();
        } else {
            reasonPhrase = ex.getStatusCode().toString(); // fallback for rare cases
        }

        ApiError body = new ApiError(
            ex.getStatusCode().value(),
            reasonPhrase,
            ex.getReason()
        );
        return ResponseEntity.status(ex.getStatusCode()).body(body);
    }
}
