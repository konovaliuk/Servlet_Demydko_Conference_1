<%--
  Created by IntelliJ IDEA.
  User: Ваня
  Date: 23.08.2019
  Time: 14:02
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
<p>
<h2 align="center">Запланированные конференции</h2>
<p> Вы зашли как ${sessionScope.user.name} ${sessionScope.user.surname} </p>
<c:forEach items="${sessionScope.reportList}" var="report" varStatus="loop">
    <div><p>id : ${report.id}</p>
        <p>Тема доклада: ${report.name}</p>
        <p>Дата: ${report.date}</p>
        <p>Время: <fmt:formatDate value="${report.time}" type="time" timeStyle="short"/></p>
        <p>Адрес: г.${report.address.city}, ул.${report.address.street}, дом ${report.address.building},
            каб.${report.address.room}</p>
        <p>Спикер: ${report.speaker.name} ${report.speaker.surname}</p>
        <p>loop index = "${loop.index}"</p>
        <c:choose>
            <c:when test="${sessionScope.user.position=='Moderator'}">
                <form method="post" action="views/updateReport.jsp">
                    <input type="hidden" name="index" value="${loop.index}">
                    <input type="submit" value="Внести изменения">
                </form>
            </c:when>
            <c:when test="${sessionScope.user.position=='User'}">
                <c:choose>
                    <c:when test="${report.isUserRegistered==true}">
                        <c:choose>
                            <c:when test="${report.id==reportId}">
                                ${errorAlreadyRegistered}
                            </c:when>
                            <c:otherwise>
                                <p>Вы зарегистрированы</p>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <form method="post" action="/Conference_war/controller?command=conferenceRegister">
                            <input type="hidden" name="index" value="${loop.index}">
                            <input type="submit" value="Зарегистрироваться">
                        </form>
                    </c:otherwise>
                </c:choose>
            </c:when>
        </c:choose>
    </div>
</c:forEach>
<p><a href="views/cabinet.jsp">Кабинет</a></p>
</body>
</html>
