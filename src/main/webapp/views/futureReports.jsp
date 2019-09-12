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
<%--    <style>--%>
<%--        <c:import url="/WEB-INF/resources/css/styles.css" charEncoding="utf-8"/>--%>
<%--    </style>--%>
</head>
<body>

<h2 align="center"><fmt:message key="label.futureConference" bundle="${rm}"/></h2>
<c:set var="countOfVisitors" value="${sessionScope.countOfVisitors}"/>
<c:forEach items="${sessionScope.reportList}" var="report" varStatus="loop">
    <div><p>id : ${report.id}</p>
        <p><fmt:message key="label.theme" bundle="${rm}"/>: ${report.name}</p>
        <p><fmt:message key="label.date" bundle="${rm}"/>: ${report.date}</p>
        <p><fmt:message key="label.time" bundle="${rm}"/>: <fmt:formatDate value="${report.time}" type="time" timeStyle="short"/></p>
        <p><fmt:message key="label.city" bundle="${rm}"/>: ${report.address.city}</p>
        <p><fmt:message key="label.street" bundle="${rm}"/>: ${report.address.street}</p>
        <p><fmt:message key="label.building" bundle="${rm}"/>: ${report.address.building}</p>
         <p><fmt:message key="label.room" bundle="${rm}"/>: ${report.address.room}</p>
        <p><fmt:message key="label.speaker" bundle="${rm}"/>: ${report.speaker.name} ${report.speaker.surname}</p>
        <p><fmt:message key="label.registered" bundle="${rm}"/>: ${countOfVisitors.get(report.id)} </p>
        <p>loop index = "${loop.index}"</p>
        <c:choose>

            <c:when test="${sessionScope.user.position=='Moderator'}">
                <form method="post" action="/Conference_war/controller?command=reportIndex">
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
                        <form method="post" action="/Conference_war/controller?command=conferenceRegister">
                            <input type="hidden" name="index" value="${loop.index}">
                            <input type="submit" value="<fmt:message key="label.signUp" bundle="${rm}"/>">
                        </form>
                    </c:otherwise>
                </c:choose>
            </c:when>
        </c:choose>
    </div>
</c:forEach>

<div class="blockBottom"><c:forEach items="${sessionScope.buttons}" var="offset">
    <div class="block2"> <form method="post" action="/Conference_war/controller?command=futureReports">
       <input type="hidden" name="offset" value="${offset}">
        <p><input type="submit" value="${offset}"></p>
   </form></div>
</c:forEach></div>

<div class="blockTop2">
    <p><fmt:message key="label.amountElementsOnPage" bundle="${rm}"/></p>
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
    <p><input type="submit" value="<fmt:message key="label.apply" bundle="${rm}"/>"></p>
</form></div>


<p><a href="views/cabinet.jsp"><fmt:message key="label.cabinet" bundle="${rm}"/></a></p>
</body>
</html>
