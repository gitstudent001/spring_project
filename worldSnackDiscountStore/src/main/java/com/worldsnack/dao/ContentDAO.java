package com.worldsnack.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
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
	
	public List<ContentDTO> selectAllForLimit(RowBounds rowBounds) {
		List<ContentDTO> contentDTO = contentMapper.selectAllForLimit(rowBounds);
		return contentDTO;
	}
	
	public List<ContentDTO> selectList(int content_idx) {
		List<ContentDTO> contentDTO = contentMapper.selectList(content_idx);
		return contentDTO;
	}
	
	// 0830
	public List<ContentDTO> selectListForLimit(int category_idx, RowBounds rowBounds) {
		List<ContentDTO> contentDTO = contentMapper.selectListForLimit(category_idx, rowBounds);
		return contentDTO;
	}	
	
	//제품페이지 페이지네이션 
	
	public String getCountselectAllForLimit(int limit) {
		RowBounds rowBounds = new RowBounds(0, limit);
		
		String contentCount = contentMapper.getCountOfselectAllForLimit(rowBounds);
		// System.out.println("전체 : " + contentCount);
		return contentCount;
	}
	
	public String  getCountselectListForLimit(int category_idx, int limit) {
		RowBounds rowBounds = new RowBounds(0, limit);
		// System.out.println("선택 : " + rowBounds);
		String contentCount = contentMapper.getCountselectListForLimit(category_idx, rowBounds);
		return contentCount;
	}	
	
	// 메인 검색
	public List<ContentDTO> selectInList(String category_idx) {
		List<ContentDTO> contentDTO = contentMapper.selectInList(category_idx);
		return contentDTO;
	}
	
	// 메인 검색어 검색
	public List<ContentDTO> selectSearchList(String searchKeyword) {
		List<ContentDTO> contentDTO = contentMapper.selectSearchList(searchKeyword);
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
	// 게시글 스크랩 (희만)
	public void insertScrap(int user_idx, int content_idx) {
		contentMapper.insertScrap(user_idx, content_idx);
	}
	
	// 게시글 스크랩 유무 확인 (희만)
	public boolean checkScrap(int user_idx, int content_idx) {
		return contentMapper.checkScrap(user_idx, content_idx);
	}
	
	// 게시글 스크랩 취소하기 (희만)
	public void deleteScrap(@Param("user_idx")int user_idx, @Param("content_idx") int content_idx) {
		contentMapper.deleteScrap(user_idx, content_idx);
	}
	
	// 조회수 증가 (희만)
	public void increaseView(int content_idx) {
		contentMapper.increaseView(content_idx);
	}
		
	//게시글 삭제하기 (용기)
	public void deleteContent(int content_idx){
		 contentMapper.deleteContent(content_idx);
	}
	
	// 게시글 작성자 닉네임 조회 (희만)
	public String getWriterNickname(int content_writer_idx) {
		return contentMapper.getWriterNickname(content_writer_idx);
	}
}
