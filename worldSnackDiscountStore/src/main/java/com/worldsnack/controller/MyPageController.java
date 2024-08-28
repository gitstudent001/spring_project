package com.worldsnack.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.worldsnack.dto.CategoryInfoDTO;
import com.worldsnack.dto.ContentDTO;
import com.worldsnack.dto.PageDTO;
import com.worldsnack.dto.UserDTO;
import com.worldsnack.service.CategoryService;
import com.worldsnack.service.MypageService;
import com.worldsnack.validator.UserValidator;

@Controller
@RequestMapping("/mypage")
public class MyPageController {
	
	@Autowired
	private MypageService mypageService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Resource(name = "loginUserDTO")
	private UserDTO loginUserDTO;
	
	@InitBinder("modifyUserDTO")
	protected void initBinder(WebDataBinder binder) {
		UserValidator validator1 = new UserValidator();
		binder.addValidators(validator1);
	}

	@GetMapping("/main")
	public String main(@ModelAttribute("modifyUserDTO") UserDTO modifyUserDTO,
										 Model model) {
		int user_idx = loginUserDTO.getUser_idx();
		// --------데이터 받기-----
		modifyUserDTO.setUser_idx(user_idx);
		// ------------------------
		
		// main 페이지의 정보수정 양식에 로그인 한 회원의 정보를 전달해줌
		mypageService.getModifyUserInfo(modifyUserDTO);
		
		// 로그인한 회원의 정보를 전달해줌
		model.addAttribute("loginUserDTO", this.loginUserDTO);
		return "myPage/main";
	}
	
	@PostMapping("/modify_procedure")
	public String modify_procedure(@Valid @ModelAttribute("modifyUserDTO") UserDTO modifyUserDTO,
																 BindingResult result,
																 Model model) {
		
		// 로그인한 회원의 정보를 전달해줌
		model.addAttribute("loginUserDTO", this.loginUserDTO);
		
		if(result.hasErrors()) {
			System.out.println(result);
			System.out.println("변경 실패");

			return "myPage/main";
		}
		
		mypageService.modifyUserInfo(modifyUserDTO);
		System.out.println("변경 성공");
		return "myPage/modify_success";
	}
	
	@GetMapping("/myContent")
	public String myContent(@RequestParam(value = "page", defaultValue = "1") int page,													 
													Model model) {
		int user_idx = loginUserDTO.getUser_idx();
		/* 내가 쓴 게시글 개수 조회 */
		String myContentCount = mypageService.myContentCount(user_idx);
		// System.out.println(myContentCount);
		model.addAttribute("myContentCount", myContentCount);
		
		/* 최근 포스팅 일자 조회 */
		Date myContentDate = mypageService.myContentDate(user_idx);
		model.addAttribute("myContentDate", myContentDate);
		
		/* 내가 올린 게시글 미리보기 조회 */
		List<ContentDTO> myContentList = mypageService.myContentPreView(user_idx, page);
		model.addAttribute("myContentList", myContentList);
		
		/* 페이지네이션을 위한 PageDTO 선언 */
		PageDTO pageDTO = mypageService.getCountOfMyContentTotal(user_idx, page);
		model.addAttribute("pageDTO", pageDTO);
		
		// 로그인한 회원의 정보를 전달해줌
		model.addAttribute("loginUserDTO", this.loginUserDTO);
		
		// 페이지의 정보를 전달해줌
		model.addAttribute("page", page);
		return "myPage/myContent";
		

	}
	
	@GetMapping("/delete")
	public String delete(Model model) {
		//System.out.println(loginUserDTO.getUser_idx());
		
		// 로그인한 회원의 정보를 전달해줌
		model.addAttribute("loginUserDTO", this.loginUserDTO);
		return "myPage/delete";
	}
	
	@PostMapping("/delete_procedure")
	public String delete_procedure(@RequestParam(value = "confirmDelete", required = false) String confirmDelete, 
																 Model model) {
		int user_idx = loginUserDTO.getUser_idx();
		
		// 로그인한 회원의 정보를 전달해줌
		model.addAttribute("loginUserDTO", this.loginUserDTO);
		
		if(confirmDelete == null) {
			return "myPage/delete_fail";
		}
		mypageService.deleteUser(user_idx);
		loginUserDTO.setUserIsLogin(false);
		return "myPage/delete_success";

	}
	
	@GetMapping("/myScrap")
	public String myScrap(@RequestParam(value = "page", defaultValue = "1") int page,
												@RequestParam(value="category_info_idx", defaultValue="0") int category_info_idx,
												Model model) {
		int user_idx = loginUserDTO.getUser_idx();
		// 카테고리 정보를 전달해줌
		List<CategoryInfoDTO> categoryDTO = categoryService.selectAll(); 
		model.addAttribute("categoryDTO", categoryDTO);
		
		// 로그인한 회원의 정보를 전달해줌
		model.addAttribute("loginUserDTO", this.loginUserDTO);
		
		/* 내가 스크랩 한 개수 조회 */
		String myScrapCount = mypageService.myScrapCount(user_idx);
		model.addAttribute("myScrapCount", myScrapCount);		
		
		/* 내가 스크랩 한 게시글 미리보기 조회 */
		List<ContentDTO> myScrapList = null;
		/* 페이지네이션을 위한 PageDTO 선언 */
		PageDTO pageDTO = null;
		boolean flag = false;
		
		if(category_info_idx > 0) {
			flag = true;
			
			myScrapList = mypageService.myScrapPreviewSelect(user_idx, page, category_info_idx);
			
			/* 페이지네이션을 위한 PageDTO 선언 */
			pageDTO = mypageService.getCountOfMyScrapTotal(user_idx, page, flag, category_info_idx);
		}else {
			flag = false;
			
			myScrapList = mypageService.myScrapPreviewAll(user_idx, page); 
			
			/* 페이지네이션을 위한 PageDTO 선언 */
			pageDTO = mypageService.getCountOfMyScrapTotal(user_idx, page, flag, category_info_idx);
		}
		System.out.println(myScrapList);
		model.addAttribute("myScrapList", myScrapList);	

		model.addAttribute("pageDTO", pageDTO);		
		
		// 페이지의 정보를 전달해줌
		model.addAttribute("page", page);
		// 현재 선택한 카테고리 인덱스 번호 전달해줌
		model.addAttribute("category_info_idx", category_info_idx);
			
		return "myPage/myScrap";
	}
	

}
