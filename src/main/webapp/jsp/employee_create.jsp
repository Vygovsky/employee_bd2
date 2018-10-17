<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Employee</title>
    <link href="<c:url value="/css/styles.css"/>" rel="stylesheet" type="text/css">
</head>

<body>
<div align="center">
    <h1>Создать сотрудника</h1>
</div>

<form method="POST" action="/employee/create">
    <div class="form-style-6">
        <table>

            <tr>
                <td>Имя сотрудника :</td>
                <td><input type="text" name="name"><br></td>
            </tr>
            <tr>
                <td> Электронная почта :</td>
                <td><input type="text" name="email"></td>
                <br>
            </tr>
            <tr>
                <td>Дата рождения :</td>
                <td><input type="date" name="date"></td>
                <br>
            </tr>
            <tr>
                <td>Департамент :</td>
                <td><label>
                    <select name="organizations">
                        <option selected value="-1">-Select Department-</option>
                        <option value="1" >Google</option>
                        <option value="2" >Yahoo</option>
                        <option value="3">Oracle</option>
                        <option value="4">Linux</option>

                    </select>

                </label>
                </td>
            </tr>
            <tr>
                <td><input type="submit" value="Отправить"></td>
            </tr>
        </table>
    </div>
</form>

</body>
</html>
