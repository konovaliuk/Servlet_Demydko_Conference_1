<%--
  Created by IntelliJ IDEA.
  User: Ваня
  Date: 22.08.2019
  Time: 10:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="header.jsp" charEncoding="utf-8"/>
<html>
<head>
    <title><fmt:message key="label.registration" bundle="${rm}"/></title>
    <%--    <style>--%>
    <%--        <c:import url="/WEB-INF/resources/css/styles.css" charEncoding="utf-8"/>--%>
    <%--    </style>--%>
</head>
<body>

<h2><fmt:message key="label.successRegister" bundle="${rm}"/> ${sessionScope.user.name}! </h2>

<p><a href="views/cabinet.jsp"><fmt:message key="label.cabinet" bundle="${rm}"/></a></p>

<form method="post" action="/Conference_war/controller?command=logout">
    <input type="submit" value="<fmt:message key="label.exit" bundle="${rm}"/>"/>

</form>
</body>
</html>
