<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>   
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel="stylesheet" href="<c:url value="/css/total.css"/>">
	<link rel="stylesheet" href="<c:url value="/css/book_data.css"/>">
	<title>Edit book data</title>

	<spring:message code="local.locbutton.name.ru" var="ru_button" />
	<spring:message code="local.locbutton.name.en" var="en_button" />
	<spring:message code="local.field.name.author" var="author" />
	<spring:message code="local.field.name.name" var="authorName" />
	<spring:message code="local.field.name.surname" var="authorSurname" />
	<spring:message code="local.field.name.title" var="title" />
	<spring:message code="local.field.name.genre" var="genre" />
	<spring:message code="local.addBookPage.select.option.name.genreSelection" var="genreSelection" />
	<spring:message code="local.addBookPage.select.option.name.fiction" var="fiction" />
	<spring:message code="local.addBookPage.select.option.name.technical" var="technical" />
	<spring:message code="local.button.name.addBook" var="addBookButton" />
	<spring:message code="local.button.name.addMoreAuthor" var="addMoreAuthorButton" />
	<spring:message code="local.button.name.remove" var="removeButton" />
	<spring:message code="local.button.name.goToAccount" var="goToAccount_button" />
	<spring:message code="local.button.name.logOut" var="logOutButton" />
	<spring:message code="local.button.name.editData" var="editDataButton" />

	<c:set var="url" value="jsp/account/admin/AddingBook.jsp" scope="session"/>

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
	<div class="header">
		<div class="layout-positioner">
			<div class="table-main-menu">
				<div class="row-main-menu">
					<div class="cell-main-menu">
						<form action="${pageContext.request.contextPath}/start-page" method="post">
							<input type="submit" value="${logOutButton}">
						</form>
					</div>
					<div class="cell-main-menu">
						<form action="${pageContext.request.contextPath}/user-data" method="get">
							<input type="submit" value="${editDataButton}">
						</form>
					</div>
					<div class="cell-main-menu">
						<form action="${pageContext.request.contextPath}/main-page" method="get">
							<input type="submit" value="${goToAccount_button}">
						</form>
					</div>
				</div>
			</div>

			<div class="table-local">
				<div class="row-local">
					<div class="cell-local">
						<a href="?lang=ru">${ru_button}</a>
					</div>
					<div class="cell-local">
						<a href="?lang=en">${en_button}</a>
					</div>
				</div>
			</div>
		</div>
	</div>

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
					<br>
					<b><c:out value="${title}" /></b>:<br/>
					<input type="text" name="title" value="" required/><br/>
					<br>
					<b><c:out value="${genre}" /></b>:<br/>
					<select name="genre" required>
						<option selected disabled><c:out value="${genreSelection}" /></option>
						<option value="fiction"><c:out value="${fiction}" /></option>
						<option value="technical"><c:out value="${technical}" /></option>
					</select><br/>
					<br>
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
	    	var br = $('<br/>').appendTo(div);
		}
		//Удаление поля
		$('.DeleteDynamicField').click(function(event) {
	                $(this).parent().remove();
	                return false;
	            });
        
	</script>
</body>
</html>