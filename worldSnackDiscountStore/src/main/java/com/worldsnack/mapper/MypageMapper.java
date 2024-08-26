package com.worldsnack.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import com.worldsnack.dto.ContentDTO;
import com.worldsnack.dto.UserDTO;

public interface MypageMapper {

	// 정보수정을 위한 로그인한 회원의 정보 가져오기
	@Select("SELECT USER_NAME, USER_ID, USER_EMAIL, USER_NICKNAME " +
		  		"FROM USER_TABLE " +
		  		"WHERE USER_IDX = #{user_idx}")
	UserDTO getModifyUserInfo(int user_idx);
	
	// 정보수정 적용을 위한 update 쿼리
	@Update("UPDATE USER_TABLE " +
					"SET USER_NICKNAME = #{user_nickname}, " +
						 	"USER_PW = #{user_pw} " +
					"WHERE USER_IDX = #{user_idx}")
	void modifyUserInfo(UserDTO modifyUserDTO);
	
	
	// 회원 정보 삭제를 위한 delete 쿼리
	@Delete("DELETE USER_TABLE " +
					"WHERE USER_IDX = #{user_idx}")
	void deleteUser(int user_idx);
	
	
	// ---------------------- 내 게시글 조회용 매퍼 ---------------------
	// 내가 올린 게시글 수 조회
	@Select("SELECT COUNT(*) " +
		  		"FROM CONTENT_TABLE " +
		  		"WHERE CONTENT_WRITER_IDX = #{user_idx}")
	String myContentCount(int user_idx);
	
	// 내가 올린 최근 게시 일자 조회
	@Select("SELECT MAX(CONTENT_DATE) " +
					"FROM CONTENT_TABLE " +
					"WHERE CONTENT_WRITER_IDX = #{user_idx}")
	String myContentDate(int user_idx);
	
	// 내가 올린 게시글 미리보기 조회
	@Select("SELECT CONTENT_IDX, CONTENT_SUBJECT, CONTENT_DATE, CONTENT_VIEW " +
					"FROM CONTENT_TABLE " +
					"WHERE CONTENT_WRITER_IDX = #{user_idx}" +
					"ORDER BY CONTENT_IDX DESC")
	List<ContentDTO> myContentPreView(int user_idx, RowBounds rowBounds);
	
	// ---------------------- 내 관심 게시글 조회용 매퍼 ---------------------
	// 내 관심 게시글 미리보기 조회
	@Select("SELECT C.CONTENT_IDX, C.CONTENT_SUBJECT, TO_CHAR(C.CONTENT_DATE, 'YYYY-MM-DD') CONTENT_DATE, C.CONTENT_VIEW " +
		  		"FROM CONTENT_TABLE C JOIN SCRAP_TABLE S " +
	    		"ON C.CONTENT_IDX = S.CONTENT_IDX " +
					"WHERE S.USER_IDX = #{user_idx}")
	List<ContentDTO> myScrapPreview(int user_idx, RowBounds rowBounds);
	
	// 내 관심 개수 조회
	@Select("SELECT COUNT(*) " +
		  		"FROM SCRAP_TABLE " +
		  		"WHERE USER_IDX = #{user_idx}")
	String myScrapCount(int user_idx);
	
}
