package com.Calendar;

import com.DAO.UserDao;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.CannotGetJdbcConnectionException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by craig on 2/25/16.
 */
@WebServlet(
        name = "RegisterServlet",
        urlPatterns = {"/register"}
)
public class RegisterServlet extends HttpServlet {
    // Variables //
    int idCount=0;
    boolean debug=true;
    private static ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("AppContext.xml");


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        addUser(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        userRegister(request,response);
    }

    /************************************************************************
     * Title: goHome
     * Description: The index home page
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     ***********************************************************************/
    private void goHome(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        request.getRequestDispatcher("/WEB-INF/jsp/view/browse.jsp")
                .forward(request, response);
    }

    /************************************************************************
     * Title: addUser
     * Description: The index home page
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     ***********************************************************************/
    private void addUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("AppContext.xml"); // New AppContext pointing to xml config
        UserDao userDao = (UserDao) context.getBean("userDao");
        HttpSession session = request.getSession(true);

        String pass1 = request.getParameter("pass");
        String pass2 = request.getParameter("pass2");
        if (request.getParameter("fname") == "") session.setAttribute("nameEmpty", "true");
        else session.setAttribute("nameEmpty", "false");

        if (request.getParameter("username") == "") session.setAttribute("unameEmpty", "true");
        else session.setAttribute("unameEmpty", "false");

        if(!pass1.equalsIgnoreCase(pass2)) session.setAttribute("passMatch", "false");
        else session.setAttribute("passMatch", "true");

        if(pass1 == "") session.setAttribute("passBlank", "true");
        else session.setAttribute("passBlank", "false");

        if( !pass1.equalsIgnoreCase(pass2) || pass1 == "" || request.getParameter("fname") == "" || request.getParameter("username") == "") // Password inputs must match
        {
            request.getRequestDispatcher("/WEB-INF/jsp/view/register.jsp")
                    .forward(request, response);
        }
        else {
            session.setAttribute("passBlank", "false");
            session.setAttribute("nameEmpty", "false");
            session.setAttribute("passMatch", "true");
            session.setAttribute("unameEmpty", "false");
            try { // catch occurs when HSQLDB is not established
                // First check if username is unique otherwise throw fail
                String username = request.getParameter("username");
                if (!userDao.userExists(username)) { //User doesn't exist. Proceed with user add:
                    // Take rest of parameters from form and set them in local variables //
                    String e_mail = request.getParameter("e_mail");
                    String pass = request.getParameter("pass");
                    String fname = request.getParameter("fname");
                    String lname = request.getParameter("lname");


                    // Create new user and set the attributes using local variables. //
                    User user = new User();
                    user.setUserID(idCount++);
                    user.setUsername(username);
                    user.setE_mail(e_mail);
                    user.setPassword(pass);
                    user.setFirst_name(fname);
                    user.setLast_name(lname);
                    userDao.insertUser(user); // Inserts the user into HSQLDB table

                    session.setAttribute("duplicate", "false");
                    request.getRequestDispatcher("/WEB-INF/jsp/view/registerSuccess.jsp")
                            .forward(request, response);
                } else { // Username exists in the database
                    session.setAttribute("duplicate", "true");

                    request.getRequestDispatcher("/WEB-INF/jsp/view/register.jsp")
                            .forward(request, response);
                }
            } catch (CannotGetJdbcConnectionException e) {
                e.printStackTrace();
                System.out.println("Database connection could not be established");
                request.getRequestDispatcher("/WEB-INF/jsp/view/databaseError.jsp")
                        .forward(request, response);
            }
        }
    }

    /************************************************************************
     * Title: userRegister
     * Description: Brings user to the register page
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     ***********************************************************************/
    private void userRegister(HttpServletRequest request,
                              HttpServletResponse response)
            throws ServletException, IOException
    {

     /*   if (idCount == 0) {
            UserDao userDao = (UserDao) context.getBean("userDao");
            EventDao eventDao = (EventDao) context.getBean("eventDao");
            try {
                try {
                    userDao.dropUserTable();
                } catch (Exception e) {
                    System.out.println("There is no user table to drop");
                }
                userDao.createUserTable();
                if (debug) System.out.println("User table cleared");
                try {
                    eventDao.dropEventTable();
                } catch (Exception e) {
                    System.out.println("There is no Event table to drop");
                }
                eventDao.createEventTable();
                if (debug) System.out.println("Event table cleared");
            }
            catch(CannotGetJdbcConnectionException e){
                e.printStackTrace();
                System.out.println("Database connection could not be established");
                request.getRequestDispatcher("/WEB-INF/jsp/view/databaseError.jsp")
                        .forward(request, response);
            }
        }*/
        // End procedure of clearing data //
        request.getSession().setAttribute("passBlank", "null");
        request.getSession().setAttribute("unameEmpty", "null");
        request.getSession().setAttribute("nameEmpty", "null");
        request.getSession().setAttribute("passMatch", "null");
        request.getRequestDispatcher("/WEB-INF/jsp/view/register.jsp")
                .forward(request, response);
    }

}
