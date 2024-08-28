package com.worldsnack.dto;

import lombok.Getter;

/*
  pagination 할 때 필요한 page 정보를 저장하는 pageDTO
  
  min         : 페이지 네비게이터에 나오는 최소 페이지 번호
  max         : 페이지 네비게이터에 나오는 최대 페이지 번호
  prevPage    : 이전 버튼의 페이지 번호
  nextPage    : 다음 버튼의 페이지 번호
  totalPage   : 전체 페이지 개수
  currentPage : 현재 페이지 번호
  
  생성자의 parameter 로 아래의 값을 받음
  totalContent  : 전체 게시글 개수
  currentPage   : 현재 페이지 번호
  countOfPagination : 페이지 네비게이터에 나오는 페이지 개수
                     (페이지 네비게이터에 나오는 페이지 버튼 개수)
  countPerPage  : 한 페이지 당 게시글 개수
*/
@Getter
public class PageDTO {
  private int min;
  private int max;
  private int prevPage;
  private int nextPage;
  private int totalPage;
  private int currentPage;
  
  public PageDTO(int countOftotalContent, int currentPage, 
  		           int countOfPagination, int countPerPage) {
  	// 현재 페이지 번호
  	this.currentPage = currentPage;
  	
  	// 전체 페이지 개수 = 전체 게시글 개수 / 한 페이지 당 게시글 개수
  	this.totalPage = countOftotalContent / countPerPage;
  	if(countOftotalContent % countPerPage > 0) {
  		totalPage++;
  	}
  	
    // 페이지 네비게이터에 나오는 최소 페이지 번호
    min = ((currentPage - 1) / countPerPage) * countPerPage + 1;
    
    // 페이지 네비게이터에 나오는 최대 페이지 번호
    max = min + countOfPagination - 1;
    
    // 최대 페이지로 설정된 값이(max:50) 실제 최대 페이지(totalPage:46)
    //   ㄴ 최대 페이지를 (max)  실제 최대 페이지(totalPage) 로 설정함  
    if(max > totalPage){
      max = totalPage;
    }
  	
    prevPage = min - 1;
    nextPage = max + 1;
    
    if(nextPage > totalPage){
      nextPage = totalPage;
    }
    
  }
}


