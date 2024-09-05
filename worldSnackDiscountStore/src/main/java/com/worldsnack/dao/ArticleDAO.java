package com.worldsnack.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.worldsnack.dto.CommDTO;
import com.worldsnack.mapper.CommMapper;

@Repository
public class ArticleDAO {
	
	@Autowired
  private CommMapper commMapper;
	
	public void increaseViewCount(int id) {
    commMapper.increaseViewCount(id);
	}
	
	public CommDTO findPostById(int id) {
   return commMapper.findPostById(id);
	}
	
  public void incrementUpvotes(int id) {
  	commMapper.incrementUpvotes(id);
  }

  public void incrementDownvotes(int id) {
    commMapper.incrementDownvotes(id);
  }

  public int getNetVotes(int id) {
    return commMapper.getNetVotes(id);
  }
	
}
