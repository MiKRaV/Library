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
<fmt:message bundle="${loc}" key="local.field.name.name" var="name" />
<fmt:message bundle="${loc}" key="local.field.name.surname" var="surname" />
<fmt:message bundle="${loc}" key="local.field.name.email" var="email" />
<fmt:message bundle="${loc}" key="local.field.name.userType" var="userType" />
<fmt:message bundle="${loc}" key="local.field.name.userStatus" var="userStatus" />
<fmt:message bundle="${loc}" key="local.field.name.actions" var="actions" />
<fmt:message bundle="${loc}" key="local.button.name.blockUnlock" var="blockUnlockButton" />
<fmt:message bundle="${loc}" key="local.button.name.goToAccount" var="goToAccount_button" />

</head>
<body>

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
	
	<c:out value="${errorMessage}" />
	
	<table border="1">
		<tr>
			<th><c:out value="${login}" /></th>
			<th><c:out value="${name}" /></th>
			<th><c:out value="${surname}" /></th>
			<th><c:out value="${email}" /></th>
			<th><c:out value="${userType}" /></th>
			<th><c:out value="${userStatus}" /></th>
			<th><c:out value="${actions}" /></th>
		</tr>
  		<tr>
    		<td>${foundUser.login}</td>
   			<td>${foundUser.userData.name}</td>
    		<td>${foundUser.userData.surname}</td>
    		<td>${foundUser.userData.email}</td>
    		<td>${foundUser.type}</td>
    		<td>${foundUser.status}</td>
    		<td>
    			<form action="FrontController" method="post">
					<input type="hidden" name="command" value="blockUnlockUser"/>
					<input type="submit" value="${blockUnlockButton}">
				</form>
    		</td>
  		</tr>		
	</table>
	
	<form action="FrontController" method="post">
		<input type="hidden" name="command" value="goToAccount"/>
		<input type="submit" value="${goToAccount_button}">
	</form>

</body>
</html>