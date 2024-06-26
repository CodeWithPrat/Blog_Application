package com.blog.security;

import org.springframework.beans.factory.annotation.Autowired; // Annotation for automatic dependency injection
import org.springframework.security.core.userdetails.UserDetails; // Core interface which loads user-specific data
import org.springframework.security.core.userdetails.UserDetailsService; // Service for retrieving user-related data
import org.springframework.security.core.userdetails.UsernameNotFoundException; // Exception thrown if user is not found
import org.springframework.stereotype.Service; // Annotation to indicate that this class is a service

import com.blog.entities.User; // Entity representing the user
import com.blog.exceptions.ResourceNotFoundException; // Custom exception for resource not found
import com.blog.repositories.UserRepo; // Repository interface for User entity

@Service // Indicates that this class is a service and a Spring component
public class CustomUserDetailService implements UserDetailsService {

    @Autowired // Automatically injects the UserRepo dependency
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Load user from database by username (email)
        User user = this.userRepo.findByEmail(username)
            .orElseThrow(() -> new ResourceNotFoundException("User", "email : " + username, 0));
        
        return user; // Return the user details
    }
}
