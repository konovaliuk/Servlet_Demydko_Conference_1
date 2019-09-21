<%--
  Created by IntelliJ IDEA.
  User: Ваня
  Date: 21.08.2019
  Time: 9:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="header.jsp" charEncoding="utf-8"/>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h2 align="center"><fmt:message key="label.cabinet" bundle="${rm}"/></h2>
<div class="center">
    <div class="blockForButtons">
        <form method="post" action="${pageContext.request.contextPath}/controller?command=futureReports">
            <p><input type="submit" value="<fmt:message key="label.futureReports" bundle="${rm}"/>"></p>
        </form>
    </div>
    <c:choose>
        <c:when test="${sessionScope.user.position=='Admin'}">
            <div>
                <p><fmt:message key="label.assignToPosition" bundle="${rm}"/></p>
                <form method="post" action="${pageContext.request.contextPath}/controller?command=assignPosition">
                    <p><input type="email" name="email" required
                              placeholder="<fmt:message key="label.userEmail" bundle="${rm}"/>" size="15"
                              pattern="[a-z0-9_%+-]+@[a-z0-9_]+\.[a-z]{2,}[\.a-z]{0,}"/></p>
                    <p>
                        <select size="1" name="userType">
                            <option value="Moderator"><fmt:message key="label.moderator" bundle="${rm}"/></option>
                            <option value="Admin"><fmt:message key="label.admin" bundle="${rm}"/></option>
                            <option value="Speaker"><fmt:message key="label.speaker" bundle="${rm}"/></option>
                            <option value="User"><fmt:message key="label.user" bundle="${rm}"/></option>
                        </select>
                    </p>
                    <p><input type="submit" value="<fmt:message key="label.assignToPosition" bundle="${rm}"/>"/></p>
                </form>
            </div>
            <div>
                <p><fmt:message key="label.addRatingToSpeaker" bundle="${rm}"/></p>
                <form method="post" action="${pageContext.request.contextPath}/controller?command=addSpeakerRating">
                    <p><input type="email" name="email" required
                              placeholder="<fmt:message key="label.speakerEmail" bundle="${rm}"/>" size="15"
                              pattern="[a-z0-9_%+-]+@[a-z0-9_]+\.[a-z]{2,}[\.a-z]{0,}"/></p>
                    <p>
                        <select size="1" name="rating">
                            <option value="1">1</option>
                            <option value="1">2</option>
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
                    <p><input type="submit" value="<fmt:message key="label.assignRating" bundle="${rm}"/>"/></p>
                </form>
            </div>
            <div>
                <p><fmt:message key="label.addBonusesToSpeaker" bundle="${rm}"/></p>
                <form method="post" action="${pageContext.request.contextPath}/controller?command=addBonuses">
                    <p><input type="email" name="email" required
                              placeholder="<fmt:message key="label.speakerEmail" bundle="${rm}"/>" size="15"
                              pattern="[a-z0-9_%+-]+@[a-z0-9_]+\.[a-z]{2,}[\.a-z]{0,}"/></p>
                    <p><input type="text" placeholder="<fmt:message key="label.amountOfBonuses" bundle="${rm}"/>"
                              required
                              name="bonuses" pattern="[0-9]{1,}"/></p>
                    <input type="submit" value="<fmt:message key="label.addBonuses" bundle="${rm}"/>">
                </form>
            </div>
        </c:when>
        <c:when test="${sessionScope.user.position=='Moderator'}">
            <jsp:useBean id="now" class="java.util.Date"/>
            <div class="block1">
                <p><fmt:message key="label.addReport" bundle="${rm}"/></p>
                <form method="post" action="${pageContext.request.contextPath}/controller?command=addReport">
                    <p><fmt:message key="label.date" bundle="${rm}"/>: <input type="date" name="date"
                                                                              min="<fmt:formatDate value="${now}" pattern="yyyy-MM-dd"/>"
                                                                              required></p>
                    <p><fmt:message key="label.time" bundle="${rm}"/>: <input type="time" name="time" required></p>
                    <p><textarea name="theme" placeholder="<fmt:message key="label.theme" bundle="${rm}"/>"
                                 required></textarea></p>
                    <p><input type="text" name="city" size="30" pattern="[а-яА-Яa-zA-ZЇїЄєІі]{2,30}"
                              placeholder="<fmt:message key="label.city" bundle="${rm}"/>" required/>
                    </p>
                    <p><input type="text" name="street" size="30" pattern="[а-яА-Яa-zA-ZЇїЄєІі\-\s]{2,50}"
                              placeholder="<fmt:message key="label.street" bundle="${rm}"/>" required/>
                    </p>
                    <p><input type="text" name="building" size="30" pattern="[а-яА-Яa-zA-ZЇїЄєІі0-9\s/-]{1,10}"
                              placeholder="<fmt:message key="label.building" bundle="${rm}"/>" required/>
                    </p>
                    <p><input type="text" name="room" size="30" pattern="[а-яА-Яa-zA-ZЇїЄєІі0-9]{1,5}"
                              placeholder="<fmt:message key="label.room" bundle="${rm}"/>" required/>
                    </p>
                    <p><input type="email" name="speakerEmail" size="30"
                              pattern="[a-z0-9_%+-]+@[a-z0-9_]+\.[a-z]{2,}[\.a-z]{0,}"
                              placeholder="<fmt:message key="label.choseSpeaker" bundle="${rm}"/>" required/>
                    </p>
                    <p><input type="submit" value="<fmt:message key="label.addReport" bundle="${rm}"/>"></p>
                </form>
            </div>
            <div class="blockForButtons">
                <form method="post" action="${pageContext.request.contextPath}/controller?command=showOfferedReports">
                    <p><input type="submit" value="<fmt:message key="label.offeredReports" bundle="${rm}"/>"></p>
                </form>
            </div>
        </c:when>
        <c:when test="${sessionScope.user.position=='Speaker'}">
            <div class="block1">
                <form method="post" action="${pageContext.request.contextPath}/controller?command=offerReport">
                    <p><textarea name="theme" placeholder="<fmt:message key="label.theme" bundle="${rm}"/>"
                                 required></textarea></p>
                    <p><input type="submit" value="<fmt:message key="label.offerReport" bundle="${rm}"/>"></p>
                </form>
            </div>
        </c:when>
    </c:choose>
    <div class="blockForButtons">
        <form method="post" action="${pageContext.request.contextPath}/controller?command=pastReports">
            <p><input type="submit" value="<fmt:message key="label.pastReports" bundle="${rm}"/>"></p>
        </form>
    </div>
    <p>
        <c:choose>
            <c:when test="${not empty successfulChanges}">
                <fmt:message key="successfulChanges" bundle="${rm}"/>
            </c:when>
            <c:when test="${not empty errorEmailForm}">
                <fmt:message key="errorEmailForm" bundle="${rm}"/>
            </c:when>
            <c:when test="${not empty errorSpeakerNotExists}">
                <fmt:message key="errorSpeakerNotExists" bundle="${rm}"/>
            </c:when>
            <c:when test="${not empty errorPosition}">
                <fmt:message key="errorPosition" bundle="${rm}"/>
                ${userPosition}
            </c:when>
            <c:when test="${not empty errorNumber}">
                <fmt:message key="errorNumber" bundle="${rm}"/>
            </c:when>
            <c:when test="${not empty bonuses}">
                <fmt:message key="bonuses" bundle="${rm}"/>
            </c:when>
            <c:when test="${not empty errorAddress}">
                <fmt:message key="errorAddress" bundle="${rm}"/>
            </c:when>
            <c:when test="${not empty errorEmptyForm}">
                <fmt:message key="errorEmptyForm" bundle="${rm}"/>
            </c:when>
            <c:when test="${not empty errorDate}">
                <fmt:message key="errorDate" bundle="${rm}"/>
            </c:when>
            <c:when test="${not empty noActionDone}">
                <fmt:message key="noActionDone" bundle="${rm}"/>
            </c:when>
            <c:when test="${not empty errorTheme}">
                <fmt:message key="errorTheme" bundle="${rm}"/>
            </c:when>
        </c:choose>
    </p>
</div>
</body>
</html>
