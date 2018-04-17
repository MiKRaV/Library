<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel="stylesheet" href="../../css/total.css">
	<title>Добро пожаловть!</title>

	<fmt:setLocale value="${sessionScope.local}" />
	<fmt:setBundle basename="localization.local" var="loc" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />
	<fmt:message bundle="${loc}" key="local.loginationMessage.message" var="message" />
	<fmt:message bundle="${loc}" key="local.button.name.goToAccount" var="goToAccount_button" />

	<c:set var="url" value="jsp/account/LoginationMessage.jsp" scope="session"/>

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

			<c:out value="${message}" />

			<form action="FrontController" method="post">
				<input type="hidden" name="command" value="goToAccount"/>
				<input type="submit" value="${goToAccount_button}">
			</form>

		</div>
	</div>
	</div>
	<div class="footer">
		<div class="layout-positioner">

		</div>
	</div>
</body>
</html>