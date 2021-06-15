<%@ page import="java.util.List" %>
<%@ page import="ru.javawebinar.topjava.model.Meal" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
    <title>Meals</title>
</head>
<body>

<table class="table w-75">
    <thead>
    <tr>
        <th scope="col">Date / Time</th>
        <th scope="col">Description</th>

        <th scope="col">Calories</th>
        <th scope="col">Update</th>
        <th scope="col">Delete</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="meal" items="${mealList}">
        <c:choose>
            <c:when test="${meal.excess}">
                <tr style="color: red"/>

            </c:when>

            <c:otherwise>
                <tr style="color: green">

            </c:otherwise>
        </c:choose>

        <td scope="row">
            <javatime:parseLocalDateTime value="${meal.dateTime}" pattern="yyyy-MM-dd HH:mm" var="parsedDate" />
        </td>
        <td scope="row">${meal.description}</td>
        <td scope="row">${meal.calories}</td>
        <td scope="row"><a href="?action=update&id=${mealList.indexOf(meal)}">Update</a></td>
        <td scope="row"><a href="?action=delete&id=${mealList.indexOf(meal)}">Delete</a></td>
        </tr>

    </c:forEach>
    </tbody>
</table>
</body>
</html>
