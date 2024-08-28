package com.worldsnack.mapper;

import org.apache.ibatis.annotations.Select;

import com.worldsnack.dto.UserDTO;

public interface UserMapper {
	
	@Select("SELECT * FROM USER_TABLE WHERE USER_ID=#{user_id}")
	UserDTO testList(String user_id);
	
}
