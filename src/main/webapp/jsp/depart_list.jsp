<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>DepartList</title>
</head>
<div align="center">
    <h1>Департаменты</h1>
</div>

<form>
    <table align="center" id="customers" width="600">
        <thead>
        <tr>
            <th>#</th>
            <th>Название департамента</th>
            <th>Колличество сотрудников</th>
            <th>Список сотрудников</th>
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

                <form action="/employee/listEmployee" method="get">
                    <td><a href="/employee/listEmployee">List</a></td>
                </form>

                <form action="/editDepart?id=${employee.id}" method="post">
                    <td><a href="/editEmployee?id=<c:out value='${employee.id}'/>">Edit</a></td>
                </form>

                <form action="/deleteDepart?id=" method="get">
                    <td><a href="/deleteDepart?id=<c:out value='${depart.key.id}'/>">Delete</a></td>
                </form>
            </tr>
        </c:forEach>

        </tbody>

    </table>
</form>

</body>
</html>
