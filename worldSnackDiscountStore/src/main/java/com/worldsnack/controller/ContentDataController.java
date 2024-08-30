package com.worldsnack.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.worldsnack.dto.ContentDTO;
import com.worldsnack.service.ContentService;

@RestController
public class ContentDataController {

	@Autowired
	private ContentService contentService;
	
	@PostMapping("/main_categorys")
	public String main_categorys(@RequestParam(value="categorySearch", defaultValue="") String categorySearch,
			 												 Model model) {
		String result = "";
		
		List<ContentDTO> contentDTO = null;
		
		if(categorySearch != null && !categorySearch.equals("")) {
			contentDTO = contentService.selectSearchList(categorySearch);
		}
				
		if(contentDTO != null) {
			
			// category_idx 담기
			List<Integer> list = new ArrayList<>();
			for(ContentDTO item : contentDTO) {
				list.add(item.getCategory_idx());
			}
			
			// 중복 제거        
			List<Integer> newList = list.stream().distinct().collect(Collectors.toList());
			
			// 중복 제거 된 category_idx 담기 
			for(int i = 0; i < newList.size(); i++) {
				if(i == 0) {
					result = Integer.toString(newList.get(i));
				}
				else {
					result = result + "," + Integer.toString(newList.get(i));
				}
			}
		}
		
		return result;
	}
}
