<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel="stylesheet" href="total.css">
	<title>Reader main page</title>

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

	<c:set var="url" value="jsp/account/reader/ReaderMainPage.jsp" scope="session"/>
</head>

<body>
	<div class="header">
		<div class="layout-positioner">
			<div class="table-main-menu">
				<div class="row-main-menu">
					<div class="cell-main-menu">
						<form action="FrontController" method="post">
							<input type="hidden" name="command" value="logOutCommand"/>
							<input type="submit" value="${logOutButton}">
						</form>
					</div>
					<div class="cell-main-menu">
						<form action="FrontController" method="post">
							<input type="hidden" name="command" value="goToPage"/>
							<input type="hidden" name="goToPage" value="jsp/account/UserDataPage.jsp"/>
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
				</div>
			</div>

			<div class="table-local">
				<div class="row-local">
					<div class="cell-local">
						<form action="FrontController" method="get">
							<input type="hidden" name="command" value="changeLocal" />
							<input type="hidden" name="local" value="ru" />
							<input type="submit" value="${ru_button}" />
						</form>
					</div>
					<div class="cell-local">
						<form action="FrontController" method="get">
							<input type="hidden" name="command" value="changeLocal" />
							<input type="hidden" name="local" value="en" />
							<input type="submit" value="${en_button}" /><br />
						</form>
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
					<form action="FrontController" method="post">
						<input type="hidden" name="command" value="getAllBooks"/>
						<input type="submit" value="${getAllBooksButton}">
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>