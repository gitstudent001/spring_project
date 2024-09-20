package com.worldsnack.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.worldsnack.dto.CommDTO;
import com.worldsnack.mapper.CommMapper;

@Repository
public class CommDAO {

  @Autowired
  private CommMapper commMapper;

  
  // 카테고리와 정렬 기준에 따른 게시글 목록 조회
  public List<CommDTO> findPostsByCategoryAndSortOrder(String category, String sortOrder, String viewType) {
    return commMapper.findPostsByCategory(category, sortOrder, viewType);
  }
  
  public List<CommDTO> getAllPostsByWeightedScore() {
    return commMapper.findAllPostsByWeightedScore();
  }
  
  public List<CommDTO> findHotPosts(String category, String viewType) {
    return commMapper.findHotPosts(category, viewType);
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
}
