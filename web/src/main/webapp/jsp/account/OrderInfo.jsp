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
    <title>Order info</title>

    <fmt:setLocale value="${sessionScope.local}" />
    <fmt:setBundle basename="localization.local" var="loc" />
    <fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button" />
    <fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />
    <fmt:message bundle="${loc}" key="local.orders.column.name.orderID" var="orderID" />
    <fmt:message bundle="${loc}" key="local.orders.column.name.userID" var="userID" />
    <fmt:message bundle="${loc}" key="local.orders.column.name.creationTime" var="creationTime" />
    <fmt:message bundle="${loc}" key="local.orders.column.name.status" var="status" />
    <fmt:message bundle="${loc}" key="local.orders.column.name.updateTime" var="updateTime" />
    <fmt:message bundle="${loc}" key="local.field.name.actions" var="actions" />
    <fmt:message bundle="${loc}" key="local.button.name.changeStatus" var="changeStatus_button" />
    <fmt:message bundle="${loc}" key="local.ordersDetail.message.orderInfo" var="orderInfo" />
    <fmt:message bundle="${loc}" key="local.ordersDetail.message.booksInOrder" var="booksInOrder" />
    <fmt:message bundle="${loc}" key="local.tableWithAllBooks.column.name.title" var="title" />
    <fmt:message bundle="${loc}" key="local.tableWithAllBooks.column.name.authors" var="authors" />
    <fmt:message bundle="${loc}" key="local.tableWithAllBooks.column.name.genre" var="genre" />
    <fmt:message bundle="${loc}" key="local.button.name.backToOrders" var="backToOrders_button" />
    <fmt:message bundle="${loc}" key="local.button.name.goToAccount" var="goToAccount_button" />
    <fmt:message bundle="${loc}" key="local.button.name.logOut" var="logOutButton" />
    <fmt:message bundle="${loc}" key="local.button.name.editData" var="editDataButton" />

    <c:set var="url" value="jsp/account/OrderInfo.jsp" scope="session"/>
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
                    <div class="cell-main-menu">
                        <form action="FrontController" method="post">
                            <input type="hidden" name="command" value="getOrders"/>
                            <input type="submit" value="${backToOrders_button}">
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
                <div class="table-data">
                    <table>
                        <caption><c:out value="${orderInfo}"/></caption>
                        <tr class="row-header">
                            <th class="cell-id"><c:out value="${orderID}" /></th>
                            <c:if test="${user.type eq 'admin'}">
                                <th class="cell-id"><c:out value="${userID}" /></th>
                            </c:if>
                            <th class="cell-date"><c:out value="${creationTime}" /></th>
                            <th class="cell-order-status"><c:out value="${status}" /></th>
                            <th class="cell-date"><c:out value="${updateTime}" /></th>
                            <c:if test="${user.type eq 'admin'}">
                                <th class="cell-actions"><c:out value="${actions}" /></th>
                            </c:if>
                        </tr>
                        <tr class="row-data">
                            <td class="cell-id">${order.id}</td>
                            <c:if test="${user.type eq 'admin'}">
                                <td class="cell-id">${order.user.id}</td>
                            </c:if>
                            <td class="cell-date">${order.orderCreationTime}</td>
                            <td class="cell-order-status">${order.status}</td>
                            <td class="cell-date">${order.orderUpdateTime}</td>
                            <c:if test="${user.type eq 'admin'}">
                                <td class="cell-actions">
                                    <form action="FrontController" method="post">
                                        <input type="hidden" name="command" value="changeOrderStatus">
                                        <select name="orderStatus" required>
                                            <option value="accepted for execution">accepted for execution</option>
                                            <option value="awaits delivery">awaits delivery</option>
                                            <option value="fulfilled">fulfilled</option>
                                        </select>
                                        <input type="submit" value="${changeStatus_button}">
                                    </form>
                                </td>
                            </c:if>
                        </tr>
                    </table>
                </div>
            </div>

            <div class="table-data-container">
                <div class="table-data">
                    <table>
                        <caption><c:out value="${booksInOrder}"/></caption>
                        <tr class="row-header">
                            <th class="cell-book-title"><c:out value="${title}" /></th>
                            <th class="cell-book-author"><c:out value="${authors}" /></th>
                            <th class="cell-book-genre"><c:out value="${genre}" /></th>
                        </tr>
                        <c:set var="bookList" scope="session" value="${order.booksInOrder}"/>
                        <c:forEach var="book" items="${bookList}">
                            <tr class="row-data">
                                <td class="cell-book-title">${book.title}</td>
                                <td class="cell-book-author">
                                    <c:forEach var="author" items="${book.authors}">
                                        ${author.surname}
                                        ${author.name}<br>
                                    </c:forEach>
                                </td>
                                <td class="cell-book-genre">${book.genre}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
