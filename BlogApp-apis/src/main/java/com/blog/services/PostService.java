package com.blog.services;

import java.util.List;



import com.blog.payload.PostDTO;
import com.blog.payload.PostResponse;
import com.blog.repositories.PostRepo;

public interface PostService {
    // create
    PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId);
    
    // update
    PostDTO updatePost(PostDTO postDTO, Integer postId);
    
    // delete
    void deletePost(Integer postId);
    
    // get all posts
    PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortby, String sortDirs);
    
    // get single post
    PostDTO getPostById(Integer postId);
    
    // get all posts by category
    List<PostDTO> getPostsByCategory(Integer categoryId);
    
    // get all posts by user
    List<PostDTO> getPostsByUser(Integer userId);
    
    // search posts
    List<PostDTO> searchPosts(String keyword);
}
