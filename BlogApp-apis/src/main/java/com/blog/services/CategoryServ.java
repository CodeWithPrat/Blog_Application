package com.blog.services;

import java.util.List;

import com.blog.payload.CategoryDTO;

public interface CategoryServ {
	//create
	public CategoryDTO createCategory(CategoryDTO categoryDTO);
	
	//update
	public CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId);
	
	//delete
	public  void deleteCategory(Integer categoryId);
	
	//get
	public CategoryDTO getCategory(Integer categoryId);
	
	//get all
	public List<CategoryDTO> getAllCategory();
}
