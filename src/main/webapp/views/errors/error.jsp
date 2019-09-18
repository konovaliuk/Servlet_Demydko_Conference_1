<%--
  Created by IntelliJ IDEA.
  User: Ваня
  Date: 17.09.2019
  Time: 18:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Error</title>
    <style>
        <c:import url="/WEB-INF/resources/css/styles.css" charEncoding="utf-8"/>
    </style>
</head>
<body>

<div class="blockCenter">
    <h1>Ops something wrong</h1>
    <h3 align="center">Request from "${wrongAction}" is failed</h3>
</div>
</body>
</html>
