<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>User update</title>
    <link href="<c:url value="/css/styles.css"/>" rel="stylesheet" type="text/css">
</head>

<body>
<form method="post" action="/editEmployee?id=${employee.id}">
    <div class="form-style-6">
        <div align="center">
            <h2>Изменить данные сотрудника </h2>
        </div>
        <table>
            <tr>
                <td>Номер сотрудника :</td>
                <td><c:out value="${employee.id} "/></td>
            </tr>
            <br/>
            <tr>
                <td> Имя сотрудника :</td>
                <td><input type="text" name="name"
                           value="<c:out value="${employee.name}" />"/></td>
                <br/>
            </tr>
            <tr>
                <td> Почта :</td>
                <td><input type="text" name="email"
                           value="<c:out value="${employee.email}" />"/></td>
                <br/>
            </tr>
            <tr>
                <td>День рожления :</td>
                <td><input type="date" name="date" value="${employee.birthday}"></td>
            </tr>
            <br/>
            <tbody>
            <tr>
                <td>Департамент :</td>
                <td><select name="departments">
                    <c:forEach var="depart" items="${departments}" >

                        <option value="${depart.id}">${depart.name}</option>
                    </c:forEach>

                </select>

                </td>
            </tr>
            </tbody>
            <br/>
            <tr>
                <td><input type="submit" value="Отправить"></td>
                <%-- <button type="submit" name="save" value="Save">Save</button>--%>
            </tr>

        </table>
    </div>
</form>

</body>

</html>
