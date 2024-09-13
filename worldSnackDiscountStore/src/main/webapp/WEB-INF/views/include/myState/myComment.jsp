<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="${pageContext.request.contextPath}/" />
								<div id="content2">
									<form action="${root}mypage/deleteComment" method="post">
										<input type="hidden" name="page" value="${commentPageDTO.currentPage }">
										<input type="hidden" name="content" value="content2">
										<table class="table table-hover" id='board_list'>
											<colgroup>
												<col class="col1" width="5%">
												<col class="col2" width="15%">
												<col class="col3" width="">
												<col class="col4" width="20%">
											</colgroup>
											<thead>
												<tr>
													<th><input type="checkbox" class="chkboxAll"></th>
													<th class="text-center">댓글번호</th>
													<th>댓글</th>
													<th class="text-center d-none d-xl-table-cell">작성일</th>
												</tr>
											</thead>
												<tbody>
													<c:forEach var="comment" items="${myCommunityCommentDTO }">
														<tr>
															<td><input type="checkbox" name="comment_idx" value="${comment.comment_idx }"  class="chkbox"></td>
															<td class="text-center">${comment.comment_idx }</td>
															<th>
																<a href="${root}board/post/${comment.post_id}" class="truncate">${comment.comment_text}</a>
											        </th>										
															<td class="text-center d-none d-xl-table-cell"><fmt:formatDate pattern="yyyy-MM-dd" value="${comment.comment_date }"/></td>
														</tr>
													</c:forEach>
												</tbody>
										</table>
									
										<div class="d-none d-md-block">
											<ul class="pagination justify-content-center">
											<c:choose>
										   	<c:when test="${commentPageDTO.prevPage <= 0 }" >	
												<li class="page-item disabled">
													<a href="#" class="page-link">이전</a>
												</li>
											</c:when>
											<c:otherwise>
												<li class="page-item">
													<a href="${root}mypage/myState?page=${commentPageDTO.prevPage}&content=content2" class="page-link">이전</a>
												</li>
											</c:otherwise>	
										    </c:choose>		
												<c:forEach var="idx" begin="${commentPageDTO.min}" end="${commentPageDTO.max}">
												<c:choose>
												<c:when test="${idx == commentPageDTO.currentPage }" >
													<li class="page-item active">
														<a href="${root}mypage/myState?page=${idx}&content=content2" class="page-link">${idx }</a>
													</li>
												</c:when>
												<c:otherwise>
												    <li class="page-item">
														<a href="${root}mypage/myState?page=${idx}&content=content2" class="page-link">${idx }</a>
													</li>
												</c:otherwise>	
												</c:choose>	
												</c:forEach>
												<c:choose>
											   	<c:when test="${commentPageDTO.max >= commentPageDTO.totalPage }" >	
													<li class="page-item disabled">
														<a href="#" class="page-link">다음</a>
													</li>
												</c:when>
												<c:otherwise>
													<li class="page-item">
														<a href="${root}mypage/myState?page=${commentPageDTO.nextPage}&content=content2" class="page-link">다음</a>
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