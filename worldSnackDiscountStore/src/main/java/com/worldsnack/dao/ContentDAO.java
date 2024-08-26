package com.worldsnack.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.worldsnack.dto.ContentDTO;
import com.worldsnack.mapper.ContentMapper;

@Repository
public class ContentDAO {

	@Autowired
	private ContentMapper contentMapper;
	
	public List<ContentDTO> selectAll() {
		List<ContentDTO> contentDTO = contentMapper.selectAll();
		return contentDTO;
	}
	
	public List<ContentDTO> selectList(int content_idx) {
		List<ContentDTO> contentDTO = contentMapper.selectList(content_idx);
		return contentDTO;
	}
	
	public ContentDTO getContentDetail(int content_idx) {
		return contentMapper.getContentDetail(content_idx);
	}
	
	public String getContentProdnoMax() {
		return contentMapper.getContentProdnoMax();
	}
	
	public void insertContent(ContentDTO writeContentDTO) {
		contentMapper.insertContent(writeContentDTO);
	}
	
	public void updateContent(ContentDTO modifyContentDTO) {
		contentMapper.updateContent(modifyContentDTO);
	}
		
}
