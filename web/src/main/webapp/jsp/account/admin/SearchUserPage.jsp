<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel="stylesheet" href="../../../css/total.css">
	<link rel="stylesheet" href="../../../css/user_data.css">
<title>User search</title>

	<fmt:setLocale value="${sessionScope.local}" />
	<fmt:setBundle basename="localization.local" var="loc" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />
	<fmt:message bundle="${loc}" key="local.field.name.login" var="login" />
	<fmt:message bundle="${loc}" key="local.searchUserPage.button.name.find" var="findUserButton" />
	<fmt:message bundle="${loc}" key="local.button.name.goToAccount" var="goToAccount_button" />
	<fmt:message bundle="${loc}" key="local.button.name.logOut" var="logOutButton" />
	<fmt:message bundle="${loc}" key="local.button.name.editData" var="editDataButton" />

	<c:set var="url" value="jsp/account/admin/SearchUserPage.jsp" scope="session"/>

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
					<div class="cell-main-menu">
						<form action="FrontController" method="post">
							<input type="hidden" name="command" value="goToAccount"/>
							<input type="submit" value="${goToAccount_button}">
						</form>
					</div>
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
				<c:out value="${errorMessage}" />
			</div>
		</div>
	</div>
	<div class="form-user-search">
		<form action="FrontController" method="post">
			<input type="hidden" name="command" value="findUser"/>
			<label for="login"><c:out value="${login}" /></label>
			<input type="text" id="login" name="login" value=""/>
			<input type="submit" value="${findUserButton}">
		</form>
	</div>

</body>
</html>