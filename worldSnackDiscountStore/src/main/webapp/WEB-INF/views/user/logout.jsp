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
  <title>logout</title>
  
  <!-- Favicons -->
  <link href="${photoFolio}img/favicon.png" rel="icon">
    
</head>
<body>
<script>
	 	alert("로그아웃 하셨습니다 !!!");
		location.href="${root}";
</script>
</body>
</html>    