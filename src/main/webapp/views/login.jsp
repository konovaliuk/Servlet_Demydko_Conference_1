<%--
  Created by IntelliJ IDEA.
  User: Ваня
  Date: 20.08.2019
  Time: 10:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Вход</title>
    <style>
        <c:import url="/WEB-INF/resources/css/styles.css" charEncoding="utf-8"/>
    </style>
</head>
<body>
<h1>Вход</h1>
<form method="post" action="/Conference_war/controller?command=login">
    <p><input type="email" name="email" placeholder="Email" size="15" required/></p>
    <p><input type="password" name="password" placeholder="Пароль" size="15"
              pattern="[A-Za-zА-Яа-яЁёІіЄєЇї0-9]{5,}" required/></p>

    ${errorEmailForm}

    ${errorPassword}

    ${errorUserNotExists}

    <p><input type="submit" value="Вход"/></p>
</form>
</body>
</html>
