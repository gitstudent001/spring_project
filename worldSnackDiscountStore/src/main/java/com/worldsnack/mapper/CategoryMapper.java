package com.worldsnack.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
//import org.apache.ibatis.annotations.Insert;
//import org.apache.ibatis.annotations.Update;

import com.worldsnack.dto.CategoryDTO;

public interface CategoryMapper {

	@Select("SELECT * FROM CATEGORY_TABLE ORDER BY CATEGORY_IDX")
	List<CategoryDTO> selectAll();
	
	@Select("SELECT * "
			+ "FROM CONTENT_TABLE CO "
			+ "INNER JOIN CATEGORY_TABLE CA "
			+ "ON CO.CONTENT_CATEGORY_IDX = CA.CATEGORY_IDX "
			+ "WHERE CO.CONTENT_IDX = #{content_idx}")
	CategoryDTO getCategorySelect(int content_idx);
	
	/*
	@Insert("INSERT INTO CATEGORY_TABLE VALUES(#{category_idx}, #{category_name}, #{category_parent})")
	void insertCategory(CategoryDTO writeCategoryDTO);
	
	@Update("UPDATE CATEGORY_TABLE SET "
			+ "CATEGORY_NAME=#{category_name} "
			+ ",CATEGORY_PARENT=#{category_parent} "
			+ "WHERE CATEGORY_IDX=#{category_idx}")
	void updateCategory(CategoryDTO modifyCategoryDTO);
	*/
}
