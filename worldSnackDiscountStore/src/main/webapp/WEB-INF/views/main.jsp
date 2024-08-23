<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>main</title>
</head>
<body>
	<h1>main.jsp</h1>

	<br>
	<button type="button" onclick="location.href='user/test'">테스트</button>

	<button type="button" onclick="location.href='user/login_join'">로그인/회원가입</button>
	
	<button type="button" onclick="location.href='mypage/main'">마이페이지</button>
	
	<button type="button" onclick="location.href='board/testInsert1?user_idx=${user_idx}'">테스트 아이디로 게시글50개 삽입</button>
	<br>

</body>
</html>
