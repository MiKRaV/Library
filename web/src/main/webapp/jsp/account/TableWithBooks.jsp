<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />
<fmt:message bundle="${loc}" key="local.button.name.basket" var="basket_button" />
<fmt:message bundle="${loc}" key="local.tableWithAllBooks.message.bookCount" var="bookCount" />
<fmt:message bundle="${loc}" key="local.tableWithAllBooks.column.name.title" var="title" />
<fmt:message bundle="${loc}" key="local.tableWithAllBooks.column.name.authors" var="authors" />
<fmt:message bundle="${loc}" key="local.tableWithAllBooks.column.name.genre" var="genre" />
<fmt:message bundle="${loc}" key="local.field.name.actions" var="actions" />
<fmt:message bundle="${loc}" key="local.button.name.apply" var="apply_button" />
<fmt:message bundle="${loc}" key="local.button.name.addToBasket" var="addToBasket_button" />
<fmt:message bundle="${loc}" key="local.button.name.edit" var="edit_button" />
<fmt:message bundle="${loc}" key="local.button.name.previous" var="previous_button" />
<fmt:message bundle="${loc}" key="local.button.name.next" var="next_button" />
<fmt:message bundle="${loc}" key="local.button.name.goToAccount" var="goToAccount_button" />
<fmt:message bundle="${loc}" key="local.button.name.logOut" var="logOutButton" />

</head>
<body>

<c:set var="url" value="jsp/account/TableWithBooks.jsp" scope="session"/>
	<table border="0">
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
	<c:out value="${errorMessage}" />

	<c:if test="${user.type eq 'reader'}">
		<form action="FrontController" method="post">
			<input type="hidden" name="command" value="goToPage"/>
			<input type="hidden" name="goToPage" value="jsp/account/reader/Basket.jsp"/>
			<input type="submit" value="${basket_button}: ${user.basket.size()}">
		</form>
	</c:if>

	<form action="FrontController" method="post">
		<c:out value="${bookCount}" />:
		<input type="hidden" name="command" value="getAllBooks">
		<select name="pageSize" required>
			<option value="1">1</option>
			<option value="2">2</option>
			<option value="5">5</option>
			<option selected value="10">10</option>
			<option value="20">20</option>
		</select>
		<input type="submit" value="${apply_button}">
	</form>

	<table border="1">
		<tr>
			<th><c:out value="${title}" /></th>
			<th><c:out value="${authors}" /></th>
			<th><c:out value="${genre}" /></th>
			<th><c:out value="${actions}" /></th>
		</tr>
		<c:set var="bookList" scope="session" value="${bookList}"/>
		<c:forEach var="book" items="${bookList}">
  		<tr>
    		<td>${book.title}</td>
    		<td>
    			<c:forEach var="author" items="${book.authors}">
    				${author.surname} 
    				${author.name}<br>
    			</c:forEach>
    		</td>
    		<td>${book.genre}</td>
			<td>
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

	<table border="0">
		<tr>
			<%--For displaying Previous link except for the 1st page --%>
			<c:if test="${currentPage != 1}">
				<td>
					<form action="FrontController" method="post">
						<input type="hidden" name="command" value="getAllBooks"/>
						<input type="hidden" name="pageSize" value="${pageSize}"/>
						<input type="hidden" name="page" value="${currentPage - 1}">
						<input type="submit" value="${previous_button}">
					</form>
				</td>
			</c:if>
			<td>
				<c:out value="${currentPage}" />
			</td>
			<%--For displaying Next link --%>
			<c:if test="${currentPage lt pageCount}">
				<td>
					<form action="FrontController" method="post">
						<input type="hidden" name="command" value="getAllBooks"/>
                        <input type="hidden" name="pageSize" value="${pageSize}"/>
						<input type="hidden" name="page" value="${currentPage + 1}">
						<input type="submit" value="${next_button}">
					</form>
				</td>
			</c:if>
		</tr>
	</table>
	
	<form action="FrontController" method="post">
		<input type="hidden" name="command" value="goToAccount"/>
		<input type="submit" value="${goToAccount_button}">
	</form>
	
	<form action="FrontController" method="post">
		<input type="hidden" name="command" value="logOutCommand"/>
		<input type="submit" value="${logOutButton}">
	</form>
</body>
</html>