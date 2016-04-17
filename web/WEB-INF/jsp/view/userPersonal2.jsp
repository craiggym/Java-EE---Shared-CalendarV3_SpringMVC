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


<h1>Welcome <c:out value="${first_name}"></c:out></h1>

<br/>
<br/>

<% // Display message if the user has no events
    EventDao eventDao = (EventDao) context.getBean("eventDao");
    System.out.println(session.getAttribute("username"));

    Date todays_date = new Date();
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.MONTH,3);
    Date beyond_date = cal.getTime();

    if (eventDao.eventsExists(session.getAttribute("username").toString()) == false) {%>
<h3>Not subscribed to any events!</h3>
<p><em>Create one or follow one from the Home page!</em>
<p></p>
<%
    } else { %> <em style="color: gray;"><strong>Showing events up to 3 months from today </strong></em><br/> <%
        List<Event> events = eventDao.selectAllEvent(session.getAttribute("username").toString());
        for (Event e : events) {
            int eventId = e.getId();
            String eventName = e.getEventName();
            Date eventDate = e.getEventDate();
            String eventDesc = e.getDescription();
            String eventAuthor = e.getEventAuthor();
            String eventDateStr = new SimpleDateFormat("MM-dd-yyyy").format(eventDate);
            if(eventDate.after(todays_date) && eventDate.before(beyond_date)){
%>

                Event Id: <%= eventId %> <br/>
                Event: <%= eventName %> <br/>
                Date: <%= eventDateStr %> <br/>
                Description: <%= eventDesc %> <br/>
                Creator: <%= eventAuthor %> <br/><br/>
<%}
        }
    }
%>

<br />

<br/>

<form action="event?action=create" method="POST">
    <input type="submit" value="Create Event"><br/>
</form>
<form action="home?action=logout" method="POST">
    <input type="submit" value="Log out"><br/>
</form>

<form action="home" method="POST">
    <input type="submit" value="Home Page">
</form>
<br/>


</body>
</html>