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
        String query = (String)request.getParameter("q");
        int numResultsToSkip = Integer.parseInt((String)request.getParameter("numResultsToSkip"));
        int numResultsToReturn = Integer.parseInt((String)request.getParameter("numResultsToReturn"));
        
        SearchResult[] results = AuctionSearchClient.basicSearch(query, 
        	numResultsToSkip, numResultsToReturn);
        String[] ids = new String[results.length];
        String[] names = new String[results.length];

        for(int i=0; i<results.length;i++) {
        	ids[i] = results[i].getItemId();
        	names[i] = results[i].getName();
        }
        request.setAttribute("ids", ids);
        request.setAttribute("names", names);
        request.getRequestDispatcher("/jsp/keywordsearchresult.jsp").forward(request, response);
    }
}
