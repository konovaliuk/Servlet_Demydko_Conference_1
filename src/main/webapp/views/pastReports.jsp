<%--
  Created by IntelliJ IDEA.
  User: Ваня
  Date: 02.09.2019
  Time: 9:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
    <style>
        <c:import url="/WEB-INF/resources/css/styles.css" charEncoding="utf-8"/>
    </style>
</head>
<body>
<h2 align="center">Прошедшие конференции</h2>
${sessionScope.pastReportList}
<p> Вы зашли как ${sessionScope.user.name} ${sessionScope.user.surname} </p>
<c:forEach items="${sessionScope.pastReportList}" var="report" varStatus="loop">
    <div><p>id : ${report.id}</p>
        <p>Тема доклада: ${report.name}</p>
        <p>Дата: ${report.date}</p>
        <p>Время: <fmt:formatDate value="${report.time}" type="time" timeStyle="short"/></p>
        <p>Адрес: г.${report.address.city}, ул.${report.address.street}, дом ${report.address.building},
            каб.${report.address.room}</p>
        <p>Спикер: ${report.speaker.name} ${report.speaker.surname}</p>
        <p>loop index = "${loop.index}"</p>

            <c:if test="${sessionScope.user.position=='Admin'}">
                <form method="post" action="/Conference_war/controller?command=addPresence">
                    <input type="hidden" name="index" value="${loop.index}">
                    <p><input type="text" placeholder="Посещаемость" required name="presence" pattern="[0-9]{1,}"/></p>
                    <input type="submit" value="Установить посещаемость">
                </form>
                <form method="post" action="/Conference_war/controller?command=deletePastReport">
                    <input type="hidden" name="reportId" value="${report.id}">
                    <input type="submit" value="Удалить">
                </form>
            </c:if>
    </div>
</c:forEach>
<p>${successfulChanges}
${errorNumber}</p>
<p><a href="views/cabinet.jsp">Кабинет</a></p>
</body>
</html>
