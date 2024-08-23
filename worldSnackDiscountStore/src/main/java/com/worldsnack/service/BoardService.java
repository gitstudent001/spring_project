package com.worldsnack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worldsnack.dao.BoardDAO;

@Service
public class BoardService {

	@Autowired
	private BoardDAO boardDAO;
	
	public void testInsertContent(int user_idx) {
		for (int i = 0; i <= 50; i++) {
			boardDAO.testInsertContent(user_idx);
			System.out.println("삽입완료.." + i);
		}
	}
}
