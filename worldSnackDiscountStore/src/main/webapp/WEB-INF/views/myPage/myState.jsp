<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

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

	<!-- 회원 탈퇴 -->
	<section class="resume-section" id="myState">
		<div class="resume-section-content">
			<h3 class="mb-5">나의 활동</h3>
			<div class="main-content">
				<div class="row">
					<!-- 나의 활동 요약 -->
					<div class="col-lg-6 col-md-6 left-box">
						<div class="content-box blue-box card shadow">
							<div class="content-box-title">
								<h4>${loginUserDTO.user_name }</h4>
								<p style="text-align:right; font-size:13px;"><fmt:formatDate pattern="yyyy.MM.dd" value="${loginUserDTO.user_first_join }"/>가입</p>
								<hr style="background-color: #879deb;height: 3px;border: none;">
							</div>
							<div class="content-box-body">
								<div class="content-box-body-left">
									<ul class="navbar-nav">
										<li class="nav-item"><span>최근방문기록</span></li>
										<li class="nav-item"><span>방문</span></li>
										<li class="nav-item"><span>총 댓글</span></li>
										<li class="nav-item"><span>총 게시글</span></li>
										<li class="nav-item"><span>받은 관심</span></li>
										<li class="nav-item"><span>활동 시간</span></li>
									</ul>
								</div>
								<div class="content-box-body-right">
									<ul class="navbar-nav">
										<li class="nav-item" style="font-size:14px;">
											<div style="height:24px;">
												<fmt:formatDate pattern="yyyy.MM.dd.HH:mm:dd" value="${recentVisitTime }"/>
											</div>
										</li>
										<li class="nav-item"><span>${visitCount }회</span></li>
										<li class="nav-item"><a href="#">400개</a></li>
										<li class="nav-item"><a href="#">200개</a></li>
										<li class="nav-item"><a href="#">56개</a></li>
										<li class="nav-item"><span>총 ${activityTime }시간</span></li>
									</ul>
								</div>
							</div>

						</div>
					</div>
					<!-- 게시글 / 댓글 요약 -->
					<div class="col-lg-6 col-md-6 right-box">
						<div class="content-box orange-box card shadow">
							<div class="content-box-title">
									<a class="nav-link js-scroll-trigger" href="#">작성글</a>
									<a class="nav-link js-scroll-trigger" href="#">작성댓글</a>
									<a class="nav-link js-scroll-trigger" href="#">관심 받은 글</a>
									<a class="nav-link js-scroll-trigger" href="#">임시 메뉴</a>
									
							</div>
							<hr style="background-color: #bd5d38;height: 3px;border: none;margin-top: 16px;margin-bottom: 0px;">
							<div class="card-body">
								<table class="table table-hover" id='board_list'>
									<thead>
										<tr>
											<th class="text-center w-25">글번호</th>
											<th>제목</th>
											<th class="text-center w-25 d-none d-xl-table-cell">작성일</th>
											<th class="text-center w-25 d-none d-xl-table-cell">조회수</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="content" items="${myContentList }">
											<tr>
												<td class="text-center">${content.content_idx }</td>
												<th>
													<form action="${root }content/detail" method="post" id="postForm_${content.content_idx }">
														<input type="hidden" name="content_idx" value="${content.content_idx }">
														<a href="#" onclick="document.getElementById('postForm_${content.content_idx}').submit();">
															${content.content_subject}
										        </a>
									        </form>		
								        </th>										
												<td class="text-center d-none d-xl-table-cell"><fmt:formatDate pattern="yyyy-MM-dd" value="${content.content_date }"/></td>
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
									<a href="${root }content/write" class="btn btn-primary" style="color:white;">글쓰기</a>
								</div>								
								<a href="${root }content/list" class="btn btn-primary" style="color:white;">제품게시판 전체보기</a>
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