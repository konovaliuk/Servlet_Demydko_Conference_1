<%--
  Created by IntelliJ IDEA.
  User: Ваня
  Date: 30.08.2019
  Time: 18:49
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
<c:choose>
    <c:when test="${not empty pageContext.request.getParameter('index')}">
        <c:set var="index" value="${pageContext.request.getParameter('index')}"/>
    </c:when>
    <c:otherwise>
        <c:set var="index" value="${reportIndex}"/>
    </c:otherwise>
</c:choose>

<c:set var="report" value="${sessionScope.offeredReportList[index]}"/>
<jsp:useBean id="now" class="java.util.Date" />
<p>"${sessionScope.offeredReportList}"</p>
<%--<form method="post" action="/Conference_war/controller?command=editReport">--%>
<form method="post" action="/Conference_war/controller?command=editReport">
    <input type="hidden" name="index" value="${index}">
    <c:out value="${index}" />

    <c:out value="${report}" />

    <p>Тема: <c:out value="${report.name}"/></p>
    <p>Спикер: <c:out value="${report.speaker.name} ${report.speaker.surname}"/>

    <p>Дата:<input type="date" name="date" min="<fmt:formatDate value="${now}" pattern="yyyy-MM-dd"/>"/></p>

    <p><input type="time" placeholder="Время" name="time"/></p>

    <p><input type="text" placeholder="Город" name="city"/></p>

    <p><input type="text" placeholder="Улица" name="street"/></p>

    <p><input type="text" placeholder="Дом" name="building"/></p>

    <p><input type="text"  placeholder="Кабинет" name="room"/></p>

    ${successfulChanges}
    ${errorEmptyForm}
    ${errorAddress}
    <p><input type="submit" value="Закрепить доклад"/></p>
</form>
<p><a href="/Conference_war/views/cabinet.jsp">Кабинет</a></p>
</body>
</html>
