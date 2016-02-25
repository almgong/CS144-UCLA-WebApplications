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

    public static String getNiceTimestamp(String origTime) {
        SimpleDateFormat targetFormat = new SimpleDateFormat("MMMMM dd, yyyy hh:mm:ss aaa");
        String ret = "";
         try {
            String inputFormat = "MMM-dd-yy HH:mm:ss";
            DateFormat df = new SimpleDateFormat(inputFormat);
            Date d = df.parse(origTime);
            ret = targetFormat.format(d);
        }
        catch(ParseException ex) {
            System.err.println("Error in getting SQL timestamp.");
            System.exit(-1);
        }

        return ret;
    }

    public static void formatBidTimes(Bid[] bids) {
        for(int i =0; i < bids.length; i++) {
            bids[i].setTime(getNiceTimestamp(bids[i].getTime()));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // your codes here
        String itemId = request.getParameter("id");	//get id
        String xmlForId = AuctionSearchClient.getXMLDataForItemId(itemId);

        //if invalid id entered
        if(xmlForId.length() == 0) {
            request.setAttribute("valid", "false");
            request.getRequestDispatcher("jsp/itemsearchresult.jsp").forward(request, response);
            return;
        }

        StringReader sr = new StringReader(xmlForId);
        Item i = JAXB.unmarshal(sr, Item.class);

        request.setAttribute("valid", "true"); //a valid + existing id entered

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
        request.setAttribute("started", getNiceTimestamp(i.getStarted()));
        request.setAttribute("ends", getNiceTimestamp(i.getEnds()));

        //seller
        request.setAttribute("sellerRating", i.getSellerRating());
        request.setAttribute("sellerUserId", i.getSellerUserId());

        //bids - assume front end knows what a Bid obj is
        Bid[] bids = convertALtoABid(i.getBids());
        formatBidTimes(bids);   //formats the timestamps in bid
        request.setAttribute("bids", bids);

        request.setAttribute("desc", i.getDescription());
        request.getRequestDispatcher("jsp/itemsearchresult.jsp").forward(request, response);
    }
}
