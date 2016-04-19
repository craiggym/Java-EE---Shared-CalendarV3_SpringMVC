package com.Calendar;

import com.DAO.EventDao;
import com.DAO.UserDao;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Controller
@RequestMapping("/")
public class HomePageController
{
   // Class static Variables //
   private static boolean debug = true;
   private static ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("AppContext.xml");


   /****************************************************************************
    * home
    * Redirect to the right page if forward slash isn't given
    ***************************************************************************/
   @RequestMapping(value="")
   public View home(Map<String,String> model){
      return new RedirectView("/home",true);
   }


   /****************************************************************************
    * viewAllEvents
    * Views all the events on the home page.
    ***************************************************************************/
   @RequestMapping(value="home", method = RequestMethod.GET)
    public String viewAllEvents(Map<String,Object> model, HttpSession session){

       model.put("auth", null); // Incorrect auth message doesn't need to be shown
       EventDao eventDao = (EventDao) context.getBean("eventDao");
       List<Event> events = eventDao.selectAllEvents();

       if(events.size() > 0) {
           model.put("events", events);
           session.setAttribute("eventsList", events);
           if (session.getAttribute("username") != null) {
               List<Event> userLikedEvents = eventDao.selectAllEvent(session.getAttribute("username").toString());
               List<Integer> userEventsId = new ArrayList<Integer>();
               for (int i = 0; i < userLikedEvents.size(); i++) {
                   userEventsId.add(userLikedEvents.get(i).getId());
               }
               model.put("likedEvents", userEventsId);
           }
       }
       return "home";

   }

   /****************************************************************************
    * login
    * Authorizes and brings user to the user page if login is correct.
    ***************************************************************************/
   @RequestMapping(value="home", method = RequestMethod.POST)
   public String login(Map<String,List> model, @RequestParam("username") String username,
                               @RequestParam("password") String password, HttpSession session){
     UserDao user = (UserDao) context.getBean("userDao"); // Need to instantiate user data access object for sql query validation

      // AUTHENTICATION //
      if(user.isAuthCorrect(username, password)) // authenticate through database
      {
          session.setAttribute("username", username);
          return "redirect:/userEvents";
      }
      else {
          List<String> notNull = new ArrayList<>();
          model.put("auth", notNull); // Bring back to same page with error
      }

       EventDao eventDao = (EventDao) context.getBean("eventDao");
       List<Event> events = eventDao.selectAllEvents();
       if(events.size() > 0) {
           model.put("events", events);
           session.setAttribute("eventsList", events);
       }
      return "home";
   }


   /****************************************************************************
    * tableRefresh
    * Refreshes the tables in the database
     ***************************************************************************/
   @RequestMapping(value = "drop")
   public String tableRefresh() {
         UserDao userDao = (UserDao) context.getBean("userDao");
         EventDao eventDao = (EventDao) context.getBean("eventDao");
         try {
            userDao.dropUserTable();
               userDao.createUserTable();
               if (debug) System.out.println("User table cleared");
               eventDao.dropEventTable();
               eventDao.createEventTable();
               if (debug) System.out.println("Event table cleared");
            } catch (CannotGetJdbcConnectionException e) {
               e.printStackTrace();
               if (debug) System.out.println("Database connection could not be established");
               return "databaseError";
            }
      return "home";
   }

    /****************************************************************************
     * logout
     * Logs the user out
     ***************************************************************************/
    @RequestMapping(value = "logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "logout";
    }
}
