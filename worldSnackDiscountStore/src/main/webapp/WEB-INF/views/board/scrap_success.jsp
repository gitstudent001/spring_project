<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<c:set var="root" value="${pageContext.request.contextPath}/" />
<c:set var="photoFolio" value="${root}template/photoFolio/" /> 

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>scrap</title>
  <link href="${photoFolio}img/favicon.png" rel="icon">
</head>
<body>
	<form action="${root}board/post/${community_idx}" id="detailForm" method="get">
		<input type="hidden" name="community_idx" value="${community_idx}">
		<input type="hidden" name="user_idx" value="${user_idx}">
	</form>
	
	<script>
		alert("해당 게시글을 스크랩하였습니다");
		document.getElementById('detailForm').submit();
	</script>
</body>
</html>    