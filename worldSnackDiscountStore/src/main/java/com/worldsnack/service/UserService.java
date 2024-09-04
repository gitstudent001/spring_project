package com.worldsnack.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worldsnack.dao.UserDAO;
import com.worldsnack.dto.UserDTO;

@Service
public class UserService {
	
	@Autowired
	private UserDAO userDAO;
	
	@Resource(name="loginUserDTO")
	private UserDTO loginUserDTO;
	
	public boolean checkUserIdExist(String user_id) {
		String user_name = userDAO.checkUserIdExist(user_id);
		
		if(user_name == null) {
			return true;
		}
		return false;
	}

	public void insertUser(UserDTO joinUserDTO) {
		userDAO.insertUser(joinUserDTO);
	}
	
	public UserDTO getLoginUserInfo(UserDTO validationLoginUserDTO) {
		UserDTO tmpUserDTO = userDAO.getLoginUserInfo(validationLoginUserDTO);
		
		if(tmpUserDTO != null) {
			loginUserDTO.setUser_idx(tmpUserDTO.getUser_idx());
			loginUserDTO.setUser_name(tmpUserDTO.getUser_name());
			loginUserDTO.setUserIsLogin(true);
		}
		return loginUserDTO;
	}
	
	// ---------------------- 내 등급 조회(희만) ---------------------	
	public List<String> getMyGrade(int user_idx) {
		Integer userGrade = userDAO.getMyGrade(user_idx);
		
		if (userGrade == null || userGrade < 1 || userGrade > 6) {
      userGrade = 1;  // 기본 등급 "맛동산"으로 설정
		}
		
		List<String> gradeNameAndClass = new ArrayList<>();
		String gradeName = "";
		String gradeClass = "";
		//System.out.println("유저 등급 : " + userGrade);
		switch (userGrade) {
		case 1:
			gradeName = "맛동산";
			gradeClass = "grade-one";
			break;
		case 2:
			gradeName = "프링글스";
			gradeClass = "grade-two";
			break;
		case 3:
			gradeName = "포카칩";
			gradeClass = "grade-three";
			break;
		case 4:
			gradeName = "새우깡";
			gradeClass = "grade-four";
			break;
		case 5:
			gradeName = "홈런볼";
			gradeClass = "grade-five";
			break;
		case 6:
			gradeName = "꼬깔콘";
			gradeClass = "grade-six";
			break;
		}
		//System.out.println("등급 이름 : " + gradeName);
		//System.out.println("등급 클래스 : " + gradeClass);
    gradeNameAndClass.add(gradeName);
    gradeNameAndClass.add(gradeClass);
		return gradeNameAndClass;
	}
	
	// 등급 분류용 content_count 증가 (희만)
	public void increaseContentCountForGrade(int user_idx) {
		userDAO.increaseContentCountForGrade(user_idx);
	}

}
