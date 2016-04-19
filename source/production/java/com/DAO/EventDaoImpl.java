package com.DAO;

import com.Calendar.Event;
import com.Calendar.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class EventDaoImpl implements EventDao{
    // class variables //
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private static boolean debug = true;


    // methods //
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /***************************************************************
     * Title: insertEvent
     * Description: Add an event to the database
     * @param event An Event object
     ***************************************************************/
    @Override
    public void insertEvent(Event event) {
        String query = "INSERT INTO Event (EventID, EventName, EventDate, EventDesc, EventUser, EventCreator) VALUES (?,?,?,?,?,?);";
        jdbcTemplate = new JdbcTemplate(dataSource);
        Object[] inputs = new Object[] {event.getId(), event.getEventName(), event.getEventDate(), event.getEventDescription(), event.getUsername(), event.getEventAuthor()};
        jdbcTemplate.update(query,inputs); // 'update' allows for non-static queries whereas execute wouldn't (e.g. '?')
        if(debug) System.out.printf("Added event with name: %s and with user: %s and author: %s", event.getEventName(), event.getUsername(), event.getEventAuthor());
    }

    @Override
    public void createEventTable() {
        String query = "CREATE TABLE Event(EventID int, EventName VARCHAR(255), EventDate Date, EventDesc VARCHAR(255), EventUser VARCHAR(255), EventCreator VARCHAR(255));";
        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute(query);
    }

    @Override
    public void dropEventTable() {
        String query = "DROP TABLE Event;";
        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute(query);
    }

    @Override
    public boolean eventsExists(String username) {
        try {
            String query = "SELECT Count(*) FROM Event WHERE EventUser=?";
            Object[] input = new Object[]{username};
            jdbcTemplate = new JdbcTemplate(dataSource);
            int result = (int) jdbcTemplate.queryForObject(query, input, int.class);

            if (debug) System.out.println("result of query(count from Event where username=user): " + result);
            if (result > 0) return true;
        } catch (Exception e) {
            if (debug) System.out.println("Exception caught in sql query for event count");
            return false;
        }
        return false;
    }

    @Override
    public boolean eventsExists() {
        try {
            String query = "SELECT Count(*) FROM Event";
            jdbcTemplate = new JdbcTemplate(dataSource);
            int result = (int) jdbcTemplate.queryForObject(query, int.class);

            if (debug) System.out.println("result of query(total events)): " + result);
            if (result > 0) return true;
        } catch (Exception e) {
            if (debug) System.out.println("Exception caught in sql query for all_user event count");
            return false;
        }
        return false;
    }

    @Override
    public boolean hasEvent(String eventname, String username, String creator) {
        try {
            String query = "SELECT EventName FROM Event WHERE EventUser='"+username+"' AND EventName='"+eventname+"' AND EventCreator='"+creator+"'";
            jdbcTemplate = new JdbcTemplate(dataSource);
            String result = (String) jdbcTemplate.queryForObject(query, String.class);

            if (debug) System.out.println("result of hasEvent: " + result);
            return true;
        } catch (Exception e) {
            if (debug) System.out.println("Exception caught in sql query for hasEvent!!");
            return false;
        }
    }

    // count update//
    /*****************************************************************************************
     * countEvents
     * @return count of users in user table
     ****************************************************************************************/
    @Override
    public int countEvents() {
        try {
            String query = "SELECT MAX(EventID) FROM Event";
            jdbcTemplate = new JdbcTemplate(dataSource);
            int res = (int) jdbcTemplate.queryForObject(query, int.class);

            return res;
        }
        catch(Exception e){
            if (debug) System.out.println("error querying for count");
            return 0;
        }
    }

    @Override
    public List<Event> selectRecentEvent(String username) {
        Date todays_date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH,3);
        Date beyond_date = cal.getTime();

        String query = "SELECT EventID, EventName, EventDate, EventDesc, EventUser, EventCreator FROM Event WHERE EventUser='"+username +"' AND EventDate >= " + todays_date + "  ORDER BY EventDate ASC";
        Object[] input = new Object[]{username};
        jdbcTemplate = new JdbcTemplate(dataSource);
        List<Event> events = jdbcTemplate.query(query, new EventMapper());
        return events;
    }

    @Override
    public List<Event> selectAllEvent(String username) {
        String query = "SELECT EventID, EventName, EventDate, EventDesc, EventUser, EventCreator FROM Event WHERE EventUser='"+username +"' ORDER BY EventDate ASC";
        Object[] input = new Object[]{username};
        jdbcTemplate = new JdbcTemplate(dataSource);
        List<Event> events = jdbcTemplate.query(query, new EventMapper());
        return events;
    }

    @Override
    public List<Event> selectAllEvents() {
        String query = "SELECT DISTINCT EventID, EventName, EventDate, EventDesc, EventUser, EventCreator FROM Event WHERE EventUser=EventCreator ORDER BY EventDate ASC";
        jdbcTemplate = new JdbcTemplate(dataSource);
        List<Event> events = jdbcTemplate.query(query, new EventMapper());
        return events;
    }

    @Override
    public Event getEventById(int id) {
        String query = "select EventID, EventName, EventDate, EventDesc, EventUser, EventCreator from Event where EventID=" + id + " limit 1";
        jdbcTemplate = new JdbcTemplate(dataSource);
        List<Event> events = jdbcTemplate.query(query, new EventMapper());
        if (events != null && events.size() > 0) {
            return events.get(0);
        }
        return null;
    }
}