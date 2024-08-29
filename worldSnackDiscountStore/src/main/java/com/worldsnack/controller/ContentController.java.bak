package com.worldsnack.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.worldsnack.dto.CategoryDTO;
import com.worldsnack.dto.ContentDTO;
import com.worldsnack.dto.UserDTO;
import com.worldsnack.service.CategoryService;
import com.worldsnack.service.ContentService;

@Controller
@RequestMapping("content")
public class ContentController {

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ContentService contentService;
	
	@Resource(name="loginUserDTO")
	private UserDTO loginUserDTO;
	
	
	@GetMapping("/list")
	public String list(@RequestParam(value="limit", defaultValue="10") int limit,
              			 @RequestParam(value="category_info_idx", defaultValue="0") int category_idx,
              			 Model model) {
		
		List<CategoryDTO> categoryDTO = categoryService.selectAll(); 
		model.addAttribute("categoryDTO", categoryDTO);
		
		List<ContentDTO> contentDTO = null;
		
		if(category_idx > 0) {
			contentDTO = contentService.selectListForLimit(category_idx, limit);
		}else {
  		contentDTO = contentService.selectAllForLimit(limit); 
		}
		model.addAttribute("category_info_idx", category_idx);
		model.addAttribute("contentDTO", contentDTO);
		model.addAttribute("limit", limit);
		return "content/list";
	}
	

	@GetMapping("/detail")
	public String detail(@RequestParam("content_idx") int content_idx,
											 @RequestParam(value="limit", defaultValue="10") int limit,
											 @RequestParam(value="category_info_idx", defaultValue="0") int category_info_idx,
											 Model model) {
		model.addAttribute("content_idx", content_idx);
		
		ContentDTO detailContentDTO = contentService.getContentDetail(content_idx);
		model.addAttribute("detailContentDTO", detailContentDTO);
		model.addAttribute("loginUserDTO", loginUserDTO);
		
		model.addAttribute("limit", limit);
		model.addAttribute("category_info_idx", category_info_idx);
		return "content/detail";
	}
	
	@GetMapping("/write")
	public String write(@ModelAttribute("writeContentDTO") ContentDTO writeContentDTO, Model model) {
		List<CategoryDTO> categoryDTO = categoryService.selectAll(); 
		model.addAttribute("categoryDTO", categoryDTO);
		
		return "content/write";
	}
	
	@PostMapping("/write_procedure")
	public String writeProcedure(@Valid @ModelAttribute("writeContentDTO") ContentDTO writeContentDTO, 
															 BindingResult result,
															 Model model) {
		
		List<CategoryDTO> categoryDTO = categoryService.selectAll(); 
		model.addAttribute("categoryDTO", categoryDTO);
		
		if(result.hasErrors()) {
			return "content/write"; 
		}
		
		System.out.println("writeContentDTO : " + writeContentDTO);
		
		contentService.addContent(writeContentDTO);
		model.addAttribute("content_idx", writeContentDTO.getContent_idx());
		model.addAttribute("category_idx", writeContentDTO.getCategory_idx());
		
		return "content/write_success";
	}
	
	@GetMapping("/modify")
	public String modify(@RequestParam(value="content_idx", defaultValue="0") int content_idx,
                       @ModelAttribute("modifyContentDTO") ContentDTO modifyContentDTO, 
                       Model model) {
		List<CategoryDTO> categoryDTO = categoryService.selectAll(); 
		model.addAttribute("categoryDTO", categoryDTO);

		ContentDTO contentDTO = contentService.getContentDetail(content_idx);
		modifyContentDTO.setContent_idx(contentDTO.getContent_idx());
		modifyContentDTO.setCategory_idx(contentDTO.getCategory_idx());
		modifyContentDTO.setContent_subject(contentDTO.getContent_subject());
		modifyContentDTO.setContent_text(contentDTO.getContent_text());
		modifyContentDTO.setContent_file(contentDTO.getContent_file());
		modifyContentDTO.setContent_writer_idx(contentDTO.getContent_writer_idx());
		modifyContentDTO.setContent_make(contentDTO.getContent_make());
		modifyContentDTO.setContent_country(contentDTO.getContent_country());
		modifyContentDTO.setContent_prodno(contentDTO.getContent_prodno());
		modifyContentDTO.setContent_prodprice(contentDTO.getContent_prodprice());
		modifyContentDTO.setContent_view(contentDTO.getContent_view());
		modifyContentDTO.setContent_date(contentDTO.getContent_date());

		return "content/modify";
	}
	
	@PostMapping("/modify_procedure")
	public String modifyProcedure(@Valid @ModelAttribute("modifyContentDTO") ContentDTO modifyContentDTO, 
                                BindingResult result,
                                Model model) {
		List<CategoryDTO> categoryDTO = categoryService.selectAll(); 
		model.addAttribute("categoryDTO", categoryDTO);
		
		int content_idx = modifyContentDTO.getContent_idx();
		model.addAttribute("content_idx", content_idx);
		
		if(result.hasErrors()) {
			return "content/modify"; 
		}
		
		contentService.editContent(modifyContentDTO);
		
		model.addAttribute("category_idx", modifyContentDTO.getCategory_idx());
		
		return "content/modify_success";
	}
	
}
