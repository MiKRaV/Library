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
	<title>Reader main page</title>

	<spring:message code="local.locbutton.name.ru" var="ru_button" />
	<spring:message code="local.locbutton.name.en" var="en_button" />
	<spring:message code="local.readerMainPage.message" var="message" />
	<spring:message code="local.button.name.basket" var="basket_button" />
	<spring:message code="local.button.name.orders" var="orders_button" />
	<spring:message code="local.readerMainPage.button.name.getAllBooks" var="getAllBooksButton" />
	<spring:message code="local.button.name.logOut" var="logOutButton" />
	<spring:message code="local.button.name.editData" var="editDataButton" />

	<c:set var="url" value="jsp/account/reader/ReaderMainPage.jsp" scope="session"/>
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
					<c:if test="${user.type eq 'reader'}">
						<div class="cell-main-menu">
							<form action="${pageContext.request.contextPath}/basket" method="get">
								<input type="submit" value="${basket_button}: ${user.basket.size()}">
							</form>
						</div>
						<div class="cell-main-menu">
							<form action="${pageContext.request.contextPath}/orders-list" method="get">
								<input type="submit" value="${orders_button}">
							</form>
						</div>
					</c:if>
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

			<div class="action-menu">
				<div class="action-menu-item">
					<form action="${pageContext.request.contextPath}/books-list" method="get">
						<input type="submit" value="${getAllBooksButton}">
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>