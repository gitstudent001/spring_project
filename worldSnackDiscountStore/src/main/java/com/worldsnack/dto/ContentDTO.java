package com.worldsnack.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ContentDTO {

	private int content_idx;
	
	private int category_idx;
	
	@NotBlank
	@Size(min=2, max=50)
	private String content_subject;
	
	@NotBlank
	private String content_text;
	
	private MultipartFile uploadFile;
	
	private String content_file;
	private int content_writer_idx;
	
	@NotBlank
	@Size(min=1, max=20)
	private String content_make;
	
	@NotBlank
	@Size(min=1, max=20)
	private String content_country;
	
	private String content_prodno;
	private int content_prodprice;
	private int content_view;
	private Date content_date;
	
	
	// 카테고리명
	private String category_name;
	
	// 작성자 닉네임
	private String content_writer_nickname;
	// 스크랩 수
	private int scrap_count;
}
