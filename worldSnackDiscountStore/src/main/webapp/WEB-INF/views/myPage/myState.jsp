<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="${pageContext.request.contextPath}/" />

<!-- 날짜 형식 변환 태그 -->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="photoFolio" value="${root}template/photoFolio/" /> 

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>myPage</title>
	
  <!-- Favicons -->
  <link href="${photoFolio}img/favicon.png" rel="icon">
  	
	<!-- Google fonts-->
	<link
		href="https://fonts.googleapis.com/css?family=Saira+Extra+Condensed:500,700"
		rel="stylesheet" type="text/css" />
	<link
		href="https://fonts.googleapis.com/css?family=Muli:400,400i,800,800i"
		rel="stylesheet" type="text/css" />
	<!-- Core theme CSS (includes Bootstrap)-->
	<link href="${root }css/mypage.css" rel="stylesheet" />
	
	<style type="text/css">
	.selectedActive {
    font-weight: bold;
    color: rgb(189 93 56);
	}
	</style>
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

	<!-- 회원 탈퇴 -->
	<section class="resume-section" id="myState">
		<div class="resume-section-content">
			<h3 class="mb-5">나의 활동</h3>
			<div class="main-content">
				<div class="row">
					<!-- 나의 활동 요약 -->
					<div class="col-lg-6 col-md-6 left-box">
						<div class="content-box blue-box card shadow">
							<div class="content-box-title">
								<h4>${loginUserDTO.user_name }</h4>
								<p style="text-align:right; font-size:13px;"><fmt:formatDate pattern="yyyy.MM.dd" value="${loginUserDTO.user_first_join }"/>가입</p>
								<hr style="background-color: #879deb;height: 3px;border: none;">
							</div>
							<div class="content-box-body">
								<div class="content-box-body-left">
									<ul class="navbar-nav">
										<li class="nav-item"><span>최근방문기록</span></li>
										<li class="nav-item"><span>방문</span></li>
										<li class="nav-item"><span>총 게시글</span></li>
										<li class="nav-item"><span>총 댓글</span></li>
										<li class="nav-item"><span>받은 관심</span></li>
										<li class="nav-item"><span>활동 시간</span></li>
									</ul>
								</div>
								<div class="content-box-body-right">
									<ul class="navbar-nav">
										<li class="nav-item" style="font-size:14px;">
											<div style="height:24px;">
												<fmt:formatDate pattern="yyyy.MM.dd.HH:mm:dd" value="${recentVisitTime }"/>
											</div>
										</li>
										<li class="nav-item"><span>${visitCount }회</span></li>
										<li class="nav-item"><a href="?content=content1" onclick="showContent('content1')">${myCommunityContentCount }개</a></li>
										<li class="nav-item"><a href="?content=content2" onclick="showContent('content2')">${myCommunityCommentCount }개</a></li>
										<li class="nav-item"><a href="?content=content3" onclick="showContent('content3')">??개</a></li>
										<li class="nav-item"><span>총 ${activityTime }시간</span></li>
									</ul>
								</div>
							</div>

						</div>
					</div>
					<!-- 게시글 / 댓글 요약 -->
					<div class="col-lg-6 col-md-6 right-box">
						<div class="content-box orange-box card shadow">
							<div class="content-box-title">
									<a class="nav-link js-scroll-trigger content1" href="?content=content1" onclick="showContent('content1')">작성글</a>
									<a class="nav-link js-scroll-trigger content2" href="?content=content2" onclick="showContent('content2')">작성댓글</a>
									<a class="nav-link js-scroll-trigger content3" href="?content=content3" onclick="showContent('content3')">관심 받은 글</a>
									<a class="nav-link js-scroll-trigger content4" href="?content=content4" onclick="showContent('content4')">임시 메뉴</a>
							</div>
							<hr style="background-color: #bd5d38;height: 3px;border: none;margin-top: 16px;margin-bottom: 0px;">
							<div class="card-body">
							
								<!-- ------------ 작성글 양식 ------------ -->
								<c:import url="/WEB-INF/views/include/myState/myContent.jsp"></c:import>
								
								<!-- ------------ 작성댓글 양식 ------------ -->
								<c:import url="/WEB-INF/views/include/myState/myComment.jsp"></c:import>
								
								<!-- ------------ 관심 받은 글 양식 ------------ -->
								<c:import url="/WEB-INF/views/include/myState/myLike.jsp"></c:import>
								
								<!-- ------------ 임시메뉴 양식 ------------ -->
								<c:import url="/WEB-INF/views/include/myState/myTemporary.jsp"></c:import>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>	
	</section>


	
	<hr class="m-0" />
	
	<!-- bottom_menu 삽입 -->
  <c:import url="/WEB-INF/views/include/bottom_menu.jsp" />
	<!-- Bootstrap core JS-->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
	<!-- Core theme JS-->
	
	<script src="${root }js/mypage.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
	
		<script type="text/javascript">
		// 페이지 로드 시 "작성글" 기본값으로 표시
		window.onload = function() {
      // URL에서 쿼리 스트링을 읽어와 기본 콘텐츠 선택
      const params = new URLSearchParams(window.location.search);
      const contentId = params.get('content') || 'content1';  // 기본값 'content1'
      showContent(contentId);
			
			$('.chkboxAll').change(function(){
				if($(this).is(':checked')){
					$('.chkbox').prop("checked", true);
				}
				else{
					$('.chkbox').prop("checked", false);
				}
			});
			
		};
	
		function showContent(contentId) {
			
			// 콘텐츠 숨김
			$('#content1').hide();
			$('#content2').hide();
			$('#content3').hide();
			$('#content4').hide();
			
			// 클릭한 콘텐츠 표시
			$('#' + contentId).show();
			
			// 선택되지 않은 탭 selectedActive 클래스 제거
			$('.content-box-title a').removeClass('selectedActive');
			
			// 선택한 탭의 css스타일 적용
			$('.' + contentId).addClass('selectedActive');
			
			/*
			// 콘텐츠 숨김
			document.getElementById('content1').style.display = 'none';
			document.getElementById('content2').style.display = 'none';
			document.getElementById('content3').style.display = 'none';
			document.getElementById('content4').style.display = 'none';
			
			// 클릭한 콘텐츠 표시
			document.getElementById(contentId).style.display = 'block';
			*/
		};
		
	</script>
</body>
</html>