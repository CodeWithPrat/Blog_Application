package com.blog.controllers;

// Importing necessary Spring and application-specific classes
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.blog.payload.ApiResponse;
import com.blog.payload.CommentDTO;
import com.blog.services.CommentService;

// Marks this class as a REST controller
@RestController
// Base URL for all endpoints in this controller
@RequestMapping("/api/")
public class CommentController {

    // Autowiring (injecting) the CommentService
    @Autowired
    private CommentService commentService;

    // Endpoint to create a new comment for a specific post
    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(
            @RequestBody CommentDTO comment,
            @PathVariable Integer postId
    ) {
        // Call the service to create the comment
        CommentDTO createdComment = this.commentService.createComment(comment, postId);
        // Return the created comment with CREATED status
        return new ResponseEntity<CommentDTO>(createdComment, HttpStatus.CREATED);
    }

    // Endpoint to delete a comment
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(
            @PathVariable Integer commentId
    ) {
        // Call the service to delete the comment
        this.commentService.deleteComment(commentId);
        // Return a success response
        return new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted successfully!!!", true), HttpStatus.OK);
    }
}