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
    <fmt:message bundle="${loc}" key="local.orders.column.name.orderID" var="orderID" />
    <fmt:message bundle="${loc}" key="local.orders.column.name.userID" var="userID" />
    <fmt:message bundle="${loc}" key="local.orders.column.name.creationTime" var="creationTime" />
    <fmt:message bundle="${loc}" key="local.orders.column.name.status" var="status" />
    <fmt:message bundle="${loc}" key="local.orders.column.name.updateTime" var="updateTime" />
    <fmt:message bundle="${loc}" key="local.ordersDetail.message.orderInfo" var="orderInfo" />
    <fmt:message bundle="${loc}" key="local.ordersDetail.message.booksInOrder" var="booksInOrder" />
    <fmt:message bundle="${loc}" key="local.tableWithAllBooks.column.name.title" var="title" />
    <fmt:message bundle="${loc}" key="local.tableWithAllBooks.column.name.authors" var="authors" />
    <fmt:message bundle="${loc}" key="local.tableWithAllBooks.column.name.genre" var="genre" />
    <fmt:message bundle="${loc}" key="local.button.name.backToOrders" var="backToOrders_button" />
    <fmt:message bundle="${loc}" key="local.button.name.goToAccount" var="goToAccount_button" />
    <fmt:message bundle="${loc}" key="local.button.name.logOut" var="logOutButton" />
</head>
<body>

<c:set var="url" value="jsp/account/OrderInfo.jsp" scope="session"/>
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

<c:out value="${orderInfo}"/>

<table border="1">
    <tr>
        <th><c:out value="${orderID}" /></th>
        <th><c:out value="${userID}" /></th>
        <th><c:out value="${creationTime}" /></th>
        <th><c:out value="${status}" /></th>
        <th><c:out value="${updateTime}" /></th>
    </tr>
    <tr>
        <td>${order.id}</td>
        <td>${order.user.id}</td>
        <td>${order.orderCreationTime}</td>
        <td>${order.status}</td>
        <td>${order.orderUpdateTime}</td>
    </tr>
</table>

<c:out value="${booksInOrder}"/>

<table border="1">
    <tr>
        <th><c:out value="${title}" /></th>
        <th><c:out value="${authors}" /></th>
        <th><c:out value="${genre}" /></th>
    </tr>
    <c:set var="bookList" scope="session" value="${order.booksInOrder}"/>
    <c:forEach var="book" items="${bookList}">
        <tr>
            <td>${book.title}</td>
            <td>
                <c:forEach var="author" items="${book.authors}">
                    ${author.surname}
                    ${author.name}<br>
                </c:forEach>
            </td>
            <td>${book.genre}</td>
        </tr>
    </c:forEach>
</table>

<form action="FrontController" method="post">
    <input type="hidden" name="command" value="getOrders"/>
    <input type="submit" value="${backToOrders_button}">
</form>


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
