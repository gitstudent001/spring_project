package com.worldsnack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.worldsnack.dto.UserDTO;
import com.worldsnack.service.UserService;

@Controller
@RequestMapping("/user")
public class UserContoller {

	@Autowired
	private UserService userService;
	
	@GetMapping("/test")
	public String test(UserDTO userDTO, Model model) {
		userDTO = userService.testList("test");
		model.addAttribute("userDTO", userDTO);
		return "user/test";
	}
	
	@GetMapping("/login_join")
	public String login_join() {
		return "user/login_join";
	}
	
}
