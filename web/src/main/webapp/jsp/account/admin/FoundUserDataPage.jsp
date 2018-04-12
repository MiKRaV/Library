<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel="stylesheet" href="total.css">
	<link rel="stylesheet" href="table.css">
	<title>User data</title>

	<fmt:setLocale value="${sessionScope.local}" />
	<fmt:setBundle basename="localization.local" var="loc" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />
	<fmt:message bundle="${loc}" key="local.field.name.login" var="login" />
	<fmt:message bundle="${loc}" key="local.field.name.name" var="name" />
	<fmt:message bundle="${loc}" key="local.field.name.surname" var="surname" />
	<fmt:message bundle="${loc}" key="local.field.name.email" var="email" />
	<fmt:message bundle="${loc}" key="local.field.name.userType" var="userType" />
	<fmt:message bundle="${loc}" key="local.field.name.userStatus" var="userStatus" />
	<fmt:message bundle="${loc}" key="local.field.name.actions" var="actions" />
	<fmt:message bundle="${loc}" key="local.button.name.blockUnlock" var="blockUnlockButton" />
	<fmt:message bundle="${loc}" key="local.removeUser.button.name.remove" var="remove" />
	<fmt:message bundle="${loc}" key="local.button.name.goToAccount" var="goToAccount_button" />
	<fmt:message bundle="${loc}" key="local.button.name.logOut" var="logOutButton" />
	<fmt:message bundle="${loc}" key="local.button.name.editData" var="editDataButton" />

	<c:set var="url" value="jsp/account/admin/FoundUserDataPage.jsp" scope="session"/>

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
			<div class="table-data-container">
				<div class="table-data">
					<table>
						<tr class="row-header">
							<th><c:out value="${login}" /></th>
							<th><c:out value="${name}" /></th>
							<th><c:out value="${surname}" /></th>
							<th><c:out value="${email}" /></th>
							<th><c:out value="${userType}" /></th>
							<th><c:out value="${userStatus}" /></th>
							<th><c:out value="${actions}" /></th>
						</tr>
						<tr class="row-data">
							<td>${foundUser.login}</td>
							<td>${foundUser.userData.name}</td>
							<td>${foundUser.userData.surname}</td>
							<td>${foundUser.userData.email}</td>
							<td>${foundUser.type}</td>
							<td>${foundUser.status}</td>
							<td class="cell-actions">
								<form action="FrontController" method="post">
									<input type="hidden" name="command" value="blockUnlockUser"/>
									<input type="submit" value="${blockUnlockButton}">
								</form>
								<form action="FrontController" method="post">
									<input type="hidden" name="command" value="removeUser"/>
									<input type="hidden" name="login" value="${foundUser.login}">
									<input type="submit" value="${remove}">
								</form>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>