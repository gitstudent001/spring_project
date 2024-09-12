<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<c:set var="root" value="${pageContext.request.contextPath}/" />
<c:set var="fruitables" value="${root}template/fruitables/" />

<c:forEach var="dataInfo" items="${contentDTO}">
	<div class="col-md-6 col-lg-4 col-xl-3 customContainerSet">
	    <div class="rounded position-relative fruite-item">
	        <div class="fruite-img customImgSet">
	            <img src="${root}upload/${dataInfo.content_file}" 
	            class="img-fluid w-100 rounded-top" alt=""
	            onerror="this.onerror=null; this.src='${fruitables}img/fruite-item-5.jpg';">
	        </div>
	        <div class="p-4 border border-secondary border-top-0 rounded-bottom customBottomSet">
		        <form action="${root }content/detail" method="post" id="postForm_${dataInfo.content_idx }">
							<input type="hidden" name="content_idx" value="${dataInfo.content_idx }">
							<input type="hidden" name="category_idx" value="${dataInfo.category_idx }">
							<a href="#" onclick="document.getElementById('postForm_${dataInfo.content_idx}').submit();">
								<h4>${dataInfo.content_subject}</h4>
	            	<p>
	            		<c:choose> 
										<c:when test="${dataInfo.content_text.length() <= 20}">
											${dataInfo.content_text}			
										</c:when> 
										<c:otherwise>
											${dataInfo.content_text.substring(0, 20)}...
										</c:otherwise> 
									</c:choose> 
	            	</p>
			        </a>
		        </form>
	        </div>
	    </div>
	</div>
</c:forEach>
