<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<c:set var="root" value="${pageContext.request.contextPath}/" />

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
	<script>
		alert("해당 게시글을 스크랩하였습니다");
		location.href="${root}content/detail?content_idx=${content_idx}&category_idx=${category_idx}&user_idx=${user_idx}&limit=${limit}";
	</script>
</body>
</html>    