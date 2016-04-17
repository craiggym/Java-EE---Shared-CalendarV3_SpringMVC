package com.Calendar;

import com.DAO.UserDao;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * Created by craig on 2/25/16.
 */

@Controller
public class RegisterController {
    // Variables //



    @ModelAttribute("user")
    public User createModel() {
        return new User();
    }
    /************************************************************************
     * Title: addUser

     ***********************************************************************/
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String addUser(@ModelAttribute("user") User user, Map<String,String> model)
    {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("AppContext.xml"); // New AppContext pointing to xml config
        UserDao userDao = (UserDao) context.getBean("userDao");

        if (user.getUsername() == "") model.put("unameEmpty", "true");
        else model.put("unameEmpty", "false");

        if(user.getPassword() == "") model.put("passEmpty", "true");
        else model.put("pass2Empty", "false");

        if(user.getPassword2() == "") model.put("pass2Empty", "true");
        else model.put("pass2Empty", "false");

        if (user.getFirst_name() == "") model.put("fnameEmpty", "true");
        else model.put("fnameEmpty", "false");

        if (user.getLast_name() == "") model.put("lnameEmpty", "true");
        else model.put("lnameEmpty", "false");

        if(!user.getPassword().equalsIgnoreCase(user.getPassword2())) model.put("passMatch", "false");
        else model.put("passMatch", "true");



        if( user.getUsername() == null|| !user.getPassword().equalsIgnoreCase(user.getPassword2()) || user.getPassword() == "" ||
                user.getFirst_name() == "" || user.getFirst_name() == "" || user.getLast_name() == "") // Password inputs must match
        {
            return "register";
        }
        else {
            try { // catch occurs when HSQLDB is not established
                // First check if username is unique otherwise throw fail
                String username = user.getUsername();
                if (!userDao.userExists(username)) { //User doesn't exist. Proceed with user add:
                    // Take rest of parameters from form and set them in local variables //
                    String e_mail = user.getE_mail();
                    String pass = user.getPassword();
                    String fname = user.getFirst_name();
                    String lname = user.getLast_name();


                    // Create new user and set the attributes using local variables. //
                    user.setUserID(userDao.countUsers()+1);
                    userDao.insertUser(user); // Inserts the user into HSQLDB table

                    model.put("duplicate", "false");
                    return "registerSuccess";
                } else { // Username exists in the database
                    model.put("duplicate", "true");

                    return "register";
                }
            } catch (CannotGetJdbcConnectionException e) {
                e.printStackTrace();
                System.out.println("Database connection could not be established");
                return "databaseError";
            }
        }
    }

    /************************************************************************
     * Title: userRegister
     * Description: Brings user to the register page
     ***********************************************************************/
    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String userRegister(Model model)
    {
        model.addAttribute("user", new User());
        return "register";
    }

}
