<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Roman_v
  Date: 22.08.2018
  Time: 13:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        #customers {
            border: solid 1px #DDEEEE;
            border-collapse: collapse;
            border-spacing: 0;
            font: normal 13px Arial, sans-serif;
        }

        #customers td, #customers th {
            border: solid 1px #DDEEEE;
            color: #333;
            padding: 10px;

        }

        #customers tr:nth-child(even) {
            background-color: #DDEFEF;
            border: solid 1px #DDEEEE;
            color: #336B6B;
            padding: 10px;
            text-align: left;
            text-shadow: 1px 1px 1px #fff;
        }

        #customers tr:hover {
            background-color: #fff;
        }

        #customers tbody tr:nth-child(even) {
            background-color: #EEF7EE;
        }

        #customers th {
            padding-top: 12px;
            padding-bottom: 12px;
            text-align: left;
            background-color: #86af90;
            color: white;
        }

        div {
        }

        #left {
            text-align: left;
        }

        #right {
            text-align: right;
        }

        #center {
            text-align: center;
        }

        .content {
            width: 75%; /* Ширина слоя */
            background: #fc0; /* Цвет фона */
        }
    </style>
<body>
<div align="center">
    <h1>List users by departments</h1>
</div>
<table align="center" id="customers" width="600">
    <%--<table id="customers">--%>
    <thead>
    <tr>
        <th>#</th>
        <th>Name</th>
        <th>E-mail</th>
        <th>Birthday</th>
        <th>Department</th>
        <th>Update</th>
        <th>Delete</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="empl" items="${list}">
        <tr>
            <td>${empl.id}</td>
            <td>${empl.name}</td>
            <td>${empl.email}</td>
            <td>${empl.birthday}</td>
            <td>${empl.department.name}</td>
            <form action="editEmployee" method="post">
                    <%--  <button type="submit" name="update" value="${user.id}">update</button>--%>
                <td><a href="/editEmployee?id=<c:out value='${empl.id}'/>">Edit</a></td>

                <form action="deleteEmployee?id=" method="get">
                    <td><a href="/deleteEmployee?id=<c:out value='${empl.id}'/>">Delete</a></td>

                </form>
        </tr>
    </c:forEach>

    </tbody>
        <tr>

                <button type="submit" name="save" value="AddNew">Add User</button>

        </tr>
</table>

</body>
</html>
