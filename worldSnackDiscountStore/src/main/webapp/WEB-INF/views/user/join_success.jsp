<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<c:set var="root" value="${pageContext.request.contextPath}/" /> 
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="UTF-8">
<title>join_success.jsp</title>
</head>
<body>
  <h1>join_success.jsp</h1>
  <script>
    alert("어서 오세요. 회원가입이 완료되었습니다 ~~~");
    location.href="${root}user/login_join";
  </script>
</body>
</html>