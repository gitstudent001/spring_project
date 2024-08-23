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
	<!-- mypage_navigation 삽입 -->
	<c:import url="/WEB-INF/views/include/mypage_nav.jsp"></c:import>
	
	<!-- Page Title -->
	<div id="page-title">
		<h1 class="mb-0 text-center">
			My <span class="text-primary">Page</span>
		</h1>
	</div>
	<!-- 회원 탈퇴 -->
	<section class="resume-section" id="delete">
		<div class="resume-section-content">
			<h2 class="mb-5 redColor">회원 탈퇴</h2>
			<div class="box">
				<h4>회원 탈퇴를 원하십니까?</h4>
				<p>탈퇴를 원하실 경우 아래 체크 박스를 선택 후 회원 탈퇴 버튼을 눌러주세요.</p>
				<form action = "${root }mypage/delete_procedure" method = "post">
					<div class="form-check">
						<input class="form-check-input" type="checkbox" name="confirmDelete" value="checked">
						<label class="form-check-label" for="confirmDelete"> 탈퇴 확인 </label>
					</div>
					<button type="submit" class="btn btn-primary">회원 탈퇴</button>
				</form>
			</div>
		</div>
	</section>
	<hr class="m-0" />

	<!-- Bootstrap core JS-->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
	<!-- Core theme JS-->
	<script src="${root }js/mypage.js"></script>
</body>
</html>