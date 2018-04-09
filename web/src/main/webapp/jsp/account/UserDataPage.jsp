<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel="stylesheet" href="total.css">
	<link rel="stylesheet" href="user_data.css">
<title>User profile</title>

	<fmt:setLocale value="${sessionScope.local}" />
	<fmt:setBundle basename="localization.local" var="loc" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />
	<fmt:message bundle="${loc}" key="local.field.name.login" var="login" />
	<fmt:message bundle="${loc}" key="local.field.name.password" var="password" />
	<fmt:message bundle="${loc}" key="local.field.name.name" var="name" />
	<fmt:message bundle="${loc}" key="local.field.name.surname" var="surname" />
	<fmt:message bundle="${loc}" key="local.field.name.email" var="email" />
	<fmt:message bundle="${loc}" key="local.userDataPage.button.name.change" var="changeButton" />
	<fmt:message bundle="${loc}" key="local.userDataPage.message.oldPassword" var="oldPassword" />
	<fmt:message bundle="${loc}" key="local.userDataPage.message.newPassword" var="newPassword" />
	<fmt:message bundle="${loc}" key="local.userDataPage.message.confirmPassword" var="confirmPassword" />
	<fmt:message bundle="${loc}" key="local.userDataPage.message.newName" var="newName" />
	<fmt:message bundle="${loc}" key="local.userDataPage.message.newSurname" var="newSurname" />
	<fmt:message bundle="${loc}" key="local.userDataPage.message.newEmail" var="newEmail" />
	<fmt:message bundle="${loc}" key="local.button.name.goToAccount" var="goToAccount_button" />
	<fmt:message bundle="${loc}" key="local.button.name.logOut" var="logOutButton" />

	<c:set var="url" value="jsp/account/UserDataPage.jsp" scope="session"/>

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
				<c:out value="${message}" />
			</div>
			<div class="form-user-data">
				<form id="password" action="FrontController" method="post">
					<input type="hidden" name="command" value="changeUserData">
					<input type="hidden" name="userParameter" value="password">
				</form>

				<form id="name" action="FrontController" method="post">
					<input type="hidden" name="command" value="changeUserData"/>
					<input type="hidden" name="userParameter" value="name"/>
				</form>

				<form id="surname" action="FrontController" method="post">
					<input type="hidden" name="command" value="changeUserData"/>
					<input type="hidden" name="userParameter" value="surname"/>
				</form>

				<form id="email" action="FrontController" method="post">
					<input type="hidden" name="command" value="changeUserData"/>
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