<%@ page language="java" contentType="text/html; charset=utf-8"
    	 pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel="stylesheet" href="<c:url value="/assets/css/total.css"/>">
	<link rel="stylesheet" href="<c:url value="/assets/css/user_data.css"/>">

	<spring:message code="local.field.name.login" var="login" />
	<spring:message code="local.field.name.password" var="password" />
	<spring:message code="local.field.name.name" var="name" />
	<spring:message code="local.field.name.surname" var="surname" />
	<spring:message code="local.field.name.email" var="email" />
	<spring:message code="local.field.name.userType" var="userType" />
	<spring:message code="local.registration.radio.name.administrator" var="administrator" />
	<spring:message code="local.registration.radio.name.reader" var="reader" />
	<spring:message code="local.registration.button.name.toRegister" var="register_button" />

</head>
<body>
	<div class="container-content">
		<div class="layout-positioner">
			<div class="message">
				<c:out value="${errorMessage}" />
			</div>
			<div class="form-registration">
				<form action="${pageContext.request.contextPath}/registration" method="post">
					<input type="hidden" name="command" value="registration"/>
					<label for="login"><c:out value="${login}" /></label>
					<input type="text" id="login" name="login" value=""/>
					<label for="password"><c:out value="${password}" /></label>
					<input type="password" id="password" name="password" value=""/>
					<label for="name"><c:out value="${name}" /></label>
					<input type="text" id="name" name="name" value=""/>
					<label for="surname"><c:out value="${surname}" /></label>
					<input type="text" id="surname" name="surname" value=""/>
					<label for="email"><c:out value="${email}" /></label>
					<input type="text" id="email" name="email" value=""/>
					<label><c:out value="${userType}" /></label>
					<div class="radio-block">
						<input class="radio" type="radio" id="radio1" name="userType" value="admin">
						<label class="radio" for="radio1"><c:out value="${administrator}" /></label>
					</div>
					<div class="radio-block">
						<input class="radio" type="radio" id="radio2" name="userType" value="reader">
						<label class="radio" for="radio2"><c:out value="${reader}" /></label>
					</div>
					<input  type="submit" value="${register_button}">
				</form>
			</div>
		</div>
	</div>
</body>
</html>