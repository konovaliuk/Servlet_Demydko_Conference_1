<%--
  Created by IntelliJ IDEA.
  User: Ваня
  Date: 11.09.2019
  Time: 15:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Error Page</title>
</head>
<body>
 <p align="center">Request from ${pageContext.errorData.requestURI} is failed</p>
<br/>
 <h1 align="center">Status code: ${pageContext.errorData.statusCode}</h1>
</body>
</html>
