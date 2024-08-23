package com.worldsnack.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.worldsnack.dto.CategoryInfoDTO;
import com.worldsnack.dto.CategorySelectDTO;

public interface CategoryMapper {

	@Select("SELECT DISTINCT CATEGORY_INFO_IDX, CATEGORY_INFO_NAME "
			+ "FROM "
			+ "(SELECT "
			+ "A.CATEGORY_INFO_IDX, "
			+ "CASE WHEN B.CATEGORY_INFO_IDX = 5 AND (B.CATEGORY_SELECT_NAME IS NOT NULL OR B.CATEGORY_SELECT_NAME <> '') "
			+ "THEN B.CATEGORY_SELECT_NAME "
			+ "ELSE A.CATEGORY_INFO_NAME "
			+ "END CATEGORY_INFO_NAME "
			+ "FROM CATEGORY_INFO_TABLE A "
			+ "LEFT OUTER JOIN CATEGORY_SELECT_TABLE B "
			+ "ON A.CATEGORY_INFO_IDX = B.CATEGORY_INFO_IDX "
			+ ") "
			+ "ORDER BY CATEGORY_INFO_IDX, CATEGORY_INFO_NAME")
	List<CategoryInfoDTO> selectAll();
	
	@Select("SELECT * FROM CATEGORY_INFO_TABLE ORDER BY CATEGORY_INFO_IDX")
	List<CategoryInfoDTO> selectInfoAll();
	
	@Select("SELECT * FROM CATEGORY_SELECT_TABLE WHERE CONTENT_IDX=#{content_idx}")
	CategorySelectDTO getCategorySelect(int content_idx);
	
	@Insert("INSERT INTO CATEGORY_SELECT_TABLE VALUES(#{content_idx}, #{category_info_idx}, #{category_select_name})")
	void insertCategorySelect(CategorySelectDTO writeCategorySelectDTO);
	
	@Update("UPDATE CATEGORY_SELECT_TABLE SET "
			+ "CATEGORY_INFO_IDX=#{category_info_idx} "
			+ ",CATEGORY_SELECT_NAME=#{category_select_name} "
			+ "WHERE CONTENT_IDX=#{content_idx}")
	void updateCategorySelect(CategorySelectDTO modifyCategorySelectDTO);
	
}
