package com.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.config.AppConstants;
import com.blog.payload.ApiResponse;
import com.blog.payload.PostDTO;
import com.blog.payload.PostResponse;
import com.blog.services.FileService;
import com.blog.services.PostService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/")
public class PostController {
    @Autowired
    private PostService postService;
    
    @Autowired
    private FileService fileService;
    
    @Value("${project.image}")
    private String path;
    
    // Create
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDTO> createPost(
            @RequestBody PostDTO postDTO,
            @PathVariable Integer userId,
            @PathVariable Integer categoryId) {
        PostDTO createPost = this.postService.createPost(postDTO, userId, categoryId);
        return new ResponseEntity<>(createPost, HttpStatus.CREATED);
    }

    // Update
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDTO> updatePost(
            @RequestBody PostDTO postDTO,
            @PathVariable Integer postId) {
        PostDTO updatedPost = this.postService.updatePost(postDTO, postId);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    // Delete
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId) {
        this.postService.deletePost(postId);
        return new ResponseEntity<>(new ApiResponse("Post is deleted successfully", true), HttpStatus.OK);
    }

    // Get All Posts
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "page", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer page,
            @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer size,
            @RequestParam(value = "sortby", defaultValue = AppConstants.SORT_BY, required = false ) String sortby,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ) {
       PostResponse postResponse = this.postService.getAllPost(page,size, sortby, sortDir);
       return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
    }

    // Get Post by ID
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Integer postId) {
        PostDTO postDTO = this.postService.getPostById(postId);
        return ResponseEntity.ok(postDTO);
    }

    // Get Posts by Category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDTO>> getPostsByCategory(@PathVariable Integer categoryId) {
        List<PostDTO> posts = this.postService.getPostsByCategory(categoryId);
        return ResponseEntity.ok(posts);
    }

    // Get Posts by User
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDTO>> getPostsByUser(@PathVariable Integer userId) {
        List<PostDTO> posts = this.postService.getPostsByUser(userId);
        return ResponseEntity.ok(posts);
    }

    // Search Posts
    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<List<PostDTO>> searchPosts(@PathVariable String keyword) {
        List<PostDTO> posts = this.postService.searchPosts(keyword);
        return ResponseEntity.ok(posts);
    }
    
    //post image upload 
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDTO> uploadPostImage(
    		@RequestParam("image") MultipartFile image,
    		@PathVariable Integer postId
    		) throws IOException{
    	PostDTO postDTO = this.postService.getPostById(postId);
    	String fileName = this.fileService.uploadImage(path, image);    	
    	postDTO.setImageName(fileName);
    	PostDTO updatePost = this.postService.updatePost(postDTO, postId);
    	return new ResponseEntity<PostDTO>(updatePost, HttpStatus.OK);
    }
    
    //method to serve all files 
    @GetMapping(value = "post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
    		@PathVariable("imageName") String imageName,
    		HttpServletResponse response
    		) throws IOException{
    	InputStream resource = this.fileService.getResource(path, imageName);
    	response.setContentType(MediaType.IMAGE_JPEG_VALUE);
    	StreamUtils.copy(resource, response.getOutputStream());
    	
    	
    }
}






















