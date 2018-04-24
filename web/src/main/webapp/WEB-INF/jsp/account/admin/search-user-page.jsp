<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel="stylesheet" href="<c:url value="/assets/css/total.css"/>">
	<link rel="stylesheet" href="<c:url value="/assets/css/user_data.css"/>">

	<spring:message code="local.field.name.login" var="login" />
	<spring:message code="local.searchUserPage.button.name.find" var="findUserButton" />

</head>
<body>
	<div class="container-content">
		<div class="layout-positioner">
			<div class="message">
				<c:out value="${errorMessage}" />
			</div>
		</div>
	</div>
	<div class="form-user-search">
		<form action="${pageContext.request.contextPath}/found-user" method="post">
			<label for="login"><c:out value="${login}" /></label>
			<input type="text" id="login" name="login" value=""/>
			<input type="submit" value="${findUserButton}">
		</form>
	</div>

</body>
</html>