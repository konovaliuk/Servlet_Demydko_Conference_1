<%--
  Created by IntelliJ IDEA.
  User: Ваня
  Date: 23.08.2019
  Time: 15:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<h1 align="center">Редактирование доклада</h1>
<c:choose>
    <c:when test="${not empty pageContext.request.getParameter('index')}">
        <c:set var="index" value="${pageContext.request.getParameter('index')}"/>
</c:when>
    <c:otherwise>
        <c:set var="index" value="${reportIndex}"/>
    </c:otherwise>
</c:choose>

<c:set var="report" value="${sessionScope.reportList[index]}"/>
<p>"${sessionScope.reportList}"</p>
<form method="post" action="/Conference_war/controller?command=updateReport">
    <input type="hidden" name="index" value="${index}">
    <c:out value="${index}" />

    <c:out value="${report}" />

    <p>Тема: <c:out value="${report.name}"/>
       | Изменить тему: <textarea name="theme"></textarea></p>

    <p>Дата: <c:out value="${report.date}"/>
       | Изменить дату: <input type="date" name="date"/></p>

    <p>Время: <fmt:formatDate value="${report.time}" type="time" timeStyle="short" />
       | Изменить время: <input type="time" name="time"/></p>

    <p>Город: <c:out value="${report.address.city}"/>
       | Изменить город: <input type="text" name="city"/></p>

    <p>Улица: <c:out value="${report.address.street}"/>
        | Изменить улицу: <input type="text" name="street"/></p>

    <p>Дом: <c:out value="${report.address.building}"/>
        | Изменить дом: <input type="text" name="building"/></p>

    <p>Кабинет: <c:out value="${report.address.room}"/>
        | Изменить кабинет: <input type="text" name="room"/></p>

    <p>Спикер: <c:out value="${report.speaker}"/>
       | Изменить спикера: <input type="email" name="speakerEmail"/></p>

    ${successfulChanges}
    ${noActionDone}
    <p><input type="submit" value="Изменить доклад"/></p>
</form>

</body>
</html>
