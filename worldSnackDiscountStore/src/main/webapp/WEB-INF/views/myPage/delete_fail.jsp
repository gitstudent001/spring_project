<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<c:set var = "root" value = "${pageContext.request.contextPath }/"/>
<c:set var="photoFolio" value="${root}template/photoFolio/" /> 

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>myPage</title>
    
  <!-- Favicons -->
  <link href="${photoFolio}img/favicon.png" rel="icon">
      
</head>
<body>
	<script type="text/javascript">
		alert("회원 탈퇴를 원하실 경우, 체크박스를 체크해 주시길 바랍니다.");
  	location.href="${root }mypage/delete";
	</script>
</body>
</html>    