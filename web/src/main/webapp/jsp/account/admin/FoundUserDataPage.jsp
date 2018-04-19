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
	<title>User data</title>

	<fmt:setLocale value="${sessionScope.local}" />
	<fmt:setBundle basename="localization.local" var="loc" />
	<spring:message code="local.locbutton.name.ru" var="ru_button" />
	<spring:message code="local.locbutton.name.en" var="en_button" />
	<spring:message code="local.field.name.login" var="login" />
	<spring:message code="local.field.name.name" var="name" />
	<spring:message code="local.field.name.surname" var="surname" />
	<spring:message code="local.field.name.email" var="email" />
	<spring:message code="local.field.name.userType" var="userType" />
	<spring:message code="local.field.name.userStatus" var="userStatus" />
	<spring:message code="local.field.name.actions" var="actions" />
	<spring:message code="local.button.name.blockUnlock" var="blockUnlockButton" />
	<spring:message code="local.removeUser.button.name.remove" var="remove" />
	<spring:message code="local.button.name.goToAccount" var="goToAccount_button" />
	<spring:message code="local.button.name.logOut" var="logOutButton" />
	<spring:message code="local.button.name.editData" var="editDataButton" />

	<c:set var="url" value="jsp/account/admin/FoundUserDataPage.jsp" scope="session"/>

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
						<div class="cell-local">
							<a href="?lang=ru">${ru_button}</a>
						</div>
					</div>
					<div class="cell-local">
						<div class="cell-local">
							<a href="?lang=en">${en_button}</a>
						</div>
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
								<form action="${pageContext.request.contextPath}/found-user" method="post">
									<input type="hidden" name="command" value="blockUnlockUser"/>
									<input type="hidden" name="login" value="${foundUser.login}"/>
									<input type="submit" value="${blockUnlockButton}">
								</form>
								<form action="${pageContext.request.contextPath}/found-user" method="post">
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