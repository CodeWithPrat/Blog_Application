package com.blog.repositories;

import java.util.List; // Represents an ordered collection (also known as a sequence)
import org.springframework.data.jpa.repository.JpaRepository; // JPA repository to provide CRUD operations

import com.blog.entities.Category; // Entity representing the category
import com.blog.entities.Post; // Entity representing the post
import com.blog.entities.User; // Entity representing the user

public interface PostRepo extends JpaRepository<Post, Integer> { // Extends JpaRepository to provide CRUD operations for Post entities
    List<Post> findByUser(User user); // Method to find posts by a specific user

    List<Post> findByCategory(Category category); // Method to find posts by a specific category

    List<Post> findByTitleContaining(String title); // Method to find posts with titles containing a specific keyword
}
