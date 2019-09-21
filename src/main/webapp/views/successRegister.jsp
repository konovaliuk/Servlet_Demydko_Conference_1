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
    <title>Title</title>
</head>
<body>
<h2 align="center" style="margin-top: 10%"><fmt:message key="label.successRegister" bundle="${rm}"/> ${sessionScope.user.name}! </h2>
</body>
</html>
