<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>User update</title>
    <link href="<c:url value="/css/styles.css"/>" rel="stylesheet" type="text/css">
</head>


<body>
<form method="post" action="/employee/create?id=${employee.id}&currentDepartId=${currentDepartId}">
    <div class="form-style-6">
        <div align="center">
            <h2>Изменить данные сотрудника </h2>
        </div>
        <table>
            <tr>
                <td>Номер сотрудника :</td>
                <td><input type="text" value="${employee.id}" readonly="readonly"/></td>
            </tr>
            <br/>
            <tr>
                <td> Имя сотрудника :</td>
                <td><input type="text" name="name"
                           value="<c:out value="${sessionScope.employee.name}"/>${employee.name}"/></td>
                <c:if test="${not empty errors}">
                    <span style="color:red"><c:out value="${sessionScope.errors[\"errorNameMessage\"]}"/></span>
                </c:if>
                <br/>

            </tr>
            <tr>
                <td> Почта :</td>
                <td><input type="text" name="email"
                           value="<c:out value="${sessionScope.employee.email}" />${employee.email}"/></td>
                <c:if test="${not empty errors}">
                    <span style="color:red"><c:out value="${sessionScope.errors[\"errorEmailMessage\"]}"/></span>
                </c:if>
                <br/>
            </tr>
            <tr>
                <td>День рождения :</td>
                <td><input type="date" name="date"
                           value="<c:out value="${sessionScope.employee.email}" />${employee.birthday}"/></td>
                <c:if test="${not empty errors}">
                    <span style="color:red"><c:out value="${sessionScope.errors[\"errorBdMessage\"]}"/></span>
                </c:if>
                <br/>
            </tr>
            <tbody>
            <tr>
                <td>Департамент :</td>
                <td><select name="organizations">
                    <c:forEach var="depart" items="${departments}">
                        <option value="${depart.id}"
                            ${depart.id == currentDepartId ? 'selected="selected"' : null}>${depart.name}</option>
                    </c:forEach>
                </select>
                </td>
            </tr>
            </tbody>
            <br/>
            <tr>
                <td><input type="submit" value="Отправить"></td>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <td><input type="button" value="Отмена"
                           onclick='location.href="/employee/create?departmentId=${currentDepartId}"'></td>
        </table>
    </div>
</form>

</body>

</html>
