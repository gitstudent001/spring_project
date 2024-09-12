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
	<title>not_login</title>
	
  <!-- Favicons -->
  <link href="${photoFolio}img/favicon.png" rel="icon">
  	
</head>
<body>
	<script>
		alert("로그인 후 이용해 주세요");
		location.href="${root}user/login_join";
	</script>
</body>
</html>    