package com.worldsnack.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import com.worldsnack.dao.CommentDAO;
import com.worldsnack.dto.CommentDTO;

@Service
public class CommentService {

  private final CommentDAO commentDAO;

  @Autowired
  public CommentService(CommentDAO commentDAO) {
    this.commentDAO = commentDAO;
  }

  // 특정 댓글 조회
  public CommentDTO getCommentById(@PathVariable("id") int commentIdx) {
    return commentDAO.getCommentById(commentIdx);
  }
  
  // 모든 댓글 조회
  public List<CommentDTO> getAllComments() {
    return commentDAO.getAllComments();
  }
  
  // 특정 게시물의 댓글 수 조회 메서드
  public int getCommentCountByPostId(int postId) {
    return commentDAO.countCommentsByPostId(postId);
  }
  
  // 댓글 추가 및 댓글 수 업데이트
  public CommentDTO addComment(CommentDTO commentDto) {
  	commentDAO.addComment(commentDto);
  	updateCommentCount(commentDto.getPost_id());
  	return commentDAO.getCommentById(commentDto.getComment_idx());
  }

  // 댓글 삭제 후 댓글 수 업데이트
  public void deleteCommentCount(int commentId) {
    CommentDTO comment = commentDAO.getCommentById(commentId);
    if (comment != null) {
        commentDAO.deleteComment(commentId);
        // 댓글 삭제 후 해당 게시물의 댓글 수 업데이트
        updateCommentCount(comment.getPost_id());
    }
  }

  // 댓글 수 업데이트 메서드
  private void updateCommentCount(int postId) {
    int commentCount = commentDAO.countCommentsByPostId(postId); // 댓글 수 조회
    commentDAO.updateCommentCount(postId, commentCount); // 두 개의 인수를 전달
  }
  
  // 특정 게시물의 모든 댓글 조회 (계층 구조로)
  public List<CommentDTO> getCommentsByPostId(int postId) {
    List<CommentDTO> comments = commentDAO.getCommentsByPostId(postId);
    return comments != null ? comments : Collections.emptyList();
  }

  // 무한 스크롤링 기능을 위한 특정 범위의 댓글 조회
  public List<CommentDTO> getCommentsByPostIdWithPagination(int postId, int offset, int limit) {
    List<CommentDTO> comments = commentDAO.getCommentsByPostIdWithPagination(postId, offset, limit);
    return comments != null ? comments : Collections.emptyList();
  }

  // 댓글 수정
  public CommentDTO updateComment(int commentId, CommentDTO commentDto, int currentUserId) {
  	
  	// 댓글 정보를 수정하고 수정된 댓글 정보를 반환
    commentDto.setComment_idx(commentId);
    commentDAO.updateComment(commentDto);
    
    // 업데이트된 댓글 정보를 가져옴
    CommentDTO updatedComment = commentDAO.getCommentById(commentId);
    
    // 현재 사용자와 댓글 작성자가 동일한지 확인
    boolean isAuthor = updatedComment.getUser_idx() == currentUserId;
    updatedComment.setAuthor(isAuthor);
  	
    return updatedComment;
  }

  // 댓글 삭제 (논리 삭제) 및 댓글 수 업데이트
  public void deleteComment(int commentId) {
    CommentDTO comment = commentDAO.getCommentById(commentId);
    if (comment != null) {
      commentDAO.deleteComment(commentId);
      // 댓글 삭제 후 해당 게시물의 댓글 수 업데이트
      updateCommentCount(comment.getPost_id());
    }
  }
  
  //특정 댓글의 대댓글(답글)만 조회 (부분 로딩)
  public List<CommentDTO> getRepliesByCommentId(int parentCommentId) {
    List<CommentDTO> replies = commentDAO.getRepliesByCommentId(parentCommentId);
    return replies != null ? replies : Collections.emptyList();
  }
 
  // 대댓글 추가 메서드
  public CommentDTO addReply(CommentDTO commentDto) {
  	commentDto.setComment_level(commentDAO.getCommentById(commentDto.getParent_comment_idx()).getComment_level() + 1);
  	// DAO 호출하여 대댓글 추가
    commentDAO.addReply(commentDto);
    // 새로 생성된 대댓글의 comment_idx를 commentDto에서 가져옴
    int newCommentId = commentDto.getComment_idx();
    // 새로 생성된 대댓글을 가져와서 반환
    CommentDTO createdReply = commentDAO.getCommentById(newCommentId);

    return createdReply;
  }

  // 댓글 숨기기
  public void hideComment(int commentIdx) {
    commentDAO.hideComment(commentIdx);
  }

  // 댓글 보이기
  public void unhideComment(int commentIdx) {
    commentDAO.unhideComment(commentIdx);
  }

  // 댓글 신고
  public void reportComment(int commentIdx) {
    commentDAO.reportComment(commentIdx);
  }
  
  @Transactional
  public void voteForComment(int commentIdx, int userIdx, String voteType) {
    // String voteType을 Integer로 변환합니다.
    Integer voteTypeInt = "upvote".equals(voteType) ? 1 : 0;

    String existingVoteType = commentDAO.checkVoteType(commentIdx, userIdx);

    if (existingVoteType != null) {
      if (!existingVoteType.equals(voteTypeInt.toString())) {
        // 다른 타입으로 이미 투표한 경우 업데이트
        commentDAO.updateVote(commentIdx, userIdx, voteTypeInt);
        }
    } else {
        // 새로운 투표 추가
        commentDAO.insertVote(commentIdx, userIdx, voteTypeInt);
    }

    // 댓글의 업보트/다운보트 수 업데이트
    if (voteTypeInt == 1) {
      commentDAO.upvoteComment(commentIdx);
    } else if (voteTypeInt == 0) {
      commentDAO.downvoteComment(commentIdx);
    }
  }
  
  // 댓글 업보트
  public void upvoteComment(int commentIdx) {
    commentDAO.upvoteComment(commentIdx);
  }

  // 댓글 다운보트
  public void downvoteComment(int commentIdx) {
    commentDAO.downvoteComment(commentIdx);
  }

  // 댓글 업보트 취소
  public void cancelUpvote(int commentIdx) {
    commentDAO.cancelUpvote(commentIdx);
  }

  // 댓글 다운보트 취소
  public void cancelDownvote(int commentIdx) {
    commentDAO.cancelDownvote(commentIdx);
  }

  // 특정 게시물의 댓글 조회 (정렬: 최신순)
  public List<CommentDTO> getCommentsByPostIdSortedByDate(int postId) {
    List<CommentDTO> comments = commentDAO.getCommentsByPostIdSortedByDate(postId);
    return comments != null ? comments : Collections.emptyList();
  }

  // 특정 게시물의 댓글 조회 (정렬: 업보트순)
  public List<CommentDTO> getCommentsByPostIdSortedByUpvotes(int postId) {
    List<CommentDTO> comments = commentDAO.getCommentsByPostIdSortedByUpvotes(postId);
    return comments != null ? comments : Collections.emptyList();
  }

  // 특정 게시물의 댓글 조회 (정렬: 추천순, 업보트 - 다운보트)
  public List<CommentDTO> getCommentsByPostIdSortedByRecommend(int postId) {
    List<CommentDTO> comments = commentDAO.getCommentsByPostIdSortedByRecommend(postId);
    return comments != null ? comments : Collections.emptyList();
  }

  // 댓글 작성자의 권한 확인 (자신의 댓글만 수정/삭제 가능)
  public boolean isUserAuthorized(int commentIdx, int userIdx) {
    CommentDTO comment = commentDAO.getCommentById(commentIdx);
    return comment != null && comment.getUser_idx() == userIdx;
  }
    
    
}
