package com.worldsnack.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import com.worldsnack.dto.ContentDTO;

public interface ContentMapper {

	@Select("SELECT * "
			+ "FROM CONTENT_TABLE CO "
			+ "INNER JOIN CATEGORY_TABLE CA "
			+ "ON CO.CATEGORY_IDX = CA.CATEGORY_IDX "
			+ "ORDER BY CO.CONTENT_DATE DESC, CO.CONTENT_IDX DESC")
	List<ContentDTO> selectAll();
	
	@Select("SELECT * "
			+ "FROM CONTENT_TABLE CO "
			+ "INNER JOIN CATEGORY_TABLE CA "
			+ "ON CO.CATEGORY_IDX = CA.CATEGORY_IDX "
			+ "ORDER BY CO.CONTENT_DATE DESC, CO.CONTENT_IDX DESC")
	List<ContentDTO> selectAllForLimit(RowBounds rowBounds);
	
	@Select("SELECT * "
			+ "FROM CONTENT_TABLE CO "
			+ "INNER JOIN CATEGORY_TABLE CA "
			+ "ON CO.CATEGORY_IDX = CA.CATEGORY_IDX "
			+ "WHERE CO.CATEGORY_IDX = #{category_idx} "
			+ "ORDER BY CO.CONTENT_DATE DESC, CO.CONTENT_IDX DESC")
	List<ContentDTO> selectList(int category_idx);
	
	
	
	@Select("SELECT * "
			+ "FROM CONTENT_TABLE CO "
			+ "INNER JOIN CATEGORY_TABLE CA "
			+ "ON CO.CATEGORY_IDX = CA.CATEGORY_IDX "
			+ "WHERE CO.CATEGORY_IDX = #{category_idx} "
			+ "ORDER BY CO.CONTENT_DATE DESC, CO.CONTENT_IDX DESC")
	List<ContentDTO> selectListForLimit(int category_idx, RowBounds rowBounds);
	
	
	//제품페이지 페이지네이션(전체)
	@Select("SELECT COUNT(*) "
			+ "FROM CONTENT_TABLE CO "
			+ "INNER JOIN CATEGORY_TABLE CA "
			+ "ON CO.CATEGORY_IDX = CA.CATEGORY_IDX "
			+ "ORDER BY CO.CONTENT_DATE DESC, CO.CONTENT_IDX DESC")
	String getCountOfselectAllForLimit(RowBounds rowBounds);
	    
	// 제품페이지 페이지네이션(카테고리 선택)
	@Select("SELECT COUNT(*) "
			+ "FROM CONTENT_TABLE CO "
			+ "INNER JOIN CATEGORY_TABLE CA "
			+ "ON CO.CATEGORY_IDX = CA.CATEGORY_IDX "
			+ "WHERE CO.CATEGORY_IDX = #{category_idx} "
			+ "ORDER BY CO.CONTENT_DATE DESC, CO.CONTENT_IDX DESC")
	String getCountselectListForLimit(int category_idx, RowBounds rowBounds);

	// 메인 검색
	@Select("SELECT * "
			+ "FROM CONTENT_TABLE CO "
			+ "INNER JOIN CATEGORY_TABLE CA "
			+ "ON CO.CATEGORY_IDX = CA.CATEGORY_IDX "
			+ "WHERE TO_CHAR(CO.CATEGORY_IDX) IN ( ${category_idx} ) "
			+ "ORDER BY CO.CONTENT_DATE DESC, CO.CONTENT_IDX DESC")
	List<ContentDTO> selectInList(String category_idx);

	// 메인 검색어 검색
	@Select("SELECT * "
			+ "FROM CONTENT_TABLE CO "
			+ "INNER JOIN CATEGORY_TABLE CA "
			+ "ON CO.CATEGORY_IDX = CA.CATEGORY_IDX "
			+ "WHERE CO.CONTENT_SUBJECT LIKE '%${searchKeyword}%' "
			+ "OR CO.CONTENT_TEXT LIKE '%${searchKeyword}%' "
			+ "OR CA.CATEGORY_NAME LIKE '%${searchKeyword}%' "
			+ "ORDER BY CO.CATEGORY_IDX, CO.CONTENT_SUBJECT, CO.CONTENT_DATE DESC")
	List<ContentDTO> selectSearchList(String searchKeyword);	
	
	@Select("SELECT CO.CONTENT_IDX, "
			+ "CO.CATEGORY_IDX, "
			+ "CO.CONTENT_SUBJECT, "
			+ "CO.CONTENT_TEXT, "
			+ "CO.CONTENT_FILE, "
			+ "CO.CONTENT_WRITER_IDX, "
			+ "CO.CONTENT_MAKE, "
			+ "CO.CONTENT_COUNTRY, "
			+ "CO.CONTENT_PRODNO, "
			+ "CO.CONTENT_PRODPRICE, "
			+ "CO.CONTENT_VIEW, "
			+ "CO.CONTENT_DATE, "
			+ "CA.CATEGORY_NAME "
			+ "FROM CONTENT_TABLE CO "
			+ "INNER JOIN CATEGORY_TABLE CA "
			+ "ON CO.CATEGORY_IDX = CA.CATEGORY_IDX "
			+ "WHERE CO.CONTENT_IDX = #{content_idx}")
	ContentDTO getContentDetail(int content_idx);
		
	@SelectKey(statement="SELECT CONTENT_SEQ.NEXTVAL FROM DUAL", 
						 keyProperty="content_idx", before=true, resultType=int.class)
	
	@Insert("INSERT INTO CONTENT_TABLE VALUES( "
			+ "#{content_idx}, "
			+ "#{category_idx}, " 
			+ "#{content_subject}, "
			+ "#{content_text}, "
			+ "#{content_file, jdbcType=VARCHAR}, "
			+ "#{content_writer_idx}, "
			+ "#{content_make}, "
			+ "#{content_country}, "
			+ "#{content_prodno}, "
			+ "#{content_prodprice}, "
			+ "0, SYSDATE)")
	void insertContent(ContentDTO writeContentDTO);
	
	@Update("UPDATE CONTENT_TABLE SET "
			+ "CATEGORY_IDX=#{category_idx} "
			+ ",CONTENT_SUBJECT=#{content_subject} "
			+ ",CONTENT_TEXT=#{content_text} "
			+ ",CONTENT_FILE=#{content_file, jdbcType=VARCHAR} " 
			+ ",CONTENT_MAKE=#{content_make} "
			+ ",CONTENT_COUNTRY=#{content_country} "
			+ ",CONTENT_PRODPRICE=#{content_prodprice} "
			+ "WHERE CONTENT_IDX=#{content_idx}")
	void updateContent(ContentDTO modifyContentDTO);
	
	@Select("SELECT NVL(MAX(CONTENT_PRODNO), '0000000') FROM CONTENT_TABLE")
	String getContentProdnoMax();
	
	// 게시글 스크랩 (희만)
	@Insert("INSERT INTO SCRAP_TABLE VALUES(#{user_idx}, #{content_idx})")
	void insertScrap(@Param("user_idx")int user_idx, @Param("content_idx") int content_idx);
	
	// 게시글 스크랩 유무 확인(희만)
	@Select("SELECT COUNT(*) " +
		  		"FROM SCRAP_TABLE " +
		  		"WHERE USER_IDX=#{user_idx} " +
	    		"AND CONTENT_IDX=#{content_idx}")
	boolean checkScrap(@Param("user_idx")int user_idx, @Param("content_idx") int content_idx);
	
	// 게시글 스크랩 취소하기 (희만)
	@Delete("DELETE FROM SCRAP_TABLE " +
					"WHERE user_idx = #{user_idx} AND content_idx = #{content_idx}")
	void deleteScrap(@Param("user_idx")int user_idx, @Param("content_idx") int content_idx);
	
	// 조회수 증가 (희만)
	@Update("UPDATE CONTENT_TABLE "
				+ "SET CONTENT_VIEW = (SELECT CONTENT_VIEW "
        +			                  "FROM CONTENT_TABLE "
        +				               "WHERE CONTENT_IDX = #{content_idx}) + 1 "
      	+ "WHERE CONTENT_IDX = #{content_idx}")
	void increaseView(int content_idx);
	
}
