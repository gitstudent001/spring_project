package com.worldsnack.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worldsnack.dao.CommentDAO;
import com.worldsnack.dto.CommentDTO;

@Service
public class CommentService {

    private final CommentDAO commentDAO;

    @Autowired
    public CommentService(CommentDAO commentDAO) {
        this.commentDAO = commentDAO;
    }

    // 댓글 추가
    public void addComment(CommentDTO commentDto) {
        commentDAO.insertComment(commentDto);
    }

    // 특정 댓글 조회
    public CommentDTO getComment(Long commentIdx) {
        return commentDAO.getCommentById(commentIdx);
    }

    // 모든 댓글 조회
    public List<CommentDTO> getAllComments() {
        return commentDAO.getAllComments();
    }

    // 특정 게시물의 모든 댓글 조회 (계층 구조로)
    public List<CommentDTO> getCommentsByPostId(Long postId) {
        List<CommentDTO> comments = commentDAO.getCommentsByPostId(postId);
        return comments != null ? comments : Collections.emptyList();
    }

    // 무한 스크롤링 기능을 위한 특정 범위의 댓글 조회
    public List<CommentDTO> getCommentsByPostIdWithPagination(Long postId, int offset, int limit) {
        List<CommentDTO> comments = commentDAO.getCommentsByPostIdWithPagination(postId, offset, limit);
        return comments != null ? comments : Collections.emptyList();
    }

    // 특정 댓글의 대댓글(답글)만 조회 (부분 로딩)
    public List<CommentDTO> getRepliesByCommentId(Long parentCommentId) {
        List<CommentDTO> replies = commentDAO.getRepliesByCommentId(parentCommentId);
        return replies != null ? replies : Collections.emptyList();
    }

    // 댓글 수정
    public void updateComment(CommentDTO commentDto) {
        commentDAO.updateComment(commentDto);
    }

    // 댓글 삭제 (논리 삭제)
    public void deleteComment(Long commentIdx) {
        commentDAO.deleteComment(commentIdx);
    }

    // 댓글 숨기기
    public void hideComment(Long commentIdx) {
        commentDAO.hideComment(commentIdx);
    }

    // 댓글 보이기
    public void unhideComment(Long commentIdx) {
        commentDAO.unhideComment(commentIdx);
    }

    // 댓글 신고
    public void reportComment(Long commentIdx) {
        commentDAO.reportComment(commentIdx);
    }

    // 댓글 업보트
    public void upvoteComment(Long commentIdx) {
        commentDAO.upvoteComment(commentIdx);
    }

    // 댓글 다운보트
    public void downvoteComment(Long commentIdx) {
        commentDAO.downvoteComment(commentIdx);
    }

    // 댓글 업보트 취소
    public void cancelUpvote(Long commentIdx) {
        commentDAO.cancelUpvote(commentIdx);
    }

    // 댓글 다운보트 취소
    public void cancelDownvote(Long commentIdx) {
        commentDAO.cancelDownvote(commentIdx);
    }

    // 특정 게시물의 댓글 조회 (정렬: 최신순)
    public List<CommentDTO> getCommentsByPostIdSortedByDate(Long postId) {
        List<CommentDTO> comments = commentDAO.getCommentsByPostIdSortedByDate(postId);
        return comments != null ? comments : Collections.emptyList();
    }

    // 특정 게시물의 댓글 조회 (정렬: 업보트순)
    public List<CommentDTO> getCommentsByPostIdSortedByUpvotes(Long postId) {
        List<CommentDTO> comments = commentDAO.getCommentsByPostIdSortedByUpvotes(postId);
        return comments != null ? comments : Collections.emptyList();
    }

    // 특정 게시물의 댓글 조회 (정렬: 추천순, 업보트 - 다운보트)
    public List<CommentDTO> getCommentsByPostIdSortedByRecommend(Long postId) {
        List<CommentDTO> comments = commentDAO.getCommentsByPostIdSortedByRecommend(postId);
        return comments != null ? comments : Collections.emptyList();
    }

    // 댓글 작성자의 권한 확인 (자신의 댓글만 수정/삭제 가능)
    public boolean isUserAuthorized(Long commentIdx, Long userIdx) {
        CommentDTO comment = commentDAO.getCommentById(commentIdx);
        return comment != null && comment.getUser_idx().equals(userIdx);
    }
}
