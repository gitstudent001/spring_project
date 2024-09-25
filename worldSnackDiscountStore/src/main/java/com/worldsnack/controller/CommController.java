package com.worldsnack.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.worldsnack.dto.CommDTO;
import com.worldsnack.dto.UserDTO;
import com.worldsnack.service.ArticleService;
import com.worldsnack.service.CommService;

@Controller
@RequestMapping("/board")
public class CommController {

  @Autowired
  private CommService commService;
  
  @Autowired
  private ArticleService articleService;
  
  @GetMapping("/community")
  public String showCommunity(
    @RequestParam(value = "category", required = false) String category,
    @RequestParam(value = "sortOrder", defaultValue = "latest") String sortOrder,
    @RequestParam(value = "viewType", defaultValue = "compact") String viewType,
    @RequestParam(value = "lastCommunityId", defaultValue = "0") int lastCommunityId,
    Model model, HttpServletRequest request) {
  	
  	// 디폴트 썸네일
  	String defaultThumbnailUrl = request.getContextPath() + "/images/default-thumbnail.png";
  	
  	UserDTO user = (UserDTO) request.getSession().getAttribute("loginUserDTO");
  	
  	// 게시글 리스트
    List<CommDTO> posts;
    
    // category가 'best'이면 가중치 기반으로 게시글 정렬
    // sortOrder가 'hot'이면 인기 기반으로 게시글 정렬
    // 그 외는 카테고리, 정렬, 뷰타입에 맞춰 게시글 정렬
    if ("best".equals(category)) {
      posts = commService.getAllPostsSortedByWeightedScore(lastCommunityId);
    } else if ("hot".equals(sortOrder)) {
      posts = commService.getHotPosts(lastCommunityId, category, viewType);
    } else {
      posts = commService.getPostsByCategoryAndSortOrder(lastCommunityId, category, sortOrder, viewType);
    }
    
    if (user != null) {
      for (CommDTO post : posts) {
          boolean alreadyScrap = commService.checkScrap(user.getUser_idx(), post.getCommunity_idx());
          post.setScraped(alreadyScrap);
          System.out.println("Post ID: " + post.getCommunity_idx() + ", Scraped: " + alreadyScrap); // 로그 추가
      }
    } else {
      for (CommDTO post : posts) {
          post.setScraped(false);
          System.out.println("Post ID: " + post.getCommunity_idx() + ", Scraped: false (Not logged in)"); // 로그 추가
      }
    }
    
    model.addAttribute("posts", posts);
    model.addAttribute("category", category);
    model.addAttribute("sortOrder", sortOrder);
    model.addAttribute("viewType", viewType);
    model.addAttribute("defaultThumbnailUrl", defaultThumbnailUrl);
    
    return "board/community";
  }
  
  @GetMapping("/post/{id}")
  public String showPostDetail(@PathVariable int id, HttpServletRequest request,Model model) {
   // 사용자 정보를 세션에서 가져옵니다.
    UserDTO user = (UserDTO) request.getSession().getAttribute("loginUserDTO");
    
    // 로그인 상태 확인
    boolean isUserLoggedIn = (user != null);
    model.addAttribute("isUserLoggedIn", isUserLoggedIn);
    
    // 서비스 메서드 호출 시 필요한 매개변수들을 모두 전달합니다.
    CommDTO post = articleService.getArticleById(id, request, user);
    
    boolean alreadyScrap = commService.checkScrap(user.getUser_idx(), post.getCommunity_idx());
    model.addAttribute("alreadyScrap", alreadyScrap);
    model.addAttribute("community_idx", post.getCommunity_idx());
    model.addAttribute("user_idx", user.getUser_idx());
    model.addAttribute("post", post);
    return "board/postDetail";
  }
  
  @GetMapping("/api/post/{id}")
  @ResponseBody
  public CommDTO getPostById(@PathVariable("id") int id) {
    return commService.getPostById(id);
  }

  @GetMapping("/newPost")
public String showNewPostForm(Model model) {
    if (!commService.isUserLoggedIn()) {
        return "redirect:/user/login_join";
    }
    model.addAttribute("post", new CommDTO());
    return "board/newPost";
  }

  @PostMapping("/saveTextPost")
  public String saveTextPost(@ModelAttribute("post") CommDTO post, Model model) {
    if (!commService.isUserLoggedIn()) {
        return "redirect:/user/login_join";
    }

    int savedPostId = commService.saveTextPost(post);
    return "redirect:/board/post/" + savedPostId;
  }

  @PostMapping("/saveImagePost")
  public String saveImagePost(@ModelAttribute("post") CommDTO post, @RequestParam("file_upload") MultipartFile file, Model model) {
    if (!commService.isUserLoggedIn()) {
        return "redirect:/user/login_join";
    }

    if (file.isEmpty()) {
        model.addAttribute("error", "파일이 선택되지 않았습니다.");
        return "board/newPost";
    }

    int savedPostId = commService.saveImagePost(post, file);
    return "redirect:/board/post/" + savedPostId;
   }
    
  @PostMapping("/saveRankingPost")
  public String saveRankingPost(@ModelAttribute("post") CommDTO post, @RequestParam("options") List<String> options, Model model) {
    if (!commService.isUserLoggedIn()) {
        return "redirect:/user/login_join";
    }

    int savedPostId = commService.saveRankingPost(post, options);
    return "redirect:/board/post/" + savedPostId;
  }

  @PostMapping("/savePromotionPost")
  public String savePromotionPost(@ModelAttribute("post") CommDTO post, @RequestParam("promotion_file") MultipartFile file, Model model) {
    if (!commService.isUserLoggedIn()) {
        return "redirect:/user/login_join";
    }

    if (file.isEmpty()) {
        model.addAttribute("error", "파일이 선택되지 않았습니다.");
        return "board/newPost";
    }

    int savedPostId = commService.savePromotionPost(post, file);
    return "redirect:/board/post/" + savedPostId;
  }
    
  @GetMapping("/editPost/{id}")
  public String showEditPostForm(@PathVariable int id, Model model) {
    if (!commService.isUserLoggedIn()) {
        return "redirect:/user/login_join";
    }

    CommDTO post = commService.getPostById(id);

    // 수정 권한 확인
    if (!commService.isUserAuthor(id)) {
        model.addAttribute("error", "수정할 권한이 없습니다.");
        return "redirect:/board/community";
    }

    model.addAttribute("post", post);
    return "board/editPost";
    }
    
  @PostMapping("/editPost/{id}")
  public String editPost(@PathVariable("id") int id, @ModelAttribute("post") CommDTO post, 
      @RequestParam(value = "file_upload", required = false) MultipartFile file,
      @RequestParam(value = "options", required = false) List<String> options, 
      @RequestParam(value = "promotion_file", required = false) MultipartFile promotion_file, Model model) {

    if (!commService.isUserLoggedIn()) {
      System.out.println("사용자가 로그인되어 있지 않습니다.");
      return "redirect:/user/login_join";
    }

    if (!commService.isUserAuthor(id)) {
      model.addAttribute("error", "수정할 권한이 없습니다.");
      System.out.println("사용자가 게시물의 작성자가 아닙니다.");
      return "redirect:/board/community";
    }

    String category = post.getCommunity_category();

    if ("my_ranking".equalsIgnoreCase(category)) {
      if (options != null && !options.isEmpty()) {
        commService.updateRankingPost(post, options);
      } else {
        model.addAttribute("error", "옵션이 선택되지 않았습니다.");
        return "board/editPost";
      }
    } else if ("promotion".equalsIgnoreCase(category)) {
      if (promotion_file != null && !promotion_file.isEmpty()) {
        commService.updatePromotionPost(post, promotion_file);  // 이름 일치시키기
      } else {
        model.addAttribute("error", "파일이 선택되지 않았습니다.");
        System.out.println("파일이 선택되지 않았습니다.");
        return "board/editPost";
      }
    } else {
      commService.updatePost(post, file);
    }
    
    model.addAttribute("post", commService.getPostById(id));	
    return "redirect:/board/post/" + id;
  }

  @PostMapping("/deletePost/{id}")
  public String deletePost(@PathVariable int id, Model model) {
    if (!commService.isUserLoggedIn()) {
        return "redirect:/user/login_join";
    }

    if (!commService.isUserAuthor(id)) {
        model.addAttribute("error", "삭제할 권한이 없습니다.");
        return "redirect:/board/community";
    }

    commService.deletePost(id);
    return "redirect:/board/community";
  }
    
  @PostMapping("/scrap")
  public String scrap(
      @RequestParam("user_idx") int user_idx,
      @RequestParam("community_idx") int community_idx,
      @RequestParam(value = "scrapCheck", defaultValue = "false") String  scrapCheckStr,
      Model model) {

  	boolean scrapCheck = Boolean.parseBoolean(scrapCheckStr);

    if (!scrapCheck) {
      // 스크랩 추가 로직
      commService.insertScrap(user_idx, community_idx);
      model.addAttribute("community_idx", community_idx);
      model.addAttribute("user_idx", user_idx);
      return "board/scrap_success";
    } else {
      // 스크랩 삭제 로직
      commService.deleteScrap(user_idx, community_idx);
      model.addAttribute("community_idx", community_idx);
      model.addAttribute("user_idx", user_idx);
      return "board/scrap_delete";
    }
  }
  
  @PostMapping("/scrap-async")
  @ResponseBody
  public ResponseEntity<Map<String, Object>> scrapAsync(
      @RequestParam("community_idx") int communityIdx,
      @RequestParam("user_idx") int userIdx,
      HttpServletRequest request) {
      Map<String, Object> response = new HashMap<>();
      
      // 로그인 상태 확인
      UserDTO user = (UserDTO) request.getSession().getAttribute("loginUserDTO");
      if (user == null || user.getUser_idx() != userIdx) {
          response.put("success", false);
          response.put("message", "로그인이 필요한 서비스입니다.");
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
      }

      try {
          boolean isScraped = commService.toggleScrap(userIdx, communityIdx);
          response.put("success", true);
          response.put("scraped", isScraped);
          response.put("message", isScraped ? "게시글을 스크랩했습니다." : "스크랩을 취소했습니다.");
          return ResponseEntity.ok(response);
      } catch (Exception e) {
          response.put("success", false);
          response.put("message", "스크랩 처리 중 오류가 발생했습니다: " + e.getMessage());
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
      }
  }
  
}
