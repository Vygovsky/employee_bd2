<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create depart</title>
    <link href="<c:url value="/css/styles.css"/>" rel="stylesheet" type="text/css">

<body>
<div align="center">
    <h1>Создать департамент</h1>
</div>


<form method="POST" action="/departments?action=edit&id=${depart.id}">

    <div class="form-style-6">
        <table>
            <tr>
                <td><input type="hidden" name="id"
                           value="${depart.id}"/></td>
            </tr>
            <tr>
                <td>Имя департамента :</td>
                <td><input type="text" name="name" required value="${depart.name}"/></td>
                <span style="color:red">${error.name}</span>

                <br/>
            </tr>
            <tr>
                <td><input type="submit" value="Отправить"></td>
                &nbsp;&nbsp;&nbsp;
                <td><input type="button" value="Отмена" onclick='location.href="/departments"'></td>
            </tr>
        </table>
    </div>
</form>

</body>
</html>



