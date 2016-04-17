<%--
  Created by IntelliJ IDEA.
  User: craig
  Date: 2/18/16
  Time: 1:49 AM
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE HTML>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
    <h1>Sign up while it's free!</h1>
    <br/>
    <br/>
    <form:form method="POST" action="register" modelAttribute="user">
        <fieldset>

            <legend><em>Personal Information</em></legend><br/>
            <label path=""> Username *: </label>
            <form:input path="username"></form:input>

            <%-- In the case the username is blank --%>
            <c:if test="${unameEmpty == \"true\"}">
                <span style="color: darkred;font-style: italic"><strong>Username cannot be empty!</strong></span>
            </c:if>
                <%-- In the case the username is not unique --%>
            <c:if test="${duplicate == \"true\"}">
                <span style="color: darkred;font-style: italic"><strong>That username is already taken!</strong></span>
            </c:if>

            <br/><br/>
            <label path="password">Password *: </label>
            <form:password path="password"></form:password>

                <%-- In the case the passwords aren't matching --%>
            <c:choose>
            <c:when test="${passMatch == \"false\"}">
                <span style="color: darkred;font-style: italic"><strong>Passwords must match!</strong></span>
            </c:when>
                <%-- In the case the passwords are empty --%>
            <c:when test="${passEmpty == \"true\"}">
                <span style="color: darkred;font-style: italic"><strong>Password cannot be empty!</strong></span>
            </c:when>
            <c:otherwise></c:otherwise></c:choose>
            <br/><br/>
            <label path="password2">Password (repeat)*: </label>
            <form:password path="password2"></form:password>

            <c:if test="${pass2Empty == \"true\"}">
                <span style="color: darkred;font-style: italic"><strong>Password cannot be empty!</strong></span>
            </c:if>

            <br/><br/>
            <label path="e_mail">E-mail: </label>
            <form:input type="email" path="e_mail"/>

            <br/><br/>
            <label path="first_name">First name *: </label>
            <form:input path="first_name"/>
                <%-- In the case the fname is empty --%>
            <c:if test="${fnameEmpty == \"true\"}">
                <span style="color: darkred;font-style: italic"><strong>Must include first name!</strong></span>
            </c:if>

            <br/><br/>
            <label path="last_name">Last name *: </label>
            <form:input path="last_name"/>
            <c:if test="${lnameEmpty == \"true\"}">
                <span style="color: darkred;font-style: italic"><strong>Must include last name!</strong></span>
            </c:if>

            <br/><br/>
            <input type="submit" value="Submit">
        </fieldset>
    </form:form>
</body>
</html>
