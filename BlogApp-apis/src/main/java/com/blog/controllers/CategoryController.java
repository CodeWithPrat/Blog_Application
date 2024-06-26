package com.blog.controllers;

// Importing necessary Java and Spring classes
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.blog.payload.ApiResponse;
import com.blog.payload.CategoryDTO;
import com.blog.services.CategoryServ;

//Each method returns a ResponseEntity object, which allows us to control the HTTP status code, headers, and body of the response.
// Marks this class as a REST controller
@RestController
// Base URL for all endpoints in this controller
@RequestMapping("/api/categories")
public class CategoryController {
    
    // Autowiring (injecting) the CategoryServ service
    @Autowired
    private CategoryServ categoryServ;
    
    // Create a new category
    //@RequestBody is used to bind the request body to a method parameter (like categoryDTO).
    @PostMapping("/")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
        CategoryDTO createdCategory = this.categoryServ.createCategory(categoryDTO);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }
    
    // Update an existing category
    // The methods use @PathVariable to extract values from the URL path (like catId).
    @PutMapping("/{catId}")
    public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable Integer catId) {
        CategoryDTO updatedCategory = this.categoryServ.updateCategory(categoryDTO, catId);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }
    
    // Delete a category
    @DeleteMapping("/{catId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId) {
        this.categoryServ.deleteCategory(catId);
        return new ResponseEntity<>(new ApiResponse("Category is deleted successfully", true), HttpStatus.OK);
    }
    
    // Get a single category by ID
    @GetMapping("/{catId}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable Integer catId) {
        CategoryDTO categoryDTO = this.categoryServ.getCategory(catId);
        return ResponseEntity.ok(categoryDTO);
    }
    
    // Get all categories
    @GetMapping("/")
    public ResponseEntity<List<CategoryDTO>> getAllCategory() {
        List<CategoryDTO> allCategories = this.categoryServ.getAllCategory();
        return ResponseEntity.ok(allCategories);
    }
}