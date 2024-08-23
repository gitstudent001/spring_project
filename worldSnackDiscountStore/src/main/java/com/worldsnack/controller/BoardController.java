package com.worldsnack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.worldsnack.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	

	@GetMapping("/testInsert1")
	public String testInsert1(@RequestParam("user_idx") int user_idx, Model model) {
		boardService.testInsertContent(user_idx);
		model.addAttribute("user_idx", user_idx);
		return "board/testInsert1";
	}
}
