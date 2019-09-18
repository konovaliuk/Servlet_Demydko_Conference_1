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
<%--    <style>--%>
<%--        <c:import url="/WEB-INF/resources/css/styles.css" charEncoding="utf-8"/>--%>
<%--    </style>--%>
</head>
<body>
<h2 align="center"><fmt:message key="label.offeredReports" bundle="${rm}"/></h2>
<c:forEach items="${sessionScope.offeredReportList}" var="report" varStatus="loop">
    <div><p>id : ${report.id}</p>
        <p><fmt:message key="label.theme" bundle="${rm}"/>: ${report.name}</p>
        <p><fmt:message key="label.speaker" bundle="${rm}"/>: ${report.speaker.name} ${report.speaker.surname}</p>
        <p>loop index = "${loop.index}"</p>

        <form method="post" action="/Conference_war/controller?command=reportIndex">
            <input type="hidden" name="index" value="${loop.index}">
            <input type="hidden" name="requestURI" value="${pageContext.request.getRequestURI()}">
            <input type="submit" value="<fmt:message key="label.makeChangesAndConfirm" bundle="${rm}"/>">
        </form>
        <form method="post" action="/Conference_war/controller?command=deleteOfferedReport">
            <input type="hidden" name="reportId" value="${report.id}">
            <input type="submit" value="<fmt:message key="label.delete" bundle="${rm}"/>">
        </form>

    </div>
</c:forEach>
<%--<p><a href="views/cabinet.jsp"><fmt:message key="label.cabinet" bundle="${rm}"/></a></p>--%>
</body>
</html>
