/* CS144
 *
 * Parser skeleton for processing item-???.xml files. Must be compiled in
 * JDK 1.5 or above.
 *
 * Instructions:
 *
 * This program processes all files passed on the command line (to parse
 * an entire diectory, type "java MyParser myFiles/*.xml" at the shell).
 *
 * At the point noted below, an individual XML file has been parsed into a
 * DOM Document node. You should fill in code to process the node. Java's
 * interface for the Document Object Model (DOM) is in package
 * org.w3c.dom. The documentation is available online at
 *
 * http://java.sun.com/j2se/1.5.0/docs/api/index.html
 *
 * A tutorial of Java's XML Parsing can be found at:
 *
 * http://java.sun.com/webservices/jaxp/
 *
 * Some auxiliary methods have been written for you. You may find them
 * useful.
 */

package edu.ucla.cs.cs144;

import java.io.*;
import java.text.*;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ErrorHandler;

/*for writing to files*/
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

//timestamp
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

class MyParser {
    
    static final String columnSeparator = "|*|";
    static DocumentBuilder builder;
    
    static final String[] typeName = {
	"none",
	"Element",
	"Attr",
	"Text",
	"CDATA",
	"EntityRef",
	"Entity",
	"ProcInstr",
	"Comment",
	"Document",
	"DocType",
	"DocFragment",
	"Notation",
    };
    
    static class MyErrorHandler implements ErrorHandler {
        
        public void warning(SAXParseException exception)
        throws SAXException {
            fatalError(exception);
        }
        
        public void error(SAXParseException exception)
        throws SAXException {
            fatalError(exception);
        }
        
        public void fatalError(SAXParseException exception)
        throws SAXException {
            exception.printStackTrace();
            System.out.println("There should be no errors " +
                               "in the supplied XML files.");
            System.exit(3);
        }
        
    }
    
    /* Non-recursive (NR) version of Node.getElementsByTagName(...)
     */
    static Element[] getElementsByTagNameNR(Element e, String tagName) {
        Vector< Element > elements = new Vector< Element >();
        Node child = e.getFirstChild();
        while (child != null) {
            if (child instanceof Element && child.getNodeName().equals(tagName))
            {
                elements.add( (Element)child );
            }
            child = child.getNextSibling();
        }
        Element[] result = new Element[elements.size()];
        elements.copyInto(result);
        return result;
    }
    
    /* Returns the first subelement of e matching the given tagName, or
     * null if one does not exist. NR means Non-Recursive.
     */
    static Element getElementByTagNameNR(Element e, String tagName) {
        Node child = e.getFirstChild();
        while (child != null) {
            if (child instanceof Element && child.getNodeName().equals(tagName))
                return (Element) child;
            child = child.getNextSibling();
        }
        return null;
    }
    
    /* Returns the text associated with the given element (which must have
     * type #PCDATA) as child, or "" if it contains no text.
     */
    static String getElementText(Element e) {
        if (e.getChildNodes().getLength() == 1) {
            Text elementText = (Text) e.getFirstChild();
            return elementText.getNodeValue();
        }
        else
            return "";
    }
    
    /* Returns the text (#PCDATA) associated with the first subelement X
     * of e with the given tagName. If no such X exists or X contains no
     * text, "" is returned. NR means Non-Recursive.
     */
    static String getElementTextByTagNameNR(Element e, String tagName) {
        Element elem = getElementByTagNameNR(e, tagName);
        if (elem != null)
            return getElementText(elem);
        else
            return "";
    }
    
    /* Returns the amount (in XXXXX.xx format) denoted by a money-string
     * like $3,453.23. Returns the input if the input is an empty string.
     */
    static String strip(String money) {
        if (money.equals(""))
            return money;
        else {
            double am = 0.0;
            NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
            try { am = nf.parse(money).doubleValue(); }
            catch (ParseException e) {
                System.out.println("This method should work for all " +
                                   "money values you find in our data.");
                System.exit(20);
            }
            nf.setGroupingUsed(false);
            return nf.format(am).substring(1);
        }
    }
    
    /* Process one items-???.xml file.
     */
    static void processFile(File xmlFile) {
        Document doc = null;
        try {
            doc = builder.parse(xmlFile);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(3);
        }
        catch (SAXException e) {
            System.out.println("Parsing error on file " + xmlFile);
            System.out.println("  (not supposed to happen with supplied XML files)");
            e.printStackTrace();
            System.exit(3);
        }
        
        /* At this point 'doc' contains a DOM representation of an 'Items' XML
         * file. Use doc.getDocumentElement() to get the root Element. */
        System.out.println("Successfully parsed - " + xmlFile);
        
        /* Our Code, get root and array of all Item Elements */
        Element e = doc.getDocumentElement();
        Element[] items = getElementsByTagNameNR(e, "Item");

        for(int i = 0; i < items.length; i++) {
            parseItem(items[i]); //parse and writes as needed
        }
        
        /**************************************************************/
        
    }


    /********************* Custom Helpers *************************/

    /**
     * Given an Item node, parse it and add to the respective files the CSV
     * values.
    **/
    public static void parseItem(Element e) {
        //filenames of files for each relation in schema
        String sellerFile = "sql/Seller.dat",
            bidderFile = "sql/Bidder.dat",
            itemFile = "sql/Item.dat",
            bidsFile = "sql/Bids.dat",
            itemCategoryFile = "sql/Category.dat"; //may need to change in final****

        //Each string is a row to add to a particular file, given current Item

        //parse Sellers and Bidders 
        String sellerRow = parseSellerData(e);
        String bidderRow = parseBidderData(e);  //can return empty string

        //parse Items
        String itemRow = parseItemData(e);

        //parse for Bids, can be empty string
        String bidsRow = parseBidsData(e);

        //parse for ItemCategory
        String itemCategoryRow = parseItemCategoryData(e);

        //code that writes each xxxRow variable to appropriate file
        writeToFile(sellerFile, sellerRow.getBytes());

        if(!bidderRow.equals(""))
            writeToFile(bidderFile, bidderRow.getBytes());

        writeToFile(itemFile, itemRow.getBytes());
            
        if(!bidsRow.equals(""))
            writeToFile(bidsFile, bidsRow.getBytes());
        
        writeToFile(itemCategoryFile, itemCategoryRow.getBytes());
    }

    /**
     * Writes bytes of 'data' to file specified by 'filename'.
    **/
    public static void writeToFile(String filename, byte[] data) {
        File file;
        FileOutputStream fstream = null;

        try {

            file = new File(filename);
            if(!file.exists()) {
                file.createNewFile(); 
            }
            
            fstream = new FileOutputStream(file, true); //true to append

            fstream.write(data);
            fstream.flush();
            fstream.close();
        }
        catch(IOException e) {
            e.printStackTrace();
            System.err.println("IOException in writeToFile()");
            System.exit(-1);
        }
        catch(Exception e) {
            System.err.println("Exception in writeToFile()");
            System.exit(-1);
        }
        finally {
            try {
                if(fstream != null) {
                    fstream.close();
                }
            }
            catch(Exception e) {
                System.err.println("Unexpected error in closing file");
            }
        }
    }


    /**
     * Returns formatted CSV string for row(s) for the input element
    **/
    public static String parseSellerData(Element e) {
        //vars to potentially populate
        String sellerID, sellerRating;
        sellerID=sellerRating="NULL"; 

        Element seller = getElementByTagNameNR(e, "Seller");
        sellerID = seller.getAttribute("UserID");
        sellerRating = seller.getAttribute("Rating");

        return (sellerID+"\t"+sellerRating+"\n");
    }

    //special: string can be "" since there could be no bidders for an item
    public static String parseBidderData(Element e) {
        String bidderID, location, country, rating;
        bidderID = location = country = rating = "NULL";

        Element locationEle, countryEle;

        String bidString = "";  //the final string, can have multiple bidders

        //array of Bid elements
        Element[] bids = getElementsByTagNameNR(getElementByTagNameNR(e, "Bids"), 
            "Bid");

        //for each bid
        Element bidderEle = null;
        for(int i=0; i< bids.length; i++) {
            bidderEle = getElementByTagNameNR(bids[i], "Bidder");

            bidderID = bidderEle.getAttribute("UserID");
            rating = bidderEle.getAttribute("Rating");
            location = "NULL";
            country = "NULL";

            locationEle = getElementByTagNameNR(bidderEle, 
                "Location");
            countryEle = getElementByTagNameNR(bidderEle,
                "Country");

            //location and country for bidder is not required
            if(locationEle!=null) location = getElementText(locationEle);
            if(countryEle!=null) country = getElementText(countryEle);
 

            bidString += (bidderID+"\t"+location+"\t"+country+"\t"+rating+"\n");
        }

        return bidString;
    }

    //same as above, but for Item relation
    public static String parseItemData(Element e) {
       
        String itemID, name, buyPrice, currently, firstBid,
        numBids, start, end, seller, description, location, country, lat, longitude;

        itemID=name=buyPrice=currently=firstBid=numBids=start=end=seller=
        description= "NULL";

        itemID = e.getAttribute("ItemID");
        name = getElementTextByTagNameNR(e, "Name");
        buyPrice = strip(getElementTextByTagNameNR(e, "Buy_Price"));

        currently = strip(getElementTextByTagNameNR(e, "Currently"));
        firstBid = strip(getElementTextByTagNameNR(e, "First_Bid"));

        //numBids = getElementTextByTagNameNR(e, "Number_of_Bids");

        start = getElementTextByTagNameNR(e, "Started");
        end = getElementTextByTagNameNR(e, "Ends");

        seller = getElementByTagNameNR(e, "Seller").getAttribute("UserID");

        description = getElementTextByTagNameNR(e, "Description");
        description = description.substring(0, 
            Math.min(4000, description.length()));  //truncate to 4000 max

        //checks
        if(buyPrice.equals("")) buyPrice = "NULL";

        start = getSQLTimestamp(start);
        end = getSQLTimestamp(end);

        Element locationEle = getElementByTagNameNR(e, "Location");
        location = getElementText(locationEle);
        country = getElementText(getElementByTagNameNR(e, "Country"));

        lat = locationEle.getAttribute("Latitude");
        longitude = locationEle.getAttribute("Longitude");

        //null checks
        if(lat.equals("")) lat = "NULL";
        if(longitude.equals("")) longitude = "NULL";

        return (itemID+"\t"+name+"\t"+buyPrice+"\t"+currently+"\t"+firstBid+"\t"+start+"\t"+end+
            "\t"+seller+"\t"+description+"\t"+location+"\t"+country+"\t"+lat+"\t"+longitude+"\n");
    }


    public static String parseItemCategoryData(Element e) {
        String itemID = "NULL";
        String catString = "";

        itemID = e.getAttribute("ItemID");
        Element[] categories = getElementsByTagNameNR(e, "Category");
        for(int i = 0; i < categories.length; i++) {
            catString += (itemID+"\t"+getElementText(categories[i])+"\n"); 
        }

        return catString;
    }

    public static String getSQLTimestamp(String origTime) {
        SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String ret = "";
         try {
            String inputFormat = "MMM-dd-yy HH:mm:ss";
            DateFormat df = new SimpleDateFormat(inputFormat);
            Date d = df.parse(origTime);
            ret = targetFormat.format(d);
        }
        catch(ParseException ex) {
            System.err.println("Error in parse");
            System.exit(-1);
        }

        return ret;
    }

    public static String parseBidsData (Element e) {        
        String itemID, bidderID, amount, timestamp;

        itemID=bidderID=amount=timestamp="NULL";   

        itemID = e.getAttribute("ItemID");  

        String bidString = "";  //the final string, can have multiple bidders

        //array of Bid elements
        Element[] bids = getElementsByTagNameNR(getElementByTagNameNR(e, "Bids"), 
            "Bid");

        //for each bid
        Element bidderEle = null;
        Element amountEle = null;
        for(int i=0; i< bids.length; i++) {
            bidderEle = getElementByTagNameNR(bids[i], "Bidder");
            bidderID = bidderEle.getAttribute("UserID");

            amount = getElementTextByTagNameNR(bids[i], "Amount");
            amount = strip(amount);
 
            timestamp = getElementTextByTagNameNR(bids[i], "Time");
            timestamp = getSQLTimestamp(timestamp);

            bidString += (bidderID+"\t"+itemID+"\t"+amount+"\t"+timestamp+"\n");
        }
        return bidString;
    }

    /***************** End Custom Helpers ************************/

    
    public static void main (String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java MyParser [file] [file] ...");
            System.exit(1);
        }
        
        /* Initialize parser. */
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(false);
            factory.setIgnoringElementContentWhitespace(true);      
            builder = factory.newDocumentBuilder();
            builder.setErrorHandler(new MyErrorHandler());
        }
        catch (FactoryConfigurationError e) {
            System.out.println("unable to get a document builder factory");
            System.exit(2);
        } 
        catch (ParserConfigurationException e) {
            System.out.println("parser was unable to be configured");
            System.exit(2);
        }
        
        /* Process all files listed on command line. */
        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
            File currentFile = new File(args[i]);
            processFile(currentFile);
        }
    }
}
