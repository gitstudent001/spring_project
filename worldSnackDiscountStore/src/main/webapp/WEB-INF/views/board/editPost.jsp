<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}/" />
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>게시글 수정하기</title>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
  <!-- Toast UI Editor 관련 스타일 추가 -->
  <link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css" />
  <!-- jQuery 및 jQuery UI 라이브러리 추가 -->
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.css">
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
  <!-- Toast UI Editor 관련 JS 추가 -->
  <script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>
  
  <link rel="stylesheet" href="${root}css/newPost.css" type="text/css" />
  <script src="${root}js/newPost.js" type="text/javascript"></script>
  <script src="${root}js/editPost.js" type="text/javascript"></script>

</head>
<body>
  <!-- top_menu 삽입 -->
  <c:import url="/WEB-INF/views/include/top_menu.jsp" />

  <div class="container mt-4">
    <h1 class="mb-4">게시글 수정하기</h1>
    
    <!-- 탭 내비게이션 -->
		<ul class="nav nav-tabs" id="postTypeTabs" role="tablist">
		  <li class="nav-item">
		    <a class="nav-link ${post.community_category == 'TEXT' ? 'active' : ''}" id="text-tab" data-toggle="tab" href="#text" role="tab" aria-controls="text" aria-selected="${post.community_category == 'TEXT'}">텍스트</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link ${post.community_category == 'IMAGE' ? 'active' : ''}" id="image-tab" data-toggle="tab" href="#image" role="tab" aria-controls="image" aria-selected="${post.community_category == 'IMAGE'}">이미지 및 비디오</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link ${post.community_category == 'RANKING' ? 'active' : ''}" id="ranking-tab" data-toggle="tab" href="#ranking" role="tab" aria-controls="ranking" aria-selected="${post.community_category == 'RANKING'}">랭킹</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link ${post.community_category == 'PROMOTION' ? 'active' : ''}" id="promotion-tab" data-toggle="tab" href="#promotion" role="tab" aria-controls="promotion" aria-selected="${post.community_category == 'PROMOTION'}">프로모션</a>
		  </li>
		</ul>

    <!-- 탭 콘텐츠 -->
    <div class="tab-content" id="postTypeContent">
      <!-- 텍스트 게시물 폼 -->
      <div class="tab-pane fade <c:if test="${post.community_category == 'TEXT'}">show active</c:if>" id="text" role="tabpanel" aria-labelledby="text-tab">
        <form:form action="${root}board/editPost/${post.community_idx}" modelAttribute="post" method="post" onsubmit="return handleFormSubmit();">
          <form:hidden path="community_idx" />
          <form:hidden path="community_text" id="hidden_text" />
          <div class="form-group mt-3 d-flex align-items-center">
            <!-- 카테고리 드롭다운 -->
            <div class="category-dropdown mr-3">
              <span class="selectedCategory">${post.community_category == 'free' ? '자유' : '질문'}</span>
              <i class="fas fa-chevron-down"></i>
              <!-- 드롭다운 리스트 -->
              <ul class="dropdown-menu">
                <li data-value="free">자유</li>
                <li data-value="question">질문</li>
              </ul>
            </div>
            <!-- 텍스트 입력 필드 -->
            <form:hidden path="community_category" value="${post.community_category}" />
            <form:input path="community_subject" id="community_subject" class="form-control" placeholder="제목" value="${post.community_subject}" required="true" />
          </div>
          <div class="form-group">
            <!-- 토스트 에디터를 위한 Div 추가 -->
            <div id="editor" class="form-control" style="height: 400px;"></div>
          </div>
          <div class="d-flex justify-content-end mt-3">
            <button type="button" class="btn btn-secondary mr-2" onclick="window.history.back();">취소</button>
            <button type="submit" class="btn btn-primary">수정 완료</button>
          </div>
        </form:form>
      </div>
      
      <!-- 이미지 게시물 폼 -->
      <div class="tab-pane fade <c:if test="${post.community_category == 'IMAGE'}">show active</c:if>" id="image" role="tabpanel" aria-labelledby="image-tab">
        <form:form action="${root}board/editPost/${post.community_idx}" modelAttribute="post" method="post" enctype="multipart/form-data">
          <form:hidden path="community_idx" />
          <div class="form-group mt-3 d-flex align-items-center">
            <!-- 카테고리 드롭다운 -->
            <div class="category-dropdown mr-3">
              <span class="selectedCategory">리뷰</span>
              <i class="fas fa-chevron-down"></i>
              <!-- 드롭다운 리스트 -->
              <ul class="dropdown-menu">
                <li data-value="free">자유</li>
                <li data-value="question">질문</li>
                <li data-value="review">리뷰</li>
              </ul>
            </div>
            <form:hidden path="community_category" value="${post.community_category}" />
            <form:input path="community_subject" id="community_subject" class="form-control" placeholder="제목" value="${post.community_subject}" required="true" />
          </div>
          <div class="form-group centered-content">
            <!-- Drag and Drop Zone -->
            <div class="drop-zone" id="drop-zone">
              <span>Drag and Drop or upload media</span>
              <form:input type="file" path="file_upload" id="community_file" class="drop-zone__input" style="display: none;" />
              <label for="community_file" class="drop-zone__button"><i class="fas fa-upload"></i></label>
            </div>
          </div>
          <div class="d-flex justify-content-end mt-3">
            <button type="button" class="btn btn-secondary mr-2" onclick="window.history.back();">취소</button>
            <button type="submit" class="btn btn-primary">수정 완료</button>
          </div>
        </form:form>
      </div>
      
      <!-- 랭킹 게시물 폼 -->
      <div class="tab-pane fade <c:if test="${post.community_category == 'RANKING'}">show active</c:if>" id="ranking" role="tabpanel" aria-labelledby="ranking-tab">
        <form:form action="${root}board/editPost/${post.community_idx}" modelAttribute="post" method="post">
          <form:hidden path="community_idx" />
          <div class="form-group mt-3 d-flex align-items-center">
            <!-- 카테고리 드롭다운 -->
            <div class="category-dropdown mr-3">
              <span class="selectedCategory">마이랭킹</span>
              <i class="fas fa-chevron-down"></i>
              <!-- 드롭다운 리스트 -->
              <ul class="dropdown-menu">
                <li data-value="my_ranking">마이랭킹</li>
              </ul>
            </div>
            <form:hidden path="community_category" value="${post.community_category}" />
            <form:input path="community_subject" id="community_subject" class="form-control" placeholder="제목" value="${post.community_subject}" required="true" />
          </div>
          <ul class="ranking-options" id="ranking-options">
            <c:forEach var="i" begin="1" end="${fn:length(fn:split(post.community_text, '<p>'))}">
              <li>
                <div class="form-group centered-content">
                  <i class="fas fa-bars sortable-handle"></i>
                  <label class="option-label">${i}.</label> 
                  <input type="text" class="form-control" name="options" placeholder="Option ${i}" value="${fn:split(post.community_text, '<p>')[i - 1]}" required />
                  <button type="button" class="btn btn-danger btn-sm remove-option"><i class="fas fa-trash"></i></button>
                </div>
              </li>
            </c:forEach>
          </ul>
          <button type="button" class="btn btn-link" id="add-option">Add Option</button>
          <div class="d-flex justify-content-end mt-3">
            <button type="button" class="btn btn-secondary mr-2" onclick="window.history.back();">취소</button>
            <button type="submit" class="btn btn-primary">수정 완료</button>
          </div>
        </form:form>
      </div>
      
      <!-- 프로모션 게시물 폼 -->
      <div class="tab-pane fade <c:if test="${post.community_category == 'PROMOTION'}">show active</c:if>" id="promotion" role="tabpanel" aria-labelledby="promotion-tab">
        <form:form action="${root}board/editPost/${post.community_idx}" modelAttribute="post" method="post" enctype="multipart/form-data">
          <form:hidden path="community_idx" />
          <div class="form-group mt-3 d-flex align-items-center">
            <!-- 카테고리 드롭다운 -->
            <div class="category-dropdown mr-3">
              <span class="selectedCategory">가게홍보</span>
              <i class="fas fa-chevron-down"></i>
              <!-- 드롭다운 리스트 -->
              <ul class="dropdown-menu">
                <li data-value="promotion">가게홍보</li>
              </ul>
            </div>
            <form:hidden path="community_category" value="${post.community_category}" />
            <form:input path="community_subject" id="community_subject" class="form-control" placeholder="제목" value="${post.community_subject}" required="true" />
          </div>
          <div class="form-group">
            <form:input path="community_url" id="community_url" class="form-control" placeholder="URL 링크" value="${post.community_url}" required="true" />
          </div>
          <div class="form-group centered-content">
            <!-- Drag and Drop Zone -->
            <div class="drop-zone" id="drop-zone-promotion">
              <span>Drag and Drop or upload media</span>
              <input type="file" name="promotion_file" id="promotion_file" class="drop-zone__input" />
              <label for="promotion_file" class="drop-zone__button"><i class="fas fa-upload"></i></label>
            </div>
          </div>
          <div class="d-flex justify-content-end mt-3">
            <button type="button" class="btn btn-secondary mr-2" onclick="window.history.back();">취소</button>
            <button type="submit" class="btn btn-primary">수정 완료</button>
          </div>
        </form:form>
      </div>
    </div>
  </div>

  
  
</body>
</html>
            

