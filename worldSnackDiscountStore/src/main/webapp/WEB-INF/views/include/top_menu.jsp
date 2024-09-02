<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<c:set var="root" value="${pageContext.request.contextPath}/" /> 
    
	<header id="header" class="bs-docs-section clearfix">
    <div class="row">
        <div class="bs-component">
          <nav class="navbar navbar-expand-lg bg-body-tertiary">
            <div class="container-fluid">
              <a class="navbar-brand" href="#">WSDS</a>
              <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarColor04" aria-controls="navbarColor04" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
              </button>

              <div class="collapse navbar-collapse" id="navbarColor04">
                <ul class="navbar-nav me-auto">
                  <li class="nav-item">
                    <a class="nav-link active"  style="color:black;" href="${root}">Home
                      <span class="visually-hidden">(current)</span>
                    </a>
                  </li>
                  <li class="nav-item">
                    <a class="nav-link" style="color:black;" href="${root}content/list">제품</a>
                  </li>
                  <li class="nav-item">
                    <a class="nav-link" style="color:black;" href="${root}test">테스트</a>
                  </li>
                  
                  <li class="nav-item">
                    <a class="nav-link" style="color:black;" href="${root}content/write">제품글쓰기</a>
                  </li>
                  <li class="nav-item">
                    <a class="nav-link" style="color:black;" href="${root}content/modify?content_idx=42">제품글수정</a>
                  </li>
                </ul>
                <c:choose>
                	<c:when test= "${loginUserDTO.userIsLogin == true }" >
                		<ul class="navbar-nav me-auto" >
	                		<li class="nav-item">
						          	<a class="nav-link" style="color:black;" href="${root}user/logout" class="active" >로그아웃</a>
						          </li>
						          
						          <li class="nav-item">
						          	<!-- 버튼 수평 정렬을 위해 div 추가 -->
						          	<div class="d-flex justify-content-end align-items-center">
							          	<a class="nav-link" style="color:black;" href="${root}/mypage/main" class="active">마이페이지</a>
							          	<c:if test="${mypageNav == true }">
							          		<button class="navbar-toggler custom-navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarResponsive" aria-controls="navbarResponsive"
															aria-expanded="false" aria-label="Toggle navigation">
															<span class="navbar-toggler-icon"></span>
														</button>							          	
							          	</c:if>
						          	</div>
					          		<!-- 마이페이지 용 네비게이션 추가 -->
					          		<c:if test="${mypageNav == true }">
						          		<div class="d-lg-none">
														<div class="collapse navbar-collapse" id="navbarResponsive">
															<ul class="navbar-nav">
																<li class="nav-item"><a class="nav-link js-scroll-trigger" href="${root }mypage/main">내 정보 수정</a></li>
																<li class="nav-item"><a class="nav-link js-scroll-trigger" href="${root }mypage/myContent">내가 쓴 게시글</a></li>
																<li class="nav-item"><a class="nav-link js-scroll-trigger" href="${root }mypage/myScrap">스크랩 한 게시글</a></li>
													
																<li class="nav-item"><a class="nav-link js-scroll-trigger" href="${root }mypage/delete">회원탈퇴</a></li>
															</ul>
														</div>							          		
						          		</div>
					          		</c:if>
						          </li>
						          
						          <li class="nav-item">
						          	<div class="nav-link" > 
						          		${loginUserDTO.user_name} <span style="color:#000;">님 환영합니다</span>
						          		<c:if test="${mypageNav == true }">
							          		<p>
							          			당신은 <span class="${loginUserDTO.user_gradeNameAndClass[1] }">${loginUserDTO.user_gradeNameAndClass[0] } </span>등급 입니다~!
														</p>
						          		</c:if>
						          	</div>  
						          </li>
                		</ul>
                	</c:when>
                	<c:otherwise>
                		<ul class="navbar-nav me-auto">
                			<li class="nav-item">
						          	<a class="nav-link" style="color:black;" href="${root}user/login_join" class="active">로그인 / 회원가입</a>
						          </li>
                		</ul>
                	</c:otherwise>
                </c:choose>
		
				        <i class="mobile-nav-toggle d-xl-none bi bi-list"></i>
              </div>
            </div>
          </nav>
               
        <!-- <button class="source-button btn btn-primary btn-xs" type="button" tabindex="0"><i class="bi bi-code"></i></button></div> -->

      </div>
    </div>
  </header>