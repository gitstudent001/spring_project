<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<c:set var="root" value="${pageContext.request.contextPath}/" />
<c:set var="fruitables" value="${root}template/fruitables/" />

<c:forEach var="dataInfo" items="${contentDTO}">
	<div class="col-md-6 col-lg-4 col-xl-3 customContainerSet" style="height:350px; !important">
	    <div class="rounded position-relative fruite-item">
	        <div class="fruite-img customImgSet" style="width:70%; margin:auto;">
	            <img src="${root}upload/${dataInfo.content_file}" 
	            class="img-fluid w-100 rounded-top" alt=""
	            onerror="this.onerror=null; this.src='${fruitables}img/fruite-item-5.jpg';">
	        </div>
	        <div class="p-4 border border-secondary border-top-0 rounded-bottom customBottomSet" style="width:70%; margin:auto;">
		        <form action="${root }content/detail" method="post" id="postForm_${dataInfo.content_idx }">
							<input type="hidden" name="content_idx" value="${dataInfo.content_idx }">
							<input type="hidden" name="category_idx" value="${dataInfo.category_idx }">
							<a href="#" onclick="document.getElementById('postForm_${dataInfo.content_idx}').submit();">
								<h4>
									<c:choose> 
										<c:when test="${dataInfo.content_subject.length() <= 20}">
											${dataInfo.content_subject}			
										</c:when> 
										<c:otherwise>
											${dataInfo.content_subject.substring(0, 20)}...
										</c:otherwise> 
									</c:choose> 
								</h4>
			        </a>
		        </form>
	        </div>
	    </div>
	</div>
</c:forEach>
