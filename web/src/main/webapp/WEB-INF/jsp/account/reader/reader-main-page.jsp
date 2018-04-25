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

	<spring:message code="local.readerMainPage.message" var="message" />
	<spring:message code="local.readerMainPage.button.name.getAllBooks" var="getAllBooksButton" />
	<spring:message code="local.button.name.myBooks" var="my_books_button" />

</head>

<body>
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
				<div class="action-menu-item">
					<form action="${pageContext.request.contextPath}/reader-books" method="get">
						<input type="submit" value="${my_books_button}">
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>