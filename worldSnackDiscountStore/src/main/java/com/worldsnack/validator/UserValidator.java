package com.worldsnack.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.worldsnack.dto.UserDTO;

public class UserValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return UserDTO.class.isAssignableFrom(clazz);
	}
  
	@Override
	public void validate(Object target, Errors errors) {
		
		UserDTO userDTO = (UserDTO)target;

		String errorObjectName = errors.getObjectName();
		System.out.println("errorObjectName : " + errorObjectName);
		
		if(errorObjectName.equals("joinUserDTO") || errorObjectName.equals("modifyUserDTO")) {
			if(userDTO.getUser_pw().equals(userDTO.getUser_pw2()) == false) {
				errors.rejectValue("user_pw", "NotEquals");
				errors.rejectValue("user_pw2", "NotEquals");
			}
		}
		
		if(errorObjectName.equals("joinUserDTO")) {
			if(userDTO.isUserIdExist() == false) {
				errors.rejectValue("user_id", "CheckUserIdExist");
			}
		}
		
		
		
	} // validate
	
} // class



