<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath }/"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
	<h1>list.jsp</h1>
	<h2>${categoryName }</h2>
	
	<input type="button" onclick="location.href='list?limit=${limit }'" value="전체"/>
	<c:forEach var="ctgInfo" items="${categoryDTO}"> 
		<input type="button" onclick="location.href='list?category_idx=${ctgInfo.category_idx }&limit=${limit }'" value="${ctgInfo.category_name }" />
	</c:forEach> 

	<form method="get" action="${root }content/list" class="contentOption">
		<label for="limit">게시글 수 :</label>
		<input type="hidden" name="category_idx" value="${category_idx }">
		<select class = "limit_select"id="limit" name="limit" onchange="this.form.submit()">
			<option value="10" ${limit == 10 ? 'selected' : '' }>10개</option>
			<option value="20" ${limit == 20 ? 'selected' : '' }>20개</option>
			<option value="30" ${limit == 30 ? 'selected' : '' }>30개</option>
			<option value="50" ${limit == 50 ? 'selected' : '' }>50개</option>
			<option value="100" ${limit == 100 ? 'selected' : '' }>100개</option>
		</select>
	</form>

	<table>
		
		<c:forEach var="dataInfo" items="${contentDTO}">
			<tr>
				<td>${dataInfo.content_idx }</td>		
				<td><a href="${root }content/detail?content_idx=${dataInfo.content_idx }&category_idx=${category_idx}&limit=${limit}&user_idx=${loginUserDTO.user_idx}">${dataInfo.content_subject }</a></td>
				<td>${dataInfo.content_text }</td>
				<td>${dataInfo.content_file }</td>
				<td>${dataInfo.content_writer_idx }</td>
				<td>${dataInfo.content_make }</td>
				<td>${dataInfo.content_country }</td>
				<td>${dataInfo.content_prodno }</td>
				<td>${dataInfo.content_prodprice }</td>
				<td>${dataInfo.content_view }</td>
				<td>${dataInfo.content_date}</td>
			</tr>
		</c:forEach>

	</table>
</body>
</html>    