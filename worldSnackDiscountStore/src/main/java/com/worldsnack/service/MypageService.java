package com.worldsnack.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.worldsnack.dao.MypageDAO;
import com.worldsnack.dto.CommDTO;
import com.worldsnack.dto.CommentDTO;
import com.worldsnack.dto.ContentDTO;
import com.worldsnack.dto.PageDTO;
import com.worldsnack.dto.UserDTO;

@Service
@PropertySource("/WEB-INF/properties/upload.properties")
public class MypageService {
	
	// 페이지 네비게이터에 나오는 페이지 (버튼) 개수
	@Value("${page.countOfPagination}") 
	private int countOfPagination;
	
	// 한 페이지 당 게시글 개수
	@Value("${page.countPerPage}") 
  private int countPerPage;

	@Autowired
	private MypageDAO mypageDAO;
	
	// Session Scope에 생성된 UserDTO 주입받기
	@Resource(name = "loginUserDTO")
	private UserDTO loginUserDTO;
	
	// 마이페이지 페이지네이션의 게시글 개수 지정
	private int countPerPageInMypage = 10;
	
	// 정보수정을 위한 로그인한 회원의 정보 가져오기	
	public void getModifyUserInfo(UserDTO modifyUserDTO) {
		int user_idx = loginUserDTO.getUser_idx();
		
		// 로그인한 유저의 인덱스 번호를 이용해서
		// 유저의 정보를 loginUserDTO에 넣어줌
		UserDTO loginUserDTO = mypageDAO.getModifyUserInfo(user_idx);
		
		modifyUserDTO.setUser_name(loginUserDTO.getUser_name());
		modifyUserDTO.setUser_id(loginUserDTO.getUser_id());
		modifyUserDTO.setUser_email(loginUserDTO.getUser_email());
		modifyUserDTO.setUser_nickname(loginUserDTO.getUser_nickname());
		modifyUserDTO.setUser_idx(user_idx);
	}
	
	// 정보수정 적용
	public void modifyUserInfo(UserDTO modifyUserDTO) {
		mypageDAO.modifyUserInfo(modifyUserDTO);
	}
	
	// 회원 정보 삭제
	public void deleteUser(int user_idx) {
		mypageDAO.deleteUser(user_idx);
	}
	
	
	// ---------------------- 내 게시글 조회용 매퍼 ---------------------
	// 내가 올린 총 게시글 수 조회
	public String myContentCount(int user_idx) {
		return mypageDAO.myContentCount(user_idx);
	}
	
	// 페이지네이션을 위해 내가 올린 게시글 수 조회 후 페이지 값 리턴
	public PageDTO getCountOfMyContentTotal(int user_idx, int currentPage) {
		int countOftotalContent = Integer.parseInt(mypageDAO.myContentCount(user_idx));
		
		// 페이지 번호가 더 많이 생기는 오류를 막기위한 계산식
		// 몫
		int tempQuotient = countOftotalContent / countPerPageInMypage;
		// 나머지
		int tempRemainder = (countOftotalContent % countPerPageInMypage);
		
		if(tempRemainder >= countPerPage) {
			tempRemainder = tempRemainder % countPerPage;
		}
		countOftotalContent = (tempQuotient * countPerPage) + tempRemainder;
		
		PageDTO pageDTO = 
				new PageDTO(countOftotalContent, currentPage, countOfPagination, countPerPage);
		return pageDTO;
	}
	
	// 내가 올린 최근 게시 일자 조회
	public Date myContentDate(int user_idx) {
		return mypageDAO.myContentDate(user_idx);
	}
	
	// 내가 올린 게시글 미리보기 조회
	public List<ContentDTO> myContentPreView(int user_idx, int page) {
		int startPage = (page - 1) * countPerPageInMypage;
		RowBounds rowBounds = new RowBounds(startPage, countPerPageInMypage);
		List<ContentDTO> myContentList = mypageDAO.myContentPreView(user_idx, rowBounds);
		return myContentList;
	}
	
	// ---------------------- 내 관심 게시글 조회용 매퍼 ---------------------
	//내 관심 게시글 미리보기 전체 조회 (전체 카테고리)
	public List<ContentDTO> myScrapPreviewAll(int user_idx, int page) {
		int startPage = (page - 1) * countPerPageInMypage;
		RowBounds rowBounds = new RowBounds(startPage, countPerPageInMypage);
		List<ContentDTO> myScrapList = mypageDAO.myScrapPreviewAll(user_idx, rowBounds);
		return myScrapList;
	}
	//내 관심 게시글 미리보기 조회 (선택한 카테고리)
	public List<ContentDTO> myScrapPreviewSelect(@Param("user_idx") int user_idx, 
																							 @Param("page") int page, 
																							 @Param("category_idx")int category_idx) {
		int startPage = (page - 1) * countPerPageInMypage;
		RowBounds rowBounds = new RowBounds(startPage, countPerPageInMypage);
		List<ContentDTO> myScrapList = mypageDAO.myScrapPreviewSelect(user_idx, rowBounds, category_idx);
		return myScrapList;
	}
	
	// 내 관심 개수 조회
	public String myScrapCount(int user_idx) {
		return mypageDAO.myScrapCount(user_idx);
	}	
	
	//페이지네이션을 위해 내가 올린 게시글 수 조회 후 페이지 값 리턴
	public PageDTO getCountOfMyScrapTotal(@Param("user_idx") int user_idx, 
																				@Param("page") int currentPage, 
																				@Param("flag") boolean flag, 
																				@Param("category_idx") int category_idx) {

		int countOftotalContent = 0;
		
		
		if (!flag) { // 전체 카테고리 일 경우
			countOftotalContent = Integer.parseInt(mypageDAO.myScrapCountForPaginationAll(user_idx));
		}
		else { // 카테고리를 선택 했을 경우
			countOftotalContent = Integer.parseInt(mypageDAO.myScrapCountForPaginationSelect(user_idx, category_idx));
		}
		
		// 페이지 번호가 더 많이 생기는 오류를 막기위한 계산식
		// 몫
		int tempQuotient = countOftotalContent / countPerPageInMypage;
		// 나머지
		int tempRemainder = (countOftotalContent % countPerPageInMypage);
		
		if(tempRemainder >= countPerPage) {
			tempRemainder = tempRemainder % countPerPage;
		}
		countOftotalContent = (tempQuotient * countPerPage) + tempRemainder;
		
		PageDTO pageDTO = 
				new PageDTO(countOftotalContent, currentPage, countOfPagination, countPerPage);

		return pageDTO;
	}
	
	// ---------------------- 나의 활동용 매퍼(희만) ---------------------
	// 활동 시간 계산
	public String activityTime(int user_idx) {
		return mypageDAO.activityTime(user_idx);
	}
	// 방문 횟수 조회
	public String visitCount(int user_idx) {
		return mypageDAO.visitCount(user_idx);
	}
	// 최근 방문 이력 조회
	public Date recentVisitTime(int user_idx) {
		return mypageDAO.recentVisitTime(user_idx);
	}
	
	// 내가 작성한 총 댓글 개수 조회 (커뮤니티 용)
	public int getMyAllCommunityCommentCount(int user_idx) {
		return mypageDAO.getMyAllCommunityCommentCount(user_idx);
	}
	
	// 내가 작성한 댓글 조회 (커뮤니티 용)
	public List<CommentDTO> getMyAllCommunityCommentList(int user_idx, int page) {
		int startPage = (page - 1) * countPerPageInMypage;
		RowBounds rowBounds = new RowBounds(startPage, countPerPageInMypage);
		
		return mypageDAO.getMyAllCommunityCommentList(user_idx, rowBounds);
	}
	/* 게시글의 페이지네이션을 위한 pageDTO 선언*/
	public PageDTO getCommentCountForPage(int user_idx, int currentPage) {
		int countOftotalContent = mypageDAO.getMyAllCommunityCommentCount(user_idx);
		
		// 페이지 번호가 더 많이 생기는 오류를 막기위한 계산식
		// 몫
		int tempQuotient = countOftotalContent / countPerPageInMypage;
		// 나머지
		int tempRemainder = (countOftotalContent % countPerPageInMypage);
		
		if(tempRemainder >= countPerPage) {
			tempRemainder = tempRemainder % countPerPage;
		}
		countOftotalContent = (tempQuotient * countPerPage) + tempRemainder;
		
		PageDTO pageDTO = 
				new PageDTO(countOftotalContent, currentPage, countOfPagination, countPerPage);
		
		return pageDTO;
	}
	
	//내가 작성한 총 게시글 수 조회 (커뮤니티 용)
	public int getMyAllCommuityContentCount(int user_idx) {
		return mypageDAO.getMyAllCommuityContentCount(user_idx);
	}
	// 내가 작성한 총 게시글 리스트 조회 (커뮤니티 용)
	public List<CommDTO> getMyAllCommunityContentList(int user_idx, int page) {
		int startPage = (page - 1) * countPerPageInMypage;
		RowBounds rowBounds = new RowBounds(startPage, countPerPageInMypage);
		
		List<CommDTO> communityList = mypageDAO.getMyAllCommunityContentList(user_idx, rowBounds);
		
		// 날짜 형식 변환
		SimpleDateFormat original = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		for (CommDTO comm : communityList) {
			try {
				Date date = original.parse(comm.getCommunity_date());
				String formattedDate = targetFormat.format(date);
				
				comm.setCommunity_date(formattedDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return communityList;
	}
	/* 게시글의 페이지네이션을 위한 pageDTO 선언*/
	public PageDTO getContentCountForPage(int user_idx, int currentPage) {
		int countOftotalContent = mypageDAO.getMyAllCommuityContentCount(user_idx);
		
		// 페이지 번호가 더 많이 생기는 오류를 막기위한 계산식
		// 몫
		int tempQuotient = countOftotalContent / countPerPageInMypage;
		// 나머지
		int tempRemainder = (countOftotalContent % countPerPageInMypage);
		
		if(tempRemainder >= countPerPage) {
			tempRemainder = tempRemainder % countPerPage;
		}
		countOftotalContent = (tempQuotient * countPerPage) + tempRemainder;
		
		PageDTO pageDTO = 
				new PageDTO(countOftotalContent, currentPage, countOfPagination, countPerPage);
		
		return pageDTO;
	}
	
	// 내가 작성한 글 중 스크랩 받은 게시글 조회 (제품용)
	public List<ContentDTO> getReceivedScrapList(int user_idx, int page) {
		int startPage = (page - 1) * countPerPageInMypage;
		RowBounds rowBounds = new RowBounds(startPage, countPerPageInMypage);
		
		return mypageDAO.getReceivedScrapList(user_idx, rowBounds);
	}
	/* 게시글의 페이지네이션을 위한 pageDTO 선언*/
	public PageDTO getReceivedScrapForPage(int user_idx, int currentPage) {
		int countOftotalContent = mypageDAO.getReceivedScrapCount(user_idx);
		
		// 페이지 번호가 더 많이 생기는 오류를 막기위한 계산식
		// 몫
		int tempQuotient = countOftotalContent / countPerPageInMypage;
		// 나머지
		int tempRemainder = (countOftotalContent % countPerPageInMypage);
		
		if(tempRemainder >= countPerPage) {
			tempRemainder = tempRemainder % countPerPage;
		}
		countOftotalContent = (tempQuotient * countPerPage) + tempRemainder;
		
		PageDTO pageDTO = 
				new PageDTO(countOftotalContent, currentPage, countOfPagination, countPerPage);
		
		return pageDTO;
	}
	
	// 내가 작성한 글 중 스크랩 받은 게시글 수 조회 (제품용)
	public int getReceivedScrapCount(int user_idx) {
		return mypageDAO.getReceivedScrapCount(user_idx);
	}
}
