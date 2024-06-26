package com.blog.services;

import java.util.List; // Represents an ordered collection (also known as a sequence)

import com.blog.payload.PostDTO; // DTO (Data Transfer Object) for post data
import com.blog.payload.PostResponse; // Response object containing posts
import com.blog.repositories.PostRepo; // Repository interface for Post entity

public interface PostService {
    
    // Create a new post
    PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId);
    
    // Update an existing post
    PostDTO updatePost(PostDTO postDTO, Integer postId);
    
    // Delete a post by its ID
    void deletePost(Integer postId);
    
    // Retrieve all posts with pagination and sorting
    PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortby, String sortDirs);
    
    // Retrieve a single post by its ID
    PostDTO getPostById(Integer postId);
    
    // Retrieve all posts belonging to a specific category
    List<PostDTO> getPostsByCategory(Integer categoryId);
    
    // Retrieve all posts created by a specific user
    List<PostDTO> getPostsByUser(Integer userId);
    
    // Search posts based on a keyword
    List<PostDTO> searchPosts(String keyword);
}
