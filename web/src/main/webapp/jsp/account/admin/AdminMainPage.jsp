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
	<title>Admin main page</title>

	<spring:message code="local.locbutton.name.ru" var="ru_button"/>
	<spring:message code="local.locbutton.name.en" var="en_button"/>
	<spring:message code="local.adminMainPage.message" var="message"/>
	<spring:message code="local.adminMainPage.button.name.getAllUsers" var="getAllUsersButton"/>
	<spring:message code="local.adminMainPage.button.name.findUser" var="findUserButton"/>
	<spring:message code="local.readerMainPage.button.name.getAllBooks" var="getAllBooksButton"/>
	<spring:message code="local.button.name.addBook" var="addBookButton"/>
	<spring:message code="local.button.name.logOut" var="logOutButton"/>
	<spring:message code="local.button.name.editData" var="editDataButton"/>
	<spring:message code="local.button.name.orders" var="orders_button"/>

	<c:set var="url" value="jsp/account/admin/AdminMainPage.jsp" scope="session"/>

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

			<c:out value="${errorMessage}" />

			<div class="action-menu">
				<div class="action-menu-item">
					<form action="${pageContext.request.contextPath}/orders-list" method="get">
						<input type="submit" value="${orders_button}">
					</form>
				</div>
				<div class="action-menu-item">
					<form action="${pageContext.request.contextPath}/search-user" method="get">
						<input type="submit" value="${findUserButton}">
					</form>
				</div>
				<div class="action-menu-item">
					<form action="${pageContext.request.contextPath}/users-list" method="get">
						<input type="submit" value="${getAllUsersButton}">
					</form>
				</div>
				<div class="action-menu-item">
					<form action="${pageContext.request.contextPath}/books-list" method="get">
						<input type="submit" value="${getAllBooksButton}">
					</form>
				</div>
				<div class="action-menu-item">
					<form action="${pageContext.request.contextPath}/adding-book" method="get">
						<input type="submit" value="${addBookButton}">
					</form>
				</div>
			</div>
		</div>
	</div>

</body>
</html>