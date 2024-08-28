package com.worldsnack.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.worldsnack.dto.UserDTO;

public class LoginCheckInterceptor implements HandlerInterceptor {

		private UserDTO loginUserDTO;
		
		public LoginCheckInterceptor(UserDTO loginUserDTO) {
			this.loginUserDTO = loginUserDTO;
		}
		@Override
		public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
			
			if(loginUserDTO.isUserIsLogin() == false) {
				String contextPath = request.getContextPath();
				response.sendRedirect(contextPath + "/user/cant_login");
				return false;
			}
			return true;
		}
}
