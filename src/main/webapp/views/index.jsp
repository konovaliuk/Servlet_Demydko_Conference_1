<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<c:import url="header.jsp"/>
<html>
<head>
    <title><fmt:message key="label.title" bundle="${rm}"/></title>
</head>
<body>
<h2 align="center"><fmt:message key="label.welcome" bundle="${rm}"/></h2>
<div class="blockCenter">

    <form method="post" action="views/register.jsp">
        <p align="center"><input type="submit" value="<fmt:message key="label.signUp" bundle="${rm}"/>"></p>
    </form>
    <form method="post" action="views/login.jsp">
        <p align="center"><input type="submit" value="<fmt:message key="label.signIn" bundle="${rm}"/>"></p>
    </form>
</div>
</body>
</html>
