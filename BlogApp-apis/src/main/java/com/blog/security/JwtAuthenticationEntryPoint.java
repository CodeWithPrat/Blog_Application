package com.blog.security;

import java.io.IOException; // Exception thrown when an I/O operation fails or is interrupted

import org.springframework.security.core.AuthenticationException; // Exception thrown when an authentication request fails
import org.springframework.security.web.AuthenticationEntryPoint; // Used to commence authentication scheme
import org.springframework.stereotype.Component; // Indicates that an annotated class is a "component"

import jakarta.servlet.ServletException; // Exception thrown when a servlet encounters difficulty
import jakarta.servlet.http.HttpServletRequest; // Represents an HTTP request
import jakarta.servlet.http.HttpServletResponse; // Represents an HTTP response

@Component // Indicates that this class is a Spring component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        // Send an error response indicating that the request requires authentication
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied !!");
    }
}
