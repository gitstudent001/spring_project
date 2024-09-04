package com.worldsnack.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
      Model model) {
        
      List<CommDTO> posts = commService.getPostsByCategoryAndSortOrder(category, sortOrder, viewType);
      
      model.addAttribute("posts", posts);
      model.addAttribute("sortOrder", sortOrder);
      model.addAttribute("viewType", viewType);
      
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
      
        model.addAttribute("post", post);
        return "board/postDetail";
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
}
