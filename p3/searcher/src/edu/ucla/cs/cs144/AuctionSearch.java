package edu.ucla.cs.cs144;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

//timestamp
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.lucene.document.Document;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import edu.ucla.cs.cs144.DbManager;
import edu.ucla.cs.cs144.SearchRegion;
import edu.ucla.cs.cs144.SearchResult;

import edu.ucla.cs.cs144.BasicSE;

public class AuctionSearch implements IAuctionSearch {

	/* 
         * You will probably have to use JDBC to access MySQL data
         * Lucene IndexSearcher class to lookup Lucene index.
         * Read the corresponding tutorial to learn about how to use these.
         *
	 * You may create helper functions or classes to simplify writing these
	 * methods. Make sure that your helper functions are not public,
         * so that they are not exposed to outside of this class.
         *
         * Any new classes that you create should be part of
         * edu.ucla.cs.cs144 package and their source files should be
         * placed at src/edu/ucla/cs/cs144.
         *
         */
	
	public SearchResult[] basicSearch(String query, int numResultsToSkip, 
			int numResultsToReturn) {
		
		//the array to return
		SearchResult[] ret = new SearchResult[0];

		//quick correctness check
		if(numResultsToSkip < 0 || numResultsToReturn < 0)
			return ret;

		try {
			BasicSE se = new BasicSE();
			TopDocs top = se.search(query, numResultsToSkip+numResultsToReturn);
			ScoreDoc[] results = top.scoreDocs;
			int retSize = results.length - numResultsToSkip;
			if(retSize < 0) retSize = 0;
			ret = new SearchResult[retSize];

			//populate ret with SearchResult objects
			int retIndex = 0; 		//index for tracking ret 
			Document currDoc = null;
			SearchResult sr = null;
			for(int i = 0; i < results.length; i++) {
				if(i >= numResultsToSkip) {	//skip numResultsToSkip
					currDoc = se.getDoc(results[i].doc);	
					sr = new SearchResult(currDoc.get("id"), currDoc.get("name"));
					ret[retIndex] = sr;
					retIndex++;
				}
			}

			//if numRestults < retSize, need to truncate array
			if(numResultsToReturn < retSize) {
				SearchResult[] retTrunc = new SearchResult[numResultsToReturn];
				for(int i = 0; i < retTrunc.length; i++) 
					retTrunc[i] = ret[i];
				ret = retTrunc;
			}
		}
		catch(IOException e) {
			e.printStackTrace();
			System.err.println("in basicSearch()");
			System.exit(-1);
		}
		catch(ParseException e) {
			e.printStackTrace();
			System.err.println("in basicSearch()");
			System.exit(-1);
		}

		return ret;
	}

	public SearchResult[] spatialSearch(String query, SearchRegion region,
			int numResultsToSkip, int numResultsToReturn) {

		//the array to return
		SearchResult[] ret = new SearchResult[0];

		//quick correctness check
		if(numResultsToSkip < 0 || numResultsToReturn < 0)
			return ret;

		ArrayList<SearchResult> intersection = new ArrayList<>();

		// Set up database connection. 
		try {
			Connection c = DbManager.getConnection(true);

			// Get keyword matches in either description, title, or category.
			SearchResult[] sr = basicSearch(query, 0, 20000);

			// Get spatially relevant items.
			// Create polygon region for search. 
			String polygon = "Polygon((" + region.getLx() + " " + region.getLy() +"," +
										    region.getRx() + " " + region.getLy() +"," +
										    region.getRx() + " " + region.getRy() +"," +
										    region.getLx() + " " + region.getRy() +"," +
										    region.getLx() + " " + region.getLy() + "))";

			// Generic spatial seqrch query. 
			PreparedStatement prepareSpatialSearch = c.prepareStatement
			(
				"SELECT item_id FROM geom WHERE MBRContains(GeomFromText(?), g);"
			);

			// setString puts single quotes around the polygon string.
			prepareSpatialSearch.setString(1, polygon);

			ResultSet rs = prepareSpatialSearch.executeQuery();
			int count = 0;

			while(rs.next()) {
				String itemId = rs.getString("item_id");
				for(SearchResult s : sr) {
					if (itemId.equals(s.getItemId())) {
						// add to total arraylist. 
						intersection.add(s);
					}
				}
				count++;
			}
			//System.out.println("Spatial Count: " + count);

		} catch (SQLException se) {
			System.err.println("SQLException: " + se.getMessage());
		}

		int numResults = intersection.size();
		int retSize = numResults - numResultsToSkip;
		if(retSize < 0) {
			retSize = 0;
		} else if(retSize > numResultsToReturn) {
			retSize = numResultsToReturn;
		}
		ret = new SearchResult[retSize];

		for (int i = numResultsToSkip; i < numResultsToSkip + retSize; i++) {
			ret[i-numResultsToSkip] = intersection.get(i);
		}

		// Return those that match both criteria. Merge the ResultSets into one array of SearchResults.
		return ret;
	}

	public String getXMLDataForItemId(String itemId) {
		String createdXML = "";
		
		// Set up database connection. 
		try {
			Connection c = DbManager.getConnection(true);

			// Generic item search query. 
			PreparedStatement itemQuery = c.prepareStatement
			(
				"SELECT * FROM Item, Seller WHERE item_id = ? AND seller = seller_id"
			);

			PreparedStatement bidsQuery = c.prepareStatement
			(
				"SELECT * FROM Bids, Bidder WHERE item = ? AND bidder = bidder_id ORDER BY time asc"
			);

			PreparedStatement categoryQuery = c.prepareStatement
			(
				"SELECT category FROM ItemCategory WHERE item = ?"
			);

			// setString puts single quotes around the itemId string.
			itemQuery.setString(1, itemId);

			ResultSet rs = itemQuery.executeQuery();

			// Check for invalid ID.
			if(!rs.next()) {
				return "";
			}

			// Get all info from the Item table.
			String id = escapeCharacters(rs.getString("item_id"));
			String idXML = "<Item ItemID=\"" + id + "\">\n";

			String name = escapeCharacters(rs.getString("name"));
			String nameXML = "<Name>" + name + "</Name>\n";

			double currently = rs.getDouble("currently");
			String currentlyXML = "<Currently>" + String.format("$%.2f", currently) + "</Currently>\n";

			double buyPrice = rs.getDouble("buy_price");
			String buyPriceXML = "";
			// Null comes back as 0. Only add a buyPrice element if it is not null.
			if(buyPrice != 0) {
				buyPriceXML = "<Buy_Price>" + String.format("$%.2f", buyPrice) + "</Buy_Price>\n";
			}
			
			double firstBid = rs.getDouble("first_bid");
			String firstBidXML = "<First_Bid>" + String.format("$%.2f", firstBid) + "</First_Bid>\n";

			String  latitude = (String) rs.getObject("latitude");
			String longitude = (String) rs.getObject("longitude");
			String location = escapeCharacters(rs.getString("location"));
			String attrLocation = "";
			if (!latitude.equals("NULL") && !longitude.equals("NULL")) {
				attrLocation = " Latitude=\"" + latitude + "\" Longitude=\"" + longitude + "\"";
			}
			String locationXML = "<Location" + attrLocation + ">" + location + "</Location>\n";

			String country = escapeCharacters(rs.getString("country"));
			String countryXML = "<Country>" + country + "</Country>\n";

			String started = getXMLTimestamp(rs.getString("started_time"));
			String startedXML = "<Started>" + started + "</Started>\n";

			String ends = getXMLTimestamp(rs.getString("end_time"));
			String endsXML = "<Ends>" + ends + "</Ends>\n";

			int sellerRating = rs.getInt("rating");
			String sellerId = escapeCharacters(rs.getString("seller_id"));
			String sellerXML = "<Seller Rating=\"" + sellerRating + "\" UserID=\"" + sellerId + "\" />\n";

			String description = escapeCharacters(rs.getString("description"));
			String descriptionXML = "<Description>" + description + "</Description>\n";

			// Get info from the Bids table. 
			bidsQuery.setString(1, itemId);
			rs = bidsQuery.executeQuery();

			String bidsXML = "<Bids>\n";
			int bidsCount = 0;
			while(rs.next()) {
				String thisBid = "<Bid>\n";

				int rating = rs.getInt("rating");
				String userID = escapeCharacters(rs.getString("bidder_id"));

				String bidderLoc = escapeCharacters(rs.getString("location"));
				String bidderCountry = escapeCharacters(rs.getString("country"));

				String bidderXML = "<Bidder Rating=\"" + rating + "\" UserID=\"" + userID + "\""; 
				if (bidderLoc.equals("NULL") && bidderCountry.equals("NULL")) {
					bidderXML += " />\n";
				} else {
					bidderXML += ">\n";
					if (!bidderLoc.equals("NULL")) {
						bidderXML += "<Location>" + bidderLoc + "</Location>\n";
					}
					if (!bidderCountry.equals("NULL")) {
						bidderXML += "<Country>" + bidderCountry + "</Country>\n";
					}
					bidderXML += "</Bidder>\n";
				}

				String bidTime = getXMLTimestamp(rs.getString("time"));
				String timeXML = "<Time>" + bidTime + "</Time>\n";

				double amt = rs.getDouble("amount");
				String amountXML = "<Amount>" + String.format("$%.2f", amt) + "</Amount>\n";

				thisBid += bidderXML + timeXML + amountXML + "</Bid>\n";
				bidsXML += thisBid;
				bidsCount++;
			}

			String numberOfBidsXML = "<Number_of_Bids>" + bidsCount + "</Number_of_Bids>\n";
			if(bidsCount == 0) {
				bidsXML = "<Bids />\n";
			} else {
				bidsXML += "</Bids>\n";
			}

			// Get info from the ItemCategory table.
			categoryQuery.setString(1, itemId);
			rs = categoryQuery.executeQuery();

			String categoryXML = "";
			while(rs.next()) {
				String cat = escapeCharacters(rs.getString("category"));
				categoryXML += "<Category>" + cat + "</Category>\n";
			}

			createdXML = idXML + nameXML + categoryXML + currentlyXML + buyPriceXML + firstBidXML + numberOfBidsXML + bidsXML + locationXML + countryXML + startedXML + endsXML + sellerXML + descriptionXML + "</Item>";


		} catch (SQLException se) {
			System.err.println("SQLException: " + se.getMessage());
		}
		return createdXML;
	}
	
	public String echo(String message) {
		return message;
	}


	private String escapeCharacters(String s) {
		return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
	}

	private String getXMLTimestamp(String origTime) {
        SimpleDateFormat targetFormat = new SimpleDateFormat("MMM-dd-yy HH:mm:ss");
        String ret = "";
        try {
            String inputFormat = "yyyy-MM-dd HH:mm:ss";
            DateFormat df = new SimpleDateFormat(inputFormat);
            Date d = df.parse(origTime);
            ret = targetFormat.format(d);
        } 
        catch(Exception ex) {
            System.err.println("Error in getting XML timestamp.");
            System.exit(-1);
         }
        return ret;
    }

}
