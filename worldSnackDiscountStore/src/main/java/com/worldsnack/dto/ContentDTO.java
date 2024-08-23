package com.worldsnack.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ContentDTO {

	private int content_idx;
	private String content_subject;
	private String content_text;
	private MultipartFile content_file;
	
	private int content_writer_idx;
	private String content_make;
	private String content_country;
	private String content_prodno;
	
	private int content_prodprice;
	private int content_view;
	private String content_date;
}
