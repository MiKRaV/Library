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

    <spring:message code="local.tableWithAllBooks.message.bookCount" var="bookCount" />
    <spring:message code="local.tableWithAllBooks.column.name.title" var="title" />
    <spring:message code="local.tableWithAllBooks.column.name.authors" var="authors" />
    <spring:message code="local.tableWithAllBooks.column.name.genre" var="genre" />
    <spring:message code="local.field.name.actions" var="actions" />
    <spring:message code="local.button.name.apply" var="apply_button" />
    <spring:message code="local.button.name.addToBasket" var="addToBasket_button" />
    <spring:message code="local.button.name.edit" var="edit_button" />
    <spring:message code="local.button.name.previous" var="previous_button" />
    <spring:message code="local.button.name.next" var="next_button" />
    <spring:message code="local.message.bookUnavailable" var="book_unavailable" />
    <spring:message code="local.button.name.returnBook" var="return_book_button" />
    <spring:message code="local.message.bookReturned" var="book_returned" />
    <spring:message code="local.message.bookNotReturned" var="book_not_returned" />
    <spring:message code="books.column.issueTime" var="issue_time" />
    <spring:message code="books.column.returnTime" var="return_time" />

</head>
<body>
<div class="container-content">
    <div class="layout-positioner">
        <div class="message">
            <c:out value="${message}" />
            <c:out value="${errorMessage}" />
        </div>
        <div class="table-data-container">
            <div class="select-page-size">
                <form action="${pageContext.request.contextPath}/books-list" method="get">
                    <c:out value="${bookCount}" />:
                    <select name="pageSize" required>
                        <option value="1">1</option>
                        <option value="2">2</option>
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
                        <th><c:out value="${title}" /></th>
                        <th><c:out value="${authors}" /></th>
                        <th><c:out value="${genre}" /></th>
                        <th class="cell-date">${issue_time}</th>
                        <th class="cell-date">${return_time}</th>
                        <th class="cell-actions"><c:out value="${actions}" /></th>
                    </tr>
                    <c:set var="subscription" scope="session" value="${subscription}"/>
                    <c:forEach var="note" items="${subscription}">
                        <tr class="row-data">
                            <td>${note.book.title}</td>
                            <td>
                                <c:forEach var="author" items="${note.book.authors}">
                                    ${author.surname}
                                    ${author.name}<br>
                                </c:forEach>
                            </td>
                            <td>
                                <c:set var="local" value="${pageContext.response.locale}"/>
                                <c:if test="${local eq 'en'}">
                                    <c:out value="${note.book.genre.en}"/>
                                </c:if>
                                <c:if test="${local eq 'ru'}">
                                    <c:out value="${note.book.genre.ru}"/>
                                </c:if>
                            </td>
                            <td class="cell-date">${note.bookIssueTime}</td>
                            <td class="cell-date">
                                <c:if test="${note.returned eq false}">
                                    <c:out value="${book_not_returned}"/>
                                </c:if>
                                <c:if test="${note.returned eq true}">
                                    ${note.bookReturnedTime}
                                </c:if>
                            </td>
                            <td class="cell-actions">
                                <c:if test="${note.returned eq false}">
                                    <form action="" method="post">
                                        <input type="hidden" name="noteID" value="${note.id}">
                                        <input type="hidden" name="bookID" value="${note.book.id}">
                                        <input type="hidden" name="pageSize" value="${pageSize}"/>
                                        <input type="hidden" name="page" value="${currentPage}">
                                        <input type="hidden" name="pageCount" value="${pageCount}">
                                        <input type="submit" value="${return_book_button}">
                                    </form>
                                </c:if>
                                <c:if test="${note.returned eq true}">
                                    <c:out value="${book_returned}"/>
                                </c:if>
                            </td>
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
                                <form action="${pageContext.request.contextPath}/books-list" method="get">
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
                                <form action="${pageContext.request.contextPath}/books-list" method="get">
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