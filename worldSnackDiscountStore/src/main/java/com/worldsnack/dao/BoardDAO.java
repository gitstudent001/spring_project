package com.worldsnack.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.worldsnack.mapper.BoardMapper;

@Repository
public class BoardDAO {

	@Autowired
	private BoardMapper boardMapper;
	
	public void testInsertContent(int user_idx) {
		boardMapper.testInsertContent(user_idx);
	}
}
