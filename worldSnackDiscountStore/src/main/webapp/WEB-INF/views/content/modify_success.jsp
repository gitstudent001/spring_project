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
  <title>제품글쓰기</title>

  <!-- Favicons -->
  <link href="${photoFolio}img/favicon.png" rel="icon">

</head>
<body>
	<form action="${root}content/detail" id="detailForm" method="post">
		<input type="hidden" name="content_idx" value="${content_idx}">
		<input type="hidden" name="category_idx" value="${category_idx}">
		<input type="hidden" name="limit" value="${limit}">
		<input type="hidden" name="page" value="${page}">		
	</form>
	
	<script>
		alert("게시글이 수정되었습니다");
		document.getElementById('detailForm').submit();
	</script>


</body>
</html>    