package com.worldsnack.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.worldsnack.dao.ContentDAO;
import com.worldsnack.dto.ContentDTO;

@Service
@PropertySource("/WEB-INF/properties/upload.properties")
public class ContentService {

	@Value("${path.upload}")
	private String pathUpload;
	
	@Autowired
	private ContentDAO contentDAO;
	
	/*
	@Resource(name="loginUserDTO")
	private UserDTO loginUserDTO;
	*/
	
	public List<ContentDTO> selectAll() {
		List<ContentDTO> contentDTO = contentDAO.selectAll();
		return contentDTO;
	}
	
	public List<ContentDTO> selectList(int content_idx) {
		List<ContentDTO> contentDTO = contentDAO.selectList(content_idx);
		return contentDTO;
	}
	
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
	
	public void addContent(ContentDTO writeContentDTO) {
		
		MultipartFile uploadFile = writeContentDTO.getUploadFile();
		
		if(uploadFile.getSize() > 0) {
			String uploadFileName = saveUploadFile(uploadFile);
			System.out.println("업로드한 파일 이름 : " + uploadFileName);
			writeContentDTO.setContent_file(uploadFileName);
		}
		
		//test
		writeContentDTO.setContent_writer_idx(1);
		//writeContentDTO.setContent_prodno("NO_0000003");
				
		//writeContentDTO.setContent_writer_idx(loginUserDTO.getUser_idx());
		writeContentDTO.setContent_prodno("NO_" + prodnoSet());

		
		contentDAO.insertContent(writeContentDTO);
	}
	
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
