package com.worldsnack.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.worldsnack.dto.UserDTO;
import com.worldsnack.mapper.UserMapper;

@Repository
public class UserDAO {
	
	@Autowired
	private UserMapper userMapper;
	
	public UserDTO testList(String user_id) {
		UserDTO userDTO = userMapper.testList(user_id);
		System.out.println("userDTO : " + userDTO);
		return userDTO;
	}
	
}
