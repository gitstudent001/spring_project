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
	<title>Document</title>
  
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
</head>
<body>

	<c:import url="/WEB-INF/views/include/top_menu.jsp" />

<div class="container-fluid py-5 mb-5 hero-header">
	<h1>test.jsp</h1>
	
	<h2>이메일 :  </h2>
	<h2>닉네임 :  </h2>
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
	
</body>
</html>    