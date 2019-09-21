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
    <title><fmt:message key="label.conference" bundle="${rm}"/></title>
    <style>
        <c:import url="/WEB-INF/resources/css/styles.css"/>
    </style>
</head>
<body>
<div class="header">
    <c:if test="${not empty sessionScope.user}">
        <p class="headerLeft"><fmt:message key="label.enterAs" bundle="${rm}"/> ${user.name} ${user.surname}.</p>
        <c:if test="${sessionScope.user.position=='Speaker'}">
            <p class="headerLeft"><fmt:message key="bonuses" bundle="${rm}"/> <bonuses:getbonuses/></p>
        </c:if>
        <c:if test="${not pageContext.request.getRequestURI().endsWith('cabinet.jsp')}">
            <form class="headerLeft" method="post" action="${pageContext.request.contextPath}/views/cabinet.jsp">
                <p><input type="submit" value="<fmt:message key="label.cabinet" bundle="${rm}"/>"/></p>
            </form>
        </c:if>
        <form class="headerLeft" method="post" action="${pageContext.request.contextPath}/controller?command=logout">
            <p><input type="submit" value="<fmt:message key="label.exit" bundle="${rm}"/>"/></p>
        </form>
    </c:if>
    <form class="headerRight" method="post"
          action="${pageContext.request.contextPath}/controller?command=changeLanguage">
        <input type="hidden" name="requestURI" value="${pageContext.request.getRequestURI()}">
        <p>
            <select size="1" name="language" onchange="submit()">
                <option><fmt:message key="label.language" bundle="${rm}"/></option>
                <option value="EN">English</option>
                <option value="UA">Українська</option>
                <option value="RU">Русский</option>
            </select>
        </p>
    </form>
</div>
</body>
</html>
