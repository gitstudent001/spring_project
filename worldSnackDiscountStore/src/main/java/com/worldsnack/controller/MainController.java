package com.worldsnack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

	@GetMapping("/main")
	public void main(@RequestParam(value="user_idx", defaultValue = "0") int user_idx, Model model) {
		model.addAttribute("user_idx", user_idx);
	}
}
