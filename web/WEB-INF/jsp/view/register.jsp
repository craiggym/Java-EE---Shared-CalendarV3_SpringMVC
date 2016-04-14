<%--
  Created by IntelliJ IDEA.
  User: craig
  Date: 2/18/16
  Time: 1:49 AM
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE HTML>
<%@ page import="com.Calendar.RegisterServlet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
    <h1>Sign up while it's free!</h1>
    <br/>
    <br/>
    <form action="register" method="POST">
        <fieldset>
            <legend><em>Personal Information</em></legend><br/>
            <label for="username">Username *: </label><span> <input type="text" id="username" name="username"></span>
            <% HttpSession reg = request.getSession();%>
            <%-- In the case the username is empty --%>
            <%  if(reg.getAttribute("unameEmpty") == "true") %>
            <span style="color: darkred;font-style: italic"><strong>Username cannot be empty!!</strong></span>
            <%-- In the case the username is not unique --%>
            <%if(reg.getAttribute("duplicate") == "true")%>
            <span style="color: darkred;font-style: italic"><strong>That username is already taken!</strong></span>
            <br/><br/>
            <label for="pass">Password *: </label> <span> <input type="password" id="pass" name="pass"></span>
            <%-- In the case the passwords aren't matching --%>
            <%  if(reg.getAttribute("passMatch") == "false") %>
            <span style="color: darkred;font-style: italic"><strong>Passwords must match!</strong></span>
            <%-- In the case the passwords are empty --%>
            <%  if(reg.getAttribute("passBlank") == "true") %>
            <span style="color: darkred;font-style: italic"><strong>Passwords cannot be empty!!</strong></span>
            <br/><br/>
            <label for="pass2">Password (again)*: </label> <span> <input type="password" id="pass2" name="pass2"></span>
            <br/><br/>
            <label for="e_mail">E-mail: </label> <span> <input type="email" id="e_mail" name="e_mail"></span>
            <br/><br/>
            <label for="fname">First name *: </label><span> <input type="text" id="fname" name="fname"></span>
            <%  if(reg.getAttribute("nameEmpty") == "true") %>
            <span style="color: darkred;font-style: italic"><strong>First name cannot be empty!</strong></span>
            <br/><br/>
            <label for="lname">Last name: </label> <span> <input type="text" id="lname" name="lname"></span>
                <br/><br/>
                <input type="submit" value="Submit">
        </fieldset>
    </form>
</body>
</html>
