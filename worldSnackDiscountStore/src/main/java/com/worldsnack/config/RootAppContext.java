package com.worldsnack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

import com.worldsnack.dto.UserDTO;

// project 작업할 때 생성하는 bean을 정의하는 클래스
@Configuration
public class RootAppContext {

	// 로그인한 회원 정보를 session scope 에 저장하기
	@Bean("loginUserDTO")
	@SessionScope
	public UserDTO loginUserDTO() {
		return new UserDTO();
	}

}
