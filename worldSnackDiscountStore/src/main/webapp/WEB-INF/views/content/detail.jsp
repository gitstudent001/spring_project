<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<c:set var="root" value="${pageContext.request.contextPath}/" /> 

<c:set var="photoFolio" value="${root}template/photoFolio/" />     
<c:set var="fruitables" value="${root}template/fruitables/" />
<c:set var="bootswatch" value="${root}template/bootswatch/" />

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>제품 상세</title>
    
 	<!-- Google Web Fonts -->
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Raleway:wght@600;800&display=swap" rel="stylesheet"> 
	
	<!-- Customized Bootstrap Stylesheet -->
	<link href="${fruitables}css/bootstrap.min.css" rel="stylesheet">
	
	<!-- Template Stylesheet -->
	<link href="${fruitables}css/style.css" rel="stylesheet">
	
	<!-- bootswatch Stylesheet -->
	<link href="${bootswatch}css/bootstrap.min.css" rel="stylesheet">
	
	<!-- 별모양 아이콘 추가 -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    
  <link rel="stylesheet" href="${root }css/detail.css" type="text/css" />
</head>
<body>
	<c:import url="/WEB-INF/views/include/top_menu.jsp" />
  <div class="product-detail-container">
      <h1 style="text-align:center;">제품 상세</h1>
      
      <div class="product-header">
          <div class="product-image">
              <img alt="사진 없음" src="${root }upload/카라멜사진.jpg" style="width:100%; text-align:center;" />
          </div>
          <div class="product-info">
              <h2>${detailContentDTO.content_subject }</h2>
              <p>가격 ${detailContentDTO.content_prodprice }원</p>
              <form id="scrapForm" action="${root }content/scrap" method="post">
              	<input type="hidden" name="content_idx" value="${detailContentDTO.content_idx }">
              	<input type="hidden" name="user_idx" value="${user_idx }">
              	<input type="hidden" name="limit" value="${limit }">
              	<input type="hidden" name="page" value="${page }">
              	<input type="hidden" name="category_idx" value="${category_idx }">
					     	<c:choose>
									<c:when test="${alreadyScrap == true }">
										<input type="hidden" name="scrapCheck" value="true">
										<button type="submit" class="btn-scrap" >
					         		<i class="fas fa-star" id="yesScrap"></i> <!-- 채워진 별 아이콘 -->
					         	</button>
									</c:when>
									<c:otherwise>
										<input type="hidden" name="scrapCheck" value="false">
										<button type="submit" class="btn-scrap">
					         		<i class="far fa-star"></i> <!-- 기본 별 아이콘 -->
					         	</button>
									</c:otherwise>
								</c:choose>
              </form>
          </div>
      </div>

      <hr>

      <div class="product-specs">
          <h3>상품필수정보</h3>
          <div class="specs-table">
              <p>제조사/원산지 : ${detailContentDTO.content_make } / ${detailContentDTO.content_country }</p>
              <p>상품번호 : ${detailContentDTO.content_prodno }</p>
              <p>식품의 유형 : 상품상세참조</p>
          </div>
      </div>

      <hr>

      <div class="product-description">
          <h3>제품 내용</h3>
          <div class="description-box">
          	<textarea title="제품 내용" readonly>${detailContentDTO.content_text }</textarea>
          </div>
      </div>

      <div class="action-buttons">
      	<a href="${root}content/list?category_idx=${category_idx }&limit=${limit}&page=${page}" class="btn btn-secondary">목록</a>
	     	<c:if test="${detailContentDTO.content_writer_idx == user_idx }">
					<a href="${root }content/modify?content_idx=${content_idx}&category_idx=${category_idx }&limit=${limit}&page=${page}" class="btn btn-info">수정하기</a>
					<a href="${root }content/delete?content_idx=${content_idx}&content_writer_idx=${detailContentDTO.content_writer_idx}" class="btn btn-danger">삭제하기</a>
				</c:if>
      </div>

  </div>
  
  <c:import url="/WEB-INF/views/include/bottom_menu.jsp" />
	<!-- Scroll Top -->
  <a href="#" id="scroll-top" class="scroll-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>


  <!-- Vendor JS Files -->
  <script src="${photoFolio}vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
  <script src="${photoFolio}vendor/php-email-form/validate.js"></script>
  <script src="${photoFolio}vendor/aos/aos.js"></script>
  <script src="${photoFolio}vendor/glightbox/js/glightbox.min.js"></script>
  <script src="${photoFolio}vendor/swiper/swiper-bundle.min.js"></script>	
  
  
  <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</body>
</html>
