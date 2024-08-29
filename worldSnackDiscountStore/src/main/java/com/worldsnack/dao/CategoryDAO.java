package com.worldsnack.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.worldsnack.dto.CategoryDTO;
import com.worldsnack.mapper.CategoryMapper;

@Repository
public class CategoryDAO {

	@Autowired
	private CategoryMapper categoryMapper;
	
	public List<CategoryDTO> selectAll(){
		List<CategoryDTO> categoryDTO = categoryMapper.selectAll(); 
		return categoryDTO;
	}
	
	public CategoryDTO getCategorySelect(int content_idx) {
		CategoryDTO categorySelectDTO =  categoryMapper.getCategorySelect(content_idx);
		return categorySelectDTO;
	}
	
	/*
	public void insertCategory(CategoryDTO writeCategoryDTO) {
		categoryMapper.insertCategory(writeCategoryDTO);
	}
	
	public void updateCategory(CategoryDTO modifyCategoryDTO) {
		categoryMapper.updateCategory(modifyCategoryDTO);
	}
	*/
}
