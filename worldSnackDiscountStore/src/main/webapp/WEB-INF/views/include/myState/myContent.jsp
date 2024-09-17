<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="${pageContext.request.contextPath}/" />

<!-- 날짜 형식 변환 태그 -->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

								<div id="content1">
									<form action="${root}mypage/deletePost" method="post">
										<input type="hidden" name="page" value="${page }">
										<table class="table table-hover" id='board_list'>
											<colgroup>
												<col class="col1" width="5%">
												<col class="col2" width="20%">
												<col class="col3" width="">
												<col class="col4" width="20%">
												<col class="col5" width="15%">
											</colgroup>
											<thead>
												<tr>
													<th><input type="checkbox" class="chkboxAll" name="checkboxAll"></th>
													<th class="text-center">글번호</th>
													<th>제목</th>
													<th class="text-center d-none d-xl-table-cell">작성일</th>
													<th class="text-center d-none d-xl-table-cell">조회수</th>
												</tr>
											</thead>
												<tbody>
													<c:forEach var="content" items="${myCommunityContentDTO }">
														<tr>
															<td><input type="checkbox" name="community_idx" value="${content.community_idx }"  class="chkbox"></td>
															<td class="text-center">${content.community_idx }</td>
															<th>
																<a href="${root}board/post/${content.community_idx}">${content.community_subject}<span style="font-weight: normal; color: red;">[${content.community_comment }]</span> </a>
											        </th>										
															<td class="text-center d-none d-xl-table-cell"><fmt:formatDate pattern="yyyy-MM-dd" value="${content.community_date }"/></td>
															<td class="text-center d-none d-xl-table-cell">${content.community_view }</td>
														</tr>
													</c:forEach>
												</tbody>
										</table>
									
										<div class="d-none d-md-block">
											<ul class="pagination justify-content-center">
											<c:choose>
										   	<c:when test="${contentPageDTO.prevPage <= 0 }" >	
												<li class="page-item disabled">
													<a href="#" class="page-link">이전</a>
												</li>
											</c:when>
											<c:otherwise>
												<li class="page-item">
													<a href="${root}mypage/myState?page=${contentPageDTO.prevPage}&content=content1" class="page-link">이전</a>
												</li>
											</c:otherwise>	
										    </c:choose>		
												<c:forEach var="idx" begin="${contentPageDTO.min}" end="${contentPageDTO.max}">
												<c:choose>
												<c:when test="${idx == contentPageDTO.currentPage }" >
													<li class="page-item active">
														<a href="${root}mypage/myState?page=${idx}&content=content1" class="page-link">${idx }</a>
													</li>
												</c:when>
												<c:otherwise>
												    <li class="page-item">
														<a href="${root}mypage/myState?page=${idx}&content=content1" class="page-link">${idx }</a>
													</li>
												</c:otherwise>	
												</c:choose>	
												</c:forEach>
												<c:choose>
											   	<c:when test="${contentPageDTO.max >= contentPageDTO.totalPage }" >	
													<li class="page-item disabled">
														<a href="#" class="page-link">다음</a>
													</li>
												</c:when>
												<c:otherwise>
													<li class="page-item">
														<a href="${root}mypage/myState?page=${contentPageDTO.nextPage}&content=content1" class="page-link">다음</a>
													</li>
												</c:otherwise>	
											    </c:choose>	
											</ul>
										</div>
										
										<div class="text-right">
											<a href="${root }board/community" class="btn btn-primary" style="color:white;margin: 0 2px;">커뮤니티 전체보기</a>
											<a href="${root }board/newPost" class="btn btn-primary" style="color:white;margin: 0 2px;">글쓰기</a>
										</div>					
										<button type="submit" class="btn btn-danger" onclick="return confirm('정말로 삭제하시겠습니까?');">삭제</button>
										
										
									</form>
								</div>