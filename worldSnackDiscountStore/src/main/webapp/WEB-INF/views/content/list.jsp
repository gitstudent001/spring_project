<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="root" value="${pageContext.request.contextPath }/"/>
<c:set var="photoFolio" value="${root}template/photoFolio/" />  
<c:set var="fruitables" value="${root}template/fruitables/" />
<c:set var="bootswatch" value="${root}template/bootswatch/" />

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>제품 목록</title>
    
    <!-- Favicons -->
	<link href="${photoFolio}img/favicon.png" rel="icon">
  <!-- <link href="${photoFolio}img/apple-touch-icon.png" rel="apple-touch-icon"> --%>
    
   
    
   <!-- Google Web Fonts -->
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Raleway:wght@600;800&display=swap" rel="stylesheet">  
   
   
   <!-- Icon Font Stylesheet -->
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"/>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">
	
	
	<!-- Libraries Stylesheet -->
	<link href="${fruitables}lib/lightbox/css/lightbox.min.css" rel="stylesheet">
	<link href="${fruitables}lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
    
    
  <!-- Customized Bootstrap Stylesheet -->
	<link href="${fruitables}css/bootstrap.min.css" rel="stylesheet">
	
	<!-- Template Stylesheet -->
	<link href="${fruitables}css/style.css" rel="stylesheet">
	
	<!-- bootswatch Stylesheet -->
	 <link href="${bootswatch}css/bootstrap.min.css" rel="stylesheet"> 
	
	<!-- 게시글 수 지정 -->
  <link href="${root }css/list.css" rel="stylesheet" />
	
	
	<style>
		.fruite .tab-class .nav-item a.active {
		    background: #FFB524 !important;
		}
	</style> 
	 
</head>
<body>


	<c:import url="/WEB-INF/views/include/top_menu.jsp" />


<div style="width:96%;margin:auto;">
	<br>
	<h2>${categoryName }</h2>
	<input type="hidden" value="${category_idx}" id="hdCategoryIdx" />
	
	
	<div class="fruite">
		<div class="tab-class">
			<ul class="nav">
				<!-- 카테고리 -->
				<li class="nav-item">
					<a onclick="location.href='list'" class="btnCategoryAll"
				 		 style="text-decoration: none; display: inline-flex; align-items: center; justify-content: center; margin: 10px; padding: 10px 20px; background-color: #f8f9fa; border: none; border-radius: 50px; color: #000; width: 130px; cursor: pointer;">
					<span>전체</span></a>
				</li> 
				 		 
				<c:forEach var="ctgInfo" items="${categoryDTO}"> 
					<li class="nav-item">
						<a  onclick="location.href='list?category_idx=${ctgInfo.category_idx }'" 
								class ="btnCategory"
								id="btnCategory_${ctgInfo.category_idx}"
								style="text-decoration: none; display: inline-flex; align-items: center; justify-content: center; margin: 10px; padding: 10px 20px; background-color: #f8f9fa; border: none; border-radius: 50px; color: #000; width: 130px; cursor: pointer;">						 
						<span>${ctgInfo.category_name }</span></a>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div> 

	<div style=" float:right;">
	<form method="get" action="${root }content/list" class="dropdown">
		<label for="limit">게시글 수 :</label>
		<input type="hidden" name="category_idx" value="${category_idx }">
		<input type="hidden" name="page" value="${page }">
		<select class = "limit_select" id="limit" name="limit" onchange="this.form.submit()">
			<option value="10" ${limit == 10 ? 'selected' : '' }>10개</option>
			<option value="20" ${limit == 20 ? 'selected' : '' }>20개</option>
			<option value="30" ${limit == 30 ? 'selected' : '' }>30개</option>
			<option value="50" ${limit == 50 ? 'selected' : '' }>50개</option>
			<option value="100" ${limit == 100 ? 'selected' : '' }>100개</option>
		</select>
	</form>
	</div>

	<table class="list">
    <thead>
        <tr>
            <th>글번호</th>
            <th>제목</th>
            <th>제품사진</th>
            <th>작성자</th>
            <th>제조사</th>
            <th>원산지</th>
            <th>제품번호</th>
            <th>가격</th>
            <th>조회수</th>
            <th>작성일자</th>
            
        </tr>
    </thead>
    <tbody>
        <c:forEach var="dataInfo" items="${contentDTO}">
            <tr>
                <td>${dataInfo.content_idx }</td>
                <td>
									<form action="${root }content/detail" method="post" id="postForm_${dataInfo.content_idx }">
										<input type="hidden" name="content_idx" value="${dataInfo.content_idx }">
										<input type="hidden" name="limit" value="${limit }">
										<input type="hidden" name="category_idx" value="${category_idx }">
										<input type="hidden" name="page" value="${page }">
										<a href="#" onclick="document.getElementById('postForm_${dataInfo.content_idx}').submit();">
											${dataInfo.content_subject}
						        </a>
					        </form>
								</td>
                <td><img src="${root}upload/${dataInfo.content_file}"  style="width:100px; height:100px;" onerror="this.style.display='none';"/></td>
								<td>${dataInfo.content_writer_nickname }</td>
								<td>${dataInfo.content_make }</td>
								<td>${dataInfo.content_country }</td>
								<td>${dataInfo.content_prodno }</td>								
								<td><fmt:formatNumber value="${dataInfo.content_prodprice }" pattern="#,###" /></td>
                <td>${dataInfo.content_view}</td>
                <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${dataInfo.content_date }" /></td>
            </tr>
        </c:forEach>
    </tbody>
	</table>

	<div class="button-container">
		<div class="d-none d-md-block" style="margin : auto">
			<ul class="pagination justify-content-center">
			<c:choose>
		   	<c:when test="${pageDTO.prevPage <= 0 }" >	
				<li class="page-item disabled">
					<a href="#" class="page-link">이전</a>
				</li>
			</c:when>
			<c:otherwise>
				<li class="page-item">
					<a href="${root}content/list?page=${pageDTO.prevPage}&category_idx=${category_idx}&limit=${limit}" class="page-link">이전</a>
				</li>
			</c:otherwise>	
		    </c:choose>		
				<c:forEach var="idx" begin="${pageDTO.min}" end="${pageDTO.max}">
				<c:choose>
				<c:when test="${idx == pageDTO.currentPage }" >
					<li class="page-item active">
						<a href="${root}content/list?page=${idx}&category_idx=${category_idx}&limit=${limit}" class="page-link">${idx }</a>
					</li>
				</c:when>
				<c:otherwise>
				    <li class="page-item">
						<a href="${root}content/list?page=${idx}&category_idx=${category_idx}&limit=${limit}" class="page-link">${idx }</a>
					</li>
				</c:otherwise>	
				</c:choose>	
				</c:forEach>
				<c:choose>
			   	<c:when test="${pageDTO.max >= pageDTO.totalPage }" >	
					<li class="page-item disabled">
						<a href="#" class="page-link">다음</a>
					</li>
				</c:when>
				<c:otherwise>
					<li class="page-item">
						<a href="${root}content/list?page=${pageDTO.nextPage}&category_idx=${category_idx}&limit=${limit}" class="page-link">다음</a>
					</li>
				</c:otherwise>	
			    </c:choose>	
			</ul>
		</div>
	    <button type="button" onclick="location.href='write?category_idx=${category_idx}'">글쓰기</button>
	</div>
</div>

	
	<c:import url="/WEB-INF/views/include/bottom_menu.jsp" />
	
	
	<!-- Scroll Top -->
  <a href="#" id="scroll-top" class="scroll-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>


  <!-- Vendor JS Files -->
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
  <script src="${fruitables}lib/easing/easing.min.js"></script>
  <script src="${fruitables}lib/waypoints/waypoints.min.js"></script>
  <script src="${fruitables}lib/lightbox/js/lightbox.min.js"></script>
  <script src="${fruitables}lib/owlcarousel/owl.carousel.min.js"></script>
	
	<!-- 이미지가 로드되지 않을 경우 안보이도록 display:none 처리 -->
	<script type="text/javascript">
		document.addEventListener("DOMContentLoaded", function() {
		    const images = document.querySelectorAll('img');
	
		    images.forEach(img => {
		        img.onerror = function() {
		            this.style.display = 'none'; // 이미지 로드 실패 시 숨김
		        };
		    });
		});
		
		
		$(window).on("load", function () { 
			
			let categoryIdx = $("#hdCategoryIdx").val();
			
			if(categoryIdx == 0) {
				$(".btnCategoryAll").addClass("active");
			}
			else{
				$("#btnCategory_" + categoryIdx).addClass("active");
			}
			
		});
	</script>
	

</body>
</html>    