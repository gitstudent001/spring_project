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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.worldsnack.dto.CategoryDTO;
import com.worldsnack.dto.CommDTO;
import com.worldsnack.dto.CommentDTO;
import com.worldsnack.dto.ContentDTO;
import com.worldsnack.dto.PageDTO;
import com.worldsnack.dto.UserDTO;
import com.worldsnack.service.CategoryService;
import com.worldsnack.service.CommService;
import com.worldsnack.service.CommentService;
import com.worldsnack.service.ContentService;
import com.worldsnack.service.MypageService;
import com.worldsnack.validator.UserValidator;

@Controller
@RequestMapping("/mypage")
public class MyPageController {
	
	@Autowired
	private MypageService mypageService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
  private CommService commService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired ContentService contentService;
	
	@Resource(name = "loginUserDTO")
	private UserDTO loginUserDTO;
	
	@InitBinder("modifyUserDTO")
	protected void initBinder(WebDataBinder binder) {
		UserValidator validator1 = new UserValidator();
		binder.addValidators(validator1);
	}
	
	private boolean mypageNav =  true;

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
		
		// top_menu 네비게이션용 boolean 전송
		model.addAttribute("mypageNav", this.mypageNav);
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
		
		// top_menu 네비게이션용 boolean 전송
		model.addAttribute("mypageNav", this.mypageNav);
		
		return "myPage/myContent";
		

	}
	
	@GetMapping("/delete")
	public String delete(Model model) {
		//System.out.println(loginUserDTO.getUser_idx());
		
		// 로그인한 회원의 정보를 전달해줌
		model.addAttribute("loginUserDTO", this.loginUserDTO);
		
		// top_menu 네비게이션용 boolean 전송
		model.addAttribute("mypageNav", this.mypageNav);
		
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
												@RequestParam(value="category_idx", defaultValue="0") int category_idx,
												Model model) {
		
		// top_menu 네비게이션용 boolean 전송
		model.addAttribute("mypageNav", this.mypageNav);
		
		int user_idx = loginUserDTO.getUser_idx();
		// 카테고리 정보를 전달해줌
		List<CategoryDTO> categoryDTO = categoryService.selectAll(); 
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
		
		if(category_idx > 0) {
			flag = true;
			
			myScrapList = mypageService.myScrapPreviewSelect(user_idx, page, category_idx);
			
			/* 페이지네이션을 위한 PageDTO 선언 */
			pageDTO = mypageService.getCountOfMyScrapTotal(user_idx, page, flag, category_idx);
		}else {
			flag = false;
			
			myScrapList = mypageService.myScrapPreviewAll(user_idx, page); 
			
			/* 페이지네이션을 위한 PageDTO 선언 */
			pageDTO = mypageService.getCountOfMyScrapTotal(user_idx, page, flag, category_idx);
		}
		System.out.println(myScrapList);
		model.addAttribute("myScrapList", myScrapList);	

		model.addAttribute("pageDTO", pageDTO);		
		
		// 페이지의 정보를 전달해줌
		model.addAttribute("page", page);
		// 현재 선택한 카테고리 인덱스 번호 전달해줌
		model.addAttribute("category_idx", category_idx);
			
		return "myPage/myScrap";
	}
	
	@GetMapping("/myState")
	public String myState(@RequestParam(value = "page", defaultValue = "1") int page,
												Model model) {
		// top_menu 네비게이션용 boolean 전송
		model.addAttribute("mypageNav", this.mypageNav);
		
		// 회원의 이름, 최초 가입일
		model.addAttribute("loginUserDTO", loginUserDTO);
		
		int user_idx = loginUserDTO.getUser_idx();
		// 회원의 총 활동 시간
		String activityTime = mypageService.activityTime(user_idx);
		model.addAttribute("activityTime", activityTime);
		// 회원의 방문 횟수
		String visitCount = mypageService.visitCount(user_idx);
		model.addAttribute("visitCount", visitCount);
		//최근 방문 날짜 조회
		Date recentVisitTime = mypageService.recentVisitTime(user_idx);
		model.addAttribute("recentVisitTime", recentVisitTime);
		
		// 내가 작성한 총 댓글 개수 조회 (커뮤니티용)
		int myCommunityCommentCount = mypageService.getMyAllCommunityCommentCount(user_idx);
		model.addAttribute("myCommunityCommentCount", myCommunityCommentCount);
		
		// 내가 작성한 댓글 조회 (커뮤니티용)
		List<CommentDTO> myCommunityCommentDTO = mypageService.getMyAllCommunityCommentList(user_idx, page);
		model.addAttribute("myCommunityCommentDTO", myCommunityCommentDTO);
		/* 페이지네이션을 위한 PageDTO 선언(게시글) */
		PageDTO commentPageDTO = mypageService.getCommentCountForPage(user_idx, page);
		model.addAttribute("commentPageDTO", commentPageDTO);
		
		// 내가 작성한 총 게시글 수 조회 (커뮤니티용)
		int myCommunityContentCount = mypageService.getMyAllCommuityContentCount(user_idx);
		model.addAttribute("myCommunityContentCount", myCommunityContentCount);
		
		// 내가 작성한 총 게시글 리스트 조회 (커뮤니티용)
		List<CommDTO> myCommunityContentDTO = mypageService.getMyAllCommunityContentList(user_idx, page);
		model.addAttribute("myCommunityContentDTO", myCommunityContentDTO);
		/* 페이지네이션을 위한 PageDTO 선언(게시글) */
		PageDTO contentPageDTO = mypageService.getContentCountForPage(user_idx, page);
		model.addAttribute("contentPageDTO", contentPageDTO);
		
		// 내가 작성한 글 중 관심받은(스크랩 받은) 게시글 리스트 조회 (제품용)
		List<ContentDTO> myContentByScrapContentDTO = mypageService.getReceivedScrapList(user_idx, page);
		model.addAttribute("myContentByScrapContentDTO", myContentByScrapContentDTO);
		/* 페이지네이션을 위한 PageDTO 선언(게시글) */
		PageDTO scrapPageDTO = mypageService.getReceivedScrapForPage(user_idx, page);
		model.addAttribute("scrapPageDTO", scrapPageDTO);
		
		// 받은 관심 수
		int likeCount = mypageService.getReceivedScrapCount(user_idx);
		model.addAttribute("likeCount", likeCount);
		
		return "myPage/myState";
	}
	
	@PostMapping("/deletePost")
	public String deletePost(@RequestParam("community_idx") List<Integer> community_idx) {
		
		for(int idx : community_idx) {
			commService.deletePost(idx);
		}
		
		return "redirect:/mypage/myState";
	}
	
	@PostMapping("/deleteComment")
	public String deleteComment(@RequestParam("comment_idx") List<Integer> comment_idx,
															@RequestParam(value = "page", defaultValue = "1") int page,
															@RequestParam(value="content", defaultValue = "content1") String content,
															RedirectAttributes redirectAttributes) {
		
		for(int idx : comment_idx) {
			commentService.deleteComment(idx);
		}
		
		redirectAttributes.addAttribute("content", content);
		redirectAttributes.addAttribute("page", page);
		return "redirect:/mypage/myState";
	}
	
	@PostMapping("/deleteContent")
	public String deleteContent(@RequestParam("content_idx") List<Integer> content_idx,
                        			@RequestParam(value = "page", defaultValue = "1") int page,
                        			@RequestParam(value="content", defaultValue = "content1") String content,
                        			RedirectAttributes redirectAttributes) {
		for(int idx : content_idx) {
			contentService.deleteContent(idx);
		}
		
		redirectAttributes.addAttribute("content", content);
		redirectAttributes.addAttribute("page", page);
		return "redirect:/mypage/myState";
	}
	

}
