package com.worldsnack.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import com.worldsnack.dto.ContentDTO;

public interface ContentMapper {

	@Select("SELECT * "
			+ "FROM CONTENT_TABLE CT "
			+ "INNER JOIN CATEGORY_SELECT_TABLE CST "
			+ "ON CT.CONTENT_IDX = CST.CONTENT_IDX "
			+ "ORDER BY CONTENT_DATE DESC")
	List<ContentDTO> selectAll();
	
	@Select("SELECT * "
			+ "FROM CONTENT_TABLE CT "
			+ "INNER JOIN CATEGORY_SELECT_TABLE CST "
			+ "ON CT.CONTENT_IDX = CST.CONTENT_IDX "
			+ "ORDER BY CONTENT_DATE DESC")
	List<ContentDTO> selectAllForLimit(RowBounds rowBounds);
	
	@Select("SELECT * "
			+ "FROM CONTENT_TABLE CT "
			+ "INNER JOIN CATEGORY_SELECT_TABLE CST "
			+ "ON CT.CONTENT_IDX = CST.CONTENT_IDX "
			+ "WHERE CST.CATEGORY_INFO_IDX = #{category_info_idx} "
			+ "ORDER BY CONTENT_DATE DESC")
	List<ContentDTO> selectList(int category_info_idx);
	
	@Select("SELECT * "
			+ "FROM CONTENT_TABLE CT "
			+ "INNER JOIN CATEGORY_SELECT_TABLE CST "
			+ "ON CT.CONTENT_IDX = CST.CONTENT_IDX "
			+ "WHERE CST.CATEGORY_INFO_IDX = #{category_info_idx} "
			+ "ORDER BY CONTENT_DATE DESC")
	List<ContentDTO> selectListForLimit(int category_info_idx, RowBounds rowBounds);

	@Select("SELECT * "
			+ "FROM CONTENT_TABLE CT "
			+ "INNER JOIN CATEGORY_SELECT_TABLE CST "
			+ "ON CT.CONTENT_IDX = CST.CONTENT_IDX "
			+ "WHERE TO_CHAR(CST.CATEGORY_INFO_IDX) IN ( ${category_info_idx} ) "
			+ "ORDER BY CONTENT_DATE DESC")
	List<ContentDTO> selectInList(String category_info_idx);

	@Select("SELECT CT.CONTENT_IDX, "
			+ "CT.CONTENT_SUBJECT, "
			+ "CT.CONTENT_TEXT, "
			+ "CT.CONTENT_FILE, "
			+ "CT.CONTENT_WRITER_IDX, "
			+ "CT.CONTENT_MAKE, "
			+ "CT.CONTENT_COUNTRY, "
			+ "CT.CONTENT_PRODNO, "
			+ "CT.CONTENT_PRODPRICE, "
			+ "CT.CONTENT_VIEW, "
			+ "CT.CONTENT_DATE, "
			+ "CST.CATEGORY_INFO_IDX, "
			+ "CST.CATEGORY_SELECT_NAME "
			+ "FROM CONTENT_TABLE CT "
			+ "INNER JOIN CATEGORY_SELECT_TABLE CST "
			+ "ON CT.CONTENT_IDX = CST.CONTENT_IDX "
			+ "WHERE CT.CONTENT_IDX = #{content_idx}")
	ContentDTO getContentDetail(int content_idx);
		
	@SelectKey(statement="SELECT CONTENT_SEQ.NEXTVAL FROM DUAL", 
						 keyProperty="content_idx", before=true, resultType=int.class)
	
	@Insert("INSERT INTO CONTENT_TABLE VALUES( "
			+ "#{content_idx}, "
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
			+ "CONTENT_SUBJECT=#{content_subject} "
			+ ",CONTENT_TEXT=#{content_text} "
			+ ",CONTENT_FILE=#{content_file, jdbcType=VARCHAR} " 
			+ ",CONTENT_MAKE=#{content_make} "
			+ ",CONTENT_COUNTRY=#{content_country} "
			+ ",CONTENT_PRODPRICE=#{content_prodprice} "
			+ "WHERE CONTENT_IDX=#{content_idx}")
	void updateContent(ContentDTO modifyContentDTO);
	
	@Select("SELECT MAX(CONTENT_PRODNO) FROM CONTENT_TABLE")
	String getContentProdnoMax();
	
}
