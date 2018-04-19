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
	<link rel="stylesheet" href="<c:url value="/css/table.css"/>">
	<title>Books</title>

	<spring:message code="local.locbutton.name.ru" var="ru_button" />
	<spring:message code="local.locbutton.name.en" var="en_button" />
	<spring:message code="local.button.name.basket" var="basket_button" />
	<spring:message code="local.button.name.orders" var="orders_button" />
	<spring:message code="local.tableWithAllBooks.message.bookCount" var="bookCount" />
	<spring:message code="local.tableWithAllBooks.column.name.title" var="title" />
	<spring:message code="local.tableWithAllBooks.column.name.authors" var="authors" />
	<spring:message code="local.tableWithAllBooks.column.name.genre" var="genre" />
	<spring:message code="local.field.name.actions" var="actions" />
	<spring:message code="local.button.name.apply" var="apply_button" />
	<spring:message code="local.button.name.addToBasket" var="addToBasket_button" />
	<spring:message code="local.button.name.edit" var="edit_button" />
	<spring:message code="local.button.name.previous" var="previous_button" />
	<spring:message code="local.button.name.next" var="next_button" />
	<spring:message code="local.button.name.goToAccount" var="goToAccount_button" />
	<spring:message code="local.button.name.logOut" var="logOutButton" />
	<spring:message code="local.button.name.editData" var="editDataButton" />

	<c:set var="url" value="jsp/account/TableWithBooks.jsp" scope="session"/>
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
							<form action="FrontController" method="post">
								<input type="hidden" name="command" value="goToPage"/>
								<input type="hidden" name="goToPage" value="jsp/account/reader/Basket.jsp"/>
								<input type="submit" value="${basket_button}: ${user.basket.size()}">
							</form>
						</div>
						<div class="cell-main-menu">
							<form action="FrontController" method="post">
								<input type="hidden" name="command" value="getOrders"/>
								<input type="submit" value="${orders_button}">
							</form>
						</div>
					</c:if>
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
				<c:out value="${errorMessage}" />
			</div>
			<div class="table-data-container">
				<div class="select-page-size">
					<form action="${pageContext.request.contextPath}/books-list" method="get">
						<c:out value="${bookCount}" />:
						<select name="pageSize" required>
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="5">5</option>
							<option selected value="10">10</option>
							<option value="20">20</option>
						</select>
						<input type="submit" value="${apply_button}">
					</form>
				</div>
				<div class="table-data">
					<table>
						<tr class="row-header">
							<th><c:out value="${title}" /></th>
							<th><c:out value="${authors}" /></th>
							<th><c:out value="${genre}" /></th>
							<th class="cell-actions"><c:out value="${actions}" /></th>
						</tr>
						<c:set var="bookList" scope="session" value="${bookList}"/>
						<c:forEach var="book" items="${bookList}">
							<tr class="row-data">
								<td>${book.title}</td>
								<td>
									<c:forEach var="author" items="${book.authors}">
										${author.surname}
										${author.name}<br>
									</c:forEach>
								</td>
								<td>${book.genre}</td>
								<td class="cell-actions">
									<c:if test="${user.type eq 'admin'}">
										<form action="FrontController" method="post">
											<input type="hidden" name="command" value=""/>
											<input type="submit" value="${edit_button}">
										</form>
									</c:if>
									<c:if test="${user.type eq 'reader'}">
										<form action="FrontController" method="post">
											<input type="hidden" name="command" value="addBookToBasket"/>
											<input type="hidden" name="bookID" value="${book.id}">
											<input type="hidden" name="pageSize" value="${pageSize}"/>
											<input type="hidden" name="page" value="${currentPage}">
											<input type="hidden" name="pageCount" value="${pageCount}">
											<input type="submit" value="${addToBasket_button}">
										</form>
									</c:if>
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
				<div class="table-pagination">
					<div class="row-pagination">
						<div class="cell-pagination">
							<%--For displaying Previous link except for the 1st page --%>
							<c:if test="${currentPage gt 1}">
								<td>
									<form action="${pageContext.request.contextPath}/books-list" method="get">
										<input type="hidden" name="pageSize" value="${pageSize}"/>
										<input type="hidden" name="page" value="${currentPage - 1}">
										<input type="submit" value="${previous_button}">
									</form>
								</td>
							</c:if>
						</div>
						<div class="cell-pagination">
							<c:out value="${currentPage}" />
						</div>
						<div class="cell-pagination">
							<%--For displaying Next link --%>
							<c:if test="${currentPage lt pageCount}">
								<td>
									<form action="${pageContext.request.contextPath}/books-list" method="get">
										<input type="hidden" name="pageSize" value="${pageSize}"/>
										<input type="hidden" name="page" value="${currentPage + 1}">
										<input type="submit" value="${next_button}">
									</form>
								</td>
							</c:if>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>