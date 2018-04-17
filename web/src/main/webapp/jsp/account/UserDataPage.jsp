<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>    
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel="stylesheet" href="<c:url value="/css/total.css"/>">
	<link rel="stylesheet" href="<c:url value="/css/user_data.css"/>">
	<title>User profile</title>

	<spring:message code="local.locbutton.name.ru" var="ru_button"/>
	<spring:message code="local.locbutton.name.en" var="en_button"/>
	<spring:message code="local.field.name.login" var="login" />
	<spring:message code="local.field.name.password" var="password" />
	<spring:message code="local.field.name.name" var="name" />
	<spring:message code="local.field.name.surname" var="surname" />
	<spring:message code="local.field.name.email" var="email" />
	<spring:message code="local.userDataPage.button.name.change" var="changeButton" />
	<spring:message code="local.userDataPage.message.oldPassword" var="oldPassword" />
	<spring:message code="local.userDataPage.message.newPassword" var="newPassword" />
	<spring:message code="local.userDataPage.message.confirmPassword" var="confirmPassword" />
	<spring:message code="local.userDataPage.message.newName" var="newName" />
	<spring:message code="local.userDataPage.message.newSurname" var="newSurname" />
	<spring:message code="local.userDataPage.message.newEmail" var="newEmail" />
	<spring:message code="local.button.name.goToAccount" var="goToAccount_button" />
	<spring:message code="local.button.name.logOut" var="logOutButton" />

	<c:set var="url" value="jsp/account/UserDataPage.jsp" scope="session"/>

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
			</div>
			<div class="form-user-data">
				<form id="password" action="${pageContext.request.contextPath}/user-data" method="post">
					<input type="hidden" name="userParameter" value="password">
				</form>

				<form id="name" action="${pageContext.request.contextPath}/user-data" method="post">
					<input type="hidden" name="userParameter" value="name"/>
				</form>

				<form id="surname" action="${pageContext.request.contextPath}/user-data" method="post">
					<input type="hidden" name="userParameter" value="surname"/>
				</form>

				<form id="email" action="${pageContext.request.contextPath}/user-data" method="post">
					<input type="hidden" name="userParameter" value="email"/>
				</form>
				<div class="table-user-data">
					<div class="row-user-data">
						<div class="cell-user-data">
							<b><c:out value="${login}" /></b>:
						</div>
						<div class="cell-user-data">
							<c:out value="${user.login}" />
						</div>
						<div class="cell-user-data"></div>
					</div>
					<div class="row-user-data">
						<div class="cell-user-data">
							<br><b><c:out value="${password}" /></b>:
						</div>
						<div class="cell-user-data"></div>
						<div class="cell-user-data"></div>
					</div>
					<div class="row-user-data">
						<div class="cell-user-data">
							<label for="oldPassword"><c:out value="${oldPassword}" />:<label/>
						</div>
						<div class="cell-user-data">
							<input type="password" id="oldPassword" name="oldPassword" value="" form="password" >
						</div>
						<div class="cell-user-data"></div>
					</div>
					<div class="row-user-data">
						<div class="cell-user-data">
							<label for="newPassword"> <c:out value="${newPassword}"/>:<label/>
						</div>
						<div class="cell-user-data">
							<input type="password" id="newPassword" name="newPassword" value="" form="password" >
						</div>
						<div class="cell-user-data"></div>
					</div>
					<div class="row-user-data">
						<div class="cell-user-data">
							<label for="confirmPassword"><c:out value="${confirmPassword}"/>:</label>
						</div>
						<div class="cell-user-data">
							<input type="password" id="confirmPassword" name="confirmPassword" value="" form="password" >
						</div>
						<div class="cell-user-data">
							<input type="submit" value="${changeButton}" form="password" >
						</div>
					</div>
					<div class="row-user-data">
						<div class="cell-user-data">
							<br><b><c:out value="${name}" /></b>:
						</div>
						<div class="cell-user-data">
							<br><c:out value="${user.userData.name}" />
						</div>
						<div class="cell-user-data"></div>
					</div>
					<div class="row-user-data">
						<div class="cell-user-data">
							<label for="newName"> <c:out value="${newName}" />:</label>
						</div>
						<div class="cell-user-data">
							<input type="text" id="newName" name="newName" value="" form="name" >
						</div>
						<div class="cell-user-data">
							<input type="submit" value="${changeButton}" form="name" >
						</div>
					</div>
					<div class="row-user-data">
						<div class="cell-user-data">
							<br><b><c:out value="${surname}" /></b>:
						</div>
						<div class="cell-user-data">
							<br><c:out value="${user.userData.surname}" />
						</div>
						<div class="cell-user-data"></div>
					</div>
					<div class="row-user-data">
						<div class="cell-user-data">
							<label for="newSurname"><c:out value="${newSurname}" />:</label>
						</div>
						<div class="cell-user-data">
							<input type="text" id="newSurname" name="newSurname" value="" form="surname" >
						</div>
						<div class="cell-user-data">
							<input type="submit" value="${changeButton}" form="surname" >
						</div>
					</div>
					<div class="row-user-data">
						<div class="cell-user-data">
							<br><b><c:out value="${email}" /></b>:
						</div>
						<div class="cell-user-data">
							<br><c:out value="${user.userData.email}" />
						</div>
						<div class="cell-user-data"></div>
					</div>
					<div class="row-user-data">
						<div class="cell-user-data">
							<label for="newEmail"><c:out value="${newEmail}" />:</label>
						</div>
						<div class="cell-user-data">
							<input type="text" id="newEmail" name="newEmail" value="" form="email" >
						</div>
						<div class="cell-user-data">
							<input type="submit" value="${changeButton}" form="email" >
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>