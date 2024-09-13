<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="root" value="${pageContext.request.contextPath}/" />
<c:set var="photoFolio" value="${root}template/photoFolio/" /> 

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>게시글 상세보기</title>
  
  <!-- Favicons -->
  <link href="${photoFolio}img/favicon.png" rel="icon">
    
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
  <link rel="stylesheet" href="${root}css/postDetail.css" type="text/css" />
  <c:if test="${post.community_category == 'TEXT'}">
    <link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor-viewer.min.css" />
  </c:if>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.4/moment.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.4/locale/ko.min.js"></script>
  <c:if test="${post.community_category == 'TEXT'}">
    <script src="https://uicdn.toast.com/editor/latest/toastui-editor-viewer.min.js"></script>
  </c:if>
  
  <script>
  		var post = {
  				community_idx: "${post.community_idx}",
 			    user_idx: "${loginUserDTO.user_idx}"
		  };
		  var root = "${root}";
  </script>
  <script src="${root}js/postDetail.js"></script>
</head>
<body>
	<!-- top_menu 삽입 -->
	<c:import url="/WEB-INF/views/include/top_menu.jsp" />
	
  <div class="container mt-4">
    <h1 class="mb-4 d-none">게시글 상세보기</h1>
    <div class="card mb-3"></div>
		  <!-- 제목과 날짜를 카드 헤더의 양쪽 끝에 정렬 -->
		  <div class="card-header d-flex justify-content-between align-items-center">
		    <h3 class="d-inline">${post.community_subject}</h3>
		    <div class="d-inline">
		      <span id="timeAgo"></span>
		      <span id="postDate" data-date="${community_date}" style="display:none;"></span>
		    </div>
		  </div>
		
		  <div class="card-header d-flex justify-content-between align-items-center">
		    <span class="text-muted"><a href="#" style="color: black;">${post.community_nickname}</a></span>
		    <div>
		      <small class="text-muted mr-3">조회 수: ${post.community_view}</small>
		      <small class="text-muted mr-3">추천 수: ${post.community_upvotes - post.community_downvotes}</small>
		      <small class="text-muted">댓글 수: ${post.community_comment}</small>
		    </div>
		  </div>
            
      <div class="card-body">
        <!-- 텍스트 게시글인 경우, Toast UI Viewer를 사용 -->
        <c:if test="${post.community_category == 'TEXT'}">
          <!-- 서버에서 불러온 콘텐츠를 data-content 속성에 저장 -->
          <div id="viewer" data-content="${post.community_text}"></div>
        </c:if>

        <!-- 텍스트 게시글이 아닌 경우, 일반 텍스트로 표시 -->
        <c:if test="${post.community_category != 'TEXT'}">
          <p class="card-text">${post.community_text}</p>
        </c:if>

        <!-- 첨부 파일이 있는 경우 -->
        <c:if test="${not empty post.community_file}">
          <div class="mt-4">
            <c:choose>
						  <%-- 이미지 파일인 경우 --%>
						  <c:when test="${post.community_file.endsWith('.jpg') || post.community_file.endsWith('.jpeg') || post.community_file.endsWith('.png') 
						  						 || post.community_file.endsWith('.gif') || post.community_file.endsWith('.jfif')}">
						    <img src="${root}${post.community_file}" alt="첨부 이미지" class="img-fluid" />
						  </c:when>
						  
						  <%-- 동영상 파일인 경우 --%>
						  <c:when test="${post.community_file.endsWith('.mp4') || post.community_file.endsWith('.webm') || post.community_file.endsWith('.ogg')}">
						    <video controls class="w-100">
						      <source src="${root}${post.community_file}" type="video/${fn:substringAfter(post.community_file, '.')}">
						    </video>
						  </c:when>
						  
						  <%-- 기타 파일인 경우 --%>
						  <c:otherwise>
						    <a href="${root}${post.community_file}" class="btn btn-outline-primary">${post.community_file}</a>
						  </c:otherwise>
						</c:choose>

          </div>
        </c:if>
        
        <!-- 프로모션 게시물인 경우 community_url 링크를 표시 -->
        <c:if test="${not empty post.community_url}">
				  <div class="mt-4 rounded p-2 bg-light border">
				    <strong>링크 : <a href="${post.community_url}" target="_blank">${post.community_url}</a></strong>
				  </div>
				</c:if>
        
      </div>
			
			<div class="card-footer d-flex justify-content-between align-items-center">
				<div class="d-flex align-items-center">
					<!-- 업보트/다운보트 -->
          <div class="btn-fills d-flex justify-content-between align-items-center mr-3">
					  <!-- 업보트 버튼 -->
					  <button type="button" id="up_vote"class="rounded-circle btn-vote up-vote" onclick="vote('upvote', ${post.community_idx})">
					    <i class="fa-regular fa-thumbs-up"></i>
					  </button>
					  <!-- 추천수 -->
						<span id="vote-count-${post.community_idx}" class="mx-2">${post.community_upvotes - post.community_downvotes}</span>
					  <!-- 다운보트 버튼 -->
					  <button type="button" id="down_vote" class="rounded-circle btn-vote down-vote" onclick="vote('downvote', ${post.community_idx})">
					    <i class="fa-regular fa-thumbs-down"></i>
					  </button>
					</div>
          <!-- 조회수 -->
          <span class="mr-3 text-muted">조회수: ${post.community_view}</span>
          <!-- 댓글 수 -->
          <span class="mr-3 text-muted">댓글수: ${post.community_comment}</span>
          <!-- 공유 버튼 -->
          <button type="button" class="btn btn-custom btn-sm mr-2">
            <i class="fas fa-share"></i> 공유
          </button>
          <!-- 저장 버튼 -->
          <button type="button" class="btn btn-custom btn-sm mr-2">
            <i class="fas fa-bookmark"></i> 저장
          </button>
          <!-- 신고 버튼 -->
          <button type="button" class="btn btn-custom btn-sm">
            <i class="fas fa-flag"></i> 신고
          </button>
				</div>
				<div class="d-flex justify-content-end align-items-center">
	        <button type="button" class="btn btn-secondary mr-2" onclick="location.href='${root}board/community'">목록으로</button>
	        <a href="${root}board/editPost/${post.community_idx}" class="btn btn-primary mr-2">수정</a>
					<form action="${root}board/deletePost/${post.community_idx}" method="post" style="display:inline;">
					  <button type="submit" class="btn btn-danger" onclick="return confirm('정말로 삭제하시겠습니까?');">삭제</button>
					</form>
      	</div>
    </div>
    <!-- 댓글 섹션 추가 -->
		<div id="comments-section" class="mt-1">
		  <!-- 로그인 상태에 따라 표시되는 내용 변경 -->
		  <div id="add-comment" class="mt-2">
		    <c:choose>
		      <c:when test="${isUserLoggedIn}">
		        <textarea id="new-comment-text" class="form-control" placeholder="댓글을 입력해주세요."></textarea>
		        <button class="btn btn-primary mt-1 float-right" onclick="addComment()">댓글 달기</button>
		      </c:when>
		      <c:otherwise>
		      	<div id="fake-textarea" class="form-control" onclick="location.href='${root}user/login_join'" 
		      			 style="cursor: pointer; color: #6c757d; background-color: #e9ecef; border: 1px solid #ced4da; 
		      			 				height: 80px; padding: .375rem .75rem; border-radius: .25rem;">
		          댓글을 작성하시려면 로그인해주세요. 로그인하시겠습니까?
		        </div>
		        <button class="btn btn-primary mt-1 float-right" id="login-prompt-button" 
		        				onclick="location.href='${root}user/login_join'">로그인</button>
		      </c:otherwise>
		    </c:choose>
		  </div>
		  
		  <div id="comments-list" class="mt-5">
		    <!-- 댓글 목록이 여기에 추가됩니다 -->
		  </div>
		</div>
  </div>
</body>
</html>
