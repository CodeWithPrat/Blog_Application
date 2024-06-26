package com.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payload.PostDTO;
import com.blog.payload.PostResponse;
import com.blog.repositories.CategoryRepo;
import com.blog.repositories.PostRepo;
import com.blog.repositories.UserRepo;
import com.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId) {
        // Retrieve the user by userId or throw ResourceNotFoundException
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));
        
        // Retrieve the category by categoryId or throw ResourceNotFoundException
        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category ID", categoryId));
        
        // Map PostDTO to Post entity and set necessary fields
        Post post = this.modelMapper.map(postDTO, Post.class);
        post.setImageName("default.png"); // Default image name
        post.setAddDate(new Date()); // Set current date as add date
        post.setUser(user); // Set the user for this post
        post.setCategory(category); // Set the category for this post
        
        // Save the post entity and map the result back to PostDTO
        Post newPost = this.postRepo.save(post);
        return this.modelMapper.map(newPost, PostDTO.class);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, Integer postId) {
        // Retrieve the post by postId or throw ResourceNotFoundException
        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));
        
        // Update fields with values from PostDTO
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setImageName(postDTO.getImageName());
        
        // Save the updated post and map the result back to PostDTO
        Post updatedPost = this.postRepo.save(post);
        return this.modelMapper.map(updatedPost, PostDTO.class);
    }

    @Override
    public void deletePost(Integer postId) {
        // Retrieve the post by postId or throw ResourceNotFoundException
        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));
        
        // Delete the post
        this.postRepo.delete(post);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortby, String sortDir) {
        // Determine sorting direction
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortby).ascending() : Sort.by(sortby).descending();
        
        // Create pageable object for pagination and sorting
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        
        // Retrieve page of posts from repository
        Page<Post> pagePost = this.postRepo.findAll(pageable);
        
        // Extract content from page
        List<Post> allPost = pagePost.getContent();
        
        // Map Post entities to PostDTOs
        List<PostDTO> collect = allPost.stream().map(post -> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
        
        // Create and populate PostResponse object with pagination information
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(collect);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());
        
        return postResponse;
    }

    @Override
    public PostDTO getPostById(Integer postId) {
        // Retrieve the post by postId or throw ResourceNotFoundException
        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));
        
        // Map Post entity to PostDTO
        return this.modelMapper.map(post, PostDTO.class);
    }

    @Override
    public List<PostDTO> getPostsByCategory(Integer categoryId) {
        // Retrieve the category by categoryId or throw ResourceNotFoundException
        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
        
        // Retrieve posts associated with the category
        List<Post> posts = this.postRepo.findByCategory(category);
        
        // Map Post entities to PostDTOs
        return posts.stream().map(post -> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<PostDTO> getPostsByUser(Integer userId) {
        // Retrieve the user by userId or throw ResourceNotFoundException
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));
        
        // Retrieve posts associated with the user
        List<Post> posts = this.postRepo.findByUser(user);
        
        // Map Post entities to PostDTOs
        return posts.stream().map(post -> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<PostDTO> searchPosts(String keyword) {
        // Retrieve all posts and filter by keyword in title or content
        List<Post> posts = this.postRepo.findAll().stream()
                .filter(post -> post.getTitle().contains(keyword) || post.getContent().contains(keyword))
                .collect(Collectors.toList());
        
        // Map filtered Post entities to PostDTOs
        return posts.stream().map(post -> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
    }
}
