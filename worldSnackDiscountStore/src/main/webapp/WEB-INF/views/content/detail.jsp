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
<h1>detailProductPage.jsp</h1>

<table>
	
			<tr>
				<td>${detailContentDTO.content_idx }</td>		
				<td>${detailContentDTO.content_subject }</td>
				<td>${detailContentDTO.content_text }</td>
				<td><%-- ${detailContentDTO.content_file } --%></td>
				<td>${detailContentDTO.content_writer_idx }</td>
				<td>${detailContentDTO.content_make }</td>
				<td>${detailContentDTO.content_country }</td>
				<td>${detailContentDTO.content_prodno }</td>
				<td>${detailContentDTO.content_prodprice }</td>
				<td>${detailContentDTO.content_view }</td>
				<td>${detailContentDTO.content_date}</td>
			</tr>
	</table> 
	 
	<c:if test="${detailContentDTO.content_writer_idx == loginUserDTO.getUser_idx() }">
		<a href="${root }content/modify?content_idx=${content_idx}" class="btn btn-info">수정하기</a>
		<a href="${root }content/delete?content_idx=${content_idx}&content_writer_idx=${detailContentDTO.content_writer_idx}" class="btn btn-danger">삭제하기</a>
	</c:if>
	<a href="${root }content/list?category_info_idx=${category_info_idx }&limit=${limit}">목록으로</a>  
</body>
</html>    