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
<fmt:message bundle="${loc}" key="local.adminMainPage.message" var="message" />
<fmt:message bundle="${loc}" key="local.adminMainPage.button.name.getAllUsers" var="getAllUsersButton" />
<fmt:message bundle="${loc}" key="local.adminMainPage.button.name.removingUser" var="removingUserButton" />
<fmt:message bundle="${loc}" key="local.adminMainPage.button.name.findUser" var="findUserButton" />
<fmt:message bundle="${loc}" key="local.readerMainPage.button.name.getAllBooks" var="getAllBooksButton" />
<fmt:message bundle="${loc}" key="local.button.name.addBook" var="addBookButton" />
<fmt:message bundle="${loc}" key="local.button.name.logOut" var="logOutButton" />
<fmt:message bundle="${loc}" key="local.button.name.editData" var="editDataButton" />
<fmt:message bundle="${loc}" key="local.button.name.orders" var="orders_button" />

</head>
<body>

<c:set var="url" value="jsp/account/admin/AdminMainPage.jsp" scope="session"/>

	<c:out value="${message}" />
	
	<c:out value="${errorMessage}" />
	
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

	<form action="FrontController" method="post">
		<input type="hidden" name="command" value="getOrders"/>
		<input type="submit" value="${orders_button}">
	</form>

	<form action="FrontController" method="post">
		<input type="hidden" name="command" value="goToPage"/>
		<input type="hidden" name="goToPage" value="jsp/account/UserDataPage.jsp"/>
		<input type="submit" value="${editDataButton}">
	</form>
	
	<form action="FrontController" method="post">
		<input type="hidden" name="command" value="goToPage"/>
		<input type="hidden" name="goToPage" value="jsp/account/admin/SearchUserPage.jsp"/>
		<input type="submit" value="${findUserButton}">
	</form>
	
	<form action="FrontController" method="post">
		<input type="hidden" name="command" value="getAllUsers"/>
		<input type="submit" value="${getAllUsersButton}">
	</form>
	
	<form action="FrontController" method="post">
		<input type="hidden" name="command" value="goToPage"/>
		<input type="hidden" name="goToPage" value="jsp/account/admin/UserRemovePage.jsp"/>
		<input type="submit" value="${removingUserButton}">
	</form>
	
	<form action="FrontController" method="post">
		<input type="hidden" name="command" value="getAllBooks"/>
		<input type="submit" value="${getAllBooksButton}">
	</form>
	
	<form action="FrontController" method="post">
		<input type="hidden" name="command" value="goToAddingBookPage"/>
		<input type="submit" value="${addBookButton}">
	</form>
	
	<form action="FrontController" method="post">
		<input type="hidden" name="command" value="logOutCommand"/>
		<input type="submit" value="${logOutButton}">
	</form>
	
</body>
</html>