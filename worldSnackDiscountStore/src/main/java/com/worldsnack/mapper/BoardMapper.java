package com.worldsnack.mapper;

import org.apache.ibatis.annotations.Insert;

public interface BoardMapper {

	@Insert("INSERT INTO CONTENT_TABLE " +
					"VALUES(CONTENT_SEQ.NEXTVAL, '양말하기린인간수치', '응가에에베베베베베베베베베베베베벱', NULL, #{user_idx}, '삼성', " +
					"'대한민국', 'ABC324', 1000, 1564, SYSDATE)")
	void testInsertContent(int user_idx);
}
