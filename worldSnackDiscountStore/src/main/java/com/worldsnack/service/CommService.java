package com.worldsnack.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.worldsnack.dao.CommDAO;
import com.worldsnack.dto.CommDTO;
import com.worldsnack.dto.UserDTO;

import net.coobird.thumbnailator.Thumbnails;

@Service
public class CommService {

  @Autowired
  private CommDAO commDAO;
  
  //커뮤니티 이미지 업로드 경로 설정
  @Value("${path.upload.community}")
  private String communityUploadPath;
  
  @Value("${path.upload.thumbnails}")
  private String thumbnailUploadPath;
  
  @Resource(name="loginUserDTO")
	private UserDTO loginUserDTO;
  
  public int getCurrentUserId() {
    return loginUserDTO.getUser_idx();
  }

  public boolean isUserLoggedIn() {
  	return loginUserDTO != null && getCurrentUserId() > 0;
  }

  // 작성자 확인
  public boolean isUserAuthor(int postId) {
      CommDTO post = commDAO.findPostById(postId);
      return post != null && getCurrentUserId() == post.getCommunity_writer_idx();
  }
  
  //이미지 파일인지 확인하는 메서드
   private boolean isImageFile(MultipartFile file) {
     String contentType = file.getContentType();
     return contentType != null && contentType.startsWith("image/");
   }

  // 카테고리와 정렬 기준에 따른 게시글 목록 조회
  public List<CommDTO> getPostsByCategoryAndSortOrder(String category, String sortOrder, String viewType) {
    return commDAO.findPostsByCategoryAndSortOrder(category, sortOrder, viewType);
  }
  
  public List<CommDTO> getAllPostsSortedByWeightedScore() {
    return commDAO.getAllPostsByWeightedScore();
  }
  
  public List<CommDTO> getHotPosts(String category, String viewType) {
    return commDAO.findHotPosts(category, viewType);
  }

  // 게시글 ID에 따른 게시글 조회
  public CommDTO getPostById(int id) {
    return commDAO.findPostById(id);
  }

  // 텍스트 게시글 저장
  public int saveTextPost(CommDTO post) {
  	post.setCommunity_writer_idx(getCurrentUserId());
    commDAO.savePost(post);
    return post.getCommunity_idx();
  }
  
  // 이미지 게시글 저장
  public int saveImagePost(CommDTO post, MultipartFile file) {
  	
  	String currentText = post.getCommunity_text() != null ? post.getCommunity_text() : "";
    post.setCommunity_text(currentText);
    
    String originalFileName = file.getOriginalFilename();
    String uploadFileName = System.currentTimeMillis() + "_" + originalFileName;
    
    // 지정된 경로에 파일 저장
    File destFile = new File(communityUploadPath, uploadFileName);
    
    try {
      // 업로드 디렉토리가 존재하지 않으면 생성
      File directory = new File(communityUploadPath);
      if (!directory.exists()) {
          directory.mkdirs();
      }

      // 파일을 지정된 경로에 저장
      file.transferTo(destFile);
      
      // 이미지 파일인지 확인 후 썸네일 생성
      if (isImageFile(file)) {
        // 썸네일 디렉토리가 존재하지 않으면 생성
        File thumbnailDirectory = new File(thumbnailUploadPath);
        if (!thumbnailDirectory.exists()) {
          thumbnailDirectory.mkdirs();
        }

        // 썸네일 파일명 및 경로 설정
        String thumbnailFileName = "thumb_" + uploadFileName;
        File thumbnailFile = new File(thumbnailUploadPath, thumbnailFileName);

        // 썸네일 생성
        Thumbnails.of(destFile)
                  .size(200, 200)
                  .outputQuality(1.0)
                  .toFile(thumbnailFile);

        // 썸네일 경로 설정
        post.setCommunity_thumb("uploads/thumbnails/" + thumbnailFileName);
      }

      // 업로드된 파일의 경로 설정 (웹 경로로 설정해야 함)
      post.setCommunity_file("uploads/community/" + uploadFileName);
      
    } catch (IOException e) {
        e.printStackTrace();
        return -1;
    }
    
    post.setCommunity_writer_idx(getCurrentUserId());
    commDAO.savePost(post);
    return post.getCommunity_idx();
  }

  // 랭킹 게시글 저장
  public int saveRankingPost(CommDTO post, List<String> options) {
  	// 옵션들을 포맷팅하여 번호와 함께 문자열로 합침
    StringBuilder optionsText = new StringBuilder();
    for (int i = 0; i < options.size(); i++) {
       optionsText.append(i + 1).append(". ").append(options.get(i)).append("<p>");
    }
    
    // community_text가 null인 경우 빈 문자열로 초기화
    String currentText = post.getCommunity_text() != null ? post.getCommunity_text() : "";
     
    // 기존 텍스트와 합쳐서 저장
    post.setCommunity_text(currentText + optionsText.toString());
    
    post.setCommunity_writer_idx(getCurrentUserId());
    commDAO.savePost(post);
    return post.getCommunity_idx();
  }

  // 프로모션 게시글 저장
  public int savePromotionPost(CommDTO post, MultipartFile file) {
    
  	String currentText = post.getCommunity_text() != null ? post.getCommunity_text() : "";
    post.setCommunity_text(currentText);
    
    String originalFileName = file.getOriginalFilename();
    String uploadFileName = System.currentTimeMillis() + "_" + originalFileName;
    
    // 지정된 경로에 파일 저장
    File destFile = new File(communityUploadPath, uploadFileName);
    
    try {
      // 업로드 디렉토리가 존재하지 않으면 생성
      File directory = new File(communityUploadPath);
      if (!directory.exists()) {
          directory.mkdirs();
      }

      // 파일을 지정된 경로에 저장
      file.transferTo(destFile);
      
    	// 이미지 파일인지 확인 후 썸네일 생성
      if (isImageFile(file)) {
        // 썸네일 디렉토리가 존재하지 않으면 생성
        File thumbnailDirectory = new File(thumbnailUploadPath);
        if (!thumbnailDirectory.exists()) {
          thumbnailDirectory.mkdirs();
        }

        // 썸네일 파일명 및 경로 설정
        String thumbnailFileName = "thumb_" + uploadFileName;
        File thumbnailFile = new File(thumbnailUploadPath, thumbnailFileName);

        // 썸네일 생성
        Thumbnails.of(destFile)
                  .size(60, 60)
                  .toFile(thumbnailFile);

        // 썸네일 경로 설정
        post.setCommunity_thumb("uploads/thumbnails/" + thumbnailFileName);
      }

      // 업로드된 파일의 경로 설정 (웹 경로로 설정해야 함)
      post.setCommunity_file("uploads/community/" + uploadFileName);
      
    } catch (IOException e) {
        e.printStackTrace();
        return -1;
    }
    
    post.setCommunity_writer_idx(getCurrentUserId());
    commDAO.savePost(post);
    return post.getCommunity_idx();
  }

  // 게시글 삭제
  public void deletePost(int id) {
    commDAO.deletePost(id);
  }

  // 게시글 업데이트
  public void updatePost(CommDTO post, MultipartFile file) {
  	if (file != null && !file.isEmpty()) {
      String originalFileName = file.getOriginalFilename();
      String uploadFileName = System.currentTimeMillis() + "_" + originalFileName;

      File destFile = new File(communityUploadPath, uploadFileName);

      try {
        File directory = new File(communityUploadPath);
        if (!directory.exists()) {
          directory.mkdirs();
        }

        file.transferTo(destFile);
        
        // 이미지 파일인지 확인 후 썸네일 생성
        if (isImageFile(file)) {
          // 썸네일 디렉토리가 존재하지 않으면 생성
          File thumbnailDirectory = new File(thumbnailUploadPath);
          if (!thumbnailDirectory.exists()) {
            thumbnailDirectory.mkdirs();
          }

          // 썸네일 파일명 및 경로 설정
          String thumbnailFileName = "thumb_" + uploadFileName;
          File thumbnailFile = new File(thumbnailUploadPath, thumbnailFileName);

          // 썸네일 생성
          Thumbnails.of(destFile)
                    .size(60, 60)
                    .toFile(thumbnailFile);

          // 썸네일 경로 설정
          post.setCommunity_thumb("uploads/thumbnails/" + thumbnailFileName);
        }

        // 업로드된 파일의 경로 설정 (웹 경로로 설정해야 함)
        post.setCommunity_file("uploads/community/" + uploadFileName);
        
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  	
  	post.setCommunity_writer_idx(getCurrentUserId());
    commDAO.updatePost(post);
  }
    
  //랭킹 게시글 업데이트 메서드
  public void updateRankingPost(CommDTO post, List<String> options) {
  	StringBuilder optionsText = new StringBuilder();
    for (int i = 0; i < options.size(); i++) {
       optionsText.append(i + 1).append(". ").append(options.get(i)).append("<p>");
    }
    
    // community_text가 null인 경우 빈 문자열로 초기화
    String currentText = post.getCommunity_text() != null ? post.getCommunity_text() : "";
     
    // 기존 텍스트와 합쳐서 저장
    post.setCommunity_text(currentText + optionsText.toString());

    post.setCommunity_writer_idx(getCurrentUserId());
    commDAO.updatePost(post);
  }

  //프로모션 게시글 업데이트 메서드
  public void updatePromotionPost(CommDTO post, MultipartFile promotion_file) {
  	
    String currentText = post.getCommunity_text() != null ? post.getCommunity_text() : "";
    post.setCommunity_text(currentText);

    // 파일이 null 또는 비어 있지 않은지 확인
    if (promotion_file != null && !promotion_file.isEmpty()) {
        try {
            String originalFileName = promotion_file.getOriginalFilename();
            String uploadFileName = System.currentTimeMillis() + "_" + originalFileName;

            // 업로드 경로와 파일 이름 설정
            File destFile = new File(communityUploadPath, uploadFileName);

            // 디렉토리 존재 여부 확인 및 생성
            File directory = new File(communityUploadPath);
            if (!directory.exists()) {
                boolean dirCreated = directory.mkdirs();
                if (!dirCreated) {
                    System.out.println("디렉토리 생성 실패!");
                    return;
                }
            }

            // 파일 전송 시도
            promotion_file.transferTo(destFile);
            
            // 이미지 파일인지 확인 후 썸네일 생성
            if (isImageFile(promotion_file)) {
              // 썸네일 디렉토리가 존재하지 않으면 생성
              File thumbnailDirectory = new File(thumbnailUploadPath);
              if (!thumbnailDirectory.exists()) {
                thumbnailDirectory.mkdirs();
              }

              // 썸네일 파일명 및 경로 설정
              String thumbnailFileName = "thumb_" + uploadFileName;
              File thumbnailFile = new File(thumbnailUploadPath, thumbnailFileName);

              // 썸네일 생성
              Thumbnails.of(destFile)
                        .size(60, 60) // 썸네일 크기 설정
                        .toFile(thumbnailFile);

              // 썸네일 경로 설정
              post.setCommunity_thumb("uploads/thumbnails/" + thumbnailFileName);
            } else {
              // 이미지 파일이 아닌 경우 경고 메시지 또는 다른 처리
              System.out.println("이미지 파일이 아닙니다. 썸네일을 생성하지 않습니다.");
            }

            // 업로드된 파일의 경로 설정 (웹 경로로 설정해야 함)
            post.setCommunity_file("uploads/community/" + uploadFileName);
            
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("파일 업로드 실패: " + e.getMessage());
        }
    } else {
        System.out.println("업로드할 파일이 비어있거나 존재하지 않습니다.");
    }

    post.setCommunity_writer_idx(getCurrentUserId());
    commDAO.updatePost(post);
}

}
