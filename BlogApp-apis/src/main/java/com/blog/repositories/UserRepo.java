package com.blog.repositories;

import java.util.Optional; // Represents an optional value that may or may not be present
import org.springframework.data.jpa.repository.JpaRepository; // JPA repository to provide CRUD operations
import com.blog.entities.User; // Entity representing the user
import java.util.List; // Represents an ordered collection (also known as a sequence)

public interface UserRepo extends JpaRepository<User, Integer> { // Extends JpaRepository to provide CRUD operations for User entities
    Optional<User> findByEmail(String email); // Method to find a user by their email address, returning an Optional
}
