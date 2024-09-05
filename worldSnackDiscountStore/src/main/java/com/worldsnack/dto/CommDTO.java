package com.worldsnack.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class CommDTO {
	private int community_idx;
	private String community_subject;
	private String community_text;
	private String community_file;
	private MultipartFile file_upload;
	
	private int community_writer_idx;
	private String community_nickname;
	private String community_date;
	
	private String community_category;
	private String community_url;
	
	private String community_sort_order;
	private String community_view_type;
	private int community_view;
	private int community_comment;
	private String community_thumb;
	
	private int community_upvotes;
  private int community_downvotes;
  private double wilsonScore;
	
}
