<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="${pageContext.request.contextPath}/" />
   
<!-- Navigation-->
<nav class="navbar navbar-expand-lg navbar-dark bg-primary fixed-top" id="sideNav">
	<div class="navbar-brand js-scroll-trigger"> 
		<!-- span class="d-block d-lg-none">${loginUserDTO.user_name }님 반갑습니다</span-->
		<div class="welcome-box d-none d-lg-block">
			<p> ${loginUserDTO.user_name }님 반갑습니다,<br> 
				당신은 <span class="${loginUserDTO.user_gradeNameAndClass[1] }">${loginUserDTO.user_gradeNameAndClass[0] } </span>등급<br> 입니다~!
			</p>
		</div>
	</div>
	<div class="collapse navbar-collapse" id="navbarResponsive">
		<ul class="navbar-nav">
			<li class="nav-item"><a class="nav-link js-scroll-trigger" href="${root }mypage/main"><i class="fa-solid fa-user-pen"></i>내 정보 수정</a></li>
			<li class="nav-item"><a class="nav-link js-scroll-trigger" href="${root }mypage/myContent"><i class="fa-solid fa-feather-pointed"></i>내가 쓴 게시글</a></li>
			<li class="nav-item"><a class="nav-link js-scroll-trigger" href="${root }mypage/myScrap"><i class="fa-regular fa-star"></i>스크랩 한 게시글</a></li>
			<li class="nav-item"><a class="nav-link js-scroll-trigger" href="${root }mypage/myState"><i class="fa-solid fa-chart-pie"></i>나의 활동</a></li>
			
			<li class="nav-item"><a class="nav-link js-scroll-trigger" href="${root }mypage/delete"><i class="fa-solid fa-user-slash"></i>회원탈퇴</a></li>
		</ul>
	</div>
</nav>