package com.worldsnack.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.worldsnack.dto.ContentDTO;
import com.worldsnack.dto.UserDTO;
import com.worldsnack.mapper.MypageMapper;

@Repository
public class MypageDAO {

	@Autowired
	private MypageMapper mypageMapper;
	
	// 정보수정을 위한 로그인한 회원의 정보 가져오기
	public UserDTO getModifyUserInfo(int user_idx) {
		return mypageMapper.getModifyUserInfo(user_idx);
	}
	
	// 정보수정 적용
	public void modifyUserInfo(UserDTO modifyUserDTO) {
		mypageMapper.modifyUserInfo(modifyUserDTO);
	}
	
	// 회원 정보 삭제
	public void deleteUser(int user_idx) {
		mypageMapper.deleteUser(user_idx);
	}
	
	
	// ---------------------- 내 게시글 조회 ---------------------
	// 내가 올린 게시글 수 조회
	public String myContentCount(int user_idx) {
		return mypageMapper.myContentCount(user_idx);
	}
	
	// 내가 올린 최근 게시 일자 조회
	public String myContentDate(int user_idx) {
		return mypageMapper.myContentDate(user_idx);
	}
	
	// 내가 올린 게시글 미리보기 조회
	public List<ContentDTO> myContentPreView(int user_idx, RowBounds rowBounds) {
		List<ContentDTO> myContentList = mypageMapper.myContentPreView(user_idx, rowBounds);
		return myContentList;
	}

	// ---------------------- 내 관심 게시글 조회용 매퍼 ---------------------
	//내 관심 게시글 미리보기 조회
	public List<ContentDTO> myScrapPreview(int user_idx, RowBounds rowBounds) {
		List<ContentDTO> myScrapList = mypageMapper.myScrapPreview(user_idx, rowBounds);
		return myScrapList;
	}

	// 내 관심 개수 조회
	public String myScrapCount(int user_idx) {
		return mypageMapper.myScrapCount(user_idx);
	}
}
