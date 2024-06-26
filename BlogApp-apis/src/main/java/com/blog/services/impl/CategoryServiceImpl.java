package com.blog.services.impl;

import java.util.List; // Represents an ordered collection (also known as a sequence)
import java.util.stream.Collectors; // Provides functional-style operations on streams of elements

import org.modelmapper.ModelMapper; // Maps objects of one type into objects of another type
import org.springframework.beans.factory.annotation.Autowired; // Annotation for automatic dependency injection
import org.springframework.stereotype.Service; // Indicates that an annotated class is a "service"

import com.blog.entities.Category; // Entity class representing a category
import com.blog.exceptions.ResourceNotFoundException; // Custom exception for resource not found
import com.blog.payload.CategoryDTO; // DTO (Data Transfer Object) for category data
import com.blog.repositories.CategoryRepo; // Repository interface for Category entity
import com.blog.services.CategoryServ; // Interface defining category-related operations

@Service // Indicates that this class is a Spring service
public class CategoryServiceImpl implements CategoryServ {

    @Autowired // Automatically injects the CategoryRepo dependency
    private CategoryRepo categoryRepo;
    
    @Autowired // Automatically injects the ModelMapper dependency
    private ModelMapper modelMapper;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        // Map CategoryDTO to Category entity
        Category cat = this.modelMapper.map(categoryDTO, Category.class);
        // Save the mapped entity and get the saved instance
        Category addedCat = this.categoryRepo.save(cat);
        // Map the saved entity back to CategoryDTO and return
        return this.modelMapper.map(addedCat, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId) {
        // Find the category by ID or throw ResourceNotFoundException
        Category cat = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
        // Update category details from CategoryDTO
        cat.setCategoryTitle(categoryDTO.getCategoryTitle());
        cat.setCategoryDescription(categoryDTO.getCategoryDescription());
        // Save the updated category and get the updated instance
        Category updatedCat = this.categoryRepo.save(cat);
        // Map the updated entity back to CategoryDTO and return
        return this.modelMapper.map(updatedCat, CategoryDTO.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        // Find the category by ID or throw ResourceNotFoundException
        Category cat = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category ID", categoryId));
        // Delete the category
        this.categoryRepo.delete(cat);
    }

    @Override
    public CategoryDTO getCategory(Integer categoryId) {
        // Find the category by ID or throw ResourceNotFoundException
        Category cat = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category ID", categoryId));
        // Map the entity to CategoryDTO and return
        return this.modelMapper.map(cat, CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> getAllCategory() {
        // Retrieve all categories from the repository
        List<Category> categories = this.categoryRepo.findAll();
        // Map each Category entity to CategoryDTO and collect into a list
        List<CategoryDTO> catDtos = categories.stream()
                .map(cat -> this.modelMapper.map(cat, CategoryDTO.class))
                .collect(Collectors.toList());
        // Return the list of CategoryDTOs
        return catDtos;
    }
}
