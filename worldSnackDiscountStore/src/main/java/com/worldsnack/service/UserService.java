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
		int userGrade = userDAO.getMyGrade(user_idx);
		
		List<String> gradeNameAndClass = new ArrayList<>();
		String gradeName = "";
		String gradeClass = "";
		
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
			
		default:
			gradeName = "알 수 없는";
			gradeClass = "grade-unknow";
		}
		
    gradeNameAndClass.add(gradeName);
    gradeNameAndClass.add(gradeClass);
		return gradeNameAndClass;
	}

}
