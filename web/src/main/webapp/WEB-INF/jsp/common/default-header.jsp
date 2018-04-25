<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="<c:url value="/assets/css/total.css"/>">

    <spring:message code="local.locbutton.name.ru" var="ru_button"/>
    <spring:message code="local.locbutton.name.en" var="en_button"/>
</head>
<body>
<div class="header">
    <div class="layout-positioner">
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
</body>
</html>
