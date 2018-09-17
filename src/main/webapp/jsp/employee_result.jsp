<%--
  Created by IntelliJ IDEA.
  User: Roman_v
  Date: 07.08.2018
  Time: 10:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Result Employee</title>
</head>
<body>

<p>Employee has been created:</p>
<p>Employee id: "${employee.id}"</p>
<p>Employee name: "${employee.name}"</p>
<p>Employee email: "${employee.email}"</p>
<p>Employee birthday: "${employee.birthday}"</p>

<a href='http://localhost:8080/employee/create'>Добавить нового Employee</a><br/>
<a href='http://localhost:8080/employee/listEmployee'>Посмотреть список</a>


</body>
</html>
