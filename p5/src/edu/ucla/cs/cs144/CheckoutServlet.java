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
public class CheckoutServlet extends HttpServlet implements Servlet {
       
    public CheckoutServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String itemID = request.getParameter("id"); //get id from url

        //expects a session object to have been made with required information
        HttpSession session = request.getSession();
        if(session==null) {
            //error, need to print message, maybe just redirect to item page with itemID
        }

        //TODO
        
        request.setAttribute("buyPrice", session.getAttribute("buyPrice"));
        request.getRequestDispatcher("jsp/checkout.jsp").forward(request, response);
    }
}
