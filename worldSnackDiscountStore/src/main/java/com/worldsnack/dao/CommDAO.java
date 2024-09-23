package com.worldsnack.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.worldsnack.dto.CommDTO;
import com.worldsnack.mapper.CommMapper;

@Repository
public class CommDAO {

  @Autowired
  private CommMapper commMapper;

  
  // 카테고리와 정렬 기준에 따른 게시글 목록 조회
  public List<CommDTO> findPostsByCategoryAndSortOrder(int lastCommunityId, String category, String sortOrder, String viewType) {
    return commMapper.findPostsByCategory(lastCommunityId, category, sortOrder, viewType);
  }
  
  public List<CommDTO> getAllPostsByWeightedScore(int lastCommunityId) {
    return commMapper.findAllPostsByWeightedScore(lastCommunityId);
  }
  
  public List<CommDTO> findHotPosts(int lastCommunityId, String category, String viewType) {
    return commMapper.findHotPosts(lastCommunityId, category, viewType);
  }

  // 게시글 ID에 따른 게시글 조회
  public CommDTO findPostById(int id) {
    return commMapper.findPostById(id);
  }

  // 게시글 저장
  public void savePost(CommDTO post) {
    commMapper.savePost(post);
  }

  // 게시글 삭제
  public void deletePost(int id) {
    commMapper.deletePost(id);
  }

  // 게시글 업데이트
  public void updatePost(CommDTO post) {
    commMapper.updatePost(post);
  }
  
  //게시글 스크랩
	public void insertScrap(int user_idx, int community_idx) {
		commMapper.insertScrap(user_idx, community_idx);
	}
	
	// 게시글 스크랩 유무 확인
	public boolean checkScrap(int user_idx, int community_idx) {
		return commMapper.checkScrap(user_idx, community_idx);
	}
	
	// 게시글 스크랩 취소하기
	public void deleteScrap(@Param("user_idx")int user_idx, @Param("community_idx") int community_idx) {
		commMapper.deleteScrap(user_idx, community_idx);
	}
  
}
