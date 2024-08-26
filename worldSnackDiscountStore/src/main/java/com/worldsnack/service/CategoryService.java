package com.worldsnack.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worldsnack.dao.CategoryDAO;
import com.worldsnack.dto.CategoryInfoDTO;
import com.worldsnack.dto.CategorySelectDTO;

@Service
public class CategoryService {

	@Autowired
	private CategoryDAO categoryDAO;
	
	public List<CategoryInfoDTO> selectAll(){
		List<CategoryInfoDTO> categoryDTO = categoryDAO.selectAll(); 
		//System.out.println("categoryDTO (Service) : " + categoryDTO);
		return categoryDTO;
	}
	
	public List<CategoryInfoDTO> selectInfoAll(){
		List<CategoryInfoDTO> categoryDTO = categoryDAO.selectInfoAll(); 
		//System.out.println("categoryDTO (Service) : " + categoryDTO);
		return categoryDTO;
	}
	
	public CategorySelectDTO getCategorySelect(int content_idx) {
		CategorySelectDTO categorySelectDTO =  categoryDAO.getCategorySelect(content_idx);
		return categorySelectDTO;
	}
	
	public void addCategorySelect(CategorySelectDTO writeCategorySelectDTO) {
		categoryDAO.insertCategorySelect(writeCategorySelectDTO);
	}
	
	public void updateCategorySelect(CategorySelectDTO modifyCategorySelectDTO) {
		categoryDAO.updateCategorySelect(modifyCategorySelectDTO);
	}
	
}
