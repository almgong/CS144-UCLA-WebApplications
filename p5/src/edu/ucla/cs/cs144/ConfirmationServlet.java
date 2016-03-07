package edu.ucla.cs.cs144;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.xml.bind.JAXB;
import java.io.StringReader;
import java.util.ArrayList;

//timestamp
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

//session
import javax.servlet.http.HttpSession;

//handles logic for user checkout of an item
public class ConfirmationServlet extends HttpServlet implements Servlet {
       
    public ConfirmationServlet() {}

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        request.setAttribute("time", sdf.format(now));
        request.setAttribute("ccnum", request.getParameter("creditCardNum"));

        //expects a session object to have been made with required information
        HttpSession session = request.getSession();
        if(session==null) {
            //error, need to print message, maybe just redirect to item search?
            //request.setAttribute("valid", false);
        }

        //pass needed information to client
        //request.setAttribute("valid", true);
        request.setAttribute("id", session.getAttribute("id")); //get id from session
        request.setAttribute("buyPrice", session.getAttribute("buyPrice"));
        request.setAttribute("name", session.getAttribute("name"));

        //TODO some logic for timestamp

        request.getRequestDispatcher("jsp/confirm.jsp").forward(request, response);
    }
}
