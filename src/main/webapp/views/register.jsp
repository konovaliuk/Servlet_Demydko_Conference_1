<%--
  Created by IntelliJ IDEA.
  User: Ваня
  Date: 20.08.2019
  Time: 10:30
  To change this template use File | Settings | File Templates.
--%>
<%--<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="header.jsp" charEncoding="utf-8"/>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h2 align="center"><fmt:message key="label.registration" bundle="${rm}"/></h2>
<div class="center">
    <div class="register">
        <form method="post" action="${pageContext.request.contextPath}/controller?command=register">
            <p><input type="text" name="name" required placeholder="<fmt:message key="label.name" bundle="${rm}"/>"
                      size="25"
                      pattern="[A-Za-zА-Яа-яЁёІіЄєЇї-]{1,50}"/></p>
            <p><input type="text" name="surname" required
                      placeholder="<fmt:message key="label.surname" bundle="${rm}"/>"
                      size="25"
                      pattern="[A-Za-zА-Яа-яЁёІіЄєЇї-]{1,50}"/></p>
            <p><input type="email" name="email" required placeholder="Email" size="25"
                      pattern="[a-z0-9_%+-]+@[a-z0-9_]+\.[a-z]{2,}[\.a-z]{0,}"/></p>
            <p><input type="password" name="password" required
                      placeholder="<fmt:message key="label.password" bundle="${rm}"/>" size="25"
                      pattern="[A-Za-zА-Яа-яЁёІіЄєЇї0-9]{5,}"/></p>
            <p>
                <select size="1" name="userType">
                    <option value="User"><fmt:message key="label.user" bundle="${rm}"/></option>
                    <option value="Speaker"><fmt:message key="label.speaker" bundle="${rm}"/></option>
                </select>
            </p>
            <p><input type="submit" value="<fmt:message key="label.registration" bundle="${rm}"/>"/></p>
        </form>
    </div>
    <p>
        <c:choose>
            <c:when test="${not empty errorEmailForm}">
                <fmt:message key="errorEmailForm" bundle="${rm}"/>
            </c:when>
            <c:when test="${not empty errorEmptyForm}">
                <fmt:message key="errorEmptyForm" bundle="${rm}"/>
            </c:when>
            <c:when test="${not empty errorPassword}">
                <fmt:message key="errorPassword" bundle="${rm}"/>
            </c:when>
            <c:when test="${not empty errorUserExists}">
                <fmt:message key="errorUserExists" bundle="${rm}"/>
            </c:when>
            <c:when test="${not empty errorNameOrSurname}">
                <fmt:message key="errorNameOrSurname" bundle="${rm}"/>
            </c:when>
        </c:choose>
    </p>
</div>
</body>
</html>
