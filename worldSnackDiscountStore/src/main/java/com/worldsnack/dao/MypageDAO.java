package com.worldsnack.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.worldsnack.dto.CommDTO;
import com.worldsnack.dto.CommentDTO;
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
	public Date myContentDate(int user_idx) {
		return mypageMapper.myContentDate(user_idx);
	}
	
	// 내가 올린 게시글 미리보기 조회
	public List<ContentDTO> myContentPreView(int user_idx, RowBounds rowBounds) {
		List<ContentDTO> myContentList = mypageMapper.myContentPreView(user_idx, rowBounds);
		return myContentList;
	}

	// ---------------------- 내 관심 게시글 조회용 매퍼 ---------------------
	//내 관심 게시글 미리보기 조회 (전체 카테고리)
	public List<ContentDTO> myScrapPreviewAll(int user_idx, RowBounds rowBounds) {
		List<ContentDTO> myScrapList = mypageMapper.myScrapPreviewAll(user_idx, rowBounds);
		return myScrapList;
	}
	
	//내 관심 게시글 미리보기 조회 (선택한 카테고리)
	public List<ContentDTO> myScrapPreviewSelect(@Param("user_idx") int user_idx, 
																							 @Param("rowBounds")	RowBounds rowBounds, 
																							 @Param("category_idx") int category_idx) {
		List<ContentDTO> myScrapList = mypageMapper.myScrapPreviewSelect(user_idx, rowBounds, category_idx);
		return myScrapList;
	}

	// 내 관심 개수 조회
	public String myScrapCount(int user_idx) {
		return mypageMapper.myScrapCount(user_idx);
	}
	
	// 페이지네이션용 카테고리별 스크랩 개수 조회 (전체)
	public String myScrapCountForPaginationAll(int user_idx) {
		return mypageMapper.myScrapCountForPaginationAll(user_idx);
	}
	
	// 페이지네이션용 카테고리별 스크랩 개수 조회 (선택)
	public String myScrapCountForPaginationSelect(int user_idx, int category_idx) {
		return mypageMapper.myScrapCountForPaginationSelect(user_idx, category_idx);
	}
	
	// ---------------------- 나의 활동용 매퍼(희만) ---------------------
	// 활동 시간 계산
	public String activityTime(int user_idx) {
		return mypageMapper.activityTime(user_idx);
	}
	
	// 방문 횟수 조회
	public String visitCount(int user_idx) {
		return mypageMapper.visitCount(user_idx);
	}
	
	// 최근 방문 이력 조회
	public Date recentVisitTime(int user_idx) {
		return mypageMapper.recentVisitTime(user_idx);
	}
	
	// 내가 작성한 댓글 조회 (커뮤니티 용)
	public List<CommentDTO> getMyAllCommunityCommentList(int user_idx, RowBounds rowBounds) {
		return mypageMapper.getMyAllCommunityCommentList(user_idx, rowBounds);
	}
	
	// 내가 작성한 총 댓글 개수 조회 (커뮤니티 용)
	public int getMyAllCommunityCommentCount(int user_idx) {
		return mypageMapper.getMyAllCommunityCommentCount(user_idx);
	}
	
	//내가 작성한 총 게시글 수 조회 (커뮤니티 용)
	public int getMyAllCommuityContentCount(int user_idx) {
		return mypageMapper.getMyAllCommuityContentCount(user_idx);
	}
	
	// 내가 작성한 총 게시글 리스트 조회 (커뮤니티 용)
	public List<CommDTO> getMyAllCommunityContentList(int user_idx, RowBounds rowBounds) {
		return mypageMapper.getMyAllCommunityContentList(user_idx, rowBounds);
	}
	
	// 내가 작성한 글 중 스크랩 받은 게시글 조회 (제품용)
	public List<ContentDTO> getReceivedScrapList(int user_idx, RowBounds rowBounds) {
		return mypageMapper.getReceivedScrapList(user_idx, rowBounds);
	}
	
	// 내가 작성한 글 중 스크랩 받은 게시글 수 조회 (제품용)
	public int getReceivedScrapCount(int user_idx) {
		return mypageMapper.getReceivedScrapCount(user_idx);
	}
}
