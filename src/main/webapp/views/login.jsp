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

<c:import url="header.jsp"/>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h2 align="center"><fmt:message key="label.enter" bundle="${rm}"/></h2>
<div class="center">
    <div class="register">
        <form method="post" action="${pageContext.request.contextPath}/controller?command=login">
            <p><input type="email" name="email" placeholder="Email" size="22" required/></p>
            <p><input type="password" name="password" placeholder="<fmt:message key="label.password" bundle="${rm}"/>"
                      size="22"
                      pattern="[A-Za-zА-Яа-яЁёІіЄєЇї0-9]{5,}" required/></p>
            <p><input type="submit" value="<fmt:message key="label.signIn" bundle="${rm}"/>"/></p>
        </form>
    </div>
    <p>
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
    </p>
</div>
</body>
</html>
