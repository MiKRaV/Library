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
<fmt:message bundle="${loc}" key="local.field.name.login" var="login" />
<fmt:message bundle="${loc}" key="local.field.name.password" var="password" />
<fmt:message bundle="${loc}" key="local.logination.button.name.signIn" var="signIn_button" />
<fmt:message bundle="${loc}" key="local.button.name.goToStartPage" var="startPage_button" />

</head>
<body>

	<c:out value="${errorMessage}" />
	
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
	
	<form action="FrontController" method="post">
		<input type="hidden" name="command" value="logination"/>
	
		<c:out value="${login}" />:<br/>
		<input type="text" name="login" value=""/><br/>
	
		<c:out value="${password}" />:<br/>
		<input type="password" name="password" value=""/><br/>
	
		<input type="submit" value="${signIn_button}">
	</form>
	
	<form action="FrontController" method="post">
		<input type="hidden" name="command" value="goToStartPage"/>
		<input type="submit" value="${startPage_button}">
	</form>
</body>
</html>