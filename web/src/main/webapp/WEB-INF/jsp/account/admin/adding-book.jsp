<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>   
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel="stylesheet" href="<c:url value="/assets/css/total.css"/>">
	<link rel="stylesheet" href="<c:url value="/assets/css/book_data.css"/>">

	<spring:message code="local.field.name.author" var="author" />
	<spring:message code="local.field.name.name" var="authorName" />
	<spring:message code="local.field.name.surname" var="authorSurname" />
	<spring:message code="local.field.name.title" var="title" />
	<spring:message code="local.field.name.genre" var="genre" />
	<spring:message code="local.addBookPage.select.option.name.genreSelection" var="genreSelection" />
	<spring:message code="local.addBookPage.select.option.name.fiction" var="fiction" />
	<spring:message code="local.addBookPage.select.option.name.technical" var="technical" />
	<spring:message code="local.addBookPage.select.option.name.psychology" var="psychology" />
	<spring:message code="local.button.name.addBook" var="addBookButton" />
	<spring:message code="local.button.name.addMoreAuthor" var="addMoreAuthorButton" />
	<spring:message code="local.button.name.remove" var="removeButton" />

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
	<div class="container-content">
		<div class="layout-positioner">
			<div class="message">
				<c:out value="${message}" />
			</div>
			<div class="form-book-data">
				<form action="${pageContext.request.contextPath}/adding-book" method="post">
					<b><c:out value="${author}" /></b>:<br/>
					<div id="DynamicFieldsContainer">
						<div class="DynamicField">
							<label for="surname"><c:out value="${authorSurname}" /></label>
							<input type="text" id="surname" name="surnames[]" value="" required/>
							<label for="name"><c:out value="${authorName}" /></label>
							<input type="text" id="name" name="names[]" value="" required/>
						</div>
					</div>
					<div id="addDynamicField">
						<input type="button" id="addDynamicFieldButton" value="${addMoreAuthorButton}">
					</div>
					<b><c:out value="${title}" /></b>:<br/>
					<input type="text" name="title" value="" required/><br/>
					<br>
					<b><c:out value="${genre}" /></b>:<br/>
					<select name="genre" required>
						<option selected disabled><c:out value="${genreSelection}" /></option>
						<option value="FICTION"><c:out value="${fiction}" /></option>
						<option value="TECHNICAL"><c:out value="${technical}" /></option>
						<option value="PSYCHOLOGY"><c:out value="${psychology}" /></option>
					</select>
					<input type="submit" value="${addBookButton}">
				</form>
			</div>
		</div>
	</div>

	<script>   
		$('#addDynamicFieldButton').click(function(event) {
	    	addDynamicField();
	    	return false;
		 });
		function addDynamicField() {
	    	var div = $('<div/>', {
	        	'class' : 'DynamicField'
	    	}).appendTo($('#DynamicFieldsContainer'));
            var br = $('<br/>').appendTo(div);
	    	var label = $('<label/>').html('<c:out value="${authorSurname}" />').appendTo(div);
	    	var input = $('<input/>', {
	        	name : 'surnames[]',
	        	type : 'text',
	        	required : 'required'
	    	}).appendTo(label);
	    	var label = $('<label/>').html(" " + '<c:out value="${authorName}" />').appendTo(div);
	    	var input = $('<input/>', {
	        	name : 'names[]',
	        	type : 'text',
	        	required : 'required'
	    	}).appendTo(label);
	    	var input = $('<input/>', {
	        	value : '${removeButton}',
	        	type : 'button',
	        	'class' : 'DeleteDynamicField'
	    	}).appendTo(div);
	    	input.click(function() {
	        	$(this).parent().remove();
	    	});
		}
		//Удаление поля
		$('.DeleteDynamicField').click(function(event) {
	                $(this).parent().remove();
	                return false;
	            });
        
	</script>
</body>
</html>