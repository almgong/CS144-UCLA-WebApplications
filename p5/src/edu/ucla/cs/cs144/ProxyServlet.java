package edu.ucla.cs.cs144;

import java.io.*;
import java.net.HttpURLConnection;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.net.URL;
import java.net.URLEncoder;

public class ProxyServlet extends HttpServlet implements Servlet {
       
    public ProxyServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	// Get the user's query string.
        String query = request.getParameter("query");
        if (query == null || query == "") {
        	// Prevent non-user-friendly errors from displaying when no query parameter given.
        	response.setContentType("text/xml");
            return;
        }

        String targetURL = "http://google.com/complete/search?output=toolbar&q=" + URLEncoder.encode(query, "utf-8");
        URL u = new URL(targetURL);

        // Make a request to Google's suggest server.
        HttpURLConnection con = (HttpURLConnection) u.openConnection();
        con.setRequestMethod("GET");
        con.connect();

        // Read the response.
        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
      	StringBuilder stringBuilder = new StringBuilder();
 
		String line = null;
		while ((line = reader.readLine()) != null)
		{
			stringBuilder.append(line + "\n");
		}
		String resp = stringBuilder.toString();
    
        // Send the response from Google's suggest server back to the user.
        response.setContentType("text/xml");
        PrintWriter out = response.getWriter();
        out.println(resp);
        //System.out.println(resp);
        out.close();

    }
}
