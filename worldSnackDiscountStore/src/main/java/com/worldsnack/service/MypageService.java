package com.worldsnack.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.worldsnack.dao.MypageDAO;
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
		/*		-- 오류 확인용 --
		String myContentCount = mypageDAO.myContentCount(user_idx);
		int toIntMyContentCount = Integer.parseInt(myContentCount);
		
		int countOftotalContent = toIntMyContentCount;
		*/
		
		/*		-- 오류 확인용 --
		System.out.println("============페이지네이션서비스 start===========");
		System.out.println("myContentCount 사용 (String) : " + myContentCount);
		System.out.println("myContentCount 사용 (int로 변경) : " + toIntMyContentCount);
		System.out.println("-----------------------------------------------------");
		System.out.println("사용할 변수 : " + countOftotalContent);
		System.out.println("============페이지네이션서비스 end===========");
		*/
		
		int countOftotalContent = Integer.parseInt(mypageDAO.myContentCount(user_idx));
		
		PageDTO pageDTO = 
				new PageDTO(countOftotalContent, currentPage, countOfPagination, countPerPage);
		
		/*		-- 오류 확인용 --
		System.out.println("현재 페이지 : " + pageDTO.getCurrentPage());
		System.out.println("마지막 페이지 : " + pageDTO.getMax());
		System.out.println("첫 페이지 : " + pageDTO.getMin());
		System.out.println("다음 페이지 : " + pageDTO.getNextPage());
		System.out.println("이전 페이지 : " + pageDTO.getPrevPage());
		System.out.println("총 페이지 : " + pageDTO.getTotalPage());
		*/
		return pageDTO;
	}
	
	// 내가 올린 최근 게시 일자 조회
	public String myContentDate(int user_idx) {
		return mypageDAO.myContentDate(user_idx);
	}
	
	// 내가 올린 게시글 미리보기 조회
	public List<ContentDTO> myContentPreView(int user_idx, int page) {
		int startPage = (page - 1) * this.countPerPage;
		RowBounds rowBounds = new RowBounds(startPage, countPerPage);
		List<ContentDTO> myContentList = mypageDAO.myContentPreView(user_idx, rowBounds);
		return myContentList;
	}
	
	// ---------------------- 내 관심 게시글 조회용 매퍼 ---------------------
	//내 관심 게시글 미리보기 조회
	public List<ContentDTO> myScrapPreview(int user_idx, int page) {
		int startPage = (page - 1) * this.countPerPage;
		RowBounds rowBounds = new RowBounds(startPage, countPerPage);
		List<ContentDTO> myScrapList = mypageDAO.myScrapPreview(user_idx, rowBounds);
		return myScrapList;
	}
	
	// 내 관심 개수 조회
	public String myScrapCount(int user_idx) {
		return mypageDAO.myScrapCount(user_idx);
	}	
	
	//페이지네이션을 위해 내가 올린 게시글 수 조회 후 페이지 값 리턴
	public PageDTO getCountOfMyScrapTotal(int user_idx, int currentPage) {

		int countOftotalContent = Integer.parseInt(mypageDAO.myScrapCount(user_idx));
		
		PageDTO pageDTO = 
				new PageDTO(countOftotalContent, currentPage, countOfPagination, countPerPage);

		return pageDTO;
	}
}
