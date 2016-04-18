<%@ page import="java.util.*" %>
<%@ page import="com.Calendar.Event" %>
<%@ page import="com.Calendar.EventController" %>
<%@ page import="com.DAO.EventDao" %>
<%@ page import="org.springframework.context.ConfigurableApplicationContext" %>
<%@ page import="org.springframework.context.support.ClassPathXmlApplicationContext" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% String appContextFile = "WEB-INF/AppContext.xml"; // Use the settings from this xml file %>
<% ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("AppContext.xml"); %>
<!DOCTYPE html>
<html>
    <head>
        <title>Home Page</title>

    </head>
    <body>
    <h1>Home page</h1>
    <form action="drop" method="POST">
        <input type="submit" value="Drop all data"  style="border: 3px solid #FF0000;position: fixed; bottom: 0; right: 0"><br/>
    </form>
    <c:choose>
    <c:when test="${username == null}">
        <h2><a href="register">Link to Register</a></h2>

        <c:if test="${auth == \"false\"}">
            <span style="color: darkred;font-style: italic"><strong>Incorrect username or password!</strong></span>
        </c:if>


    <form action="home" method="POST">
        Username: <input type="text" name="username"/><br>
        Password: <input type="password" name="password"/><br>
        <input type="submit" value="login"/><br><br/>
        </c:when>
        <c:otherwise>
        <form action="welcome" method="POST">
            <input type="submit" Value="User Page">
        </form>
        <form action="home?action=logout" method="POST">
            <input type="submit" value="Log out"><br/>
        </form>
    </form>

        </c:otherwise>
        </c:choose>
    </body>

</html>
