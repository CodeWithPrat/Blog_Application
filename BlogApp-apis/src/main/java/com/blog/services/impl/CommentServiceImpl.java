package com.blog.services.impl;

import org.modelmapper.ModelMapper; // Maps objects of one type into objects of another type
import org.springframework.beans.factory.annotation.Autowired; // Annotation for automatic dependency injection
import org.springframework.stereotype.Service; // Indicates that an annotated class is a "service"

import com.blog.entities.Comment; // Entity class representing a comment
import com.blog.entities.Post; // Entity class representing a post
import com.blog.exceptions.ResourceNotFoundException; // Custom exception for resource not found
import com.blog.payload.CommentDTO; // DTO (Data Transfer Object) for comment data
import com.blog.repositories.CommentRepo; // Repository interface for Comment entity
import com.blog.repositories.PostRepo; // Repository interface for Post entity
import com.blog.services.CommentService; // Interface defining comment-related operations

@Service // Indicates that this class is a Spring service
public class CommentServiceImpl implements CommentService {
    
    @Autowired // Automatically injects the PostRepo dependency
    private PostRepo postRepo;
    
    @Autowired // Automatically injects the CommentRepo dependency
    private CommentRepo commentRepo;
    
    @Autowired // Automatically injects the ModelMapper dependency
    private ModelMapper modelMapper;
    
    @Override
    public CommentDTO createComment(CommentDTO commentDTO, Integer postId) {
        // Find the post by ID or throw ResourceNotFoundException
        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Post ID", postId));
        
        // Map CommentDTO to Comment entity
        Comment comment = this.modelMapper.map(commentDTO, Comment.class);
        // Set the post for the comment
        comment.setPost(post);
        
        // Save the comment and get the saved instance
        Comment savedComment = this.commentRepo.save(comment);
        
        // Map the saved entity back to CommentDTO and return
        return this.modelMapper.map(savedComment, CommentDTO.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        // Find the comment by ID or throw ResourceNotFoundException
        Comment comment = this.commentRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "Comment Id", commentId));
        
        // Delete the comment
        this.commentRepo.delete(comment);
    }
}
