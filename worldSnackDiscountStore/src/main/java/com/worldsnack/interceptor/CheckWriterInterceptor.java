package com.worldsnack.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.worldsnack.dto.ContentDTO;
import com.worldsnack.dto.UserDTO;
import com.worldsnack.service.ContentService;

public class CheckWriterInterceptor implements HandlerInterceptor{

	private UserDTO loginUserDTO;
	private ContentService contentService;
	
	public CheckWriterInterceptor(UserDTO loginUserDTO, ContentService contentService) {
		this.loginUserDTO = loginUserDTO;
		this.contentService = contentService;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		int content_idx = Integer.parseInt(request.getParameter("content_idx"));
		ContentDTO contentDTO = contentService.getContentDetail(content_idx);
		
		if(contentDTO.getContent_writer_idx() != loginUserDTO.getUser_idx()) {
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + "/content/cant_modify_delete");
			return false;
		}
		
		return true;
	}
}
