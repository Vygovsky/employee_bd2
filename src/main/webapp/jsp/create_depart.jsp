<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Roman_v
  Date: 21.09.2018
  Time: 13:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create depart</title>
    <link href="<c:url value="/css/styles.css"/>" rel="stylesheet" type="text/css">
</head>

<body>
<div align="center">
    <h1>Создать департамент</h1>
</div>
<form method="POST" action="/employee/departments">
    <div class="form-style-6">
        <table>
            <tr>
                <td>Имя департамента :</td>
                <td><label>
                    <input type="text" name="name">
                </label><br></td>
            </tr>

            <tr>
                <td><input type="submit" value="Отправить"></td>
            </tr>
        </table>
    </div>
</form>

</body>
</html>



