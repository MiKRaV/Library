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
	<link rel="stylesheet" href="<c:url value="/css/user_data.css"/>">
<title>User search</title>

	<spring:message code="local.locbutton.name.ru" var="ru_button" />
	<spring:message code="local.locbutton.name.en" var="en_button" />
	<spring:message code="local.field.name.login" var="login" />
	<spring:message code="local.searchUserPage.button.name.find" var="findUserButton" />
	<spring:message code="local.button.name.goToAccount" var="goToAccount_button" />
	<spring:message code="local.button.name.logOut" var="logOutButton" />
	<spring:message code="local.button.name.editData" var="editDataButton" />

	<c:set var="url" value="jsp/account/admin/SearchUserPage.jsp" scope="session"/>

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
						<div class="cell-local">
							<a href="?lang=ru">${ru_button}</a>
						</div>
					</div>
					<div class="cell-local">
						<div class="cell-local">
							<a href="?lang=en">${en_button}</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="container-content">
		<div class="layout-positioner">
			<div class="message">
				<c:out value="${errorMessage}" />
			</div>
		</div>
	</div>
	<div class="form-user-search">
		<form action="${pageContext.request.contextPath}/found-user" method="post">
			<label for="login"><c:out value="${login}" /></label>
			<input type="text" id="login" name="login" value=""/>
			<input type="submit" value="${findUserButton}">
		</form>
	</div>

</body>
</html>