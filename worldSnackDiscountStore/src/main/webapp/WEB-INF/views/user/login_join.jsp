<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="${pageContext.request.contextPath}/" />

<c:set var="photoFolio" value="${root}template/photoFolio/" />     
<%-- <c:set var="fruitables" value="${root}template/fruitables/" /> --%>
<c:set var="bootswatch" value="${root}template/bootswatch/" />

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>로그인/회원가입</title>

  <!-- Favicons -->
  <link href="${photoFolio}img/favicon.png" rel="icon">

	<!-- Google Web Fonts -->
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Raleway:wght@600;800&display=swap" rel="stylesheet"> 
	
	
	<!-- bootswatch Stylesheet -->
	<link href="${bootswatch}css/bootstrap.min.css" rel="stylesheet">	
	
	<!-- jQuery 라이브러리 추가 (CDN 사용) -->
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<link rel="stylesheet" href="${root }css/login_join.css" type="text/css" />
</head>
<body>
	<!-- top_menu 삽입 -->
	<c:import url="/WEB-INF/views/include/top_menu.jsp" />

	<div class="wrapper">
		<div class="container-login">
			<div class="sign-up-container">
				<form:form action="${root }user/join_procedure" modelAttribute="joinUserDTO">
					<div class="sign-up-header">
						<h1>회원가입</h1>
						<div class="social-links">
							<div>
								<a href="https://accounts.google.com/v3/signin/identifier?continue=https%3A%2F%2Fwww.google.com%2Fwebhp%3Fhl%3Dko%26ictx%3D2%26sa%3DX%26ved%3D0ahUKEwjl1Nqg06OIAxXodfUHHS8aHIAQPQgJ&ec=GAZAmgQ&hl=ko&ifkv=Ab5oB3pbIO112pvqEE01vqulSnZqhCWj-AIFqvsmoCbbpeIHznsmETbp5QPRrjyPOa_h0CAGjAPA&passive=true&flowName=GlifWebSignIn&flowEntry=ServiceLogin&dsh=S-2093252842%3A1725259182100357&ddm=1"><i class="fa-brands fa-google fa-xl"></i></a>
							</div>
							<div>
								<a href="https://www.facebook.com/login.php?skip_api_login=1&api_key=195707104118397&kid_directed_site=0&app_id=195707104118397&signed_next=1&next=https%3A%2F%2Fwww.facebook.com%2Fv2.5%2Fdialog%2Foauth%3Fclient_id%3D195707104118397%26response_type%3Dcode%26scope%3Dpublic_profile%252Cuser_friends%252Cemail%26redirect_uri%3Dhttps%253A%252F%252Fauth.danawa.com%252Fsns%252Fcallback%253Ftype%253DFACEBOOK%26ret%3Dlogin%26fbapp_pres%3D0%26logger_id%3D8b87546d-d798-4d97-b45b-e532506bb9a1%26tp%3Dunspecified&cancel_url=https%3A%2F%2Fauth.danawa.com%2Fsns%2Fcallback%3Ftype%3DFACEBOOK%26error%3Daccess_denied%26error_code%3D200%26error_description%3DPermissions%2Berror%26error_reason%3Duser_denied%23_%3D_&display=page&locale=ko_KR&pl_dbl=0"><i class="fa-brands fa-facebook fa-xl"></i></a> 
							</div>
							<div>
								<a href="https://www.instagram.com/accounts/login/"><i class="fa-brands fa-instagram fa-xl"></i></a>
							</div>
						</div>
						<span>또는 새로운 계정으로 회원가입</span> 
						<% 
						  String errorMessage = (String) request.getAttribute("errorMessage");
						  if (errorMessage != null) {
						%>
						  <div id="errorMessage" class = "errorCode"><%= errorMessage %></div>
						<% 
						  } 
						%>
					</div>
					<div class="sign-up-content">
						<div class="sign-up-left">
							<form:hidden path="userIdExist"/>
							<form:label path="user_name">이름</form:label>
							<form:input path="user_name" class="form_input" placeholder="Name"/>
		          <form:errors path="user_name" class="errors" />
		          
		          <form:label path="user_nickname">닉네임</form:label>
							<form:input path="user_nickname" class="form_input" placeholder="NickName"/>
		          <form:errors path="user_nickname" class="errors" />
		          
		          <form:label for= "join_user_id" path="user_id">아이디</form:label>
		          <form:input id= "join_user_id" path="user_id" class="form_input" placeholder="ID" onkeypress="resetUserIdExist()" />
		          <button type = "button" class="form_btn btn_check" onclick="checkUserIdExist()">중복확인</button>
		          <form:errors path="user_id" class="errors" />
	          </div>
	          <div class="sign-up-right">
		          <form:label path="user_email">이메일</form:label>
							<form:input type="email" path="user_email" class="form_input" placeholder="Email"/>
		          <form:errors path="user_email" class="errors" />
		          
		          <form:label for="join_user_pw" path="user_pw">비밀번호</form:label>
							<form:password id="join_user_pw" path="user_pw" class="form_input" placeholder="Password"/>
		          <form:errors path="user_pw" class="errors" />
		          
		          <form:label path="user_pw2">비밀번호 확인</form:label>
							<form:password path="user_pw2" class="form_input" placeholder="Password2"/>
		          <form:errors path="user_pw2" class="errors" />
	          </div>
          </div>
          <div class= "sign-up-footer">
						<form:button class="form_btn">회원가입</form:button>
					</div>
				</form:form>
			</div>
			<div class="sign-in-container">
				<form:form action="${root}user/login_procedure" modelAttribute="loginUserDTO" >
					<h1>로그인</h1>
					<c:if test="${fail == true }">
						<div class="alert alert-danger">
							<h3> 로그인 실패</h3>
							<p>아이디 비밀번호를 확인해주세요</p>
						</div>
					</c:if>
					<div class="social-links">
						<div>
							<a href="https://accounts.google.com/v3/signin/identifier?continue=https%3A%2F%2Fwww.google.com%2Fwebhp%3Fhl%3Dko%26ictx%3D2%26sa%3DX%26ved%3D0ahUKEwjl1Nqg06OIAxXodfUHHS8aHIAQPQgJ&ec=GAZAmgQ&hl=ko&ifkv=Ab5oB3pbIO112pvqEE01vqulSnZqhCWj-AIFqvsmoCbbpeIHznsmETbp5QPRrjyPOa_h0CAGjAPA&passive=true&flowName=GlifWebSignIn&flowEntry=ServiceLogin&dsh=S-2093252842%3A1725259182100357&ddm=1"><i class="fa-brands fa-google fa-xl"></i></a>
						</div>
						<div>
							<a href="https://www.facebook.com/login.php?skip_api_login=1&api_key=195707104118397&kid_directed_site=0&app_id=195707104118397&signed_next=1&next=https%3A%2F%2Fwww.facebook.com%2Fv2.5%2Fdialog%2Foauth%3Fclient_id%3D195707104118397%26response_type%3Dcode%26scope%3Dpublic_profile%252Cuser_friends%252Cemail%26redirect_uri%3Dhttps%253A%252F%252Fauth.danawa.com%252Fsns%252Fcallback%253Ftype%253DFACEBOOK%26ret%3Dlogin%26fbapp_pres%3D0%26logger_id%3D8b87546d-d798-4d97-b45b-e532506bb9a1%26tp%3Dunspecified&cancel_url=https%3A%2F%2Fauth.danawa.com%2Fsns%2Fcallback%3Ftype%3DFACEBOOK%26error%3Daccess_denied%26error_code%3D200%26error_description%3DPermissions%2Berror%26error_reason%3Duser_denied%23_%3D_&display=page&locale=ko_KR&pl_dbl=0"><i class="fa-brands fa-facebook fa-xl"></i></a>
						</div>
						<div>
							<a href="https://www.instagram.com/accounts/login/"><i class="fa-brands fa-instagram fa-xl"></i></a>
						</div>
					</div>
					<span>또는 계정으로 로그인</span> 
					<form:input id= "login_user_id" path="user_id" placeholder="ID" /> 
					<form:errors path="user_id" class="errors" />
					
					<form:password id="login_user_pw" path="user_pw" placeholder="Password" />
					<form:errors path="user_pw" class="errors" />
					
					<form:button class="form_btn">로그인</form:button>
				</form:form>
			</div>
			<div class="overlay-container">
				<div class="overlay-left">
					<h1>방문을 환영합니다</h1>
					<p>이미 계정이 있으시다면 아래 버튼을 눌러 맛있는 간식을 구경하세요!</p>
					<button id="signIn" class="overlay_btn">로그인</button>
				</div>
				<div class="overlay-right">
					<h1>처음 방문하시나요?</h1>
					<p>회원가입을 원하시면 아래에 버튼을 클릭해주세요.</p>
					<button id="signUp" class="overlay_btn">회원가입</button>
				</div>
			</div>
		</div>
	</div>

	<!-- bottom_menu 삽입 -->
  <c:import url="/WEB-INF/views/include/bottom_menu.jsp" />
  
	<!-- Scroll Top -->
  <a href="#" id="scroll-top" class="scroll-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>


  <!-- Vendor JS Files -->
  <script src="${photoFolio}vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
  <script src="${photoFolio}vendor/php-email-form/validate.js"></script>
  <script src="${photoFolio}vendor/aos/aos.js"></script>
  <script src="${photoFolio}vendor/glightbox/js/glightbox.min.js"></script>
  <script src="${photoFolio}vendor/swiper/swiper-bundle.min.js"></script>	  
  
  
	<script type="text/javascript">
		function checkUserIdExist() {
			let user_id = $("#join_user_id").val();
			
			if(user_id.length == 0) {
				alert("아이디를 입력해 주세요");
				return;
			}
			
			$.ajax({
				url: "${root}user/checkUserIdExist/" + user_id,
				type : "get",
				datatype : "text",
				success : function(result) {
					if(result.trim() == "true") {
						alert("사용할 수 있는 아이디 입니다.");
						$("#userIdExist").val("true");
					}
					else {
						alert("이미 존재하는 아이디 입니다.");
						$("#userIdExist").val("false");
					}
				}
			});
			
			
		}
		function resetUserIdExist() {
			$("#userIdExist").val("false");
		}
	</script>
	
	<script src="${root }js/login_join.js" type="text/javascript"></script>
</body>
</html>
