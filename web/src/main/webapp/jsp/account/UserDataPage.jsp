<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>    
<!DOCTYPE html>
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

</head>
<body>

<c:set var="url" value="jsp/account/UserDataPage.jsp" scope="session"/>

	<table>
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
	
	<c:out value="${message}" /><br />
	
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
	
	<table>
    	<tr>
    		<td><b><c:out value="${login}" /></b>:</td>
    		<td><c:out value="${user.login}" /></td>
    	</tr>
    	<tr>
    		<td><b><c:out value="${password}" /></b>:</td>
    	</tr>
    	<tr>
    		<td><c:out value="${oldPassword}" />:</td>
			<td><input type="password" name="oldPassword" value="" form="password" ></td>
    	</tr>
    	<tr>
			<td><c:out value="${newPassword}" />:</td>
			<td><input type="password" name="newPassword" value="" form="password" ></td>
		</tr>
		<tr>
			<td><c:out value="${confirmPassword}" />:</td>
			<td><input type="password" name="confirmPassword" value="" form="password" ></td>
			<td><input type="submit" value="${changeButton}" form="password" ></td>
		</tr>
		<tr>
			<td><b><c:out value="${name}" /></b>:</td>
			<td><c:out value="${user.userData.name}" /></td>
		</tr>
		<tr>
			<td><c:out value="${newName}" />:</td>
			<td><input type="text" name="newName" value="" form="name" ></td>
			<td><input type="submit" value="${changeButton}" form="name" ></td>
		</tr>
		<tr>
			<td><b><c:out value="${surname}" /></b>:</td>
			<td><c:out value="${user.userData.surname}" /></td>
		</tr>
		<tr>
			<td><c:out value="${newSurname}" />:</td>
			<td><input type="text" name="newSurname" value="" form="surname" ></td>
			<td><input type="submit" value="${changeButton}" form="surname" ></td>
		</tr>
		<tr>
			<td><b><c:out value="${email}" /></b>:</td>
			<td><c:out value="${user.userData.email}" /></td>
		</tr>
		<tr>
			<td><c:out value="${newEmail}" />:</td>
			<td><input type="text" name="newEmail" value="" form="email" ></td>
			<td><input type="submit" value="${changeButton}" form="email" ></td>
		</tr>
    </table>
	
	<form action="FrontController" method="post">
		<input type="hidden" name="command" value="goToAccount"/>
		<input type="submit" value="${goToAccount_button}">
	</form>
	
	<form action="FrontController" method="post">
		<input type="hidden" name="command" value="logOutCommand"/>
		<input type="submit" value="${logOutButton}">
	</form>

</body>
</html>