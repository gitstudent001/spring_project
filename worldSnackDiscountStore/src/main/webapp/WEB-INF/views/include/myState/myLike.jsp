<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="${pageContext.request.contextPath}/" />

								<div id="content3">
									<form action="${root}mypage/deleteContent" method="post">
										<input type="hidden" name="page" value="${page }">
										<input type="hidden" name="content" value="content3">										
										<table class="table table-hover" id='board_list'>
											<colgroup>
												<col class="col1" width="5%">
												<col class="col2" width="15%">
												<col class="col3" width="">
												<col class="col4" width="20%">
											</colgroup>
											<thead>
												<tr>
													<th><input type="checkbox" class="chkboxAll" name="checkboxAll"></th>
													<th class="text-center">글번호</th>
													<th>제목</th>
													<th class="text-center d-none d-xl-table-cell">관심 수</th>
												</tr>
											</thead>
												<tbody>
													<c:forEach var="content" items="${myContentByScrapContentDTO }">
														<tr>
															<td><input type="checkbox" name="content_idx" value="${content.content_idx }"  class="chkbox"></td>
															<td class="text-center">${content.content_idx }</td>
															<th>
																<a href="#" class="submit-link" data-content-idx="${content.content_idx }">${content.content_subject }</a>
											        </th>
															<td class="text-center d-none d-xl-table-cell">${content.scrap_count }</td>
														</tr>
													</c:forEach>
												</tbody>
										</table>
									
										<div class="d-none d-md-block">
											<ul class="pagination justify-content-center">
											<c:choose>
										   	<c:when test="${scrapPageDTO.prevPage <= 0 }" >	
												<li class="page-item disabled">
													<a href="#" class="page-link">이전</a>
												</li>
											</c:when>
											<c:otherwise>
												<li class="page-item">
													<a href="${root}mypage/myState?page=${scrapPageDTO.prevPage}&content=content3" class="page-link">이전</a>
												</li>
											</c:otherwise>	
										    </c:choose>		
												<c:forEach var="idx" begin="${scrapPageDTO.min}" end="${scrapPageDTO.max}">
												<c:choose>
												<c:when test="${idx == scrapPageDTO.currentPage }" >
													<li class="page-item active">
														<a href="${root}mypage/myState?page=${idx}&content=content3" class="page-link">${idx }</a>
													</li>
												</c:when>
												<c:otherwise>
												    <li class="page-item">
														<a href="${root}mypage/myState?page=${idx}&content=content3" class="page-link">${idx }</a>
													</li>
												</c:otherwise>	
												</c:choose>	
												</c:forEach>
												<c:choose>
											   	<c:when test="${scrapPageDTO.max >= scrapPageDTO.totalPage }" >	
													<li class="page-item disabled">
														<a href="#" class="page-link">다음</a>
													</li>
												</c:when>
												<c:otherwise>
													<li class="page-item">
														<a href="${root}mypage/myState?page=${scrapPageDTO.nextPage}&content=content3" class="page-link">다음</a>
													</li>
												</c:otherwise>	
											    </c:choose>	
											</ul>
										</div>
										
										<div class="text-right">
											<a href="${root }mypage/myContent" class="btn btn-primary" style="color:white;margin: 0 2px;">내 작성글 전체보기</a>
										</div>					
										<button type="submit" class="btn btn-danger" onclick="return confirm('정말로 삭제하시겠습니까?');">삭제</button>

									</form>
								</div>