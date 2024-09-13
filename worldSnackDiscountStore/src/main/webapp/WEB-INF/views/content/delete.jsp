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
  <title>제품 상세</title>
    
  <!-- Favicons -->
  <link href="${photoFolio}img/favicon.png" rel="icon">
      
</head>
<body>
	<script>
        alert("게시글이 삭제되었습니다 !!!");
        location.href="${root}content/list";
  </script>
</body>
</html>    