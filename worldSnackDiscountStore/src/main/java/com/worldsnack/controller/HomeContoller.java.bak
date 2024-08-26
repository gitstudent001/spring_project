package com.worldsnack.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeContoller {

	@GetMapping("/")
	public String home(HttpServletRequest request) {
		String contextPath = request.getServletContext().getRealPath("/");
		System.out.println("contextPath : " + contextPath);

		return "redirect:/main";
	}
}
