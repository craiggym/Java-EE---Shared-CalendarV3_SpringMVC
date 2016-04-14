<%@ page import="java.util.*" %>
<%@ page import="com.Calendar.Event" %>
<%@ page import="com.Calendar.EventServlet" %>
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

    <% if (session.getAttribute("username") == null) {%>
    <h2><a href="register">Link to Register</a></h2>
    <% if (session.getAttribute("auth") == "false") %>
    <span style="color: darkred;font-style: italic"><strong>Incorrect username or password!</strong></span>

    <form action="home?action=login" method="POST">
        Username: <input type="text" name="username"/><br>
        Password: <input type="password" name="password"/><br>
        <input type="submit" value="login"/><br><br/>
            <%}else{%>
        <form action="welcome" method="POST">
            <input type="submit" Value="User Page">
        </form>
        <form action="home?action=logout" method="POST">
            <input type="submit" value="Log out"><br/>
        </form>
            <%}%>

        <br>
            <% // Display message if the user has no events
    EventDao eventDao = (EventDao) context.getBean("eventDao");
    System.out.println(session.getAttribute("username"));
    if (eventDao.eventsExists() == false) {%>
        <h3>There are no events from any users</h3>
        <p><em>Create one from the User Page after logging in!</em>
        <p></p>
            <%
    } else { %>
        <em style="color: gray;"><strong>Showing all events</strong></em><br/>
            <%
        int counter = 0;
        List<Event> events = eventDao.selectAllEvents();
        session.setAttribute("eventsList", events);
        for (Event e : events) {
            int eventId = e.getId();
            String eventName = e.getEventName();
            Date eventDate = e.getEventDate();
            String eventDesc = e.getDescription();
            String eventAuthor = e.getEventAuthor();
            String eventDateStr = new SimpleDateFormat("MM-dd-yyyy").format(eventDate);

            %>

        Event Id: <%= eventId %> <br/>
        Event: <%= eventName %> <br/>
        Date: <%= eventDateStr %> <br/>
        Description: <%= eventDesc %> <br/>
        Creator: <%= eventAuthor %> <br/><br/>

        <%    if(session.getAttribute("username") !=null)
                if(eventDao.hasEvent(eventName,session.getAttribute("username").toString())==false){%>
        <form action="event?action=likedEvent" method="POST">
            <input type="hidden" name="it" value="<%= counter %>"/>
            <input type="submit" value="Like">
        </form>

            <%
            }counter++;%><br/>
        <%}
    }
%>

    </body>
</html>
