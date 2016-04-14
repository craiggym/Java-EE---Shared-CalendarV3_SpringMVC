<%--
  Created by IntelliJ IDEA.
  User: craig
  Date: 2/22/16
  Time: 7:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration Successful</title>
</head>
<body>
    <h1>Congratulations <%= request.getParameter("fname")%></h1>
    <h2>You have successfully registered!</h2>
    <a href="home">Click here to log in!</a>
</body>
</html>
