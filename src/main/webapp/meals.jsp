<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Meals</title>
    <link href="resources/css/style.css" rel="stylesheet">
</head>
<body>
    <div class = container>
        <table class="meals">
            <tr>
                <th>Дата</th>
                <th>Описание</th>
                <th>Каллории</th>
                <th colspan="2">Операция</th>
            </tr>

            <c:forEach var="mealTo" items="${meals}" varStatus="mealStatus">
                <tr style="background-color:${mealTo.excess ? 'green' : 'red'}">
                    <td><c:out value="${mealTo.date}"/></td>
                    <td><c:out value="${mealTo.description}"/></td>
                    <td><c:out value="${mealTo.calories}"/></td>
                    <td><a href="edit?id=<c:out value='${mealStatus.index}' />">Изменить</a>
                    <td><a href="delete?id=<c:out value='${mealStatus.index}' />">Удалить</a>
                    </td>
                </tr>


            </c:forEach>
        </table>
        <a href="<%=request.getContextPath()%>/new">Добавить новое блюдо</a>
    </div>
</body>
</html>