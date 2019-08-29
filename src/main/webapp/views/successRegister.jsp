<%--
  Created by IntelliJ IDEA.
  User: Ваня
  Date: 22.08.2019
  Time: 10:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Успех</title>

</head>
<body>

<h2>Поздравляет с успешной регистрацией ${sessionScope.user.name}! </h2>

<p><a href="views/cabinet.jsp">Личный кабинет</a></p>
<form method="post" action="/Conference_war/controller?command=logout">
    <p><input type="submit" value="Выход"/></p>
</form>
</body>
</html>
