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
                    <a class="nav-link" style="color:black;" href="${root}user/test">테스트</a>
                  </li>
                  
                  <li class="nav-item">
                    <a class="nav-link" style="color:black;" href="${root}content/write">제품글쓰기</a>
                  </li>
                  <li class="nav-item">
                    <a class="nav-link" style="color:black;" href="${root}content/modify?content_idx=42">제품글수정</a>
                  </li>
                </ul>
                <ul class="navbar-nav me-auto" style="float:right;">
				          <li class="nav-item">
				          	<a class="nav-link" style="color:black;" href="${root}user/login_join" class="active">로그인 / 회원가입</a>
				          </li>
				          <li class="nav-item">
				          	<a class="nav-link" style="color:black;" href="${root}mypage/main" class="active">마이페이지</a>
				          </li>
				        </ul>
				        <i class="mobile-nav-toggle d-xl-none bi bi-list"></i>
              </div>
            </div>
          </nav>
               
        <!-- <button class="source-button btn btn-primary btn-xs" type="button" tabindex="0"><i class="bi bi-code"></i></button></div> -->

      </div>
    </div>
  </header>