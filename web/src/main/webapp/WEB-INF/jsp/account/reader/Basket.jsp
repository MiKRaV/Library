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
    <fmt:message bundle="${loc}" key="local.message.basketIsEmpty" var="emptyBasket" />
    <fmt:message bundle="${loc}" key="local.tableWithAllBooks.column.name.title" var="title" />
    <fmt:message bundle="${loc}" key="local.tableWithAllBooks.column.name.authors" var="authors" />
    <fmt:message bundle="${loc}" key="local.tableWithAllBooks.column.name.genre" var="genre" />
    <fmt:message bundle="${loc}" key="local.field.name.actions" var="actions" />
    <fmt:message bundle="${loc}" key="local.button.name.remove" var="remove_button" />
    <fmt:message bundle="${loc}" key="local.button.name.goToAccount" var="goToAccount_button" />
    <fmt:message bundle="${loc}" key="local.button.name.logOut" var="logOutButton" />

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

    <c:out value="${message}" />

    <c:if test="${user.basket.size() eq 0}">
        <c:out value="${emptyBasket}" />
    </c:if>

    <c:if test="${user.basket.size() gt 0}">
        <table border="1">
            <tr>
                <th><c:out value="${title}" /></th>
                <th><c:out value="${authors}" /></th>
                <th><c:out value="${genre}" /></th>
                <th><c:out value="${actions}" /></th>
            </tr>
            <c:forEach var="book" items="${user.basket}">
                <tr>
                    <td>${book.title}</td>
                    <td>
                        <c:forEach var="author" items="${book.authors}">
                            ${author.surname}
                            ${author.name}<br>
                        </c:forEach>
                    </td>
                    <td>${book.genre}</td>
                    <td>
                        <form action="FrontController" method="post">
                            <input type="hidden" name="command" value="removeBookFromBasket"/>
                            <input type="hidden" name="bookID" value="${book.id}"/>
                            <input type="submit" value="${remove_button}">
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>

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
