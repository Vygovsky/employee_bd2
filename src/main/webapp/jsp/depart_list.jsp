<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>DepartList</title>
</head>
<body>
    <div align="center"><h1>Департаменты</h1></div>
    <table align="center" id="customers" width="600">
        <thead>
            <tr>
                <th>#</th>
                <th>Название департамента</th>
                <th>Колличество сотрудников</th>
                <th>Список сотрудников</th>
                <th>Добавить дпартамент</th>
                <th>Редактировать</th>
                <th>Удалить</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="depart" items="${mapDepart}">
                <tr>
                    <td>${depart.key.id}</td>
                    <td>${depart.key.name}</td>
                    <td>${depart.value}</td>
                    <td><a href="/employee/listEmployee?departmentId=${depart.key.id}">List</a></td>
                    <td><a href="/departments?action=add">Add Depart</a></td>
                    <td><a href="/departments?action=edit&id=${depart.key.id}">Edit</a></td>
                    <td><a href="/departments?action=delete&id=${depart.key.id}">Delete</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
