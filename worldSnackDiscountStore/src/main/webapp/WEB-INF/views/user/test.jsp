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
	<h1>test.jsp</h1>
	
	<h2>이메일 :  ${userDTO.user_email }</h2>
	<h2>닉네임 :  ${userDTO.user_nickname }</h2>
	
</body>
</html>    