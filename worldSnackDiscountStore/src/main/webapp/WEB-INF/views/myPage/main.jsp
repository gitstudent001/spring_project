<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="${pageContext.request.contextPath}/" />

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>myPage</title>
<!-- Google fonts-->
<link	href="https://fonts.googleapis.com/css?family=Saira+Extra+Condensed:500,700" rel="stylesheet" type="text/css" />
<link	href="https://fonts.googleapis.com/css?family=Muli:400,400i,800,800i" rel="stylesheet" type="text/css" />
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

	<!-- Page Content-->
	<div class="container-fluid p-0">
		<h4 class="mt-5 text-xxl-center">- 내 정보 수정 -</h4>
		<!-- 정보수정-->
		<section class="resume-section" id="modify">
			<div class="resume-section-content">

				<form:form class="lead mb-5" action="${root }mypage/modify_procedure" modelAttribute="modifyUserDTO">
					<form:hidden path="user_idx"/>
					<form:label path="user_name">이름</form:label>
					<form:input path="user_name" readonly="true" /><br>
					
					<form:label path="user_nickname">닉네임</form:label>
					<form:input path="user_nickname" /> <br>
					<form:errors class="errors" path="user_nickname" /> <br>
					
					<form:label path="user_id">아이디</form:label>
					<form:input path="user_id" readonly="true" /> <br>
					
					<form:label path="user_email">이메일</form:label>
					<form:input type="email" path="user_email" readonly="true" /> <br>
					
					<form:label path="user_pw">비밀번호</form:label> 
					<form:password path="user_pw"	/> <br> 
					<form:errors class="errors" path="user_pw"/> <br>
					
					<form:label path="user_pw2">비밀번호 확인</form:label>
					<form:password path="user_pw2" /><br> 
					<form:errors class="errors" path="user_pw2"/> <br>
					
					<form:button>정보 수정</form:button>
				</form:form>


			</div>
		</section>
    <hr class="m-0" />
	</div>
	
	<!-- bottom_menu 삽입 -->
  <c:import url="/WEB-INF/views/include/bottom_menu.jsp" />
	<!-- Bootstrap core JS-->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" ></script>
	<!-- Core theme JS-->
	<script src="${root }js/mypage.js"></script>
</body>
</html>