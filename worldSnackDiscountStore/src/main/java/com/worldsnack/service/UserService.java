package com.worldsnack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worldsnack.dao.UserDAO;
import com.worldsnack.dto.UserDTO;

@Service
public class UserService {
	
	@Autowired
	private UserDAO userDAO;
	
	public UserDTO testList(String user_id) {
		return userDAO.testList(user_id);
	}
	
}
