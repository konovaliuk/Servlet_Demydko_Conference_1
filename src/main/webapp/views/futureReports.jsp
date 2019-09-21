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

<c:import url="header.jsp" charEncoding="utf-8"/>

<html>
<head>
    <title>Title</title>
</head>
<body>
<h2 align="center"><fmt:message key="label.futureConference" bundle="${rm}"/></h2>
<c:set var="countOfVisitors" value="${sessionScope.countOfVisitors}"/>
<div class="center">
    <c:forEach items="${sessionScope.reportList}" var="report" varStatus="loop">
        <div class="futureReports">
            <p>id : ${report.id}</p>
            <p><fmt:message key="label.theme" bundle="${rm}"/>: ${report.name}</p>
            <p><fmt:message key="label.date" bundle="${rm}"/>: ${report.date}</p>
            <p><fmt:message key="label.time" bundle="${rm}"/>: <fmt:formatDate value="${report.time}" type="time"
                                                                               timeStyle="short"/></p>
            <p><fmt:message key="label.city" bundle="${rm}"/>: ${report.address.city}</p>
            <p><fmt:message key="label.street" bundle="${rm}"/>: ${report.address.street}</p>
            <p><fmt:message key="label.building" bundle="${rm}"/>: ${report.address.building}</p>
            <p><fmt:message key="label.room" bundle="${rm}"/>: ${report.address.room}</p>
            <p><fmt:message key="label.speaker" bundle="${rm}"/>: ${report.speaker.name} ${report.speaker.surname}</p>
            <p><fmt:message key="label.registered" bundle="${rm}"/>: ${countOfVisitors.get(report.id)} </p>
            <c:choose>
                <c:when test="${sessionScope.user.position=='Moderator'}">
                    <form method="post" action="${pageContext.request.contextPath}/controller?command=reportIndex">
                        <input type="hidden" name="index" value="${loop.index}">
                        <input type="submit" value="<fmt:message key="label.change" bundle="${rm}"/>">
                    </form>
                </c:when>
                <c:when test="${sessionScope.user.position=='User'}">
                    <c:choose>
                        <c:when test="${report.isUserRegistered==true}">
                            <c:choose>
                                <c:when test="${report.id==reportId}">
                                    <c:if test="${not empty errorAlreadyRegistered}">
                                        <fmt:message key="errorAlreadyRegistered" bundle="${rm}"/>
                                    </c:if>
                                </c:when>
                                <c:otherwise>
                                    <fmt:message key="label.youAreRegistered" bundle="${rm}"/>
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                            <form method="post"
                                  action="${pageContext.request.contextPath}/controller?command=conferenceRegister">
                                <input type="hidden" name="index" value="${loop.index}">
                                <input type="submit" value="<fmt:message key="label.signUp" bundle="${rm}"/>">
                            </form>
                        </c:otherwise>
                    </c:choose>
                </c:when>
            </c:choose>
        </div>
    </c:forEach>
</div>
<div class="blockBottom">
    <c:forEach items="${sessionScope.buttons}" var="button">
        <form class="buttons" method="post"
              action="${pageContext.request.contextPath}/controller?command=futureReports">
            <input type="hidden" name="button" value="${button}">
            <c:choose>
                <c:when test="${sessionScope.futureButton==button}">
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
    <form method="post" action="${pageContext.request.contextPath}/controller?command=futureReports">
        <select size="1" name="maxCount" onchange="submit()">
            <option>${sessionScope.maxCount}</option>
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
