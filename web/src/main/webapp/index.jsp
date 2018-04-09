<%@ page language="java" contentType="text/html; charset=utf-8"
		 pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel="stylesheet" href="total.css">
	<link rel="stylesheet" href="login.css">
	<title>Library</title>

	<fmt:setLocale value="${sessionScope.local}" />
	<fmt:setBundle basename="localization.local" var="loc" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />
	<fmt:message bundle="${loc}" key="local.field.name.login" var="login" />
	<fmt:message bundle="${loc}" key="local.field.name.password" var="password" />
	<fmt:message bundle="${loc}" key="local.logination.button.name.signIn" var="signIn_button" />
	<fmt:message bundle="${loc}" key="local.index.button.name.registration" var="reg_button" />

	<c:set var="url" value="index.jsp" scope="session"/>

</head>
<body>
	<div class="header">
		<div class="layout-positioner">
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

			<c:out value="${errorMessage}" />

			<div class="formLogin">
				<form id="formLogin" action="FrontController" method="post">
					<input type="hidden" name="command" value="logination"/>
					<label for="login"><c:out value="${login}" /></label>
					<input type="text" id="login" name="login" value=""/><br/>
					<label for="password"><c:out value="${password}" /></label>
					<input type="password" id="password" name="password" value=""/><br/>
				</form>
				<div class="table-login">
					<div class="row-login">
						<div class="cell-login">
							<input type="submit" value="${signIn_button}" form="formLogin">
						</div>
						<div class="cell-login registration">
							<form action="FrontController" method="post">
								<input type="hidden" name="command" value="goToPage"/>
								<input type="hidden" name="goToPage" value="jsp/registration.jsp"/>
								<input type="submit" value="${reg_button}">
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="footer">
		<div class="layout-positioner">

		</div>
	</div>
</body>
</html>