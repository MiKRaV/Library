<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Title</title>

    <fmt:setLocale value="${sessionScope.local}" />
    <fmt:setBundle basename="localization.local" var="loc" />
    <fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button" />
    <fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />
    <fmt:message bundle="${loc}" key="local.orders.message.orderCount" var="orderCount" />
    <fmt:message bundle="${loc}" key="local.button.name.apply" var="apply_button" />
    <fmt:message bundle="${loc}" key="local.orders.column.name.orderID" var="orderID" />
    <fmt:message bundle="${loc}" key="local.orders.column.name.userID" var="userID" />
    <fmt:message bundle="${loc}" key="local.orders.column.name.creationTime" var="creationTime" />
    <fmt:message bundle="${loc}" key="local.orders.column.name.status" var="status" />
    <fmt:message bundle="${loc}" key="local.orders.column.name.updateTime" var="updateTime" />
    <fmt:message bundle="${loc}" key="local.field.name.actions" var="actions" />
    <fmt:message bundle="${loc}" key="local.button.name.view" var="view_button" />
    <fmt:message bundle="${loc}" key="local.button.name.changeStatus" var="changeStatus_button" />
    <fmt:message bundle="${loc}" key="local.button.name.previous" var="previous_button" />
    <fmt:message bundle="${loc}" key="local.button.name.next" var="next_button" />
    <fmt:message bundle="${loc}" key="local.button.name.goToAccount" var="goToAccount_button" />
    <fmt:message bundle="${loc}" key="local.button.name.logOut" var="logOutButton" />
</head>
<body>

<c:set var="url" value="jsp/account/Orders.jsp" scope="session"/>
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
    </tr>
</table>

<form action="FrontController" method="post">
    <c:out value="${orderCount}" />:
    <input type="hidden" name="command" value="getOrders">
    <select name="pageSize" required>
        <option value="1">1</option>
        <option value="5">5</option>
        <option selected value="10">10</option>
        <option value="20">20</option>
    </select>
    <input type="submit" value="${apply_button}">
</form>

<table border="1">
    <tr>
        <th><c:out value="${orderID}" /></th>
        <c:if test="${user.type eq 'admin'}">
            <th><c:out value="${userID}" /></th>
        </c:if>
        <th><c:out value="${creationTime}" /></th>
        <th><c:out value="${status}" /></th>
        <th><c:out value="${updateTime}" /></th>
        <th><c:out value="${actions}" /></th>
    </tr>
    <c:forEach var="order" items="${orderList}">
        <tr>
            <td>${order.id}</td>
            <c:if test="${user.type eq 'admin'}">
                <td>${order.user.id}</td>
            </c:if>
            <td>${order.orderCreationTime}</td>
            <td>${order.status}</td>
            <td>${order.orderUpdateTime}</td>
            <td>
                <form action="FrontController" method="post">
                    <input type="hidden" name="command" value="getOrderInfo"/>
                    <input type="hidden" name="orderID" value="${order.id}"/>
                    <input type="submit" value="${view_button}">
                </form>
                <c:if test="${user.type eq 'admin'}">
                    <form action="FrontController" method="post">
                        <input type="hidden" name="command" value="changeOrderStatus">
                        <select name="orderStatus" required>
                            <option value="accepted for execution">accepted for execution</option>
                            <option value="awaits delivery">awaits delivery</option>
                            <option value="fulfilled">fulfilled</option>
                        </select>
                        <input type="hidden" name="orderID" value="${order.id}"/>
                        <input type="hidden" name="pageSize" value="${pageSize}"/>
                        <input type="hidden" name="page" value="${currentPage}">
                        <input type="submit" value="${changeStatus_button}">
                    </form>
                </c:if>
            </td>
        </tr>
    </c:forEach>
</table>

<table border="0">
    <tr>
        <%--For displaying Previous link except for the 1st page --%>
        <c:if test="${currentPage gt 1}">
            <td>
                <form action="FrontController" method="post">
                    <input type="hidden" name="command" value="getOrders"/>
                    <input type="hidden" name="pageSize" value="${pageSize}"/>
                    <input type="hidden" name="page" value="${currentPage - 1}">
                    <input type="submit" value="${previous_button}">
                </form>
            </td>
        </c:if>
        <td>
            <c:out value="${currentPage}" />
        </td>
        <%--For displaying Next link --%>
        <c:if test="${currentPage lt pageCount}">
            <td>
                <form action="FrontController" method="post">
                    <input type="hidden" name="command" value="getOrders"/>
                    <input type="hidden" name="pageSize" value="${pageSize}"/>
                    <input type="hidden" name="page" value="${currentPage + 1}">
                    <input type="submit" value="${next_button}">
                </form>
            </td>
        </c:if>
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
