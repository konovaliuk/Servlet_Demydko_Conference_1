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
<body>
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
                <form method="post" action="/Conference_war/controller?command=reportIndex">
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

<div class="blockCenter"><c:forEach items="${sessionScope.buttons}" var="offset">
    <div class="block2"> <form method="post" action="/Conference_war/controller?command=futureReports">
       <input type="hidden" name="offset" value="${offset}">
        <p><input type="submit" value="${offset}"></p>
   </form></div>
</c:forEach></div>

<div class="blockTop">
    <p>Количество елементов на странице</p>
    <form method="post" action="/Conference_war/controller?command=futureReports">
        <p>
            <select size="1"  name="maxCount">
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
                <option value="6">6</option>
                <option value="7">7</option>
                <option value="8">8</option>
                <option value="9">9</option>
                <option value="10">10</option>
            </select>
        </p>
    <p><input type="submit" value="Количество"></p>
</form></div>


<p><a href="views/cabinet.jsp">Кабинет</a></p>
</body>
</html>
