<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<c:set var="root" value="${pageContext.request.contextPath}/" /> 
<c:set var="photoFolio" value="${root}template/photoFolio/" />     
<c:set var="fruitables" value="${root}template/fruitables/" />
<c:set var="bootswatch" value="${root}template/bootswatch/" />
    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
        
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>제품글쓰기</title>
  
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
	
	<!-- Froala Editor -->
	<link href="https://cdnjs.cloudflare.com/ajax/libs/froala-editor/3.2.7/css/froala_editor.pkgd.min.css" rel="stylesheet" type="text/css" />
</head>
<body>

<c:import url="/WEB-INF/views/include/top_menu.jsp" />
	
	<br>
	<h3 style="text-align:center;">제품 글쓰기</h3>
	
	<form:form action="${root }content/write_procedure" 
	           modelAttribute="writeContentDTO" method="post"
	           enctype="multipart/form-data">
	
		
		<table style="width:90%;margin-left:auto;margin-right:auto;">
			<colgroup>
	        <col width="12%" />
	        <col width="85%" />
	    </colgroup>
	    <tr>
				<td style="text-align:right;padding-right:10px;">
					<form:label class="col-form-label mt-4" path="category_idx">카테고리</form:label>
				</td>
				<td>
					<div class="row" style="padding-left:12px;">
						<form:select class="form-select" path="category_idx" style="width:29%;" title="카테고리종류">
							<c:forEach var="item" items="${categoryDTO }">
								<option value="${item.category_idx }" <c:if test ="${category_idx eq item.category_idx}">selected="true"</c:if>>${item.category_name }</option>
							</c:forEach>
						</form:select>
						
						<%-- <form:input type="text" class="form-control" path="category_select_name" style="width:70%;display:none;" placeholder="카테고리를 입력하세요" /> --%>
					</div>
				</td>
			</tr>
	    <tr>
				<td style="text-align:right;padding-right:10px;">
					<form:label class="col-form-label mt-4" path="content_subject">제목</form:label>
				</td>
				<td>
					<form:input type="text" class="form-control" path="content_subject" placeholder="제목을 입력하세요" />
			  	<form:errors path="content_subject" class="text-danger" />
				</td>
			</tr>
			<tr>
				<td style="text-align:right;padding-right:10px;">
					<form:label path="content_text" class="form-label mt-4">내용</form:label>
				</td>
				<td>
					<form:textarea id="froala-editor" class="form-control" path="content_text" rows="10" />
				</td>
			</tr>
			<tr>
				<td style="text-align:right;padding-right:10px;">
					<form:label path="uploadFile" class="form-label mt-4">첨부 이미지</form:label>
				</td>
				<td>
					<form:input class="form-control" type="file" path="uploadFile" accept="images/*" />
				</td>
			</tr>
			<tr>
				<td style="text-align:right;padding-right:10px;">
					<form:label class="col-form-label mt-4" path="content_make">제조사</form:label>
				</td>
				<td>
					<form:input type="text" class="form-control" path="content_make" placeholder="제조사를 입력하세요" />
			 		<form:errors path="content_make" class="text-danger" />
				</td>
			</tr>
			<tr>
				<td style="text-align:right;padding-right:10px;">
					<form:label class="col-form-label mt-4" path="content_country">원산지</form:label>
				</td>
				<td>
					<form:input type="text" class="form-control" path="content_country" placeholder="원산지를 입력하세요" />
		  		<form:errors path="content_country" class="text-danger" />
				</td>
			</tr>
			<tr>
				<td style="text-align:right;padding-right:10px;">
					<form:label class="col-form-label mt-4" path="content_prodprice">제품가격</form:label>
				</td>
				<td>
					<form:input type="number" class="form-control" path="content_prodprice" placeholder="제품가격을 입력하세요" />
				</td>
			</tr>
			<tr>
				<td colspan="2" style="text-align:center;">
					<form:button type="submit" class="btn btn-primary">작성하기</form:button>&nbsp;&nbsp;
					<form:button type="button" class="btn btn-secondary" onclick="location.href='${root}content/list'">취소(목록)</form:button>
				</td>
			</tr>
		</table>	
	
	</form:form>
		
	
<c:import url="/WEB-INF/views/include/bottom_menu.jsp" />
	
	<!-- Scroll Top -->
  <a href="#" id="scroll-top" class="scroll-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>


  <!-- Vendor JS Files -->
  <script src="${photoFolio}vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
  <script src="${photoFolio}vendor/php-email-form/validate.js"></script>
  <script src="${photoFolio}vendor/aos/aos.js"></script>
  <script src="${photoFolio}vendor/glightbox/js/glightbox.min.js"></script>
  <script src="${photoFolio}vendor/swiper/swiper-bundle.min.js"></script>	
  
  <!-- Froala Editor js -->
  <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/froala-editor/3.2.7/js/froala_editor.pkgd.min.js"></script>


	<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
	<script>
		var editor = new FroalaEditor('#froala-editor');
		// froala 에디터 실행 jquery
		$(function() {
			$('#froala-editor').foralaEditor({
				toolbarButtons: ['bold', 'italic', 'underline', 'strikeThrough', 'subscript', 'superscript', '|', 'formatOL', 'formatUL', '|', 'insertImage', 'insertLink'],
				heightMin: 300
				
			});
		});
	
		/*
		function selectCategory(obj){
			//console.log(obj.id);
			let id = obj.id;
			let val = $("#" + id).val();
			
			//console.log(val);
			$("#category_select_name").val("");
			if(val == 5) {
				$("#category_select_name").show();
			}
			else{
				$("#category_select_name").hide();
			}
		}
		*/
	</script>

</body>
</html>    