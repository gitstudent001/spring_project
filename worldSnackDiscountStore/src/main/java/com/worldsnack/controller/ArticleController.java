package com.worldsnack.controller;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.worldsnack.dto.CommDTO;
import com.worldsnack.dto.UserDTO;
import com.worldsnack.service.ArticleService;

@Controller
@RequestMapping("/article")
public class ArticleController {

  @Autowired
  private ArticleService articleService;

  @GetMapping("/view/{id}")
  public String showArticleDetail(@PathVariable int id, HttpServletRequest request, Model model) {
    UserDTO user = (UserDTO) request.getSession().getAttribute("loginUserDTO");
    
    // 조회수 증가를 위해 서비스 메서드 호출
    CommDTO post = articleService.getArticleById(id, request, user);

    model.addAttribute("post", post);
    return "board/postDetail"; 
  }
  
  @PostMapping("/vote")
  public ResponseEntity<?> handleVote(@RequestParam("id") int id, @RequestParam("voteType") String voteType) {
      try {
          int newVoteCount;
          if ("upvote".equals(voteType)) {
              newVoteCount = articleService.upvoteArticle(id);
          } else if ("downvote".equals(voteType)) {
              newVoteCount = articleService.downvoteArticle(id);
          } else {
              return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Invalid vote type."));
          }

          // JSON 형식으로 새 투표 수 반환
          return ResponseEntity.ok(Map.of("success", true, "newVoteCount", newVoteCount));
      } catch (Exception e) {
      		e.printStackTrace();
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("success", false, "message", "Internal server error."));
      }
  }
}
