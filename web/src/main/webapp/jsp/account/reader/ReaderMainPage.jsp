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
<fmt:message bundle="${loc}" key="local.readerMainPage.message" var="message" />
<fmt:message bundle="${loc}" key="local.button.name.basket" var="basket_button" />
<fmt:message bundle="${loc}" key="local.button.name.orders" var="orders_button" />
<fmt:message bundle="${loc}" key="local.readerMainPage.button.name.getAllBooks" var="getAllBooksButton" />
<fmt:message bundle="${loc}" key="local.button.name.logOut" var="logOutButton" />
<fmt:message bundle="${loc}" key="local.button.name.editData" var="editDataButton" />

</head>

<body>

<c:set var="url" value="jsp/account/reader/ReaderMainPage.jsp" scope="session"/>

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

	<form action="FrontController" method="post">
		<input type="hidden" name="command" value="goToPage"/>
		<input type="hidden" name="goToPage" value="jsp/account/reader/Basket.jsp"/>
		<input type="submit" value="${basket_button}: ${user.basket.size()}">
	</form>

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
		<input type="hidden" name="command" value="getAllBooks"/>
		<input type="submit" value="${getAllBooksButton}">
	</form>
	
	<form action="FrontController" method="post">
		<input type="hidden" name="command" value="logOutCommand"/>
		<input type="submit" value="${logOutButton}">
	</form>
</body>
</html>