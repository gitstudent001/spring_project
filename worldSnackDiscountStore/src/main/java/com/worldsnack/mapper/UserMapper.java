package com.worldsnack.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
	
	// 등급 분류용 content_count 증가 (희만)
	@Update("UPDATE USER_TABLE " +
					"SET USER_CONTENT_COUNT = (SELECT USER_CONTENT_COUNT " +
																		"FROM USER_TABLE " +
                              			"WHERE USER_IDX = #{user_idx}) + 1 " +
					"WHERE USER_IDX = #{user_idx}")
	void increaseContentCountForGrade(int user_idx);
	
	// ---------------------- 나의 활동용 매퍼(희만) ---------------------
	// 로그인 로그 저장
	@Update({"BEGIN",
    			 "	UPDATE LOGIN_LOG_TABLE ",
  			 	 "	SET LOGIN_END_DATE = SYSDATE ",
  			 	 "	WHERE USER_IDX = #{user_idx} ",
  			 	 "	AND USER_IDX IS NOT NULL ",
  			 	 "	AND LOGIN_START_DATE IS NOT NULL ",
  			 	 "	AND LOGIN_END_DATE IS NULL;",
    
  			 	 "	INSERT INTO LOGIN_LOG_TABLE (USER_IDX, LOGIN_START_DATE) ",
  			 	 "	VALUES (#{user_idx}, SYSDATE);",
      
  			 	 "	COMMIT;",
  			 	 "END;"})
	void setLoginLog(int user_idx);
	
	//로그아웃 로그 저장
	@Update("UPDATE LOGIN_LOG_TABLE " +
					"SET LOGIN_END_DATE = SYSDATE " +
					"WHERE USER_IDX = #{user_idx} " +
					"AND USER_IDX IS NOT NULL " +
					"AND LOGIN_START_DATE IS NOT NULL " +
					"AND LOGIN_END_DATE IS NULL")
	void setLogoutLog(int user_idx);
	
	// 서버 종료 시 모든 로그인 한 유저 로그아웃 로그 저장
	@Update("UPDATE LOGIN_LOG_TABLE " +
					"SET LOGIN_END_DATE = SYSDATE " +
					"WHERE USER_IDX IS NOT NULL " +
					"AND LOGIN_START_DATE IS NOT NULL " +
					"AND LOGIN_END_DATE IS NULL")
	void setAllLogoutLog();
	
}
