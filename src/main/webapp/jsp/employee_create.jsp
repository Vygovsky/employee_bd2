<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Employee</title>
    <link href="<c:url value="/css/styles.css"/>" rel="stylesheet" type="text/css">
    <style>.error {
        color: red;
    }
    .success {
        color: green;
    }</style>
</head>



<body>

<div align="center">
    <h1>Создать сотрудника</h1>
</div>
<form method="POST" action="${pageContext.request.contextPath}/employee/create">
    <div class="form-style-6">
           <table>
            <tr>
                <td>Имя сотрудника :</td>
                <td><input type="text" name="name" required value="${name}"><br></td>
                <span style="color: red">${errorMassage}</span>
            </tr>
            <tr>
                <td> Электронная почта :</td>
                <td><input type="text" name="email" required value="${email}"></td>
                <br>
            </tr>
            <tr>
                <td>Дата рождения :</td>
                <td><input type="date" name="date" required value="${date}"></td>
                <br>
            </tr>
            <tr>
                <td>Департамент :</td>
                <td><label>

                    <select name="organizations">
                        <c:forEach var="depart" items="${departments}">
                            <option value="${depart.id}"
                                ${depart.id == currentDepartId ? 'selected="selected"' : null}>${depart.name}</option>
                        </c:forEach>
                    </select>
                </label>
                </td>
            </tr>
            <tr>
                <td><input type="submit" value="Отправить"></td>
                <span class="success">${message.success}</span>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <td><input type="button" value="Отмена" onclick='location.href="/employee/listEmployee?departmentId=${currentDepartId}"'></td>
               <%-- <td><input type="button" value="Отмена" onclick="window.history.back()"></td>--%>
            </tr>
        </table>
    </div>
</form>

</body>
</html>
