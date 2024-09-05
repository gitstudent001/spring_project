package com.worldsnack.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.worldsnack.dto.CommentDTO;
import com.worldsnack.service.CommentService;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // 특정 게시물의 모든 댓글을 가져오는 엔드포인트 (계층 구조 포함)
    @GetMapping("/post/{id}")
    public List<CommentDTO> getCommentsByPostId(@PathVariable("id") Long postId) {
        List<CommentDTO> comments = commentService.getCommentsByPostId(postId);
        return comments;
    }

    // 특정 게시물의 댓글 가져오기 (무한 스크롤링, 페이지네이션 지원)
    @GetMapping("/post/{id}/page")
    public List<CommentDTO> getCommentsByPostIdWithPagination(@PathVariable("id") Long postId,
                                                             @RequestParam(value = "offset", defaultValue = "0") int offset,
                                                             @RequestParam(value = "limit", defaultValue = "10") int limit) {
        return commentService.getCommentsByPostIdWithPagination(postId, offset, limit);
    }

    // 특정 게시물의 댓글 가져오기 (정렬 기능 추가: 최신순, 업보트순, 추천순)
    @GetMapping("/post/{id}/sorted")
    public List<CommentDTO> getSortedCommentsByPostId(@PathVariable("id") Long postId,
                                                     @RequestParam(value = "sort", defaultValue = "date") String sort) {
        switch (sort) {
            case "upvotes":
                return commentService.getCommentsByPostIdSortedByUpvotes(postId);
            case "recommend":
                return commentService.getCommentsByPostIdSortedByRecommend(postId);
            default:
                return commentService.getCommentsByPostIdSortedByDate(postId);
        }
    }

    // 특정 댓글의 대댓글(답글)만 가져오기
    @GetMapping("/{id}/replies")
    public List<CommentDTO> getRepliesByCommentId(@PathVariable("id") Long parentCommentId) {
        return commentService.getRepliesByCommentId(parentCommentId);
    }
    
    // 대댓글 추가 메서드
    @PostMapping("/reply")
    public ResponseEntity<Map<String, Object>> addReply(@RequestBody CommentDTO commentDto) {
        Map<String, Object> response = new HashMap<>();
        try {
            commentService.addReply(commentDto);
            response.put("success", true);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "대댓글 추가 중 오류가 발생했습니다: " + e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    // 댓글 추가 
    @PostMapping("")
    public ResponseEntity<Map<String, Object>> addComment(@RequestBody CommentDTO commentDto) {
        Map<String, Object> response = new HashMap<>();
        try {
            // user_idx가 null이 아닌지 확인
            if (commentDto.getUser_idx() == null) {
                throw new IllegalArgumentException("User ID is required and cannot be null.");
            }

            // 댓글 추가 로직 수행
            commentService.addComment(commentDto);
            response.put("success", true);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "댓글 추가 중 오류가 발생했습니다: " + e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    // 댓글 수정
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateComment(
        @PathVariable("id") Long commentId,
        @RequestBody CommentDTO commentDto) {

        // System.out.println("Received commentId: " + commentId + ", currentUserId: " + commentDto.getCurrentUserId());

        Map<String, Object> response = new HashMap<>();
        try {
            CommentDTO updatedComment = commentService.updateComment(commentId, commentDto, commentDto.getCurrentUserId());
            response.put("success", true);
            response.put("updatedComment", updatedComment);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 댓글 삭제 
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteComment(@PathVariable("id") Long commentId) {
        Map<String, Object> response = new HashMap<>();
        try {
            commentService.deleteComment(commentId);
            response.put("success", true);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "댓글 삭제 중 오류가 발생했습니다: " + e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    // 댓글 투표 (업보트 또는 다운보트)
    @PostMapping("/vote/{id}/{voteType}")
    public ResponseEntity<Map<String, Object>> voteForComment(
        @PathVariable("id") Long commentId, 
        @PathVariable("voteType") String voteType,
        @RequestParam("userId") Long userId) {  // RequestParam으로 userId를 받음
        Map<String, Object> response = new HashMap<>();
        try {
            if (!"upvote".equals(voteType) && !"downvote".equals(voteType)) {
                throw new IllegalArgumentException("Invalid vote type. Must be 'upvote' or 'downvote'.");
            }
            
            // 서비스 레이어의 메서드를 호출하여 투표 로직을 처리
            commentService.voteForComment(commentId, userId, voteType);
            
            response.put("success", true);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "투표 중 오류가 발생했습니다: " + e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    // 댓글 숨기기
    @PostMapping("/{id}/hide")
    public ResponseEntity<Map<String, Object>> hideComment(@PathVariable("id") Long commentId) {
        Map<String, Object> response = new HashMap<>();
        try {
            commentService.hideComment(commentId);
            response.put("success", true);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "댓글 숨기기 중 오류가 발생했습니다: " + e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    // 댓글 보이기
    @PostMapping("/{id}/unhide")
    public ResponseEntity<Map<String, Object>> unhideComment(@PathVariable("id") Long commentId) {
        Map<String, Object> response = new HashMap<>();
        try {
            commentService.unhideComment(commentId);
            response.put("success", true);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "댓글 보이기 중 오류가 발생했습니다: " + e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    // 댓글 신고
    @PostMapping("/{id}/report")
    public ResponseEntity<Map<String, Object>> reportComment(@PathVariable("id") Long commentId) {
        Map<String, Object> response = new HashMap<>();
        try {
            commentService.reportComment(commentId);
            response.put("success", true);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "댓글 신고 중 오류가 발생했습니다: " + e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

}
