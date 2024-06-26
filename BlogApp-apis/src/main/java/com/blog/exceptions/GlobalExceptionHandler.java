package com.blog.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus; // Represents HTTP status codes
import org.springframework.http.ResponseEntity; // Represents the entire HTTP response
import org.springframework.validation.FieldError; // Encapsulates an error on a specific field
import org.springframework.web.bind.MethodArgumentNotValidException; // Exception thrown when method argument validation fails
import org.springframework.web.bind.annotation.ExceptionHandler; // Annotation for handling exceptions
import org.springframework.web.bind.annotation.RestControllerAdvice; // Annotation for global exception handling in Spring

import com.blog.payload.ApiResponse; // Custom response structure for API responses

@RestControllerAdvice // Annotation to handle exceptions globally across all controllers
public class GlobalExceptionHandler {

    // Handles ResourceNotFoundException and returns a custom API response with HTTP status 404
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
        String message = ex.getMessage(); // Get the exception message
        ApiResponse apiResponse = new ApiResponse(message, false); // Create a custom API response
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND); // Return response with 404 status
    }

    // Handles MethodArgumentNotValidException and returns validation errors with HTTP status 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgsNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> resp = new HashMap<>(); // Map to store field errors
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField(); // Get the field name with error
            String message = error.getDefaultMessage(); // Get the error message
            resp.put(fieldName, message); // Add field name and message to the map
        });
        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST); // Return response with 400 status
    }
    
    // Handles ApiException and returns a custom API response with HTTP status 400
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse> ApiExceptionHandler(ApiException ex) {
        String message = ex.getMessage(); // Get the exception message
        ApiResponse apiResponse = new ApiResponse(message, true); // Create a custom API response
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST); // Return response with 400 status
    }
}
