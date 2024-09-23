package com.worldsnack.dao;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

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
  public CommentDTO addComment(CommentDTO commentDto) {
  	commentMapper.addComment(commentDto);
	  return commentMapper.getCommentById(commentDto.getComment_idx());
  }

  // 댓글 ID로 특정 댓글 조회
  public CommentDTO getCommentById(@PathVariable("id") int commentIdx) {
  	CommentDTO comment = commentMapper.getCommentById(commentIdx);
    return comment;
  }

  // 모든 댓글 조회 (삭제되지 않은 댓글만)
  public List<CommentDTO> getAllComments() {
    return commentMapper.getAllComments();
  }

  // 특정 게시물의 모든 댓글 조회 (계층 구조로 조회)
  public List<CommentDTO> getCommentsByPostId(int postId) {
    List<CommentDTO> comments = commentMapper.selectCommentsByPostId(postId);
    return comments != null ? comments : Collections.emptyList(); // null이면 빈 리스트 반환
  }

  // 특정 게시물의 댓글 조회 (부분 로딩 및 동적 로딩을 위한 페이징 처리)
  public List<CommentDTO> getCommentsByPostIdWithPagination(int postId, int offset, int limit) {
    List<CommentDTO> comments = commentMapper.selectCommentsByPostIdWithPagination(postId, offset, limit);
    return comments != null ? comments : Collections.emptyList();
  }
  
  // 특정 게시물의 댓글 수 조회 메서드
  public int countCommentsByPostId(int postId) {
    return commentMapper.countCommentsByPostId(postId);
  }
  
  // 댓글 수 업데이트 메서드
  public void updateCommentCount(int postId, int commentCount) {
    commentMapper.updateCommentCount(postId, commentCount);
  }

  // 특정 댓글의 대댓글(답글)만 조회
  public List<CommentDTO> getRepliesByCommentId(int parentCommentId) {
    List<CommentDTO> replies = commentMapper.selectRepliesByCommentId(parentCommentId);
    return replies != null ? replies : Collections.emptyList();
  }
  
  //대댓글 추가
  public void addReply(CommentDTO commentDto) {
    commentMapper.addReply(commentDto);
  }

  // 댓글 수정 DAO
  public void updateComment(CommentDTO commentDto) {
    commentMapper.updateComment(commentDto);
  }

  // 댓글 삭제 (논리 삭제)
  public void deleteComment(int commentIdx) {
    commentMapper.deleteComment(commentIdx);
  }

  // 댓글 숨기기
  public void hideComment(int commentIdx) {
    commentMapper.hideComment(commentIdx);
  }

  // 댓글 보이기
  public void unhideComment(int commentIdx) {
    commentMapper.unhideComment(commentIdx);
  }

  // 댓글 신고
  public void reportComment(int commentIdx) {
    commentMapper.reportComment(commentIdx);
  }
  
  // 댓글에 대한 투표를 추가하는 메서드
  public void insertVote(int commentIdx, int userIdx, Integer voteType) {
    commentMapper.insertVote(commentIdx, userIdx, voteType);
  }

  // 댓글에 대한 투표를 업데이트하는 메서드
  public void updateVote(int commentIdx, int userIdx, Integer voteType) {
    commentMapper.updateVote(commentIdx, userIdx, voteType);
  }

 // 특정 댓글에 대한 사용자의 투표 타입을 확인하는 메서드
  public String checkVoteType(int commentIdx, int userIdx) {
    return commentMapper.checkVoteType(commentIdx, userIdx);
  }
  
  // 댓글 업보트 증가
  public void upvoteComment(int commentIdx) {
    commentMapper.upvoteComment(commentIdx);
  }

  // 댓글 다운보트 증가
  public void downvoteComment(int commentIdx) {
    commentMapper.downvoteComment(commentIdx);
  }

  // 댓글 업보트 취소
  public void cancelUpvote(int commentIdx) {
    commentMapper.cancelUpvote(commentIdx);
  }

  // 댓글 다운보트 취소
  public void cancelDownvote(int commentIdx) {
    commentMapper.cancelDownvote(commentIdx);
  }

  // 특정 게시물의 댓글 조회 (정렬: 최신순)
  public List<CommentDTO> getCommentsByPostIdSortedByDate(int postId) {
    List<CommentDTO> comments = commentMapper.selectCommentsByPostIdSortedByDate(postId);
    return comments != null ? comments : Collections.emptyList(); // null이면 빈 리스트 반환
  }

  // 특정 게시물의 댓글 조회 (정렬: 업보트순)
  public List<CommentDTO> getCommentsByPostIdSortedByUpvotes(int postId) {
    List<CommentDTO> comments = commentMapper.selectCommentsByPostIdSortedByUpvotes(postId);
    return comments != null ? comments : Collections.emptyList(); // null이면 빈 리스트 반환
  }

  // 특정 게시물의 댓글 조회 (정렬: 추천순, 업보트 - 다운보트)
  public List<CommentDTO> getCommentsByPostIdSortedByRecommend(int postId) {
    List<CommentDTO> comments = commentMapper.selectCommentsByPostIdSortedByRecommend(postId);
    return comments != null ? comments : Collections.emptyList(); // null이면 빈 리스트 반환
  }
}
