<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<c:set var = "root" value = "${pageContext.request.contextPath }/"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>myPage</title>
</head>
<body>
	<script type="text/javascript">
		alert("회원 탈퇴를 하셨습니다. 그동안 이용해주셔서 감사합니다.");
  	location.href="${root }main";
	</script>
</body>
</html>    