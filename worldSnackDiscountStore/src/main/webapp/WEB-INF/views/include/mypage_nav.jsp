<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="${pageContext.request.contextPath}/" />
   
<!-- Navigation-->
<nav class="navbar navbar-expand-lg navbar-dark bg-primary fixed-top" id="sideNav">
	<div class="navbar-brand js-scroll-trigger"> 
		<span class="d-block d-lg-none">${loginUserDTO.user_name }님 반갑습니다</span>
		<div class="welcome-box d-none d-lg-block">
			<p> ${loginUserDTO.user_name }님 반갑습니다,<br> 
				당신은 <span class="${loginUserDTO.user_gradeNameAndClass[1] }">${loginUserDTO.user_gradeNameAndClass[0] } </span>등급<br> 입니다~!
			</p>
		</div>
	</div>
	<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarResponsive" aria-controls="navbarResponsive"
		aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>
	<div class="collapse navbar-collapse" id="navbarResponsive">
		<ul class="navbar-nav">
			<li class="nav-item"><a class="nav-link js-scroll-trigger" href="${root }mypage/main">내 정보 수정</a></li>
			<li class="nav-item"><a class="nav-link js-scroll-trigger" href="${root }mypage/myContent">내가 쓴 게시글</a></li>
			<li class="nav-item"><a class="nav-link js-scroll-trigger" href="${root }mypage/myScrap">스크랩 한 게시글</a></li>

			<li class="nav-item"><a class="nav-link js-scroll-trigger" href="${root }mypage/delete">회원탈퇴</a></li>
		</ul>
	</div>
</nav>