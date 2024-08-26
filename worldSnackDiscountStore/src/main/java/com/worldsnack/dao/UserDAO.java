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
	
}