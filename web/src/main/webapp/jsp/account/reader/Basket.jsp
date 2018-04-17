<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" href="../../../css/total.css">
    <link rel="stylesheet" href="../../../css/table.css">
    <title>Basket</title>

    <fmt:setLocale value="${sessionScope.local}" />
    <fmt:setBundle basename="localization.local" var="loc" />
    <fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button" />
    <fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />
    <fmt:message bundle="${loc}" key="local.message.basketIsEmpty" var="emptyBasket" />
    <fmt:message bundle="${loc}" key="local.button.name.clearBasket" var="clearBasket_button" />
    <fmt:message bundle="${loc}" key="local.button.name.orders" var="orders_button" />
    <fmt:message bundle="${loc}" key="local.tableWithAllBooks.column.name.title" var="title" />
    <fmt:message bundle="${loc}" key="local.tableWithAllBooks.column.name.authors" var="authors" />
    <fmt:message bundle="${loc}" key="local.tableWithAllBooks.column.name.genre" var="genre" />
    <fmt:message bundle="${loc}" key="local.field.name.actions" var="actions" />
    <fmt:message bundle="${loc}" key="local.button.name.remove" var="remove_button" />
    <fmt:message bundle="${loc}" key="local.button.name.toOrder" var="toOrder_button" />
    <fmt:message bundle="${loc}" key="local.button.name.goToAccount" var="goToAccount_button" />
    <fmt:message bundle="${loc}" key="local.button.name.logOut" var="logOutButton" />
    <fmt:message bundle="${loc}" key="local.button.name.editData" var="editDataButton" />

    <c:set var="url" value="jsp/account/reader/Basket.jsp" scope="session"/>
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
                            <input type="hidden" name="command" value="getOrders"/>
                            <input type="submit" value="${orders_button}">
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
            <div class="message">
                <c:out value="${message}" />
                <c:if test="${user.basket.size() eq 0}">
                    <c:out value="${emptyBasket}" />
                </c:if>
            </div>

            <c:if test="${user.basket.size() gt 0}">
                <div class="table-basket-action">
                    <div class="row-basket-action">
                        <div class="cell-basket-action">
                            <form action="FrontController" method="post">
                                <input type="hidden" name="command" value="createOrder"/>
                                <input type="submit" value="${toOrder_button}">
                            </form>
                        </div>
                        <div class="cell-basket-action">
                            <form action="FrontController" method="post">
                                <input type="hidden" name="command" value="clearBasket" />
                                <input type="submit" value="${clearBasket_button}" /><br />
                            </form>
                        </div>
                    </div>
                </div>

                <div class="table-data">
                    <table>
                        <tr class="row-header">
                            <th><c:out value="${title}" /></th>
                            <th><c:out value="${authors}" /></th>
                            <th><c:out value="${genre}" /></th>
                            <th class="cell-actions"><c:out value="${actions}" /></th>
                        </tr>
                        <c:forEach var="book" items="${user.basket}">
                            <tr class="row-data">
                                <td>${book.title}</td>
                                <td>
                                    <c:forEach var="author" items="${book.authors}">
                                        ${author.surname}
                                        ${author.name}<br>
                                    </c:forEach>
                                </td>
                                <td>${book.genre}</td>
                                <td class="cell-actions">
                                    <form action="FrontController" method="post">
                                        <input type="hidden" name="command" value="removeBookFromBasket"/>
                                        <input type="hidden" name="bookID" value="${book.id}"/>
                                        <input type="submit" value="${remove_button}">
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </c:if>
        </div>
    </div>




</body>
</html>
