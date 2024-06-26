package com.blog.controllers;

// Importing necessary Java and Spring classes
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.blog.payload.ApiResponse;
import com.blog.payload.UserDTO;
import com.blog.services.UserService;
import jakarta.validation.Valid;

// Marks this class as a REST controller
@RestController
// Base URL for all endpoints in this controller
@RequestMapping("/api/users")
public class UserController {

    // Autowiring (injecting) the UserService
    @Autowired
    private UserService userService;

    // Create a new user
    // The @Valid annotation is used on the createUser and updateUser methods to ensure that the incoming UserDTO object passes validation checks.
    @PostMapping("/")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDto) {
        UserDTO createdUser = userService.createUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // Update an existing user
    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDto, @PathVariable Integer userId) {
        UserDTO updatedUser = userService.updateUser(userDto, userId);
        return ResponseEntity.ok(updatedUser);
    }

    // Delete a user (only accessible to admins)
    // The @PreAuthorize("hasRole('ADMIN')") annotation on the deleteUser method ensures that only users with the ADMIN role can delete users.
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(new ApiResponse("User deleted successfully", true), HttpStatus.OK);
    }

    // Get all users
    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUser());
    }

    // Get a single user by ID
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getSingleUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }
}