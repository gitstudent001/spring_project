package com.worldsnack.dto;

import java.util.Date;

import lombok.Data;

@Data
public class CommentDTO {
	private int comment_idx;					// 댓글 인덱스
	private int post_id;							// 게시글 인덱스
  private int user_idx;						// 작성자 인덱스
  private Integer parent_comment_idx;  // 부모 댓글 인덱스, null 가능
  private String comment_text;			// 댓글 내용
  private Date comment_date;				// 작성된 시간
  private Date comment_update_date; // 수정된 시간, null 가능
  
  private String user_nickname;			// 유저 닉네임
  private int comment_level;        // 댓글의 계층 구조
  private String is_deleted;        // 댓글 삭제 여부 ('Y' or 'N', 기본값 'N')
  private String is_hidden;         // 댓글 숨기기 여부 ('Y' or 'N', 기본값 'N')
  private int upvote_count;					
  private int downvote_count;
  private int reported_count;       // 댓글이 신고된 수
  
  private Integer vote_type; 				// 현재 사용자가 한 투표 타입 ('up' or 'down')
  private int vote_idx;    				// 투표 인덱스
  
  private boolean isAuthor;
  private int currentUserId;
}
