<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="header.jsp" charEncoding="utf-8"/>

<html>
<head>
    <title><fmt:message key="label.title" bundle="${rm}"/></title>
</head>
<body>
<h2 align="center"><fmt:message key="label.welcome" bundle="${rm}"/></h2>
<div class="blockCenter">
    <p><a href="views/register.jsp"><fmt:message key="label.signUp" bundle="${rm}"/></a></p>
    <p><a href="views/login.jsp"><fmt:message key="label.signIn" bundle="${rm}"/></a></p>
</div>
</body>
</html>
