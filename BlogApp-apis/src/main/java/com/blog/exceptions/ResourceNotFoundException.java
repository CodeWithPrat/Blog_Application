package com.blog.exceptions;

import lombok.Getter; // Lombok annotation to generate getters for all fields
import lombok.Setter; // Lombok annotation to generate setters for all fields

@Getter // Lombok annotation to generate getters for all fields
@Setter // Lombok annotation to generate setters for all fields
public class ResourceNotFoundException extends RuntimeException { // Custom exception extending RuntimeException
    private String resourceName; // Name of the resource that was not found
    private String fieldName; // Name of the field used in the search
    private long fieldvalue; // Value of the field used in the search

    // Constructor to initialize the exception with details about the resource, field, and value
    public ResourceNotFoundException(String resourceName, String fieldName, long fieldvalue) {
        super(String.format("%s not found with %s: %s", resourceName, fieldName, fieldvalue)); // Message passed to the RuntimeException constructor
        this.resourceName = resourceName; // Initialize the resource name
        this.fieldName = fieldName; // Initialize the field name
        this.fieldvalue = fieldvalue; // Initialize the field value
    }
}
