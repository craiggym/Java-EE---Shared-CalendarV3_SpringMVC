<%@ page import="org.springframework.context.support.ClassPathXmlApplicationContext" %>
<%@ page import="org.springframework.context.ConfigurableApplicationContext" %>
<!DOCTYPE HTML>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String appContextFile = "WEB-INF/AppContext.xml"; // Use the settings from this xml file %>
<% ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("AppContext.xml"); %>
<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" type="text/css" href="styles/styles.css"/>
</head>
<body>
<h1></h1>
<br/>
<br/>
<form:form action="add" method="POST" modelAttribute="event">
    <fieldset>
        <legend><em>Event Details</em></legend><br/>
        <label>Event name: </label><span>
        <form:input path="username" value="${username}" type="hidden"/>
        <form:input path="eventName"/>

        <%-- No null titles --%>
        <c:if test="${eNameEmpty == \"true\"}">
            <span style="color: darkred;font-style: italic"><strong>Title of event cannot be empty!</strong></span>
        </c:if>
        <%-- No duplicate events --%>
        <c:if test="${isDuplicate == \"true\"}">
            <span style="color: darkred;font-style: italic"><strong>Duplicate event!</strong></span>
        </c:if>

        <br/><br/>
        <label>Event Date: </label>
        <form:select path="month">
            <option value="_01">jan</option>
            <option value="_02">feb</option>
            <option value="_03">mar</option>
            <option value="_04">apr</option>
            <option value="_05">may</option>
            <option value="_06">jun</option>
            <option value="_07">jul</option>
            <option value="_08">aug</option>
            <option value="_09">sep</option>
            <option value="_10">oct</option>
            <option value="_11">nov</option>
            <option value="_12">dec</option>
        </form:select>
        <form:select path="day">
            <option value="_01">01</option>
            <option value="_02">02</option>
            <option value="_03">03</option>
            <option value="_04">04</option>
            <option value="_05">05</option>
            <option value="_06">06</option>
            <option value="_07">07</option>
            <option value="_08">08</option>
            <option value="_09">09</option>
            <option value="_10">10</option>
            <option value="_11">11</option>
            <option value="_12">12</option>
            <option value="_13">13</option>
            <option value="_14">14</option>
            <option value="_15">15</option>
            <option value="_16">16</option>
            <option value="_17">17</option>
            <option value="_18">18</option>
            <option value="_19">19</option>
            <option value="_20">20</option>
            <option value="_21">21</option>
            <option value="_22">22</option>
            <option value="_23">23</option>
            <option value="_24">24</option>
            <option value="_25">25</option>
            <option value="_26">26</option>
            <option value="_27">27</option>
            <option value="_28">28</option>
            <option value="_29">29</option>
            <option value="_30">30</option>
            <option value="_31">31</option>
        </form:select>
        <form:select path="year">
            <option value="_2016">2016</option>
            <option value="_2017">2017</option>
            <option value="_2018">2018</option>
            <option value="_2019">2019</option>
            <option value="_2020">2020</option>
            <option value="_2021">2021</option>
            <option value="_2022">2022</option>
            <option value="_2023">2023</option>
            <option value="_2024">2024</option>
            <option value="_2025">2025</option>
            <option value="_2026">2026</option>
            <option value="_2027">2027</option>
            <option value="_2028">2028</option>
            <option value="_2029">2029</option>
            <option value="_2030">2030</option>
        </form:select>
        <br/><br/>

        Event Description:
        <form:textarea path='eventDescription' id='edescription' style="margin-left: 8px;"></form:textarea>
        <input type="submit" value="Submit">
    </fieldset>
</form:form>
</body>
</html>