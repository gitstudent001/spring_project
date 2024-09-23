<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="root" value="${pageContext.request.contextPath}/" />
<c:set var="photoFolio" value="${root}template/photoFolio/" />     
<c:set var="fruitables" value="${root}template/fruitables/" />
<c:set var="bootswatch" value="${root}template/bootswatch/" />

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>게시글 상세보기</title>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
  <c:if test="${post.community_category == 'TEXT'}">
    <link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor-viewer.min.css" />
  </c:if>
  
  <!-- Customized Bootstrap, Template, bootswatch -->
<%-- 	<link href="${fruitables}css/bootstrap.min.css" rel="stylesheet"> --%>
<%-- 	<link href="${fruitables}css/style.css" rel="stylesheet"> --%>
<%-- 	<link href="${bootswatch}css/bootstrap.min.css" rel="stylesheet"> --%>
	<link href="${photoFolio}img/favicon.png" rel="icon">
  
  <link rel="stylesheet" href="${root}css/postDetail.css" type="text/css" />
  
 	<!-- JavaScript Libraries -->
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.4/moment.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.4/locale/ko.min.js"></script>
	<script src="https://cdn.tiny.cloud/1/lj6fsht0vl3f2csgel7g6ejztfio3wa02ytlfro86i33mec1/tinymce/7/tinymce.min.js" referrerpolicy="origin"></script>
	<script src="https://cdn.jsdelivr.net/npm/dompurify@3.1.6/dist/purify.min.js"></script>
	<c:if test="${post.community_category == 'TEXT'}">
    <script src="https://uicdn.toast.com/editor/latest/toastui-editor-viewer.min.js"></script>
  </c:if>
  
  <script src="${fruitables}lib/easing/easing.min.js"></script>
  <script src="${fruitables}lib/waypoints/waypoints.min.js"></script>
  <script src="${fruitables}lib/lightbox/js/lightbox.min.js"></script>
  <script src="${fruitables}lib/owlcarousel/owl.carousel.min.js"></script>
  
  <script>
  		let post = {
  				community_idx: "${post.community_idx}",
  				community_nickname: "${post.community_nickname}",
 			    user_idx: "${loginUserDTO.user_idx}"
		  };
		  let rootPath = "${root}";
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
		      <span id="postDate" data-date="${post.community_date}" style="display:none;"></span>
		    </div>
		  </div>
		
		  <div class="card-header d-flex justify-content-between align-items-center">
		    <span class="text-muted"><a href="#" style="color: black;">${post.community_nickname}</a></span>
		    <div>
		      <small class="text-muted mr-3 ">조회 수: ${post.community_view}</small>
		      <small id="top-vote-count" class="text-muted mr-3">추천 수: ${post.community_upvotes - post.community_downvotes}</small>
	      	<a href="#comments-section" class="comment-count" style="color: black; font-size:14px;">댓글 수: ${post.community_comment}</a>
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
          <span class="mr-3 text-muted comment-count">댓글수: ${post.community_comment}</span>
         <!-- 공유 버튼 -->
							<button type="button" class="shareBtn btn btn-custom btn-sm mr-2" aria-label="공유" data-post-id="${post.community_idx}">
							  <i class="fas fa-share"></i> 공유
							</button>
							<!-- 공유 모달 -->
							<div id="shareModal_${post.community_idx}" class="modal-share" role="dialog" aria-labelledby="shareModalTitle_${post.community_idx}">
							  <div class="modal-content">
							    <div class="d-flex justify-content-between align-items-center">
							      <h3 id="shareModalTitle_${post.community_idx}">공유하기</h3>
							      <h4 class="close" aria-label="닫기">&times;</h4>
							    </div>
							    <div class="mt-3">
							      <button type="button">페이스북</button>
							      <button type="button">트위터</button>
							      <button type="button">이메일</button>
							      <button type="button" id="copyLinkBtn_${post.community_idx}">링크 복사</button>
							    </div>
							  </div>
							</div>
          <!-- 스크랩 버튼 -->
          <form id="scrapForm" action="${root}board/scrap" method="post">
					  <input type="hidden" name="community_idx" value="${post.community_idx}">
					  <input type="hidden" name="user_idx" value="${loginUserDTO.user_idx}">
					  <input type="hidden" name="scrapCheck" value="${alreadyScrap ? 'true' : 'false'}">
					  <button type="submit" class="btn btn-custom btn-sm mr-2">
					    <c:choose>
					      <c:when test="${alreadyScrap}">
					        <i class="fa-solid fa-bookmark"></i> 취소
					      </c:when>
					      <c:otherwise>
					        <i class="fa-regular fa-bookmark"></i> 스크랩
					      </c:otherwise>
					    </c:choose>
					  </button>
					</form>
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
			 <!-- 에러 메시지를 표시할 영역 -->
  		<div id="error-message" class="d-none alert"></div>
			
		  <!-- 로그인 상태에 따라 표시되는 내용 변경 -->
		  <div id="add-comment" class="mt-2">
		    <c:choose>
		      <c:when test="${isUserLoggedIn}">
		        <textarea id="new-comment-text" class="form-control"></textarea>
			      <textarea id="comment-editor" style="display:none;"></textarea>
			      <div id="comment-toolbar">
					    <button id="text-editor-btn" type="button">T</button>
					    <button id="image-editor-btn" type="button">이미지</button>
			        <button class="btn btn-primary mt-1 float-right" onclick="addComment()">댓글 달기</button>
					  </div>
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
 	<!-- 로딩 스피너 -->
   <div class="loader" id="loader">
     <img src="${root}images/loader.gif" alt="Loading..." />
   </div>
   <!-- Scroll Top -->
 	<a href="#" id="scroll-top" class="scroll-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>
  <c:import url="/WEB-INF/views/include/bottom_menu.jsp" />
</body>
</html>
