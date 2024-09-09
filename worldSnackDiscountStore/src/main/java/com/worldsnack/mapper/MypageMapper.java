package com.worldsnack.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import com.worldsnack.dto.CommDTO;
import com.worldsnack.dto.CommentDTO;
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
	Date myContentDate(int user_idx);
	
	// 내가 올린 게시글 미리보기 조회
	@Select("SELECT CONTENT_IDX, CONTENT_SUBJECT, CONTENT_DATE, CONTENT_VIEW " +
					"FROM CONTENT_TABLE " +
					"WHERE CONTENT_WRITER_IDX = #{user_idx}" +
					"ORDER BY CONTENT_IDX DESC")
	List<ContentDTO> myContentPreView(int user_idx, RowBounds rowBounds);
	
	// ---------------------- 내 관심 게시글 조회용 매퍼 ---------------------
	// 내 관심 게시글 미리보기 조회 (선오형꺼 활용)
	@Select("SELECT * "
  			+ "FROM CONTENT_TABLE CO "
  			+ "INNER JOIN CATEGORY_TABLE CA "
  			+ "ON CO.CATEGORY_IDX = CA.CATEGORY_IDX "
  			+ "INNER JOIN SCRAP_TABLE ST " 
  			+ "ON CO.CONTENT_IDX = ST.CONTENT_IDX "
	   		+ "WHERE ST.USER_IDX = #{user_idx} "
	  		+ "ORDER BY CONTENT_DATE DESC")
	List<ContentDTO> myScrapPreviewAll(int user_idx, RowBounds rowBounds);
	
	//내 관심 게시글 미리보기 조회 (선택한 카테고리) (선오형꺼 활용)
	@Select("SELECT * "
    			+ "FROM CONTENT_TABLE CO "
    			+ "INNER JOIN CATEGORY_TABLE CA "
    			+ "ON CO.CATEGORY_IDX = CA.CATEGORY_IDX "
					+ "INNER JOIN SCRAP_TABLE ST " 
					+ "ON CO.CONTENT_IDX = ST.CONTENT_IDX "
					+ "WHERE CO.CATEGORY_IDX = #{category_idx} "
					+ "AND ST.USER_IDX = #{user_idx} "
					+ "ORDER BY CONTENT_DATE DESC")	
	public List<ContentDTO> myScrapPreviewSelect(@Param("user_idx") int user_idx,
																							 @Param("rowBounds") RowBounds rowBounds,
																							 @Param("category_idx") int category_idx);
	
	// 내 전체 스크랩 개수 조회
	@Select("SELECT COUNT(*) " +
		  		"FROM SCRAP_TABLE " +
		  		"WHERE USER_IDX = #{user_idx}")
	String myScrapCount(int user_idx);
	
	// 페이지네이션용 카테고리별 스크랩 개수 조회 (전체)
	@Select("SELECT COUNT(*) "
  			+ "FROM CONTENT_TABLE CO "
  			+ "INNER JOIN CATEGORY_TABLE CA "
  			+ "ON CO.CATEGORY_IDX = CA.CATEGORY_IDX "
  			+ "INNER JOIN SCRAP_TABLE ST " 
  			+ "ON CO.CONTENT_IDX = ST.CONTENT_IDX "
     		+ "WHERE ST.USER_IDX = #{user_idx} "
    		+ "ORDER BY CONTENT_DATE DESC")
	String myScrapCountForPaginationAll(int user_idx);
	
	// 페이지네이션용 카테고리별 스크랩 개수 조회 (선택)
	@Select("SELECT COUNT(*) "
  			+ "FROM CONTENT_TABLE CO "
  			+ "INNER JOIN CATEGORY_TABLE CA "
  			+ "ON CO.CATEGORY_IDX = CA.CATEGORY_IDX "
  			+ "INNER JOIN SCRAP_TABLE ST " 
  			+ "ON CO.CONTENT_IDX = ST.CONTENT_IDX "
  			+ "WHERE CO.CATEGORY_IDX = #{category_idx} "
  			+ "AND ST.USER_IDX = #{user_idx} "
  			+ "ORDER BY CONTENT_DATE DESC")	
	String myScrapCountForPaginationSelect(@Param("user_idx") int user_idx, @Param("category_idx") int category_idx);
	
	
	// ---------------------- 나의 활동용 매퍼(희만) ---------------------
	// 활동 시간 계산
	@Select("SELECT TRUNC(SUM(LOGIN_END_DATE - LOGIN_START_DATE)*24, 2) AS activityTime " +
					"FROM LOGIN_LOG_TABLE " +
					"WHERE USER_IDX = #{user_idx}")
	String activityTime(int user_idx);
	
	// 방문 횟수 조회
	@Select("SELECT COUNT(*) " +
					"FROM LOGIN_LOG_TABLE " +
					"WHERE USER_IDX = #{user_idx}")
	String visitCount(int user_idx);
	
	// 최근 방문 이력 조회
	@Select("SELECT MAX(LOGIN_START_DATE) " +
					"FROM LOGIN_LOG_TABLE " +
					"WHERE USER_IDX=#{user_idx}")
	Date recentVisitTime(int user_idx);
	
	// 내가 작성한 댓글 조회 (커뮤니티 용)
	@Select("SELECT COMMENT_IDX, POST_ID, COMMENT_TEXT, COMMENT_DATE " +
					"FROM COMMENT_TABLE " +
					"WHERE USER_IDX = #{user_idx} " +
					"ORDER BY COMMENT_DATE DESC")
	List<CommentDTO> getMyAllCommunityCommentList(int user_idx, RowBounds rowBounds);
	
	// 내가 작성한 총 댓글 개수 조회 (커뮤니티 용)
	@Select("SELECT COUNT(*) " +
			"FROM COMMENT_TABLE " +
			"WHERE USER_IDX = #{user_idx} ")
	int getMyAllCommunityCommentCount(int user_idx);
	
	// 내가 작성한 총 게시글 수 조회 (커뮤니티 용)
	@Select("SELECT COUNT(*) " +
					"FROM COMMUNITY_TABLE " +
					"WHERE COMMUNITY_WRITER_IDX = #{user_idx}")
	int getMyAllCommuityContentCount(int user_idx);
	
	// 내가 작성한 총 게시글 리스트 조회 (커뮤니티 용)
	@Select("SELECT COMMUNITY_IDX, COMMUNITY_SUBJECT, COMMUNITY_VIEW, COMMUNITY_COMMENT, COMMUNITY_DATE " +
					"FROM COMMUNITY_TABLE " +
					"WHERE COMMUNITY_WRITER_IDX = #{user_idx} " +
					"ORDER BY COMMUNITY_DATE DESC")
	List<CommDTO> getMyAllCommunityContentList(int user_idx, RowBounds rowBounds);
	
}
