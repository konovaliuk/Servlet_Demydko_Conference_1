<%--
  Created by IntelliJ IDEA.
  User: Ваня
  Date: 30.08.2019
  Time: 17:06
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
<h2 align="center"><fmt:message key="label.offeredReports" bundle="${rm}"/></h2>
<div class="center">
    <c:forEach items="${sessionScope.offeredReportList}" var="report" varStatus="loop">
        <div><p>id : ${report.id}</p>
            <p><fmt:message key="label.theme" bundle="${rm}"/>: ${report.name}</p>
            <p><fmt:message key="label.speaker" bundle="${rm}"/>: ${report.speaker.name} ${report.speaker.surname}</p>
            <form method="post" action="${pageContext.request.contextPath}/controller?command=reportIndex">
                <input type="hidden" name="index" value="${loop.index}">
                <input type="hidden" name="requestURI" value="${pageContext.request.getRequestURI()}">
                <input type="submit" value="<fmt:message key="label.makeChangesAndConfirm" bundle="${rm}"/>">
            </form>
            <form method="post" action="${pageContext.request.contextPath}/controller?command=deleteOfferedReport">
                <input type="hidden" name="reportId" value="${report.id}">
                <input type="submit" value="<fmt:message key="label.delete" bundle="${rm}"/>">
            </form>
        </div>
    </c:forEach>
</div>
<div class="blockBottom">
    <c:forEach items="${sessionScope.buttonsOffered}" var="button">
        <form class="buttons" method="post"
              action="${pageContext.request.contextPath}/controller?command=showOfferedReports">
            <input type="hidden" name="button" value="${button}">
            <c:choose>
                <c:when test="${sessionScope.offeredButton==button}">
                    <input class="b" type="submit" value="${button}">
                </c:when>
                <c:otherwise>
                    <input type="submit" value="${button}">
                </c:otherwise>
            </c:choose>
        </form>
    </c:forEach>
</div>
<div class="elements">
    <fmt:message key="label.amountElementsOnPage" bundle="${rm}"/>
    <form method="post"
          action="${pageContext.request.contextPath}/controller?command=showOfferedReports">
        <select size="1" name="maxCount" onchange="submit()">
            <option>${sessionScope.maxCountOffered}</option>
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
    </form>
</div>
</body>
</html>
