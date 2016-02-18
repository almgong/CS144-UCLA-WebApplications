package edu.ucla.cs.cs144;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchServlet extends HttpServlet implements Servlet {
       
    public SearchServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // get parameters
        String query = (String)request.getAttribute("q");
        int numResultsToSkip = Integer.parseInt((String)request.getAttribute("numResultsToSkip"));
        int numResultsToReturn = Integer.parseInt((String)request.getAttribute("numResultsToReturn"));
        
        SearchResult[] results = AuctionSearchClient.basicSearch(query, 
        	numResultsToSkip, numResultsToReturn);
    }
}
