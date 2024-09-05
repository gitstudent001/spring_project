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
import com.worldsnack.service.UserService;


@Controller
public class HomeController {

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ContentService contentService;
	
	@Autowired
	private UserService userService;
	
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
		
		// 예기치 못한 상황에 로그아웃 못하고 서버가 재시작 됐을 때
		// 로그아웃 로그 저장
		userService.setAllLogoutLog();
		
		return "main";
	}
	
	@PostMapping("/main")
	public String main(@RequestParam(value="category_idx", defaultValue="0") int category_idx,
										 @RequestParam(value="categorys", defaultValue="") String categorys,
										 @RequestParam(value="categoryCnt", defaultValue="0") int categoryCnt,
										 @RequestParam(value="categorySearch", defaultValue="") String categorySearch,
										 Model model) {
		
		List<CategoryDTO> categoryDTO = categoryService.selectAll(); 
		model.addAttribute("categoryDTO", categoryDTO);
		
		List<ContentDTO> contentDTO = null;
		
		if(categorySearch != null && !categorySearch.equals("")) {
			contentDTO = contentService.selectSearchList(categorySearch);
		}
		else {
  		if(categoryCnt > 0) {
  			contentDTO = contentService.selectInList(categorys);
  		}else {
    		contentDTO = contentService.selectAll();
  		}
		}
		model.addAttribute("contentDTO", contentDTO);
		
		//System.out.println("category_idx : " + category_idx);
		//System.out.println("contentDTO : " + contentDTO);
		
		return "productList";
	}
	
	@GetMapping("/test")
	public String test() {
		return "test";
	}
	
}
