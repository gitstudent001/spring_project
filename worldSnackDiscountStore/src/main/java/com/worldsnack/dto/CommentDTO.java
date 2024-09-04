package com.worldsnack.dto;

import java.util.Date;

import lombok.Data;

@Data
public class CommentDTO {
	private Long comment_idx;
	private Long post_id;
  private Long user_idx;
  private Long parent_comment_idx;  // 부모 댓글 ID, null 가능
  private String comment_text;
  private Date comment_date;
  private Date comment_update_date; // 수정된 시간, null 가능
  
  private String user_nickname;
  private int comment_level;        // 댓글의 계층 구조
  private String is_deleted;        // 댓글 삭제 여부 ( Y or N, 기본값 N)
  private String is_hidden;         // 댓글 숨기기 여부 ('Y' or 'N', 기본값 'N')
  private int upvote_count;
  private int downvote_count;
  private int reported_count;       // 댓글이 신고된 수
}
