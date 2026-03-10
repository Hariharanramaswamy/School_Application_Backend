package com.example.School_Application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * NEW FILE — GlobalExceptionHandler
 *
 * Without this, any RuntimeException thrown in the service returns a
 * generic 500 Internal Server Error to the frontend.
 *
 * This handler converts exceptions into proper HTTP responses:
 *   - RuntimeException  → 400 Bad Request  (e.g. "Email Already Exists")
 *   - Validation errors → 400 Bad Request  (e.g. "Email is required")
 *   - Any other error   → 500 Internal Server Error
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles RuntimeExceptions from UserService
     * (duplicate email, invalid credentials, etc.)
     * Returns 400 Bad Request with the error message as plain text.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    /**
     * Handles @Valid annotation failures from DTOs
     * (e.g. blank email, password too short)
     * Collects all validation messages into a single string.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fe -> fe.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }

    /**
     * Catch-all for any unexpected errors
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred. Please try again later.");
    }
}