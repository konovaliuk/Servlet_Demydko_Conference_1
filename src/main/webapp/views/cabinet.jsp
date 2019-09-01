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
<html>
<head>
    <title>Кабинет</title>
    <style>
        <c:import url="/WEB-INF/resources/css/styles.css" charEncoding="utf-8"/>
    </style>
</head>

<body>
<p>Кабинет</p>
<p> Вы зашли как ${sessionScope.user.name} ${sessionScope.user.surname} </p>
<c:choose>
    <c:when test="${sessionScope.user.position=='Admin'}">
        <div>
            <form method="post" action="/Conference_war/controller?command=assignPosition">
                <p><input type="email" name="email" required placeholder="Email пользователя" size="15"
                          pattern="[a-z0-9_%+-]+@[a-z0-9_]+\.[a-z]{2,}[\.a-z]{0,}"/></p>
                <p>
                    <select size="1" name="userType">
                        <option value="Moderator">Модератор</option>
                        <option value="Admin">Админ</option>
                        <option value="Speaker">Спикер</option>
                        <option value="User">Пользователь</option>
                    </select>
                </p>
                <p><input type="submit" value="Назначить на позицию"/></p>
            </form>
        </div>
        <div>
            <form method="post" action="/Conference_war/controller?command=addSpeakerRating">
                <p><input type="email" name="email" required placeholder="Email спикера" size="15"
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

                <p><input type="submit" value="Присвоить рейтинг"/></p>
            </form>
        </div>
        <%--        ${errorEmailForm}--%>
        <%--        ${errorSpeakerNotExists}--%>
        <%--        ${errorPosition}--%>
        <%--        ${successfulChanges}--%>
    </c:when>
    <c:when test="${sessionScope.user.position=='Moderator'}">
        <jsp:useBean id="now" class="java.util.Date"/>
        <div class="block1">
            <form method="post" action="/Conference_war/controller?command=addreport">
                <p>Выберите дату: <input type="date" name="date"
                                         min="<fmt:formatDate value="${now}" pattern="yyyy-MM-dd"/>" required></p>
                <p>Выберите время: <input type="time" name="time" required></p>
                <p><textarea name="theme" placeholder="Тема" required></textarea></p>
                <p><input type="text" name="city" size="30" placeholder="Выберите город" required/></p>
                <p><input type="text" name="street" size="30" placeholder="Выберите улицу" required/></p>
                <p><input type="text" name="building" size="30" placeholder="Выберите дом" required/></p>
                <p><input type="text" name="room" size="30" placeholder="Выберите кабинет" required/></p>
                <p><input type="email" name="speakerEmail" size="30" placeholder="Выберите спикера(email спикера)"
                          required/></p>

                    <%--                    ${successfulChanges}--%>
                    <%--                    ${errorEmptyForm}--%>
                    <%--                    ${errorSpeakerNotExists}--%>
                    <%--                    ${errorDate}--%>

                <p><input type="submit" value="Добавить доклад"></p>
            </form>
        </div>
        <div class="block1">
            <form method="post" action="/Conference_war/controller?command=showOfferedReports">
                <p><input type="submit" value="Посмотреть предложенные доклады"></p>
            </form>
        </div>
    </c:when>
    <c:when test="${sessionScope.user.position=='Speaker'}">
        <div class="block1">
            <form method="post" action="/Conference_war/controller?command=offerReport">
                <p><textarea name="theme" placeholder="Тема" required></textarea></p>
                    <%--                    ${successfulChanges}--%>
                    <%--                    ${noActionDone}--%>
                    <%--                    ${errorTheme}--%>
                <p><input type="submit" value="Предложить доклад"></p>
            </form>
        </div>
    </c:when>
    <c:otherwise>

    </c:otherwise>
</c:choose>
<p>${errorEmailForm}
    ${errorSpeakerNotExists}
    ${errorPosition}
    ${successfulChanges}


    ${errorEmptyForm}
    ${errorDate}

    ${noActionDone}
    ${errorTheme}</p>


<div class="block1">
    <form method="post" action="/Conference_war/controller?command=futureReports">
        <p><input type="submit" value="Посмотреть запланированные доклады"></p>
    </form>
</div>
<div class="block1">
    <form method="post" action="/Conference_war/controller?command=logout">
        <p><input type="submit" value="Выход"/></p>
    </form>
</div>

</body>
</html>
