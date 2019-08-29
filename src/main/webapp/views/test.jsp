<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Ваня
  Date: 27.08.2019
  Time: 19:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<p>"${pageContext.request.getParameter("index")}"</p>
<c:set var="index" value="${pageContext.request.getParameter('index')}"/>
<c:set var="report" value="${sessionScope.reportList[index]}"/>
<c:out value="${report.id}"/>
<br>
<c:out value="${report.name}"/>
<br>
<c:out value="${report.date}"/>
<%--<c:out value="${sessionScope.reportList[index]}"/>--%>


</body>
</html>
