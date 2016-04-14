package com.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
//Added for proj 2//
import com.DAO.EventDao;
import com.DAO.UserDao;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.CannotGetJdbcConnectionException;


@WebServlet(
        name = "HomeServlet",
        urlPatterns = {"/home","/loginservlet"}
)
public class HomeServlet extends HttpServlet
{
    // Variables //
    boolean debug = true;
    private static boolean freshInstance = true;
    private static ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("AppContext.xml");

    /*****************************************************
     * doPost Method
     * Goes directly to doGet
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     ****************************************************/
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        doGet(request,response);
    }

    /****************************************************
     * doGet Method
     * Handles user requests relating to logging in, logging out, adding, and going home
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     ***************************************************/
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        if(freshInstance) {
            freshInstance = false; // This table refresh only occurs at start of web app
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
            } catch (CannotGetJdbcConnectionException e) {
                e.printStackTrace();
                System.out.println("Database connection could not be established");
                request.getRequestDispatcher("/WEB-INF/jsp/view/databaseError.jsp")
                        .forward(request, response);
            }
        }

        String action = request.getParameter("action");
        if(action == null)
            action = "goHome";

        switch(action)
        {
            case "login":
                this.login(request, response);
                break;
            case "logout":
                this.logout(request, response);
                break;
            case "goHome":
                this.goHome(request, response);
                break;

            default:
                this.goHome(request, response);
                break;
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username=request.getParameter("username");
        String password=request.getParameter("password");
        HttpSession session = request.getSession(true);
        UserDao user = (UserDao) context.getBean("userDao");
        session.setAttribute("auth", "true");

        if(username == null) { // Accessing the page via link without logging in
            request.getRequestDispatcher("/WEB-INF/jsp/view/home.jsp")
                    .forward(request, response);
        }

        // AUTHENTICATION //
        if(user.isAuthCorrect(username, password)) // authenticate through database
        {
            session.setAttribute("username", username);
            session.setAttribute("first_name", user.selectFirstName(username));
            request.getRequestDispatcher("/welcome")
                    .forward(request, response);
        }
        else
        {
            session.setAttribute("auth","false");
            request.getRequestDispatcher("/WEB-INF/jsp/view/home.jsp")
                    .forward(request, response);
        }
    }
    /*********************************************************
     *logout
     * Logs the user out
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     ********************************************************/
    private void logout(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException
    {
        if (request.getSession().getAttribute("username") == null) {
            response.sendRedirect("home");
            return;
        }
        HttpSession session = request.getSession(false);
        session.invalidate();//to invalidate the session
        request.getRequestDispatcher("/WEB-INF/jsp/view/logout.jsp")
                .forward(request, response);
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
        HttpSession session = request.getSession(true);
        session.setAttribute("auth", "null"); // Incorrect auth message doesn't need to be shown

        request.getRequestDispatcher("/WEB-INF/jsp/view/home.jsp")
                .forward(request, response);
    }
}
