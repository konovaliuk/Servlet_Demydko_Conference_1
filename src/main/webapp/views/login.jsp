<%--
  Created by IntelliJ IDEA.
  User: Ваня
  Date: 20.08.2019
  Time: 10:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="header.jsp" charEncoding="utf-8"/>
<html>
<head>
    <title><fmt:message key="label.enter" bundle="${rm}"/></title>
    <style>
        <c:import url="/WEB-INF/resources/css/styles.css" charEncoding="utf-8"/>
    </style>
</head>
<body>
<h1 align="center"><fmt:message key="label.enter" bundle="${rm}"/></h1>
<div class="blockCenter">
    <form method="post" action="/Conference_war/controller?command=login">
        <p><input type="email" name="email" placeholder="Email" size="15" required/></p>
        <p><input type="password" name="password" placeholder="<fmt:message key="label.password" bundle="${rm}"/>"
                  size="15"
                  pattern="[A-Za-zА-Яа-яЁёІіЄєЇї0-9]{5,}" required/></p>


        <c:choose>
            <c:when test="${not empty errorEmailForm}">
                <fmt:message key="errorEmailForm" bundle="${rm}"/>
            </c:when>
            <c:when test="${not empty errorPassword}">
                <fmt:message key="errorPassword" bundle="${rm}"/>
            </c:when>
            <c:when test="${not empty errorUserNotExists}">
                <fmt:message key="errorUserNotExists" bundle="${rm}"/>
            </c:when>
        </c:choose>


        <p><input type="submit" value="<fmt:message key="label.signIn" bundle="${rm}"/>"/></p>
    </form>
</div>
</body>
</html>
