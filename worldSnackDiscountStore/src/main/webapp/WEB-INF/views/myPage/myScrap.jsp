<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- c 태그 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="${pageContext.request.contextPath}/" />

<!-- 날짜 형식 변환 태그 -->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>myPage</title>
<!-- Google fonts-->
<link
	href="https://fonts.googleapis.com/css?family=Saira+Extra+Condensed:500,700"
	rel="stylesheet" type="text/css" />
<link
	href="https://fonts.googleapis.com/css?family=Muli:400,400i,800,800i"
	rel="stylesheet" type="text/css" />
<!-- Core theme CSS (includes Bootstrap)-->
<link href="${root }css/mypage.css" rel="stylesheet" />
</head>
<body id="page-top">
	<!-- top_menu 삽입 -->
	<c:import url="/WEB-INF/views/include/top_menu.jsp" />

	<!-- mypage_navigation 삽입 -->
	<c:import url="/WEB-INF/views/include/mypage_nav.jsp"></c:import>
	
	<!-- Page Title -->
	<div id="page-title">
		<h1 class="mb-0 text-center">
			My <span class="text-primary">Page</span>
		</h1>
	</div>
	<!-- 내가 스크랩한 게시글-->
	<section class="resume-section" id="myContent">
		<div class="resume-section-content">
			<h3 class="mb-5">내 스크랩</h3>
			<div class="d-flex justify-content-between align-items-start mb-5">
				<div class="subheading mb-3">스크랩 한 게시글 수 : ${myScrapCount }</div>
				<div>
					<form method="get" action="${root }mypage/myScrap" class="text-primary">
						<label for="categoryList">관심 카테고리</label>
						<select id="categoryList" name="category_info_idx" onchange="this.form.submit()">
							<option value="0">전체</option>
							<c:if test="${categoryDTO != null }">
								<c:forEach var="category" items="${categoryDTO }">
									<option value="${category.category_info_idx }" ${category.category_info_idx == category_info_idx ? 'selected' : '' }>${category.category_info_name }</option>
								</c:forEach>
							</c:if>
							
						</select>
					</form>
				</div>
			</div>
			<!-- 게시판 미리보기 부분 -->
			<div class="container" style="margin-top: 100px">
				<div class="row">
					<div class="col-lg-6" style="margin-top: 20px">
						<div class="card shadow">
							<div class="card-body">
								<h4 class="card-title"></h4>
								<table class="table table-hover" id='board_list'>
									<thead>
										<tr>
											<th class="text-center w-25">글번호</th>
											<th>제목</th>
											<th class="text-center w-25 d-none d-xl-table-cell">작성날짜</th>
											<th class="text-center w-25 d-none d-xl-table-cell">조회수</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="scrapContent" items="${myScrapList }">
											<tr>
												<td class="text-center">${scrapContent.content_idx }</td>
												<th><a href='${root }content/detail?content_idx=${scrapContent.content_idx}&page=${page}'>${scrapContent.content_subject }</a></th>
												<td class="text-center d-none d-xl-table-cell"><fmt:formatDate pattern="yyyy-MM-dd" value="${scrapContent.content_date }"/></td>
												<td class="text-center d-none d-xl-table-cell">${scrapContent.content_view }</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								<div class="d-none d-md-block">
									<ul class="pagination justify-content-center">
									<c:choose>
								   	<c:when test="${pageDTO.prevPage <= 0 }" >	
										<li class="page-item disabled">
											<a href="#" class="page-link">이전</a>
										</li>
									</c:when>
									<c:otherwise>
										<li class="page-item">
											<a href="${root}mypage/myScrap?page=${pageDTO.prevPage}&category_info_idx=${category_info_idx}" class="page-link">이전</a>
										</li>
									</c:otherwise>	
								    </c:choose>		
										<c:forEach var="idx" begin="${pageDTO.min}" end="${pageDTO.max}">
										<c:choose>
										<c:when test="${idx == pageDTO.currentPage }" >
											<li class="page-item active">
												<a href="${root}mypage/myScrap?page=${idx}&category_info_idx=${category_info_idx}" class="page-link">${idx }</a>
											</li>
										</c:when>
										<c:otherwise>
										    <li class="page-item">
												<a href="${root}mypage/myScrap?page=${idx}&category_info_idx=${category_info_idx}" class="page-link">${idx }</a>
											</li>
										</c:otherwise>	
										</c:choose>	
										</c:forEach>
										<c:choose>
									   	<c:when test="${pageDTO.max >= pageDTO.totalPage }" >	
											<li class="page-item disabled">
												<a href="#" class="page-link">다음</a>
											</li>
										</c:when>
										<c:otherwise>
											<li class="page-item">
												<a href="${root}mypage/myScrap?page=${pageDTO.nextPage}&category_info_idx=${category_info_idx}" class="page-link">다음</a>
											</li>
										</c:otherwise>	
									    </c:choose>	
									</ul>
								</div>
								
								<div class="text-right">
									<a href="${root }content/write" class="btn btn-primary">글쓰기</a>
								</div>								
								<a href="${root }content/main" class="btn btn-primary">제품게시판 전체보기</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	<hr class="m-0" />
	
	<!-- bottom_menu 삽입 -->
  <c:import url="/WEB-INF/views/include/bottom_menu.jsp" />

	<!-- Bootstrap core JS-->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
	<!-- Core theme JS-->
	<script src="${root }js/mypage.js"></script>
</body>
</html>
