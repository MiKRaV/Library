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

    <spring:message code="local.orders.message.orderCount" var="orderCount" />
    <spring:message code="local.button.name.apply" var="apply_button" />
    <spring:message code="local.orders.column.name.orderID" var="orderID" />
    <spring:message code="local.orders.column.name.userID" var="userID" />
    <spring:message code="local.orders.column.name.creationTime" var="creationTime" />
    <spring:message code="local.orders.column.name.status" var="status" />
    <spring:message code="local.orders.column.name.updateTime" var="updateTime" />
    <spring:message code="local.button.name.view" var="view_button" />
    <spring:message code="local.button.name.previous" var="previous_button" />
    <spring:message code="local.button.name.next" var="next_button" />
    <spring:message code="order.status.inProcessing" var="order_in_processing" />
    <spring:message code="order.status.acceptedForExecution" var="order_accepted" />
    <spring:message code="order.status.readyForIssue" var="order_ready" />
    <spring:message code="order.status.fulfilled" var="order_fulfilled" />

</head>
<body>
    <div class="container-content">
        <div class="layout-positioner">
            <div class="table-data-container">
                <div class="select-page-size">
                    <form action="${pageContext.request.contextPath}/orders-list" method="get">
                        <c:out value="${orderCount}" />:
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
                            <th class="cell-id"><c:out value="${orderID}" /></th>
                            <c:if test="${user.type eq 'ADMIN'}">
                                <th class="cell-id"><c:out value="${userID}" /></th>
                            </c:if>
                            <th class="cell-date"><c:out value="${creationTime}" /></th>
                            <th class="cell-order-status"><c:out value="${status}" /></th>
                            <th class="cell-date"><c:out value="${updateTime}" /></th>
                        </tr>
                        <c:forEach var="order" items="${orderList}">
                            <tr class="row-data">
                                <td class="cell-submit cell-id">
                                    <form action="${pageContext.request.contextPath}/order-info" method="get">
                                        <input type="hidden" name="command" value="getOrderInfo"/>
                                        <input type="hidden" name="orderID" value="${order.id}"/>
                                        <input type="submit" value="${order.id}">
                                    </form>
                                </td>
                                <c:if test="${user.type eq 'ADMIN'}">
                                    <td class="cell-id">${order.user.id}</td>
                                </c:if>
                                <td class="cell-date">${order.orderCreationTime}</td>
                                <td class="cell-order-status">
                                    <c:if test="${order.status eq 'IN_PROCESSING'}">
                                        <c:out value="${order_in_processing}"/>
                                    </c:if>
                                    <c:if test="${order.status eq 'ACCEPTED_FOR_EXECUTION'}">
                                        <c:out value="${order_accepted}"/>
                                    </c:if>
                                    <c:if test="${order.status eq 'READY_FOR_ISSUE'}">
                                        <c:out value="${order_ready}"/>
                                    </c:if>
                                    <c:if test="${order.status eq 'FULFILLED'}">
                                        <c:out value="${order_fulfilled}"/>
                                    </c:if>
                                </td>
                                <td class="cell-date">${order.orderUpdateTime}</td>
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
                                    <form action="${pageContext.request.contextPath}/orders-list" method="get">
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
                                    <form action="${pageContext.request.contextPath}/orders-list" method="get">
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
