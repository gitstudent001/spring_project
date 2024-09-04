package com.worldsnack.dao;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.worldsnack.dto.CommentDTO;
import com.worldsnack.mapper.CommentMapper;

@Repository
public class CommentDAO {

    private final CommentMapper commentMapper;

    @Autowired
    public CommentDAO(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    // 댓글 추가
    public void insertComment(CommentDTO commentDto) {
        commentMapper.insertComment(commentDto);
    }

    // 댓글 ID로 특정 댓글 조회
    public CommentDTO getCommentById(Long commentIdx) {
        return commentMapper.getCommentById(commentIdx);
    }

    // 모든 댓글 조회 (삭제되지 않은 댓글만)
    public List<CommentDTO> getAllComments() {
        return commentMapper.getAllComments();
    }

    // 특정 게시물의 모든 댓글 조회 (계층 구조로 조회)
    public List<CommentDTO> getCommentsByPostId(Long postId) {
        List<CommentDTO> comments = commentMapper.selectCommentsByPostId(postId);
        return comments != null ? comments : Collections.emptyList(); // null이면 빈 리스트 반환
    }

    // 특정 게시물의 댓글 조회 (부분 로딩 및 동적 로딩을 위한 페이징 처리)
    public List<CommentDTO> getCommentsByPostIdWithPagination(Long postId, int offset, int limit) {
        List<CommentDTO> comments = commentMapper.selectCommentsByPostIdWithPagination(postId, offset, limit);
        return comments != null ? comments : Collections.emptyList();
    }

    // 특정 댓글의 대댓글(답글)만 조회
    public List<CommentDTO> getRepliesByCommentId(Long parentCommentId) {
        List<CommentDTO> replies = commentMapper.selectRepliesByCommentId(parentCommentId);
        return replies != null ? replies : Collections.emptyList();
    }

    // 댓글 수정
    public void updateComment(CommentDTO commentDto) {
        commentMapper.updateComment(commentDto);
    }

    // 댓글 삭제 (논리 삭제)
    public void deleteComment(Long commentIdx) {
        commentMapper.deleteComment(commentIdx);
    }

    // 댓글 숨기기
    public void hideComment(Long commentIdx) {
        commentMapper.hideComment(commentIdx);
    }

    // 댓글 보이기
    public void unhideComment(Long commentIdx) {
        commentMapper.unhideComment(commentIdx);
    }

    // 댓글 신고
    public void reportComment(Long commentIdx) {
        commentMapper.reportComment(commentIdx);
    }

    // 댓글 업보트 증가
    public void upvoteComment(Long commentIdx) {
        commentMapper.upvoteComment(commentIdx);
    }

    // 댓글 다운보트 증가
    public void downvoteComment(Long commentIdx) {
        commentMapper.downvoteComment(commentIdx);
    }

    // 댓글 업보트 취소
    public void cancelUpvote(Long commentIdx) {
        commentMapper.cancelUpvote(commentIdx);
    }

    // 댓글 다운보트 취소
    public void cancelDownvote(Long commentIdx) {
        commentMapper.cancelDownvote(commentIdx);
    }

    // 특정 게시물의 댓글 조회 (정렬: 최신순)
    public List<CommentDTO> getCommentsByPostIdSortedByDate(Long postId) {
        List<CommentDTO> comments = commentMapper.selectCommentsByPostIdSortedByDate(postId);
        return comments != null ? comments : Collections.emptyList(); // null이면 빈 리스트 반환
    }

    // 특정 게시물의 댓글 조회 (정렬: 업보트순)
    public List<CommentDTO> getCommentsByPostIdSortedByUpvotes(Long postId) {
        List<CommentDTO> comments = commentMapper.selectCommentsByPostIdSortedByUpvotes(postId);
        return comments != null ? comments : Collections.emptyList(); // null이면 빈 리스트 반환
    }

    // 특정 게시물의 댓글 조회 (정렬: 추천순, 업보트 - 다운보트)
    public List<CommentDTO> getCommentsByPostIdSortedByRecommend(Long postId) {
        List<CommentDTO> comments = commentMapper.selectCommentsByPostIdSortedByRecommend(postId);
        return comments != null ? comments : Collections.emptyList(); // null이면 빈 리스트 반환
    }
}
