package com.Calendar;

import com.DAO.EventDao;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@RequestMapping(value = "userEvents")
public class EventController {
    private static ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("AppContext.xml");
    private static String username;
    String userPage = "userPersonal2";


    /************************************************************************
     * Title: userHome
     * Views the user's home page
     ***********************************************************************/
    @RequestMapping(value = {"","/"}, method = RequestMethod.GET)
    public String userHome(Map<String, String> model) {
        EventDao eventDao = (EventDao) context.getBean("eventDao");
        if (eventDao.eventsExists(model.get("username")) == false) model.put("events", null);

        return userPage;
    }

    /************************************************************************
     * Title: likeEvent
     * Handles the liked events
     ***********************************************************************/
    @RequestMapping(value = "like")
    public String likeEvent(Map<String, String> model, @PathVariable("it") String iterator){
        int it = Integer.parseInt(iterator);

        return userPage;
    }

    /************************************************************************
     * Title: createEvent -- GET and POST methods
     * Handles the creation of events
     ***********************************************************************/
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String createEvent(Map<String, Object> model, @RequestParam("username") String username){
        Event event = new Event();
        model.put("event", event);
        model.put("username", event);
        return "createEvent";
    }
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String createEvent(@ModelAttribute("event") Event event, Map<String, String> model){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("AppContext.xml"); // New AppContext pointing to xml config
        EventDao eventDao = (EventDao) context.getBean("eventDao"); // bean

        event.setEventAuthor(username);
        event.setUsername(username);
        // Check for event duplication and event name empty
        if (eventDao.hasEvent(event.getEventName(),event.getUsername(),event.getEventAuthor())) model.put("isDuplicate", "true");
        else model.put("isDuplicate", "false");
        if (event.getEventName() == "") model.put("eNameEmpty", "true"); // Check if event name is empty
        else model.put("eNameEmpty", "false");
        if(model.get("eNameEmpty") == "true" || model.get("isDuplicate") == "true")
            return "createEvent"; // return to same page with error if empty

        // Proceed if above conditions are met
        else {
            try { // catch occurs when HSQLDB is not established
                int id = eventDao.countEvents()+1;

                Date eventDateFormatted= formatDate(event);

                Event e = new Event(id, event.getEventName(), eventDateFormatted, event.getEventDescription(), event.getUsername(), event.getEventAuthor());
                eventDao.insertEvent(e);

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Database connection could not be established");
                return "databaseError";
            }
        }

        return userPage;
    }

    /************************************************************************
     * Title: formatDate
     * Formats the date for event input
     ***********************************************************************/
    public Date formatDate(Event event){
        // Parsing the date passed from the HTML form //
        String string = event.getMonth(); // Passed from HTML
        String[] parser = string.split("_"); // Parse using the indicator
        String parsedMonth = parser[1]; // Take what we want
        string = event.getDay(); // Repeat for date...
        parser = string.split("_");
        String parsedDay = parser[1];
        string = event.getYear(); // Repeat for year...
        parser = string.split("_");
        String parsedYear = parser[1];
        String eventDate =  parsedMonth + "-" + parsedDay + "-" + parsedYear;
        Date eventDateFormatted = new Date();
        try {
            eventDateFormatted = new SimpleDateFormat("MM-dd-yyyy").parse(eventDate);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return eventDateFormatted;
    }


}
