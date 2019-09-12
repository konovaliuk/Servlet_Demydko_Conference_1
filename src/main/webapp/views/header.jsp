<%--
  Created by IntelliJ IDEA.
  User: Ваня
  Date: 09.09.2019
  Time: 13:21
  To change this template use File | Settings | File Templates.
--%>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="bonuses" uri="/WEB-INF/bonusestag.tld" %>

<c:choose>
    <c:when test="${not empty sessionScope.language}">
        <fmt:setLocale value="${sessionScope.language}" scope="session"/>
    </c:when>
    <c:otherwise>
        <fmt:setLocale value="en_US" scope="session"/>
    </c:otherwise>
</c:choose>

<fmt:setBundle basename="messages" var="rm" scope="session"/>
<html>
<head>
    <title>Title</title>
    <style>
        <c:import url="/WEB-INF/resources/css/styles.css" charEncoding="utf-8"/>
    </style>
</head>
<body>

<c:if test="${not empty sessionScope.user}">
    <p><fmt:message key="label.enterAs" bundle="${rm}"/> ${user.name} ${user.surname}</p>
    <c:if test="${sessionScope.user.position=='Speaker'}">
        <p><fmt:message key="bonuses" bundle="${rm}"/> <bonuses:getbonuses/></p>
    </c:if>
</c:if>

<div class="blockTop">
    <form method="post" action="/Conference_war/controller?command=changeLanguage">
        <input type="hidden" name="requestURI" value="${pageContext.request.getRequestURI()}">
        <p>
            <select size="1" name="language">
                <option value="EN">English</option>
                <option value="UA">Українська</option>
                <option value="RU">Русский</option>
            </select>
        </p>
        <p><input type="submit" value="<fmt:message key="label.selectLanguage" bundle="${rm}"/>"></p>
    </form>
</div>

</body>
</html>
