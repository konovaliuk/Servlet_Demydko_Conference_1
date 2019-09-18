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

<c:import url="header.jsp" charEncoding="utf-8"/>

<html>
<head>
    <title>Title</title>
    <style>
        <c:import url="/WEB-INF/resources/css/styles.css" charEncoding="utf-8"/>
    </style>
</head>
<body>
<%--<h1 align="center"><fmt:message key="label.reportEditing" bundle="${rm}"/></h1>--%>

<c:set var="report" value="${sessionScope.reportList[index]}"/>
<jsp:useBean id="now" class="java.util.Date"/>

<form method="post" action="${pageContext.request.contextPath}/controller?command=updateReport">
    <input type="hidden" name="index" value="${index}">

    <div class="blockUpdateReport">
        <p align="center"><fmt:message key="label.theme" bundle="${rm}"/> : <c:out value="${report.name}"/></p>
        <p align="left"> <fmt:message key="label.changeTheme" bundle="${rm}"/>: <textarea name="theme"></textarea></p>
        <hr>
        <p align="center"><fmt:message key="label.date" bundle="${rm}"/>: <c:out value="${report.date}"/></p>
           <p align="left"> <fmt:message key="label.changeDate" bundle="${rm}"/>:
               <input type="date" name="date" min="<fmt:formatDate value="${now}" pattern="yyyy-MM-dd"/>"/></p>
        <hr>
        <p align="center"><fmt:message key="label.time" bundle="${rm}"/>: <fmt:formatDate value="${report.time}" type="time"
                                                                           timeStyle="short"/></p>
        <p align="left"> <fmt:message key="label.changeTime" bundle="${rm}"/> : <input type="time" name="time"/></p>
        <hr>
        <p align="center"><fmt:message key="label.city" bundle="${rm}"/>: <c:out value="${report.address.city}"/></p>
        <p align="left"><fmt:message key="label.changeCity" bundle="${rm}"/> : <input type="text" name="city"
                                                                                      pattern="[а-яА-Яa-zA-ZЇїЄєІі]{2,30}"/></p>
        <hr>

        <p align="center"><fmt:message key="label.street" bundle="${rm}"/> : <c:out value="${report.address.street}"/></p>
            <p align="left"><fmt:message key="label.changeStreet" bundle="${rm}"/>: <input type="text" name="street"
                                                                              pattern="[а-яА-Яa-zA-ZЇїЄєІі\-\s]{2,50}"/></p>
        <hr>
        <p align="center"><fmt:message key="label.building" bundle="${rm}"/>: <c:out value="${report.address.building}"/></p>
        <p align="left"><fmt:message key="label.changeBuilding" bundle="${rm}"/>: <input type="text" name="building"
                                                                            pattern="[а-яА-Яa-zA-ZЇїЄєІі0-9\s/-]{1,10}"/></p>
        <hr>
        <p align="center"><fmt:message key="label.room" bundle="${rm}"/>: <c:out value="${report.address.room}"/></p>
            <p align="left"><fmt:message key="label.changeRoom" bundle="${rm}"/>: <input type="text" name="room"
                                                                           pattern="[а-яА-Яa-zA-ZЇїЄєІі0-9]{1,5}"/></p>
        <hr>
        <p align="center"><fmt:message key="label.speaker" bundle="${rm}"/>: <c:out value="${report.speaker.name}"/>
            <c:out value="${report.speaker.surname}"/></p>
            <p align="left"><fmt:message key="label.changeSpeaker" bundle="${rm}"/>: <input type="email"
               placeholder="<fmt:message key="label.speakerEmail" bundle="${rm}"/>" name="speakerEmail"pattern="[a-z0-9_%+-]+@[a-z0-9_]+\.[a-z]{2,}[\.a-z]{0,}"/></p>

        <p align="center"><input type="submit" value="<fmt:message key="label.changeReport" bundle="${rm}"/>"/></p>
    </div>


</form>
<p align="right">
    <c:choose>
        <c:when test="${not empty errorEmailForm}">
            <fmt:message key="errorEmailForm" bundle="${rm}"/>
        </c:when>
        <c:when test="${not empty successfulChanges}">
            <fmt:message key="successfulChanges" bundle="${rm}"/>
        </c:when>
        <c:when test="${not empty noActionDone}">
            <fmt:message key="noActionDone" bundle="${rm}"/>
        </c:when>
        <c:when test="${not empty errorAddress}">
            <fmt:message key="errorAddress" bundle="${rm}"/>
        </c:when>
        <c:when test="${not empty errorDate}">
            <fmt:message key="errorDate" bundle="${rm}"/>
        </c:when>
        <c:when test="${not empty errorTheme}">
            <fmt:message key="errorTheme" bundle="${rm}"/>
        </c:when>
        <c:when test="${not empty errorSpeakerNotExists}">
            <fmt:message key="errorSpeakerNotExists" bundle="${rm}"/>
        </c:when>
        <c:when test="${not empty NoChangesMade}">
            <fmt:message key="NoChangesMade" bundle="${rm}"/>
        </c:when>
    </c:choose>
</p>
<%--<p><a href="${pageContext.request.contextPath}/views/cabinet.jsp"><fmt:message key="label.cabinet" bundle="${rm}"/></a>--%>
</p>
</body>
</html>
