package com.worldsnack.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.worldsnack.dto.UserDTO;

public interface UserMapper {
	
	@Select("SELECT USER_NAME " +
					"FROM USER_TABLE " +
					"WHERE USER_ID=#{user_id}")
	String checkUserIdExist(String user_id);
	
	@Insert("INSERT INTO USER_TABLE (USER_IDX, USER_NAME, USER_ID, USER_PW, USER_EMAIL, USER_NICKNAME) " +
					"VALUES(USER_SEQ.NEXTVAL, #{user_name}, #{user_id}, #{user_pw}, #{user_email}, #{user_nickname})")
	void insertUser(UserDTO joinUserDTO);
	
	@Select("SELECT * " +
					"FROM USER_TABLE " +
					"WHERE USER_ID=#{user_id} AND USER_PW=#{user_pw}")
	UserDTO getLoginUserInfo(UserDTO validationLoginUserDTO);

	
	// ---------------------- 내 등급 조회용 매퍼(희만) ---------------------	
	@Select("SELECT G.GRADE_IDX " +
		  		"FROM USER_TABLE U JOIN GRADE_TABLE G " +
	    		"ON U.USER_CONTENT_COUNT BETWEEN G.GRADE_LOW AND G.GRADE_HIGH " +
					"WHERE USER_IDX = #{user_idx}")
	int getMyGrade(int user_idx);
	
	
	
	
	
	
	
}
