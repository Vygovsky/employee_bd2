<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
<form method="POST" action="/departments">

    <div class="form-style-6">
        <table>
            <tr>
                <td><input type="hidden" name="id"
                           value="${depart.id}"/></td>
            </tr>
            <tr>
                <td>Имя департамента :</td>
                <td><input type="text" name="name"
                           value="${depart.name}"/></td>
                <br/>
            </tr>
            <tr>
                <td><input type="submit" value="Отправить"></td>
            </tr>
        </table>
    </div>
</form>

</body>
</html>



