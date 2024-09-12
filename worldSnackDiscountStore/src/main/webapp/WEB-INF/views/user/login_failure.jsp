<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}/" />
<c:set var="photoFolio" value="${root}template/photoFolio/" /> 

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>login_failure</title>
  
  <!-- Favicons -->
  <link href="${photoFolio}img/favicon.png" rel="icon">
    
</head>
<body>
	<script>
	alert("로그인에 실패하셨습니다 !!!");
	location.href="${root}user/login_join?fail=true";
	</script>
</body>
</html>    