<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="<c:url value="/assets/css/total.css"/>">

    <spring:message code="local.locbutton.name.ru" var="ru_button"/>
    <spring:message code="local.locbutton.name.en" var="en_button"/>
    <spring:message code="local.button.name.logOut" var="logOutButton"/>
    <spring:message code="local.button.name.editData" var="editDataButton"/>
    <spring:message code="local.button.name.basket" var="basket_button" />

</head>
<body>
<header>
    <div class="header">
        <div class="layout-positioner">
            <div class="table-main-menu">
                <div class="row-main-menu">
                    <div class="cell-main-menu">
                        <form action="${pageContext.request.contextPath}/start-page" method="post">
                            <input type="submit" value="${logOutButton}">
                        </form>
                    </div>
                    <div class="cell-main-menu">
                        <form action="${pageContext.request.contextPath}/user-data" method="get">
                            <input type="submit" value="${editDataButton}">
                        </form>
                    </div>
                    <c:if test="${user.type eq 'READER'}">
                        <div class="cell-main-menu">
                            <form action="${pageContext.request.contextPath}/basket" method="get">
                                <input type="submit" value="${basket_button}: ${user.basket.size()}">
                            </form>
                        </div>
                    </c:if>
                </div>
            </div>
            <div class="table-local">
                <div class="row-local">
                    <div class="cell-local">
                        <a href="?lang=ru">${ru_button}</a>
                    </div>
                    <div class="cell-local">
                        <a href="?lang=en">${en_button}</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</header>
</body>
</html>
