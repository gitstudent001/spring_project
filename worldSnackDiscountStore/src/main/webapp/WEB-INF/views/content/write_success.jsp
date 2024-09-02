<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<c:set var="root" value="${pageContext.request.contextPath}/" />

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>제품글쓰기</title>

</head>
<body>
	<form action="${root}content/detail" id="detailForm" method="post">
		<input type="hidden" name="content_idx" value="${content_idx}">
		<input type="hidden" name="category_idx" value="${category_idx}">
	</form>
	
	<script>
		alert("게시글이 등록되었습니다");
		document.getElementById('detailForm').submit();
	</script>

</body>
</html>    