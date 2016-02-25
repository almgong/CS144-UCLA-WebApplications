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

public class ItemServlet extends HttpServlet implements Servlet {
       
    public ItemServlet() {}

    //really only for use with Category
    private String[] convertALtoA(ArrayList<Category> a) {
        if(a==null)
            return new String[0];

        String[] cats = new String[a.size()];
        for(int i = 0; i < a.size(); i++) 
            cats[i] = a.get(i).getCategory();
        return cats;
    }

    private Bid[] convertALtoABid(ArrayList<Bid> a) {
        if(a==null) 
            return new Bid[0];

        Bid[] bids = new Bid[a.size()];
        for(int i = 0; i < a.size(); i++)
            bids[i] = a.get(i);
        return bids;
    }

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

        //pass all info needed for view to render
        request.setAttribute("id", i.getID());
        request.setAttribute("name", i.getName());
        request.setAttribute("categories", convertALtoA(i.getCategories()));
        request.setAttribute("currently", i.getCurrently());
        request.setAttribute("firstBid", i.getFirstBid());
        request.setAttribute("numBids", i.getNumBids());
        request.setAttribute("item-location", i.getLocation().getLocation());
        request.setAttribute("item-location-lat", i.getLocation().getLatitude());
        request.setAttribute("item-location-lon", i.getLocation().getLongitude());
        request.setAttribute("country", i.getCountry());
        request.setAttribute("started", i.getStarted());
        request.setAttribute("ends", i.getEnds());

        //seller
        request.setAttribute("sellerRating", i.getSellerRating());
        request.setAttribute("sellerUserId", i.getSellerUserId());

        //bids - assume front end knows what a Bid obj is
        Bid[] bids = convertALtoABid(i.getBids());
        request.setAttribute("bids", bids);

        request.setAttribute("desc", i.getDescription());
        request.getRequestDispatcher("jsp/itemsearchresult.jsp").forward(request, response);
    }
}
