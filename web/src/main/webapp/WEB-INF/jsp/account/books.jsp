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
	<link rel="stylesheet" href="<c:url value="/assets/css/table.css"/>">

	<spring:message code="local.tableWithAllBooks.message.bookCount" var="bookCount" />
	<spring:message code="local.tableWithAllBooks.column.name.bookID" var="bookID" />
	<spring:message code="local.tableWithAllBooks.column.name.title" var="title" />
	<spring:message code="local.tableWithAllBooks.column.name.authors" var="authors" />
	<spring:message code="local.tableWithAllBooks.column.name.genre" var="genre" />
	<spring:message code="local.field.name.actions" var="actions" />
	<spring:message code="local.button.name.apply" var="apply_button" />
	<spring:message code="local.button.name.addToBasket" var="addToBasket_button" />
	<spring:message code="local.button.name.edit" var="edit_button" />
	<spring:message code="local.button.name.previous" var="previous_button" />
	<spring:message code="local.button.name.next" var="next_button" />
    <spring:message code="local.message.bookUnavailable" var="book_unavailable" />
    <spring:message code="local.message.bookInBasket" var="book_in_basket" />
	<spring:message code="local.addBookPage.select.option.name.genreSelection" var="genreSelection" />
	<spring:message code="local.addBookPage.select.option.name.fiction" var="fiction" />
	<spring:message code="local.addBookPage.select.option.name.technical" var="technical" />
	<spring:message code="local.addBookPage.select.option.name.psychology" var="psychology" />

</head>
<body>
	<div class="container-content">
		<div class="layout-positioner">
			<div class="message">
				<c:out value="${message}" />
				<c:out value="${errorMessage}" />
			</div>
			<div class="table-data-container">
				<div class="select-genre">
					<form action="${pageContext.request.contextPath}/books-list" method="get">
						<c:out value="${genre}" />:
						<select name="genre" required>
							<option selected disabled><c:out value="${genreSelection}" /></option>
							<option value="FICTION"><c:out value="${fiction}" /></option>
							<option value="TECHNICAL"><c:out value="${technical}" /></option>
							<option value="PSYCHOLOGY"><c:out value="${psychology}" /></option>
						</select>
						<input type="submit" value="${apply_button}">
					</form>
				</div>
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
                        <input type="hidden" name="genre" value="${bookGenre}">
						<input type="submit" value="${apply_button}">
					</form>
				</div>
				<div class="table-data">
					<table>
						<tr class="row-header">
							<th class="cell-id"><c:out value="${bookID}" /></th>
							<th><c:out value="${title}" /></th>
							<th><c:out value="${authors}" /></th>
							<th><c:out value="${genre}" /></th>
							<th class="cell-actions"><c:out value="${actions}" /></th>
						</tr>
						<c:set var="bookList" scope="session" value="${bookList}"/>
						<c:forEach var="book" items="${bookList}">
							<tr class="row-data">
								<td class="cell-id"><c:out value="${book.id}" /></td>
								<td>${book.title}</td>
								<td>
									<c:forEach var="author" items="${book.authors}">
										${author.surname}
										${author.name}<br>
									</c:forEach>
								</td>
								<td>
									<c:set var="local" value="${pageContext.response.locale}"/>
									<c:if test="${local eq 'en'}">
										<c:out value="${book.genre.en}"/>
									</c:if>
									<c:if test="${local eq 'ru'}">
										<c:out value="${book.genre.ru}"/>
									</c:if>
								</td>
								<td class="cell-actions">
									<c:if test="${user.type eq 'ADMIN'}">
										<form action="FrontController" method="post">
											<input type="hidden" name="command" value=""/>
											<input type="submit" value="${edit_button}">
										</form>
									</c:if>
									<c:if test="${user.type eq 'READER'}">
										<c:if test="${book.status eq 'AVAILABLE'}">
                                            <c:if test="${book.user.id eq user.id}">
                                                <c:out value="${book_in_basket}"/>
                                            </c:if>
											<c:if test="${book.user.id ne user.id}">
												<form action="${pageContext.request.contextPath}/books-list" method="post">
													<input type="hidden" name="bookID" value="${book.id}">
													<input type="hidden" name="pageSize" value="${pageSize}"/>
													<input type="hidden" name="page" value="${currentPage}">
													<input type="hidden" name="pageCount" value="${pageCount}">
													<input type="hidden" name="genre" value="${bookGenre}">
													<input type="submit" value="${addToBasket_button}">
												</form>
											</c:if>
										</c:if>
                                        <c:if test="${book.status eq 'UNAVAILABLE'}">
                                            <c:out value="${book_unavailable}"/>
                                        </c:if>
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
                                        <input type="hidden" name="genre" value="${bookGenre}">
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
                                        <input type="hidden" name="genre" value="${bookGenre}">
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