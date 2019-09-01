<%--
  Created by IntelliJ IDEA.
  User: Ваня
  Date: 20.08.2019
  Time: 10:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <title>Регистрация</title>
    <style>
        <c:import url="/WEB-INF/resources/css/styles.css" charEncoding="utf-8"/>
    </style>
</head>
<body>
<h1>Регистрация</h1>
<form method="post" action="/Conference_war/controller?command=register">
    <p><input type="text" name="name" required placeholder="Имя" size="15"
              pattern="[A-Za-zА-Яа-яЁёІіЄєЇї-]{1,50}"/></p>
    <p><input type="text" name="surname" required placeholder="Фамилия" size="15"
              pattern="[A-Za-zА-Яа-яЁёІіЄєЇї-]{1,50}"/></p>
    <p><input type="email" name="email" required placeholder="Email" size="15"
    pattern="[a-z0-9_%+-]+@[a-z0-9_]+\.[a-z]{2,}[\.a-z]{0,}"/></p>
    <p><input type="password" name="password" required placeholder="Пароль" size="15"
              pattern="[A-Za-zА-Яа-яЁёІіЄєЇї0-9]{5,}"/></p>
    <p>
        <select size="1" name="userType">
            <option value="User">Пользователь</option>
            <option value="Speaker">Спикер</option>
        </select>
    </p>

    ${errorEmptyForm}

    ${errorEmailForm}

    ${errorPassword}

    ${errorUserExists}

    ${errorNameOrSurname}

    <p><input type="submit" value="Регистрация"/></p>
</form>
</body>
</html>
