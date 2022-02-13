<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Добавление блюда</title>
    <link href="resources/css/style.css" rel="stylesheet">
</head>
<body>
<div class = container>
    <c:if test="${existingMeal != null}">
        <form action="update" method="post">
    </c:if>
    <c:if test="${existingMeal == null}">
        <form action="insert" method="post">
    </c:if>
    <h1>
    <c:if test="${existingMeal != null}">
    Изменить блюдо
    </c:if>
    <c:if test="${existingMeal == null}">
    Добавить блюдо
    </c:if>
        <table>
            <tr><td>Дата и время:</td><td><input type="datetime-local" value="<c:out value='${existingMeal.dateTime}' />" name="dateTime"/></td></tr>
            <tr><td>Название:</td><td><input type="text" value="<c:out value='${existingMeal.description}' />" name="description"/></td></tr>
            <tr><td>Каллории:</td><td><input type="text" value="<c:out value='${existingMeal.calories}' />" name="calories"/></td></tr>
            <tr><td colspan="2"><input type="submit" value="Сохранить"/></td></tr>
        </table>
    </form>
</div>
</body>
</html>