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

                    <select name="organizations" >
                        <c:forEach var="organizations" items="${departments}">
                        <option value="${organizations.id}" > <c:out value="${organizations.name}"/></option>
                        </c:forEach>



                    </select>

                </label>
                </td>
            </tr>
            <tr>
                <td><input type="submit" value="Отправить"></td>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <td><input type="submit" value="Отмена"></td>
            </tr>
        </table>
    </div>
</form>

</body>
</html>
