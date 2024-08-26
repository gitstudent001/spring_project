package com.worldsnack.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.worldsnack.dto.CategoryInfoDTO;
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
		
		List<CategoryInfoDTO> categoryDTO = categoryService.selectAll(); 
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
	public String main(@RequestParam(value="category_info_idx", defaultValue="0") int category_info_idx, Model model) {
		
		List<CategoryInfoDTO> categoryDTO = categoryService.selectAll(); 
		model.addAttribute("categoryDTO", categoryDTO);
		
		List<ContentDTO> contentDTO = null;
		
		if(category_info_idx > 0) {
			contentDTO = contentService.selectList(category_info_idx);
		}else {
  		contentDTO = contentService.selectAll(); 
		}
		model.addAttribute("contentDTO", contentDTO);
		
		System.out.println("category_info_idx : " + category_info_idx);
		System.out.println("contentDTO : " + contentDTO);
		
		return "productList";
	}
	
}
