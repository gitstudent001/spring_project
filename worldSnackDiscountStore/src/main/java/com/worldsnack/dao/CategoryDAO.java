package com.worldsnack.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.worldsnack.dto.CategoryInfoDTO;
import com.worldsnack.dto.CategorySelectDTO;
import com.worldsnack.mapper.CategoryMapper;

@Repository
public class CategoryDAO {

	@Autowired
	private CategoryMapper categoryMapper;
	
	public List<CategoryInfoDTO> selectAll(){
		List<CategoryInfoDTO> categoryDTO = categoryMapper.selectAll(); 
		//System.out.println("categoryDTO (DAO) : " + categoryDTO);
		return categoryDTO;
	}
	
	public List<CategoryInfoDTO> selectInfoAll(){
		List<CategoryInfoDTO> categoryDTO = categoryMapper.selectInfoAll(); 
		//System.out.println("categoryDTO (DAO) : " + categoryDTO);
		return categoryDTO;
	}
	
	public CategorySelectDTO getCategorySelect(int content_idx) {
		CategorySelectDTO categorySelectDTO =  categoryMapper.getCategorySelect(content_idx);
		return categorySelectDTO;
	}
	
	public void insertCategorySelect(CategorySelectDTO writeCategorySelectDTO) {
		categoryMapper.insertCategorySelect(writeCategorySelectDTO);
	}
	
	public void updateCategorySelect(CategorySelectDTO modifyCategorySelectDTO) {
		categoryMapper.updateCategorySelect(modifyCategorySelectDTO);
	}
	
}
