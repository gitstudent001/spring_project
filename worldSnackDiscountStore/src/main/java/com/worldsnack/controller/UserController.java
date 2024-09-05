package com.worldsnack.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.worldsnack.dto.UserDTO;
import com.worldsnack.service.UserService;
import com.worldsnack.validator.UserValidator;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Resource(name="loginUserDTO")
	private UserDTO loginUserDTO;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		UserValidator userValidator = new UserValidator();
		binder.addValidators(userValidator);
	}
	
	@GetMapping("/login_join")
	public String login(@ModelAttribute("loginUserDTO") UserDTO loginUserDTO,
											@ModelAttribute("joinUserDTO") UserDTO joinUserDTO,
											@RequestParam(value="fail", defaultValue="false") boolean fail,
											Model model) {
		model.addAttribute("fail", fail);
		return "user/login_join";
	}
	
	@PostMapping("/login_procedure")
	public String login_procedure(@Valid @ModelAttribute("loginUserDTO") UserDTO loginUserDTO,
																BindingResult result,
																@ModelAttribute("joinUserDTO") UserDTO joinUserDTO,
																Model model, HttpServletRequest request) {
		if(result.hasErrors()) {
			//System.out.println(result);
			return "user/login_join";
		}
		this.loginUserDTO = userService.getLoginUserInfo(loginUserDTO);
		
		if(this.loginUserDTO.isUserIsLogin()) {
			// 로그인 한 유저의 등급 가져오기
			this.loginUserDTO.setUser_gradeNameAndClass(userService.getMyGrade(this.loginUserDTO.getUser_idx()));
			
			// 세션에 업데이트된 사용자 정보 설정
			request.getSession().setAttribute("loginUserDTO", this.loginUserDTO);
			
			model.addAttribute("loginUserDTO", this.loginUserDTO);
			//System.out.println(this.loginUserDTO);
			
			return "user/login_success";
		}else {
			return "user/login_failure";
		}
	}
	
  @PostMapping("/join_procedure")
  public String join_procedure(@Valid @ModelAttribute("joinUserDTO") UserDTO joinUserDTO, 
  		                         BindingResult result,
  		                         @ModelAttribute("loginUserDTO") UserDTO loginUserDTO,
  		                         HttpServletRequest request) {
  	     
  	if(result.hasErrors()) {
  		request.setAttribute("errorMessage", "정보를 다시 확인해주세요");
  		return "user/login_join";  		
  	}
  	
  	userService.insertUser(joinUserDTO);
  	
  	return "user/join_success";
  }	
	
	@GetMapping("/logout")
	public String logout() {
		loginUserDTO.setUserIsLogin(false);
		return "user/logout";
	}
		
	@GetMapping("/cant_login")
	public String cantlogin() {
		return "user/cant_login";
	}
		
	
	@GetMapping("/checkUserIdExist/{user_id}")
	public @ResponseBody String checkUserIdExist(@PathVariable("user_id") String userId) {
    boolean isUserIdExist = userService.checkUserIdExist(userId);
    
    return isUserIdExist ? "true" : "false";
	}
		
		
}
