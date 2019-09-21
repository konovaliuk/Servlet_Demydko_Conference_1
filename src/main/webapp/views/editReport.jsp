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
</head>
<body>
<c:set var="report" value="${sessionScope.offeredReportList[index]}"/>
<jsp:useBean id="now" class="java.util.Date"/>
<div class="center">
    <div class="assign">
        <form method="post" action="${pageContext.request.contextPath}/controller?command=editReport">
            <input type="hidden" name="index" value="${index}">
            <p><fmt:message key="label.theme" bundle="${rm}"/>: <c:out value="${report.name}"/></p>
            <p><fmt:message key="label.speaker" bundle="${rm}"/>:
                <c:out value="${report.speaker.name} ${report.speaker.surname}"/>
            <p><fmt:message key="label.date" bundle="${rm}"/>: <input type="date" required name="date"
                                                                      min="<fmt:formatDate value="${now}" pattern="yyyy-MM-dd"/>"/>
            </p>
            <p><fmt:message key="label.time" bundle="${rm}"/>: <input type="time" required name="time"/></p>
            <p><input type="text" placeholder="<fmt:message key="label.city" bundle="${rm}"/>" name="city"
                      required pattern="[а-яА-Яa-zA-ZЇїЄєІі]{2,30}" size="25"/></p>
            <p><input type="text" placeholder="<fmt:message key="label.street" bundle="${rm}"/>" name="street"
                      required pattern="[а-яА-Яa-zA-ZЇїЄєІі\-\s]{2,50}" size="25"/></p>
            <p><input type="text" placeholder="<fmt:message key="label.building" bundle="${rm}"/>" name="building"
                      required pattern="[а-яА-Яa-zA-ZЇїЄєІі0-9\s/-]{1,10}" size="25"/></p>
            <p><input type="text" placeholder="<fmt:message key="label.room" bundle="${rm}"/>" name="room"
                      required pattern="[а-яА-Яa-zA-ZЇїЄєІі0-9]{1,5}" size="25"/></p>
            <p><input type="submit" value="<fmt:message key="label.pinReport" bundle="${rm}"/>"/></p>
        </form>
    </div>
    <p><c:choose>
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
    </p>
</div>
</body>
</html>
