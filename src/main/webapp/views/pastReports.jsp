<%--
  Created by IntelliJ IDEA.
  User: Ваня
  Date: 02.09.2019
  Time: 9:54
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
<h2 align="center"><fmt:message key="label.pastReports" bundle="${rm}"/></h2>
<c:forEach items="${sessionScope.pastReportList}" var="report" varStatus="loop">
    <div><p>id : ${report.id}</p>
        <p><fmt:message key="label.theme" bundle="${rm}"/>: ${report.name}</p>
        <p><fmt:message key="label.date" bundle="${rm}"/>: ${report.date}</p>
        <p><fmt:message key="label.time" bundle="${rm}"/>: <fmt:formatDate value="${report.time}" type="time"
                                                                           timeStyle="short"/></p>
        <p><fmt:message key="label.city" bundle="${rm}"/>: ${report.address.city}</p>
        <p><fmt:message key="label.street" bundle="${rm}"/>: ${report.address.street}</p>
        <p><fmt:message key="label.building" bundle="${rm}"/>: ${report.address.building}</p>
        <p><fmt:message key="label.room" bundle="${rm}"/>: ${report.address.room}</p>
        <p><fmt:message key="label.speaker" bundle="${rm}"/>: ${report.speaker.name} ${report.speaker.surname}</p>
        <p><fmt:message key="label.presence" bundle="${rm}"/>: ${sessionScope.pastReportPresence.get(report.id)}</p>

        <p>loop index = "${loop.index}"</p>

        <c:if test="${sessionScope.user.position=='Admin'}">
            <form method="post" action="/Conference_war/controller?command=addPresence">
                <input type="hidden" name="index" value="${loop.index}">
                <p><input type="text" placeholder="<fmt:message key="label.attendance" bundle="${rm}"/>" required
                          name="presence" pattern="[0-9]{1,}"/></p>
                <input type="submit" value="<fmt:message key="label.setAttendance" bundle="${rm}"/>">
            </form>
            <form method="post" action="/Conference_war/controller?command=deletePastReport">
                <input type="hidden" name="reportId" value="${report.id}">
                <input type="submit" value="<fmt:message key="label.delete" bundle="${rm}"/>">
            </form>
        </c:if>
    </div>
</c:forEach>

<p>
    <c:choose>
        <c:when test="${not empty successfulChanges}">
            <fmt:message key="successfulChanges" bundle="${rm}"/>
        </c:when>
        <c:when test="${not empty errorNumber}">
            <fmt:message key="errorNumber" bundle="${rm}"/>
        </c:when>
    </c:choose>
</p>

<div class="blockBottom">
    <c:forEach items="${sessionScope.buttonsPast}" var="offsetPast">
        <div class="block2">
            <form method="post" action="/Conference_war/controller?command=pastReports">
                <input type="hidden" name="offsetPast" value="${offsetPast}">
                <p><input type="submit" value="${offsetPast}"></p>
            </form>
        </div>
    </c:forEach></div>
<div class="blockTop2">

    <p><fmt:message key="label.amountElementsOnPage" bundle="${rm}"/></p>
    <form method="post" action="/Conference_war/controller?command=pastReports">
        <p>
            <select size="1" name="maxCountPast">
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
    </form>
</div>


<%--<p><a href="views/cabinet.jsp"><fmt:message key="label.cabinet" bundle="${rm}"/></a></p>--%>
</body>
</html>
