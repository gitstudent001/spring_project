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
	<form action="${root}content/detail" id="scrapForm" method="post">
		<input type="hidden" name="content_idx" value="${content_idx}">
		<input type="hidden" name="category_idx" value="${category_idx}">
		<input type="hidden" name="limit" value="${limit}">
	</form>
		
	<script>
		alert("스크랩을 취소했습니다");
		document.getElementById('scrapForm').submit();
	</script>
</body>
</html>    