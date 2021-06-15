<%@ page import="ru.javawebinar.topjava.model.MealTo" %><%--
  Created by IntelliJ IDEA.
  User: AveZomer
  Date: 6/15/2021
  Time: 12:33 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Save Edit</title>
</head>
<body>
    <h3>Enter data for meal:</h3>
    <form action="/mealServlet" method="get">
        <input type="hidden" name="id" id="id" value="<%= request.getAttribute("id") %>">
        <input type="datetime-local" name="dataTime" id="dataTime" placeholder="yyyy-MM-dd HH:mm" value="<%= request.getAttribute("dateTime") %>">
        <input type="text" name="description" id="description" placeholder="Enter description" value="<%= request.getAttribute("description") %>">
        <input type="number" name="calories" id="calories" placeholder="calories" value="<%= request.getAttribute("calories") %>">
        <input type="submit" name="action" value="Save">
    </form>
    <a href="mealServlet">Back</a>
</body>
</html>
