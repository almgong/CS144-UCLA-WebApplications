package edu.ucla.cs.cs144;

import java.util.Calendar;
import java.util.Date;

import edu.ucla.cs.cs144.AuctionSearch;
import edu.ucla.cs.cs144.SearchRegion;
import edu.ucla.cs.cs144.SearchResult;

public class AuctionSearchTest {
	public static void main(String[] args1)
	{
		AuctionSearch as = new AuctionSearch();

		String message = "Test message";
		String reply = as.echo(message);
		System.out.println("Reply: " + reply);
		
		String query = "kitchenware";
		SearchResult[] basicResults = as.basicSearch(query, 0, 10000);
		System.out.println("Basic Seacrh Query: " + query);
		System.out.println("Received " + basicResults.length + " results");
		//for(SearchResult result : basicResults) {
	//		System.out.println(result.getItemId() + ": " + result.getName());
	//	}
		
		SearchRegion region =
		    new SearchRegion(33.774, -118.63, 34.201, -117.38); 
		SearchResult[] spatialResults = as.spatialSearch("camera", region, 0, 20);
		System.out.println("Spatial Seacrh");
		System.out.println("Received " + spatialResults.length + " results");
		for(SearchResult result : spatialResults) {
			System.out.println(result.getItemId() + ": " + result.getName());
		}
		
		// String itemId = "1497595357";
		// String item = as.getXMLDataForItemId(itemId);
		// System.out.println("XML data for ItemId: " + itemId);
		// System.out.println(item);
		

		// Add your own test here
		// Test Name and description with quotes inside.
	// 	itemId = "1045033048";
	// 	item = as.getXMLDataForItemId(itemId);
	// 	System.out.println("XML data for ItemId: " + itemId);
	// 	System.out.println(item);
	
	// 	// Test description and category with &amp;.
	// 	itemId = "1045034546";
	// 	item = as.getXMLDataForItemId(itemId);
	// 	System.out.println("XML data for ItemId: " + itemId);
	// 	System.out.println(item);

	// 	// Test location with no lat/long attributes.
	// 	itemId = "1045034667";
	// 	item = as.getXMLDataForItemId(itemId);
	// 	System.out.println("XML data for ItemId: " + itemId);
	// 	System.out.println(item);

	// 	// Test invalid itemId.
	// 	itemId = "0000";
	// 	item = as.getXMLDataForItemId(itemId);
	// 	System.out.println("XML data for ItemId: " + itemId);
	// 	System.out.println(item);

	// 	// Test auction with bids.
	// 	itemId = "1045041605";
	// 	item = as.getXMLDataForItemId(itemId);
	// 	System.out.println("XML data for ItemId: " + itemId);
	// 	System.out.println(item);

	// 	// Test auction with bidder with neither location nor country.
	// 	itemId = "1045700537";
	// 	item = as.getXMLDataForItemId(itemId);
	// 	System.out.println("XML data for ItemId: " + itemId);
	// 	System.out.println(item);

	// 	// Test auction with bidder with location but no country.
	// 	itemId = "1045460719";
	// 	item = as.getXMLDataForItemId(itemId);
	// 	System.out.println("XML data for ItemId: " + itemId);
	// 	System.out.println(item);
	}
}
