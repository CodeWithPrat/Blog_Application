package com.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payload.ApiResponse;
import com.blog.payload.CategoryDTO;
import com.blog.services.CategoryServ;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    
    @Autowired
    private CategoryServ categoryServ;
    
    // Create
    @PostMapping("/")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
        CategoryDTO createCategory = this.categoryServ.createCategory(categoryDTO);
        return new ResponseEntity<>(createCategory, HttpStatus.CREATED);
    }
    
    // Update
    @PutMapping("/{catId}")
    public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable Integer catId) {
        CategoryDTO updatedCategory = this.categoryServ.updateCategory(categoryDTO, catId);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }
    
    // Delete
    @DeleteMapping("/{catId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId) {
        this.categoryServ.deleteCategory(catId);
        return new ResponseEntity<>(new ApiResponse("Category is deleted successfully", true), HttpStatus.OK);
    }
    
    // Get
    @GetMapping("/{catId}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable Integer catId) {
        CategoryDTO categoryDTO = this.categoryServ.getCategory(catId);
        return ResponseEntity.ok(categoryDTO);
    }
    
    // Get all
    @GetMapping("/")
    public ResponseEntity<List<CategoryDTO>> getAllCategory() {
        List<CategoryDTO> allCategory = this.categoryServ.getAllCategory();
        return ResponseEntity.ok(allCategory);
    }
}
