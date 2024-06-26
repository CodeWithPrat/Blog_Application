package com.blog.security;

import java.io.IOException; // Exception thrown when an I/O operation fails or is interrupted

import org.springframework.beans.factory.annotation.Autowired; // Annotation for automatic dependency injection
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; // Represents an authentication request using a username and password
import org.springframework.security.core.context.SecurityContextHolder; // Holds the security context, which contains authentication and security information
import org.springframework.security.core.userdetails.UserDetails; // Represents the user details
import org.springframework.security.core.userdetails.UserDetailsService; // Service for retrieving user-related data
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource; // Contains additional details for authentication
import org.springframework.stereotype.Component; // Indicates that an annotated class is a "component"
import org.springframework.web.filter.OncePerRequestFilter; // Filter base class that guarantees a single execution per request dispatch

import io.jsonwebtoken.ExpiredJwtException; // Exception thrown when a JWT token is expired
import io.jsonwebtoken.MalformedJwtException; // Exception thrown when a JWT token is malformed
import io.jsonwebtoken.SignatureException; // Exception thrown when a JWT signature does not match locally computed signature
import jakarta.servlet.FilterChain; // Used to pass a request from one filter to the next in the chain
import jakarta.servlet.ServletException; // Exception thrown when a servlet encounters difficulty
import jakarta.servlet.http.HttpServletRequest; // Represents an HTTP request
import jakarta.servlet.http.HttpServletResponse; // Represents an HTTP response
import org.slf4j.Logger; // Logger for logging messages
import org.slf4j.LoggerFactory; // Factory for creating loggers

@Component // Indicates that this class is a Spring component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired // Automatically injects the UserDetailsService dependency
    private UserDetailsService userDetailsService;

    @Autowired // Automatically injects the JwtTokenHelper dependency
    private JwtTokenHelper jwtTokenHelper;

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class); // Logger for logging messages

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Get token from the Authorization header
        String requestToken = request.getHeader("Authorization");
        logger.debug("Authorization Header: {}", requestToken);

        String username = null;
        String token = null;

        // Check if the token is present and starts with "Bearer "
        if (requestToken != null && requestToken.startsWith("Bearer ")) {
            token = requestToken.substring(7);
            try {
                // Extract username from the token
                username = jwtTokenHelper.getUsernameFromToken(token);
            } 
            catch (IllegalArgumentException e) 
            {
                logger.error("Unable to get JWT Token", e);
            } 
            catch (ExpiredJwtException e) 
            {
                logger.warn("JWT Token has expired", e);
            } 
            catch (MalformedJwtException e) 
            {
                logger.error("Invalid JWT Token", e);
            } 
            catch (SignatureException e) 
            { // Catching signature exception
                logger.error("JWT signature does not match locally computed signature", e);
            }
        } 
        else 
        {
            logger.warn("JWT token does not begin with Bearer");
        }

        // 2. Once we get the token, validate it
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Validate the token
            if (jwtTokenHelper.validateToken(token, userDetails)) {
                // Create an authentication token
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                // Set authentication details
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the authentication in the security context
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                logger.warn("Invalid JWT token");
            }
        } else {
            logger.debug("Username is null or context is not null");
        }

        // Proceed with the next filter
        filterChain.doFilter(request, response);
    }
}
