package com.DAO;

import com.Calendar.Event;
import java.util.List;

public interface EventDao {
    void insertEvent(Event event);
    void createEventTable();
    void dropEventTable();
    boolean eventsExists(String username);
    int countEvents();
    boolean eventsExists();
    boolean hasEvent(String eventname, String username, String creator);
    List<Event> selectRecentEvent(String username);
    List<Event> selectAllEvent(String username);
    List<Event> selectAllEvents();
    Event getEventById(int id);
}
