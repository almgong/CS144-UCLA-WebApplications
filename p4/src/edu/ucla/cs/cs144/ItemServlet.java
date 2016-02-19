package edu.ucla.cs.cs144;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.xml.bind.JAXB;
import java.io.StringReader;

public class ItemServlet extends HttpServlet implements Servlet {
       
    public ItemServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // your codes here
        String itemId = request.getParameter("id");	//get id

        //existence check
        if(itemId.length() == 0)
        	throw new ServletException();

        String xmlForId = AuctionSearchClient.getXMLDataForItemId(itemId);
        StringReader sr = new StringReader(xmlForId);
        Item i = JAXB.unmarshal(sr, Item.class);

        request.setAttribute("xml", xmlForId);
        request.setAttribute("desc", i.getDescription());
        request.setAttribute("name", i.getName());
        request.getRequestDispatcher("jsp/itemsearchresult.jsp").forward(request, response);
    }
}
