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
	<title>main</title>
    
  <!-- Favicons -->
  <link href="${photoFolio}img/favicon.png" rel="icon">
  <link href="${photoFolio}img/apple-touch-icon.png" rel="apple-touch-icon">

  <!-- Fonts -->
  <link href="https://fonts.googleapis.com" rel="preconnect">
  <link href="https://fonts.gstatic.com" rel="preconnect" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&family=Inter:wght@100;200;300;400;500;600;700;800;900&family=Cardo:ital,wght@0,400;0,700;1,400;1,700&display=swap" rel="stylesheet">

  <!-- Vendor CSS Files -->
  <%-- <link href="${photoFolio}vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <link href="${photoFolio}vendor/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
  <link href="${photoFolio}vendor/vendor/aos/aos.css" rel="stylesheet">
  <link href="${photoFolio}vendor/vendor/glightbox/css/glightbox.min.css" rel="stylesheet">
  <link href="${photoFolio}vendor/vendor/swiper/swiper-bundle.min.css" rel="stylesheet"> --%>

  <!-- Main CSS File -->
  <link href="${photoFolio}css/main.css" rel="stylesheet">
  
  
  
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
	
	<!-- bootswatch Stylesheet -->
	<link href="${bootswatch}css/bootstrap.min.css" rel="stylesheet">
	
	<!-- Customized Bootstrap Stylesheet -->
	<link href="${fruitables}css/bootstrap.min.css" rel="stylesheet">
	
	<!-- Template Stylesheet -->
	<link href="${fruitables}css/style.css" rel="stylesheet">
	
	
	
	<style>
		body {
		  margin: 0;
		  padding: 0;
		}
		
		.wrapper {
		  width: 100%;
		  /*height: 100vh;*/
		  height: 460px;
		  display: flex;
		  flex-direction: column;
		  justify-content: flex-start;
		  align-items: center;
		  overflow: hidden;
		}
		
		.container-hero {
			display: flex;
		  flex-direction: column;
		  justify-content: flex-start;
		  align-items: center;
		  margin: auto;
		  margin-top: 50px;
		}
	</style>
</head>



<!-- <body class="index-page"> -->
<body>
	
	<c:import url="/WEB-INF/views/include/top_menu.jsp" />

	
	<!-- <div class="wrapper"> -->
		
		<!-- Hero Start -->
		<div class="container-fluid py-5 mb-5 hero-header container-hero">
	      <div class="container py-5">
	          <div class="row g-5 align-items-center">
	              <div class="col-md-12 col-lg-7">
	                  <h4 class="mb-3 text-secondary">World Snack Discount Store</h4>
	                  <h1 class="mb-5 display-4 text-primary">세계 과자 할인점 커뮤니티</h1>
	                  <div class="position-relative mx-auto">
	                      <input type="text" id="txtSearch" class="form-control border-2 border-secondary w-75 py-3 px-4 rounded-pill" placeholder="Search">
	                      <button type="button" id="btnSearch" class="btn btn-primary border-2 border-secondary py-3 px-4 position-absolute rounded-pill text-white h-100" style="top: 0; right: 25%;">Submit Now</button>
	                  </div>
	              </div>	
	              <div class="col-md-12 col-lg-5">
	              </div>
	          </div>
	      </div>
	  </div>
	  <!-- Hero End -->  	
	
	<!-- </div> -->
	


  <main class="main">

		<!-- Category Start -->
		<div class="container-fluid fruite" style="margin:auto; margin:-70px !important;">
		   <div class="container py-5">
		       <div class="tab-class text-center" >
		           <div class="row g-4" style="width:100%; margin:auto; margin-left:10%;">
		               
		               <div class="col-lg-8" style="width:100%; margin:auto;">
		                   <ul class="nav nav-pills d-inline-flex text-center mb-5" style="padding-right:5%;">
		                       <li class="nav-item">
		                           <a class="d-flex m-2 py-2 bg-light rounded-pill active btnCategoryAll" data-bs-toggle="pill" href="#tab-1" 
		                           		id="btnCategoryAll" 
		                           		data-categoryIdx="0"> 
		                           		<%-- onclick="location.href='${root}'"> --%> 
		                               <span class="text-dark" style="width: 130px;">All Products</span>
		                           </a>
		                       </li>
		                       
		                       <%-- onclick="location.href='${root}?category_info_idx=${category.category_info_idx}'"  --%>
		                       <c:forEach var="category" items="${categoryDTO}">
		                       <li class="nav-item">
		                           <a class="d-flex m-2 py-2 bg-light rounded-pill btnCategory" data-bs-toggle="pill" href="#tab-1"
		                           		id="btnCategory_${category.category_idx}" 
		                           		data-categoryIdx="${category.category_idx}">
 			                            <%-- onclick="location.href='${root}?category_info_idx=${category.category_info_idx}'" --%>
		                               <span class="text-dark" style="width: 130px;">${category.category_name }</span>
		                           </a>
		                           <input type="hidden" id="hdCategoryClickChk_${category.category_idx}" data-categoryIdx="${category.category_idx}" />
		                       </li>
		                       </c:forEach>
		                       
		                   </ul>
		               </div>
		           </div>
						</div>
				</div>
		</div>                    
		<!-- Category End -->

                    
		<!-- Fruits Shop Start-->
    <div class="container-fluid fruite py-5" style="margin-top:-150px !important;">
        <div class="container py-5">
            <div class="tab-class text-center">
            
                <div class="tab-content">
                
                		<div id="tab-1" class="tab-pane fade show p-0 active">
                        <div class="row g-4">
                            <div class="col-lg-12">
                                <div class="row g-4" id="rowProductList">
                                <%-- <c:forEach var="dataInfo" items="${contentDTO}">
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
																</c:forEach> --%>                                    
                                </div>
                            </div>
                        </div>
                    </div>
                    
                </div>
            </div>      
        </div>
    </div>
    <!-- Fruits Shop End-->

  </main>



  <c:import url="/WEB-INF/views/include/bottom_menu.jsp" />
  
  

  <!-- Scroll Top -->
  <a href="#" id="scroll-top" class="scroll-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>

  <!-- Preloader -->
  <div id="preloader">
    <div class="line"></div>
  </div>


  <!-- Vendor JS Files -->
  <%-- <script src="${photoFolio}vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
  <script src="${photoFolio}vendor/php-email-form/validate.js"></script>
  <script src="${photoFolio}vendor/aos/aos.js"></script>
  <script src="${photoFolio}vendor/glightbox/js/glightbox.min.js"></script>
  <script src="${photoFolio}vendor/swiper/swiper-bundle.min.js"></script> --%>

  <!-- Main JS File -->
  <script src="${photoFolio}js/main.js"></script>
  
  
	<!-- JavaScript Libraries -->
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
  <script src="${fruitables}lib/easing/easing.min.js"></script>
  <script src="${fruitables}lib/waypoints/waypoints.min.js"></script>
  <script src="${fruitables}lib/lightbox/js/lightbox.min.js"></script>
  <script src="${fruitables}lib/owlcarousel/owl.carousel.min.js"></script>  
  
  <script src="${fruitables}js/main.js"></script>
	

	<!-- <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script> -->  
  <script>
  	$(window).on("load", function () {
  		
  		$("#txtSearch").on("keypress",function(key) {
  			// Enter 키 이벤트 실행
  			if(key.keyCode==13) {
  				$("#btnSearch").trigger("click");
  			}
  		});
  		
  		$("#btnSearch").on("click", function() {
  			let txtSearch = $("#txtSearch").val();
  			
  			if($.trim(txtSearch).length > 0) {
  				dataSearch(0, "", 0, txtSearch);
  				categorySearch(txtSearch);
  			}
  			else{
  				alert("검색어를 입력하세요");
  				$("#txtSearch").focus();
  			}
  		});
  		
  		$(".btnCategoryAll").on("click", function() {
  			/*
  			console.log(this);
 	  		console.log("id : " + this.id);
 	  		console.log("className : " + this.className);
 	  		*/
 	  		
 	  		$("#txtSearch").val("");
 	  		$(".btnCategory").removeClass("active");
 	  		
 	  		dataSearch(0, "", 0, "");
 	  		$(".btnCategory").each(function() {
	  			let categoryIdx = $("#" + this.id).attr("data-categoryIdx");
	 	  		let categoryChkId = $("#hdCategoryClickChk_" + categoryIdx).attr("id");
	 	  		
	 	  		$("#" + categoryChkId).val("");
 	  		});
 	  		
  		});
  		
  		$(".btnCategory").on("click", function(obj) {
  			/*
  			console.log(this);
 	  		console.log("id : " + this.id);
 	  		console.log("className : " + this.className);
 	  		*/
 	  		let categoryIdx = $("#" + this.id).attr("data-categoryIdx");
 	  		let categoryChkId = $("#hdCategoryClickChk_" + categoryIdx).attr("id");
 	  		
 	  		$("#txtSearch").val("");
 	  		$(".btnCategoryAll").removeClass("active");
 	  		
        if ($("#" + categoryChkId).val() == "Y") {
        	$("#" + this.id).removeClass("active");
        	$("#" + categoryChkId).val("N");
        }
        else{
        	$("#" + this.id).addClass("active");
        	$("#" + categoryChkId).val("Y");
        }
        
	  		let categoryChk = false;
	  		let categoryCnt = 0;
	  		let idxArr = "";
	  		$(".btnCategory").each(function() {
	  			let categoryIdx = $("#" + this.id).attr("data-categoryIdx");
	 	  		let categoryChkId = $("#hdCategoryClickChk_" + categoryIdx).attr("id");
	 	  		
	 	  		if($("#" + categoryChkId).val() == "Y") {
	 	  			$("#" + this.id).addClass("active");
	 	  			categoryChk = true;
	 	  			categoryCnt = categoryCnt + 1;
	 	  			
	 	  			if($.trim(idxArr) == ""){
	 	  				//idxArr = "'" + categoryIdx + "'";
	 	  				idxArr = categoryIdx;
	 	  			}
	 	  			else{
	 	  				//idxArr = idxArr + ",'" + categoryIdx + "'";
	 	  				idxArr = idxArr + "," + categoryIdx;
	 	  			}
	 	  		}
	  		});
	  		
	  		if(categoryChk == false) {
	  			$(".btnCategoryAll").addClass("active");
	  			dataSearch(0, "", 0, "");
	  		}
	  		else{
	  			dataSearch(categoryIdx, idxArr, categoryCnt, "");
	  		}
	  		
			});
  		  		
  		$(".btnCategoryAll").trigger("click");
  		
  	});
  	
  	
  	// 제품 데이터 리스트
  	function dataSearch(categoryIdx, categorys, categoryCnt, txtSearch){
  		console.log(`categoryIdx : ` + categoryIdx);
  		console.log(`categorys : ` + categorys);
  		console.log(`categoryCnt : ` + categoryCnt);
  		
  		
			$.ajax({
	        url : '${root}main',
	        type : 'post',
	        dataType : "html",
	        //contentType:"application/json",
	        data : { category_idx : categoryIdx,
	        				 categorys : categorys,
	        				 categoryCnt : categoryCnt,
	        				 categorySearch : txtSearch
	        			},
	        timeout: 10000,
	        beforeSend:function(){
	            //$('#loading').removeClass('display-none');
	        },
	        success : function(data){
	            //console.log(data);
	            
	            $("#rowProductList").html("");
            	$("#rowProductList").html(data);
	        },
	        error : function(request, status, error){
	            //alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	            var err=JSON.parse(request.responseText);

	            //alert(err.resData[0].errorMsg);
	                
	            //$('#loading').addClass('display-none');
	        },
	        complete:function(){
	            //$('#loading').addClass('display-none');
	        }
	 		});
  	}
  	
  	// 카테고리 번호 리스트
  	function categorySearch(txtSearch){
  		
			$.ajax({
	        url : '${root}main_categorys',
	        type : 'post',
	        dataType : "html",
	        //contentType:"application/json",
	        data : { categorySearch : txtSearch },
	        timeout: 10000,
	        beforeSend:function(){
	            //$('#loading').removeClass('display-none');
	        },
	        success : function(data){
	            //console.log(data);
	            
	            $(".btnCategoryAll").removeClass("active");
	            $(".btnCategory").removeClass("active");
	            
	            if(data.indexOf(',') > -1) {
	            	let arr = data.split(",");
	            	
	            	for(let i=0; i < arr.length; i++) {
	            		$("#btnCategory_" + arr[i]).addClass("active");
	            		$("#hdCategoryClickChk_" + arr[i]).val("Y");
	            	}
	            }
	            else{
	            	$("#btnCategory_" + data).addClass("active");
	            	$("#hdCategoryClickChk_" + data).val("Y");
	            }
	            
	        },
	        error : function(request, status, error){
	            //alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	            var err=JSON.parse(request.responseText);

	            //alert(err.resData[0].errorMsg);
	                
	            //$('#loading').addClass('display-none');
	        },
	        complete:function(){
	            //$('#loading').addClass('display-none');
	        }
	 		});
  	}
  </script>
		
</body>
</html>    