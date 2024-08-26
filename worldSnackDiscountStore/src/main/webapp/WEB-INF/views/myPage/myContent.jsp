<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="${pageContext.request.contextPath}/" />

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
	<!-- 내가 쓴 게시글-->
	<section class="resume-section" id="myContent">
		<div class="resume-section-content">
			<h3 class="mb-5">내 게시글</h3>
			<div class="d-flex justify-content-between align-items-start mb-5">
				<div class="subheading mb-3">게시글 업로드 수 : ${myContentCount }</div>
				<div>
					<span class="text-primary">최근 포스팅 - ${myContentDate }</span>
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
										<c:forEach var="content" items="${myContentList }">
											<tr>
												<td class="text-center">${content.content_idx }</td>
												<th><a href='${root }board/read?content_idx=${content.content_idx}&page=${page}'>${content.content_subject }</a></th>
												<td class="text-center d-none d-xl-table-cell">${content.content_date }</td>
												<td class="text-center d-none d-xl-table-cell">${content.content_view }</td>
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
											<a href="${root}mypage/myContent?page=${pageDTO.prevPage}" class="page-link">이전</a>
										</li>
									</c:otherwise>	
								    </c:choose>		
										<c:forEach var="idx" begin="${pageDTO.min}" end="${pageDTO.max}">
										<c:choose>
										<c:when test="${idx == pageDTO.currentPage }" >
											<li class="page-item active">
												<a href="${root}mypage/myContent?page=${idx}" class="page-link">${idx }</a>
											</li>
										</c:when>
										<c:otherwise>
										    <li class="page-item">
												<a href="${root}mypage/myContent?page=${idx}" class="page-link">${idx }</a>
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
												<a href="${root}mypage/myContent?page=${pageDTO.nextPage}" class="page-link">다음</a>
											</li>
										</c:otherwise>	
									    </c:choose>	
									</ul>
								</div>
								
								<div class="text-right">
									<a href="${root }board/write" class="btn btn-primary">글쓰기</a>
								</div>								
								<a href="${root }board/main" class="btn btn-primary">제품게시판 전체보기</a>
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
