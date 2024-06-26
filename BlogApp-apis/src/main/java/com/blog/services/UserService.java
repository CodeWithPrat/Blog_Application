package com.blog.services;

import java.util.List; // Represents an ordered collection (also known as a sequence)
import com.blog.payload.UserDTO; // DTO (Data Transfer Object) for user data

public interface UserService {
    
    // Register a new user
    UserDTO registerNewUser(UserDTO user);
    
    // Create a new user
    UserDTO createUser(UserDTO user);
    
    // Update an existing user by their ID
    UserDTO updateUser(UserDTO user, Integer userId);
    
    // Retrieve a user by their ID
    UserDTO getUserById(Integer userId);
    
    // Retrieve all users
    List<UserDTO> getAllUser();
    
    // Delete a user by their ID
    void deleteUser(Integer userId);
}
