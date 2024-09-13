package com.worldsnack.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserDTO {
	
	private int user_idx; 
	
	@Size(min=2, max=4)
	@Pattern(regexp="[가-힣]*")
	private String user_name;
	
	@Size(min=4, max=30)
	@Pattern(regexp="[a-zA-Z0-9]*")
	private String user_id;
	
	@Size(min=4, max=30)
	@Pattern(regexp="[a-zA-Z0-9]*")
	private String user_pw;
	
	@Size(min=4, max=30)
	@Pattern(regexp="[a-zA-Z0-9]*")
	private String user_pw2;
	
	@Size(min=5, max=30)
	@Email
  @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
	private String user_email;
	
	@Size(min=2, max=10)
	@Pattern(regexp="[가-힣a-zA-Z0-9-_]*")
	private String user_nickname;
	
	private int user_content_count;
	
	private Date user_first_join;
	
	private List<String> user_gradeNameAndClass;
	
	private boolean userIdExist;
	
	private boolean userIsLogin;
}
