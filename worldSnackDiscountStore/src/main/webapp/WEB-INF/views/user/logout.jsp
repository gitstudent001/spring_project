<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<c:set var="root" value="${pageContext.request.contextPath}/" />
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>logout</title>
</head>
<body>
<script>
	 	alert("로그아웃 하셨습니다 !!!");
		location.href="${root}";
</script>
</body>
</html>    