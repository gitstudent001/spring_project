package com.worldsnack.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class CommDTO {
	// 게시글 기본 정보
	private int community_idx;
	private String community_subject;
	private String community_text;
	private String community_file;
	private String community_thumb;
	private MultipartFile file_upload;
	
	// 작성자 정보
	private int community_writer_idx;
	private String community_nickname;
	private String community_date;
	
	// 게시글 타입, 카테고리
	private String community_type;
	private String community_category;
	private String community_url;
	
	// 정렬, 보기 설정
	private String community_sort_order;
	private String community_view_type;
	
	// 통계
	private int community_view;
	private int community_comment;
	
	// 추천
	private int community_upvotes;
  private int community_downvotes;
}
