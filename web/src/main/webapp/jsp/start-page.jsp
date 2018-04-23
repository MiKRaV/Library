<%@ page language="java" contentType="text/html; charset=utf-8"
		 pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel="stylesheet" href="<c:url value="/css/total.css"/>">
	<link rel="stylesheet" href="<c:url value="/css/login.css"/>">
	<title>Library</title>

	<spring:message code="local.locbutton.name.ru" var="ru_button"/>
	<spring:message code="local.locbutton.name.en" var="en_button"/>
	<spring:message code="local.field.name.login" var="login"/>
	<spring:message code="local.field.name.password" var="password"/>
	<spring:message code="local.logination.button.name.signIn" var="signIn_button"/>
	<spring:message code="local.index.button.name.registration" var="reg_button"/>

</head>
<body>
	<div class="header">
		<div class="layout-positioner">
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

			<c:out value="${errorMessage}" />

			<div class="formLogin">
				<form id="formLogin" action="${pageContext.request.contextPath}/main-page" method="post">
					<label for="login"><c:out value="${login}" /></label>
					<input type="text" id="login" name="login" value=""/><br/>
					<label for="password"><c:out value="${password}" /></label>
					<input type="password" id="password" name="password" value=""/><br/>
				</form>
				<div class="table-login">
					<div class="row-login">
						<div class="cell-login">
							<input type="submit" value="${signIn_button}" form="formLogin">
						</div>
						<div class="cell-login registration">
							<form action="${pageContext.request.contextPath}/registration" method="get">
								<input type="hidden" name="command" value="goToPage"/>
								<input type="hidden" name="goToPage" value="jsp/registration.jsp"/>
								<input type="submit" value="${reg_button}">
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="footer">
		<div class="layout-positioner">

		</div>
	</div>
</body>
</html>