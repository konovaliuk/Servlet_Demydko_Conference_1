<%--
  Created by IntelliJ IDEA.
  User: Ваня
  Date: 30.08.2019
  Time: 17:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <style>
        <c:import url="/WEB-INF/resources/css/styles.css" charEncoding="utf-8"/>
    </style>
</head>
<body>
<h2 align="center">Предложенные доклады</h2>
<c:forEach items="${sessionScope.offeredReportList}" var="report" varStatus="loop">
    <div><p>id : ${report.id}</p>
        <p>Тема доклада: ${report.name}</p>
        <p>Спикер: ${report.speaker.name} ${report.speaker.surname}</p>
        <p>loop index = "${loop.index}"</p>

        <form method="post" action="views/editReport.jsp">
            <input type="hidden" name="index" value="${loop.index}">
            <input type="submit" value="Внести изменения и подтвердить">
        </form>
        <form method="post" action="/Conference_war/controller?command=deleteReport">
            <input type="hidden" name="index" value="${loop.index}">
            <input type="submit" value="Удалить">
        </form>

    </div>
</c:forEach>
<p><a href="views/cabinet.jsp">Кабинет</a></p>
</body>
</html>
