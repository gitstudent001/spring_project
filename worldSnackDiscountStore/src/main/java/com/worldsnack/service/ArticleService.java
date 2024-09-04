package com.worldsnack.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.benmanes.caffeine.cache.Cache;
import com.worldsnack.dao.ArticleDAO;
import com.worldsnack.dto.CommDTO;
import com.worldsnack.dto.UserDTO;

@Service
public class ArticleService {

  @Autowired
  private Cache<String, Boolean> viewCache;

  @Autowired
  private ArticleDAO articleDAO;

  @Transactional
  public CommDTO getArticleById(int id, HttpServletRequest request, UserDTO user) {
    String identifier = (user != null) ? String.valueOf(user.getUser_idx()) : getClientIp(request);
    String cacheKey = "article:" + id + ":viewed:" + identifier;

    if (viewCache.getIfPresent(cacheKey) == null) {
      articleDAO.increaseViewCount(id);
      viewCache.put(cacheKey, true);
    }

    return articleDAO.findPostById(id); 
  }

  private String getClientIp(HttpServletRequest request) {
    String ip = request.getHeader("X-Forwarded-For");
    if (ip == null) {
      ip = request.getRemoteAddr();
    }
    return ip.split(",")[0];
  }
  
  public int upvoteArticle(int id) {
    articleDAO.incrementUpvotes(id);
    return articleDAO.getNetVotes(id);
  }
  
  public int downvoteArticle(int id) {
      articleDAO.incrementDownvotes(id);
      return articleDAO.getNetVotes(id);
  }
  
}



