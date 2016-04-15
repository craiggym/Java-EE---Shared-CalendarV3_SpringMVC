package com.Calendar;

import com.DAO.EventDao;
import com.DAO.UserDao;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;


@Controller
@RequestMapping("/")
public class HomePageController
{
   @RequestMapping(value={"", "home"})
    public ModelAndView viewAllEvents(Map<String,String> map){
       ModelAndView model = new ModelAndView("register");
       map.put("name", "CraigYouBeast");
       return model;
   }

}
