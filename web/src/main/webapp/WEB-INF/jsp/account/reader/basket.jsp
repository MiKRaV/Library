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
    <link rel="stylesheet" href="<c:url value="/assets/css/table.css"/>">

    <spring:message code="local.message.basketIsEmpty" var="emptyBasket" />
    <spring:message code="local.button.name.clearBasket" var="clearBasket_button" />
    <spring:message code="local.tableWithAllBooks.column.name.title" var="title" />
    <spring:message code="local.tableWithAllBooks.column.name.authors" var="authors" />
    <spring:message code="local.tableWithAllBooks.column.name.genre" var="genre" />
    <spring:message code="local.field.name.actions" var="actions" />
    <spring:message code="local.button.name.remove" var="remove_button" />
    <spring:message code="local.button.name.toOrder" var="toOrder_button" />

</head>
<body>
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
                            <form action="${pageContext.request.contextPath}/new-order" method="post">
                                <input type="hidden" name="command" value="createOrder"/>
                                <input type="submit" value="${toOrder_button}">
                            </form>
                        </div>
                        <div class="cell-basket-action">
                            <form action="${pageContext.request.contextPath}/basket" method="post">
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
                                <td>
                                    <c:set var="local" value="${pageContext.response.locale}"/>
                                    <c:if test="${local eq 'en'}">
                                        <c:out value="${book.genre.en}"/>
                                    </c:if>
                                    <c:if test="${local eq 'ru'}">
                                        <c:out value="${book.genre.ru}"/>
                                    </c:if>
                                </td>
                                <td class="cell-actions">
                                    <form action="${pageContext.request.contextPath}/basket" method="post">
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
