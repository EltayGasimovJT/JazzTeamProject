<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>AddNewUser</title>
</head>
<body>
<form method = "post" action="${pageContext.request.contextPath}/addClient">
    name:<input name="name" type="text" maxlength="50"><br>
    surname:<input surname="password" type="text" maxlength="50"><br>
    passportID:<input name="age" type="text" maxlength="80"><br>
    phoneNumber:<input name="active" type="text" maxlength="50">Should be true or false<br>
    <input type="submit" value="Add">
</form>
</body>
</html>
