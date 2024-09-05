package com.worldsnack.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.worldsnack.dto.UserDTO;
import com.worldsnack.mapper.UserMapper;

@Repository
public class UserDAO {
	
	@Autowired
	private UserMapper userMapper;
	
	public String checkUserIdExist(String user_id) {
		String user_name = userMapper.checkUserIdExist(user_id);
		return user_name;
}

	public void insertUser(UserDTO joinUserDTO) {
		userMapper.insertUser(joinUserDTO);
	}
	
	
	
	public UserDTO getLoginUserInfo(UserDTO validationLoginUserDTO) {
		return userMapper.getLoginUserInfo(validationLoginUserDTO);
	}

	// ---------------------- 내 등급 조회(희만) ---------------------	
	public int getMyGrade(int user_idx) {
		return userMapper.getMyGrade(user_idx);
	}
	
	// 등급 분류용 content_count 증가 (희만)
	public void increaseContentCountForGrade(int user_idx) {
		userMapper.increaseContentCountForGrade(user_idx);
	}
	
	// ---------------------- 나의 활동용 매퍼(희만) ---------------------
	// 로그인 로그 저장
	public void setLoginLog(int user_idx) {
		userMapper.setLoginLog(user_idx);
	}
	
	//로그아웃 로그 저장
	public void setLogoutLog(int user_idx) {
		userMapper.setLogoutLog(user_idx);
	}
	
	// 서버 종료 시 모든 로그인 한 유저 로그아웃 로그 저장
	public void setAllLogoutLog() {
		userMapper.setAllLogoutLog();
	}
}