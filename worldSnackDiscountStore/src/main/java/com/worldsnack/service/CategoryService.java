package com.worldsnack.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worldsnack.dao.CategoryDAO;
import com.worldsnack.dto.CategoryDTO;

@Service
public class CategoryService {

	@Autowired
	private CategoryDAO categoryDAO;
	
	public List<CategoryDTO> selectAll(){
		List<CategoryDTO> categoryDTO = categoryDAO.selectAll(); 
		return categoryDTO;
	}
	
	public CategoryDTO getCategorySelect(int content_idx) {
		CategoryDTO categoryDTO =  categoryDAO.getCategorySelect(content_idx);
		return categoryDTO;
	}
	
	/*
	public void addCategorySelect(CategoryDTO writeCategoryDTO) {
		categoryDAO.insertCategory(writeCategoryDTO);
	}
	
	public void updateCategory(CategoryDTO modifyCategoryDTO) {
		categoryDAO.updateCategory(modifyCategoryDTO);
	}
	*/
	
}
