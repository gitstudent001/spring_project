<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="${pageContext.request.contextPath}/" />
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="photoFolio" value="${root}template/photoFolio/" />
<c:set var="fruitables" value="${root}template/fruitables/" />
<c:set var="bootswatch" value="${root}template/bootswatch/" />

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>커뮤니티</title>
  
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
  
	<!-- Customized Bootstrap, Template, bootswatch -->
	<link href="${fruitables}css/bootstrap.min.css" rel="stylesheet">
	<link href="${fruitables}css/style.css" rel="stylesheet">
	<link href="${bootswatch}css/bootstrap.min.css" rel="stylesheet">
	<link href="${photoFolio}img/favicon.png" rel="icon">
	
	<link rel="stylesheet" href="${root}css/community.css" type="text/css" />

	<!-- JavaScript Libraries -->
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.4/moment.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.4/locale/ko.min.js"></script>
	
  <script src="${fruitables}lib/easing/easing.min.js"></script>
  <script src="${fruitables}lib/waypoints/waypoints.min.js"></script>
  <script src="${fruitables}lib/lightbox/js/lightbox.min.js"></script>
  <script src="${fruitables}lib/owlcarousel/owl.carousel.min.js"></script>
	
	<script>
		let rootPath = "${root}";
    let defaultThumbnailUrl = "${root}images/default-thumbnail.png";
  </script>
  <script src="${root}template/post_templates.js" defer></script>
  <script src="${root}js/community.js" defer></script>
  
</head>
<body>
  <!-- top_menu 삽입 -->
  <c:import url="/WEB-INF/views/include/top_menu.jsp" />

  <div class="container">
    <h1 class="mt-4"><a href="${root}board/community">커뮤니티</a></h1>

    <!-- 카테고리 탭 및 글쓰기 버튼 -->
    <div class="my-4 d-flex justify-content-between align-items-center">
      <!-- 카테고리 탭들 -->
      <ul class="nav nav-tabs">
        <li class="nav-item">
          <a class="nav-link" href="${root}board/community?category=best">베스트</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="${root}board/community?category=free">자유</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="${root}board/community?category=question">질문</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="${root}board/community?category=my_ranking">마이랭킹</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="${root}board/community?category=review">리뷰</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="${root}board/community?category=promotion">가게홍보</a>
        </li>
      </ul>

      <div class="d-flex">
        <!-- 정렬 드롭다운 -->
        <select id="sortOrder" class="form-control mr-2" onchange="updateSortAndView()">
          <option value="latest" ${sortOrder == 'latest' ? 'selected' : ''}>최신순</option>
          <option value="hot" ${sortOrder == 'hot' ? 'selected' : ''}>HOT</option>
        </select>
        
        <!-- 뷰타입 드롭다운 -->
        <select id="viewType" class="form-control mr-2" onchange="updateSortAndView()">
          <option value="text" ${viewType == 'text' ? 'selected' : ''}>텍스트</option>
          <option value="compact" ${viewType == 'compact' ? 'selected' : ''}>컴팩트</option>
          <option value="card" ${viewType == 'card' ? 'selected' : ''}>카드</option>
        </select>
        
        <!-- 글쓰기 버튼 -->
        <button class="btn btn-primary" onclick="location.href='${root}board/newPost'">글쓰기</button>
      </div>
    </div>

    <!-- 게시글 목록 컴팩트 스타일 -->
    <div id="post-container">
      <c:forEach var="post" items="${posts}">
        <div class="compact-post d-flex p-2 border-bottom post-link" 
		     data-post-id="${post.community_idx}" 
		     style="cursor: pointer;">
          <!-- 썸네일 이미지 -->
          <div class="thumbnail mr-3 post-thumbnail" style="width: 100px; height: 100px; overflow: hidden;" 
				     data-thumbnail="${not empty post.community_thumb ? root.concat(post.community_thumb) : ''}">
				     
				     <%-- src="${not empty post.community_thumb ? root.concat(post.community_thumb) : defaultThumbnailUrl}" --%>
				     <%-- onerror="this.onerror=null; this.src='${root}images/default-thumbnail.png';" --%>
				    <img id="imgThumb_${post.community_idx}" class="imgThumb"
				    		 src="" 
						     alt="썸네일" class="img-thumbnail"
						     style="width: 100%; height: 100%; object-fit: cover;">
					</div>

          <!-- 게시글 내용 -->
          <div class="post-content flex-grow-1">
            <!-- 작성자와 작성 시간 -->
            <div class="d-flex justify-content-between align-items-center mb-1">
              <small class="text-muted">${post.community_nickname}</small>
              <!-- 작성 시간에 data-date 속성을 추가 -->
              <small class="postDate text-muted" data-date="${post.community_date}"></small>
            </div>
            
            <!-- 제목 -->
            <h5 class="mb-1"><a href="${root}board/post/${post.community_idx}" class="text-dark">${post.community_subject}</a></h5>
            
            <!-- 하단 기능 버튼들 -->
            <div class="post-actions d-flex align-items-center">
              <!-- 확장 -->
              <button type="button" id="btn-expand" class="btn-fills mr-2">
              	<i class="fa-solid fa-up-right-and-down-left-from-center"></i>
              </button>
              
              <!-- 업보트/다운보트 -->
							<div class="btn-fills d-flex justify-content-between align-items-center mr-3">
							  <button type="button" class="rounded-circle btn-vote up-vote" data-vote-type="upvote" data-post-id="${post.community_idx}">
							    <i class="fa-regular fa-thumbs-up"></i>
							  </button>
							  <span id="vote-count-${post.community_idx}" class="mx-2 text-center">${post.community_upvotes - post.community_downvotes}</span>
							  <button type="button" class="rounded-circle btn-vote down-vote" data-vote-type="downvote" data-post-id="${post.community_idx}">
							    <i class="fa-regular fa-thumbs-down"></i>
							  </button>
							</div>
						              
              <!-- 조회수 -->
              <small class="mr-3 text-muted">조회수: ${post.community_view}</small>

              <!-- 댓글 수 -->
              <a href="${root}board/post/${post.community_idx}#comments-section" class="mr-3 comment-count" style="color: black; font-size:15px; ">댓글 수: ${post.community_comment}</a>
              
              <!-- 공유 버튼 -->
							<button type="button" class="shareBtn btn btn-custom btn-sm mr-2" aria-label="공유" data-post-id="${post.community_idx}">
							  <i class="fas fa-share"></i> 공유
							</button>
							<!-- 공유 모달 -->
							<div id="shareModal_${post.community_idx}" class="modal-share" role="dialog" aria-labelledby="shareModalTitle_${post.community_idx}">
							  <div class="modal-content">
							    <div class="d-flex justify-content-between align-items-center">
							      <h3 id="shareModalTitle_${post.community_idx}">공유하기</h3>
							      <h4 class="close position-absolute top-0 end-0 p-2 mr-1" aria-label="닫기">&times;</h4>
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
		          <button type="button" class="btn btn-custom btn-sm mr-2 btn-scrap" 
					            data-post-id="${post.community_idx}"
					            data-user-idx="${not empty loginUserDTO ? loginUserDTO.user_idx : ''}"
					            data-is-scraped="${post.scraped ? 'true' : 'false'}">
						      <c:choose>
						        <c:when test="${scrapStatus[post.community_idx]}">
						          <i class="fa-solid fa-bookmark"></i> 취소
						        </c:when>
						        <c:otherwise>
						          <i class="fa-regular fa-bookmark"></i> 스크랩
						        </c:otherwise>
						      </c:choose>
				    		</button>
              
              <!-- 숨기기 버튼 -->
              <button type="button" class="btn btn-custom btn-sm mr-2">
                <i class="fas fa-eye-slash"></i> 숨기기
              </button>
              
              <!-- 신고 버튼 -->
              <button type="button" class="btn btn-custom btn-sm">
                <i class="fas fa-flag"></i> 신고
              </button>
            </div>
          </div>
        </div>
      </c:forEach>
    </div>

    <!-- 로딩 스피너 -->
    <div class="loader" id="loader">
      <img src="${root}images/loader.gif" alt="Loading..." />
    </div>
    
  </div>
  
  <c:import url="/WEB-INF/views/include/bottom_menu.jsp" />
  
  <!-- Scroll Top -->
  <a href="#" id="scroll-top" class="scroll-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>

</body>
</html>