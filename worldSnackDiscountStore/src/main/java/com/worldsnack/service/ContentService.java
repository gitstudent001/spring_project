package com.worldsnack.service;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.worldsnack.dao.ContentDAO;
import com.worldsnack.dto.ContentDTO;
import com.worldsnack.dto.PageDTO;
import com.worldsnack.dto.UserDTO;

@Service
@PropertySource("/WEB-INF/properties/upload.properties")
public class ContentService {

	@Value("${path.upload}")
	private String pathUpload;
	
	// 페이지 네비게이터에 나오는 페이지 (버튼) 개수
	@Value("${page.countOfPagination}") 
	private int countOfPagination;
	
	// 한 페이지 당 게시글 개수
	@Value("${page.countPerPage}") 
  private int countPerPage;
	
	@Autowired
	private ContentDAO contentDAO;
	
	
	@Resource(name="loginUserDTO")
	private UserDTO loginUserDTO;
	
	/* 카테고리 전체 게시글 조회 */
	public List<ContentDTO> selectAll() {
		List<ContentDTO> contentDTO = contentDAO.selectAll();
		return contentDTO;
	}
	
	/* 카테고리 전체 게시글 중 원하는 갯수만큼 조회 */
	public List<ContentDTO> selectAllForLimit(int limit, int page) {
		int startPage = (page - 1) * limit;
		RowBounds rowBounds = new RowBounds(startPage, limit);
		List<ContentDTO> contentDTO = contentDAO.selectAllForLimit(rowBounds);
		
		// 작성자 idx를 통해 작성자의 닉네임 조회 (희만)
		for(ContentDTO tempContent : contentDTO) {
			tempContent.setContent_writer_nickname(contentDAO.getWriterNickname(tempContent.getContent_writer_idx()));
		}
		return contentDTO;
	}

	/* 선택한 카테고리 게시글 조회 */
	public List<ContentDTO> selectList(int content_idx) {
		List<ContentDTO> contentDTO = contentDAO.selectList(content_idx);
		return contentDTO;
	}
	
	/* 선택한 카테고리 게시글 중 원하는 갯수만큼 조회 */
	public List<ContentDTO> selectListForLimit(int category_idx, int limit, int page) {
		int startPage = (page - 1) * limit;
		RowBounds rowBounds = new RowBounds(startPage, limit);
		List<ContentDTO> contentDTO = contentDAO.selectListForLimit(category_idx, rowBounds);

		// 작성자 idx를 통해 작성자의 닉네임 조회 (희만)
		for(ContentDTO tempContent : contentDTO) {
			tempContent.setContent_writer_nickname(contentDAO.getWriterNickname(tempContent.getContent_writer_idx()));
		}
		return contentDTO;
	}
	
//제품페이지 카테고리에 맞게 페이지네이션을 하기위해 페이지 값 리턴
	public PageDTO getCountOfTotalContent(@Param("category_idx")int category_idx,
																	 		  @Param("page") int currentPage,
																			  @Param("flag") boolean flag,
																			  @Param("limit")int limit) {
		
		int countOftotalContent = 0;
		if (!flag) { // 전체 카테고리 일 경우
			countOftotalContent = Integer.parseInt(contentDAO.getCountselectAllForLimit(limit));
		}
		else { // 카테고리를 선택 했을 경우
			countOftotalContent = Integer.parseInt(contentDAO.getCountselectListForLimit(category_idx, limit));
		}
		// 게시글의 총 개수가 limit 보다 크거나 같을 때 (몫과 나머지가 딱 떨어질 때)
		if(limit <= countOftotalContent) {
			// limit 가 10 일 때는 기본적으로 게시글 '10개당 1페이지'로 나눔
			// 몫 : 10의 자릿수로 사용
			int tempQuotient = countOftotalContent / limit;
			// 나머지 : 1의 자릿수로 사용
			int tempRemainder = (countOftotalContent % limit);
			
			if(tempRemainder >= countPerPage) {
				tempRemainder = tempRemainder % countPerPage;
			}
			countOftotalContent = (tempQuotient * countPerPage) + tempRemainder;
		}
		// limit가 게시글의 총 개수보다 클 경우 (소수점으로 떨어질 경우)
		else if (limit > countOftotalContent) {
			// 무조건 1페이지만 나오고 그 안에 모든 게시글이 나올 수 있도록 10으로 지정
			countOftotalContent = countPerPage;
		}
		
		
		PageDTO pageDTO = 
				new PageDTO(countOftotalContent, currentPage, countOfPagination, countPerPage);
		return pageDTO;
	}
	
	
	// 메인 검색
	public List<ContentDTO> selectInList(String category_idx) {
		List<ContentDTO> contentDTO = contentDAO.selectInList(category_idx);
		return contentDTO;
	}	
	
	// 메인 검색어 검색
	public List<ContentDTO> selectSearchList(String searchKeyword) {
		List<ContentDTO> contentDTO = contentDAO.selectSearchList(searchKeyword);
		return contentDTO;
	}	
	
	/* 게시글 상세 정보 조회 */
	public ContentDTO getContentDetail(int content_idx) {
		return contentDAO.getContentDetail(content_idx);
	}
	
	// 제품번호 마지막번호에서 +1 증가시킴
	private String prodnoSet() {
		String result = "";
		
		String prodno = contentDAO.getContentProdnoMax();
		
		String[] prodnoArr = prodno.split("_");
		
		String sNumber = "0000000";
		int iNumber = 0;
		
		// NO_0000001 (제품번호 0000001만 사용하도록 예외처리) 
		if(prodnoArr != null) {
			for(String str : prodnoArr) {
				if(!str.equals("NO")) {
					sNumber = str;
				}
			}
		}
		
		if(!sNumber.trim().equals("")) {
			iNumber = Integer.parseInt(sNumber);
		}
		
		// 증가값
		iNumber = iNumber + 1;
		
		result = String.format("%07d", iNumber);
		
		return result;
	}
	
	/* 파일 저장 메소드 */
	private String saveUploadFile(MultipartFile multipartFile) {
		
		String originalFilename = multipartFile.getOriginalFilename();
		String uploadFileName = System.currentTimeMillis() + "_" + originalFilename;

		String uploadPath = pathUpload.replace("file:///", "");
		//경로 자동생성
		File chkMkdir = new File(uploadPath);
		
		try {
			//경로 존재 확인
			if (!chkMkdir.exists()) {
				chkMkdir.mkdirs(); //경로가 존재하지 않을 경우 상위폴더까지 모두 생성
			}
			
			multipartFile.transferTo(new File(uploadPath + uploadFileName));
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return uploadFileName;
	}
	
	/* 게시글 등록 */
	public void addContent(ContentDTO writeContentDTO) {
		
		MultipartFile uploadFile = writeContentDTO.getUploadFile();
		
		if(uploadFile.getSize() > 0) {
			String uploadFileName = saveUploadFile(uploadFile);
			System.out.println("업로드한 파일 이름 : " + uploadFileName);
			writeContentDTO.setContent_file(uploadFileName);
		}
		
		//test
		//writeContentDTO.setContent_writer_idx(1);
		//writeContentDTO.setContent_prodno("NO_0000003");
		
		System.out.println("제품번호 : NO_" + prodnoSet());
		
		writeContentDTO.setContent_writer_idx(loginUserDTO.getUser_idx());
		writeContentDTO.setContent_prodno("NO_" + prodnoSet());

		
		contentDAO.insertContent(writeContentDTO);
		
		
		/* 게시글 100개 등록하는 반복문
		for (int i = 0; i < 100; i++) {
			MultipartFile uploadFile = writeContentDTO.getUploadFile();
			
			if(uploadFile.getSize() > 0) {
				String uploadFileName = saveUploadFile(uploadFile);
				System.out.println("업로드한 파일 이름 : " + uploadFileName);
				writeContentDTO.setContent_file(uploadFileName);
			}
			
			//test
			//writeContentDTO.setContent_writer_idx(1);
			//writeContentDTO.setContent_prodno("NO_0000003");
			
			System.out.println("제품번호 : NO_" + prodnoSet());
			
			writeContentDTO.setContent_writer_idx(loginUserDTO.getUser_idx());
			writeContentDTO.setContent_prodno("NO_" + prodnoSet());

			
			contentDAO.insertContent(writeContentDTO);
			
		}
		*/
		
	}
	
	/* 게시글 수정 */
	public void editContent(ContentDTO modifyContentDTO) {
		
		MultipartFile uploadFile = modifyContentDTO.getUploadFile();
		
		String contentFile = modifyContentDTO.getContent_file();
		
		if(uploadFile.getSize() > 0) {
			//기본 파일 삭제
			if(contentFile != null && !contentFile.trim().equals("")) {
  			File file = new File(pathUpload + "/" + modifyContentDTO.getContent_file());
        boolean fileDelCheck = file.delete();
			}
			
			String uploadFileName = saveUploadFile(uploadFile);
			System.out.println("업로드한 파일 이름 : " + uploadFileName);
			modifyContentDTO.setContent_file(uploadFileName);
		}
		
		contentDAO.updateContent(modifyContentDTO);
	}
	
	//게시글 스크랩 (희만)
	public void insertScrap(int user_idx, int content_idx) {
		contentDAO.insertScrap(user_idx, content_idx);
	}
	
	// 게시글 스크랩 유무 확인 (희만)
	public boolean checkScrap(int user_idx, int content_idx) {
		return contentDAO.checkScrap(user_idx, content_idx);
	}
	
	//게시글 스크랩 취소하기 (희만)
	public void deleteScrap(@Param("user_idx")int user_idx, @Param("content_idx") int content_idx) {
		contentDAO.deleteScrap(user_idx, content_idx);
	}
	
	// 조회수 증가 (희만)
	public void increaseView(int content_idx) {
		contentDAO.increaseView(content_idx);
	}	
	
	
//게시글 삭제하기
	public void deleteContent(int content_idx) {
		try {
		// 실제 삭제 작업 수행
		contentDAO.deleteContent(content_idx);
	} catch (Exception e) {
		// 삭제 중 오류가 발생한 경우 예외를 발생
		throw new RuntimeException("삭제 중 오류 발생 with content_idx :" + content_idx, e);
	}
}
}
