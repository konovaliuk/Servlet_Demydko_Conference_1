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

<c:import url="header.jsp" charEncoding="utf-8"/>

<html>
<head>
    <title>Title</title>
<%--    <style>--%>
<%--        <c:import url="/WEB-INF/resources/css/styles.css" charEncoding="utf-8"/>--%>
<%--    </style>--%>
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


<form method="post" action="${pageContext.request.contextPath}/controller?command=editReport">
    <input type="hidden" name="index" value="${index}">
    <c:out value="${index}" />

    <c:out value="${report}" />

    <p><fmt:message key="label.theme" bundle="${rm}"/>: <c:out value="${report.name}"/></p>
    <p><fmt:message key="label.speaker" bundle="${rm}"/>: <c:out value="${report.speaker.name} ${report.speaker.surname}"/>

    <p><fmt:message key="label.date" bundle="${rm}"/>:<input type="date" name="date" min="<fmt:formatDate value="${now}" pattern="yyyy-MM-dd"/>"/></p>

    <p><input type="time" placeholder="<fmt:message key="label.time" bundle="${rm}"/>" name="time"/></p>

    <p><input type="text" placeholder="<fmt:message key="label.city" bundle="${rm}"/>" name="city" pattern="[а-яА-Яa-zA-ZЇїЄєІі]{2,30}"/></p>

    <p><input type="text" placeholder="<fmt:message key="label.street" bundle="${rm}"/>" name="street" pattern="[а-яА-Яa-zA-ZЇїЄєІі\-\s]{2,50}"/></p>

    <p><input type="text" placeholder="<fmt:message key="label.building" bundle="${rm}"/>" name="building" pattern="[а-яА-Яa-zA-ZЇїЄєІі0-9\s/-]{1,10}"/></p>

    <p><input type="text" placeholder="<fmt:message key="label.room" bundle="${rm}"/>" name="room" pattern="[а-яА-Яa-zA-ZЇїЄєІі0-9]{1,5}"/></p>



    <c:choose>
        <c:when test="${not empty successfulChanges}">
            <fmt:message key="successfulChanges" bundle="${rm}"/>
        </c:when>
        <c:when test="${not empty errorEmptyForm}">
            <fmt:message key="errorEmptyForm" bundle="${rm}"/>
        </c:when>
        <c:when test="${not empty errorAddress}">
            <fmt:message key="errorAddress" bundle="${rm}"/>
        </c:when>
    </c:choose>



    <p><input type="submit" value="<fmt:message key="label.pinReport" bundle="${rm}"/>"/></p>
</form>
<%--<p><a href="${pageContext.request.contextPath}/views/cabinet.jsp"><fmt:message key="label.cabinet" bundle="${rm}"/></a></p>--%>
</body>
</html>
