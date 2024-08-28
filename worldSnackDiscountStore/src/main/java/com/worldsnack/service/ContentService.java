package com.worldsnack.service;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.worldsnack.dao.ContentDAO;
import com.worldsnack.dto.ContentDTO;
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
	public List<ContentDTO> selectAllForLimit(int limit) {
		RowBounds rowBounds = new RowBounds(0, limit);
		List<ContentDTO> contentDTO = contentDAO.selectAllForLimit(rowBounds);
		return contentDTO;
	}

	/* 선택한 카테고리 게시글 조회 */
	public List<ContentDTO> selectList(int content_idx) {
		List<ContentDTO> contentDTO = contentDAO.selectList(content_idx);
		return contentDTO;
	}
	
	/* 선택한 카테고리 게시글 중 원하는 갯수만큼 조회 */
	public List<ContentDTO> selectListForLimit(int content_idx, int limit) {
		RowBounds rowBounds = new RowBounds(0, limit);
		List<ContentDTO> contentDTO = contentDAO.selectListForLimit(content_idx, rowBounds);
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
		
		String sNumber = "";
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
		
		try {
			multipartFile.transferTo(new File(pathUpload + "/" + uploadFileName));
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
				
		writeContentDTO.setContent_writer_idx(loginUserDTO.getUser_idx());
		writeContentDTO.setContent_prodno("NO_" + prodnoSet());

		
		contentDAO.insertContent(writeContentDTO);
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
	
}
