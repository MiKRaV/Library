<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>   
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />
<fmt:message bundle="${loc}" key="local.field.name.author" var="author" />
<fmt:message bundle="${loc}" key="local.field.name.name" var="authorName" />
<fmt:message bundle="${loc}" key="local.field.name.surname" var="authorSurname" />
<fmt:message bundle="${loc}" key="local.field.name.title" var="title" />
<fmt:message bundle="${loc}" key="local.field.name.genre" var="genre" />
<fmt:message bundle="${loc}" key="local.addBookPage.select.option.name.genreSelection" var="genreSelection" />
<fmt:message bundle="${loc}" key="local.addBookPage.select.option.name.fiction" var="fiction" />
<fmt:message bundle="${loc}" key="local.addBookPage.select.option.name.technical" var="technical" />
<fmt:message bundle="${loc}" key="local.button.name.addBook" var="addBookButton" />
<fmt:message bundle="${loc}" key="local.button.name.addMoreAuthor" var="addMoreAuthorButton" />
<fmt:message bundle="${loc}" key="local.button.name.remove" var="removeButton" />
<fmt:message bundle="${loc}" key="local.button.name.goToAccount" var="goToAccount_button" />
<fmt:message bundle="${loc}" key="local.button.name.logOut" var="logOutButton" />

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>


</head>
<body>

	<table>
		<tr>
			<th>
				<form action="FrontController" method="get">
					<input type="hidden" name="command" value="changeLocal" />
					<input type="hidden" name="local" value="ru" /> 
					<input type="submit" value="${ru_button}" />
				</form>
			</th>
			<th>
				<form action="FrontController" method="get">
					<input type="hidden" name="command" value="changeLocal" />
					<input type="hidden" name="local" value="en" /> 
					<input type="submit" value="${en_button}" /><br />
				</form>
			</th>
	</table>

	<c:out value="${message}" />
	
	<form action="FrontController" method="post">
		<input type="hidden" name="command" value="addBook"/>
		
		<b><c:out value="${author}" /></b>:<br/>
    	<div id="DynamicFieldsContainer">
      		<div class="DynamicField">
          		<label>
            		<c:out value="${authorSurname}" />:<input type="text" name="surnames[]" value="" required/>
          		</label>
          		<label>
            		<c:out value="${authorName}" />:<input type="text" name="names[]" value="" required/>
          		</label>
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
	
	<br>
	
	<form action="FrontController" method="post">
		<input type="hidden" name="command" value="goToAccount"/>
		<input type="submit" value="${goToAccount_button}">
	</form>
	
	<form action="FrontController" method="post">
		<input type="hidden" name="command" value="logOutCommand"/>
		<input type="submit" value="${logOutButton}">
	</form>

	<script>   
		$('#addDynamicFieldButton').click(function(event) {
	    	addDynamicField();
	    	return false;
		 });
		function addDynamicField() {
	    	var div = $('<div/>', {
	        	'class' : 'DynamicField'
	    	}).appendTo($('#DynamicFieldsContainer'));
	    	var label = $('<label/>').html('<c:out value="${authorSurname}" />' + ":").appendTo(div);
	    	var input = $('<input/>', {
	        	name : 'surnames[]',
	        	type : 'text',
	        	required : 'required'
	    	}).appendTo(label);
	    	var label = $('<label/>').html(" " + '<c:out value="${authorName}" />' + ":").appendTo(div);
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
		//Удаление поля поля
		$('.DeleteDynamicField').click(function(event) {
	                $(this).parent().remove();
	                return false;
	            });
        
	</script>

</body>
</html>