package com.worldsnack.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.worldsnack.dto.CategoryDTO;
import com.worldsnack.dto.ContentDTO;
import com.worldsnack.service.CategoryService;
import com.worldsnack.service.ContentService;


@Controller
public class HomeContoller {

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ContentService contentService;
	
	@GetMapping("/")
	public String home(@RequestParam(value="category_info_idx", defaultValue="0") int category_info_idx, 
										 HttpServletRequest request, 
										 Model model) {
		//String contextPath = request.getServletContext().getRealPath("/");
		//System.out.println("contextPath : " + contextPath);
		
		List<CategoryDTO> categoryDTO = categoryService.selectAll(); 
		model.addAttribute("categoryDTO", categoryDTO);
		
		List<ContentDTO> contentDTO = null;
		
		if(category_info_idx > 0) {
			contentDTO = contentService.selectList(category_info_idx);
		}else {
  		contentDTO = contentService.selectAll(); 
		}
		model.addAttribute("contentDTO", contentDTO);
		
		return "main";
	}
	
	@PostMapping("/main")
	public String main(@RequestParam(value="category_idx", defaultValue="0") int category_idx,
										 @RequestParam(value="categorys", defaultValue="") String categorys,
										 @RequestParam(value="categoryCnt", defaultValue="0") int categoryCnt,
										 Model model) {
		
		List<CategoryDTO> categoryDTO = categoryService.selectAll(); 
		model.addAttribute("categoryDTO", categoryDTO);
		
		List<ContentDTO> contentDTO = null;
		
		String[] arr = null;
		if(categoryCnt > 0) {
			//if(categoryCnt > 1) {
				
				int len = 0;
				if(categorys.trim().equals(",")) {
					arr = categorys.split(",");
					len = arr.length;
				}
				
				/*
				//int[] category_info_idx_arr = new int[len];
				//List<Category> category = new ArrayList<Category>();
				if(arr != null) {
  				for(int i=0; i < len; i++) {
  					//category_info_idx_arr[i] = Integer.parseInt(arr[i]);
  					
  					Category cate = new Category();
  					cate.setCategory_info_idx(Integer.parseInt(arr[i]));
  					
  					category.add(cate);
  				}
				}
				*/
				
				contentDTO = contentService.selectInList(categorys);
			//}
			//else {
				//contentDTO = contentService.selectList(category_idx);
			//}
		}else {
  		contentDTO = contentService.selectAll();
		}
		model.addAttribute("contentDTO", contentDTO);
		
		//System.out.println("category_idx : " + category_idx);
		//System.out.println("contentDTO : " + contentDTO);
		
		return "productList";
	}
	
}
