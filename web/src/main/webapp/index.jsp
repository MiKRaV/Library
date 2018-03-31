<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel="stylesheet" href="stylesheet/index.css">
<title>Library</title>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />
<fmt:message bundle="${loc}" key="local.index.button.name.logination" var="log_button" />
<fmt:message bundle="${loc}" key="local.index.button.name.registration" var="reg_button" />

</head>
<body>

	<c:set var="url" value="index.jsp" scope="session"/>

	<table border="0">
		<tr>
			<th>
				<form class="formLocal" action="FrontController" method="get">
					<input type="hidden" name="command" value="changeLocal" />
					<input type="hidden" name="local" value="ru" /> 
					<input type="submit" value="${ru_button}" />
				</form>
			</th>
			<th>
				<form class="formLocal" action="FrontController" method="get">
					<input type="hidden" name="command" value="changeLocal" />
					<input type="hidden" name="local" value="en" /> 
					<input type="submit" value="${en_button}" /><br />
				</form>
			</th>
	</table>

	<form action="FrontController" method="post">
		<input type="hidden" name="command" value="goToPage"/>
		<input type="hidden" name="goToPage" value="jsp/logination.jsp"/>
		<input type="submit" value="${log_button}">
	</form>
	
	<form action="FrontController" method="post">
		<input type="hidden" name="command" value="goToPage"/>
		<input type="hidden" name="goToPage" value="jsp/registration.jsp"/>
		<input type="submit" value="${reg_button}">
	</form>

</body>
</html>