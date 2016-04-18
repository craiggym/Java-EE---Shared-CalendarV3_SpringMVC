<%@ page import="java.io.PrintWriter" %>
<!DOCTYPE HTML>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.Calendar.Event" %>
<%@ page import="com.DAO.UserDao" %>
<%@ page import="org.springframework.context.ConfigurableApplicationContext" %>
<%@ page import="org.springframework.context.support.ClassPathXmlApplicationContext" %>
<%@ page import="com.DAO.EventDao" %>
<%@ page import="com.DAO.EventMapper" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>
<% String appContextFile = "WEB-INF/AppContext.xml"; // Use the settings from this xml file %>
<% ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("AppContext.xml"); %>


<html>
<head>
    <title>User Page</title>
    <link rel="stylesheet" type="text/css" href="styles/styles.css"/>
</head>
<body>


<h1>Welcome <%= session.getAttribute("username") %></h1>

<br/>
<br/>
<%-- Display message if the user has no events --%>
<c:choose>
    <c:when test="${events == null}">
        <h3>Not subscribed to any events!</h3>
        <p><em>Create one or follow one from the Home page!</em>
        <p></p>
    </c:when>
    <c:otherwise> <em style="color: gray;"><strong>Showing events up to 3 months from today </strong></em><br/>
        <c:forEach var="event" items="${events}">
            Event Id: <c:out value="${event.id}"></c:out> <br/>
            Event: <c:out value="${event.eventName}"></c:out> <br/>
            Date: <c:out value="${event.eventDate}"></c:out> <br/>
            Description: <c:out value="${event.eventDescription}"></c:out> <br/>
            Creator: <c:out value="${event.eventAuthor}"></c:out> <br/><br/>
        </c:forEach>
    </c:otherwise>
</c:choose>
<br />

<br/>
<form:form action="userEvents/add" method="GET">
    <input type="submit" value="Create Event"><br/>
</form:form>
<form action="logout" method="POST">
    <input type="submit" value="Log out"><br/>
</form>
<form action="home" method="GET">
    <input type="submit" value="Home Page">
</form>
<br/>

</body>
</html>