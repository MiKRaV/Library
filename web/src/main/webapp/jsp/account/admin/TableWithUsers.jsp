<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel="stylesheet" href="total.css">
	<link rel="stylesheet" href="table.css">
	<title>Users</title>

	<fmt:setLocale value="${sessionScope.local}" />
	<fmt:setBundle basename="localization.local" var="loc" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />
	<fmt:message bundle="${loc}" key="local.tableWithAllUsers.column.name.login" var="login" />
	<fmt:message bundle="${loc}" key="local.tableWithAllUsers.column.name.name" var="name" />
	<fmt:message bundle="${loc}" key="local.tableWithAllUsers.column.name.surname" var="surname" />
	<fmt:message bundle="${loc}" key="local.tableWithAllUsers.column.name.email" var="email" />
	<fmt:message bundle="${loc}" key="local.tableWithAllUsers.column.name.userType" var="userType" />
	<fmt:message bundle="${loc}" key="local.tableWithAllUsers.message.userCount" var="userCount" />
	<fmt:message bundle="${loc}" key="local.button.name.apply" var="apply_button" />
	<fmt:message bundle="${loc}" key="local.button.name.previous" var="previous_button" />
	<fmt:message bundle="${loc}" key="local.button.name.next" var="next_button" />
	<fmt:message bundle="${loc}" key="local.button.name.goToAccount" var="goToAccount_button" />
	<fmt:message bundle="${loc}" key="local.button.name.logOut" var="logOutButton" />
	<fmt:message bundle="${loc}" key="local.button.name.editData" var="editDataButton" />

	<c:set var="url" value="jsp/account/admin/TableWithUsers.jsp" scope="session"/>
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
							<input type="hidden" name="command" value="goToPage"/>
							<input type="hidden" name="goToPage" value="jsp/account/UserDataPage.jsp"/>
							<input type="submit" value="${editDataButton}">
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
			<div class="table-data-container">
				<div class="select-page-size">
					<form action="FrontController" method="post">
						<c:out value="${userCount}" />:
						<input type="hidden" name="command" value="getAllUsers">
						<select name="pageSize" required>
							<option value="1">1</option>
							<option value="5">5</option>
							<option selected value="10">10</option>
							<option value="20">20</option>
						</select>
						<input type="submit" value="${apply_button}">
					</form>
				</div>
				<div class="table-data">
					<table>
						<tr class="row-header">
							<th><c:out value="${login}" /></th>
							<th><c:out value="${name}" /></th>
							<th><c:out value="${surname}" /></th>
							<th><c:out value="${email}" /></th>
							<th><c:out value="${userType}" /></th>
						</tr>
						<c:forEach var="user" items="${userList}">
							<tr class="row-data">
								<td>${user.login}</td>
								<td>${user.userData.name}</td>
								<td>${user.userData.surname}</td>
								<td>${user.userData.email}</td>
								<td>${user.type}</td>
							</tr>
						</c:forEach>
					</table>
				</div>
				<div class="table-pagination">
					<div class="row-pagination">
						<div class="cell-pagination">
							<%--For displaying Previous link except for the 1st page --%>
							<c:if test="${currentPage gt 1}">
								<td>
									<form action="FrontController" method="post">
										<input type="hidden" name="command" value="getAllUsers"/>
										<input type="hidden" name="pageSize" value="${pageSize}"/>
										<input type="hidden" name="page" value="${currentPage - 1}">
										<input type="submit" value="${previous_button}">
									</form>
								</td>
							</c:if>
						</div>
						<div class="cell-pagination">
							<c:out value="${currentPage}" />
						</div>
						<div class="cell-pagination">
							<%--For displaying Next link --%>
							<c:if test="${currentPage lt pageCount}">
								<td>
									<form action="FrontController" method="post">
										<input type="hidden" name="command" value="getAllUsers"/>
										<input type="hidden" name="pageSize" value="${pageSize}"/>
										<input type="hidden" name="page" value="${currentPage + 1}">
										<input type="submit" value="${next_button}">
									</form>
								</td>
							</c:if>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>