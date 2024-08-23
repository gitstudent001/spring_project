<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<c:set var="root" value="${pageContext.request.contextPath}/" />
<c:set var="fruitables" value="${root}template/fruitables/" />

<c:forEach var="dataInfo" items="${contentDTO}">
	<div class="col-md-6 col-lg-4 col-xl-3">
	    <div class="rounded position-relative fruite-item">
	        <div class="fruite-img">
	            <img src="${root}upload/${dataInfo.content_file}" 
	            class="img-fluid w-100 rounded-top" alt=""
	            onerror="this.onerror=null; this.src='${fruitables}img/fruite-item-5.jpg';">
	        </div>
	        <div class="p-4 border border-secondary border-top-0 rounded-bottom">
	            <h4>${dataInfo.content_subject}</h4>
	            <p>${dataInfo.content_text }...</p>
	        </div>
	    </div>
	</div>
</c:forEach>
