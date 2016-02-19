package edu.ucla.cs.cs144;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/* For Representing and Parsing an XML String */

@XmlRootElement
public class Item {
	@XmlAttribute(name="ItemID")
	String id;			//itemID attribute

	@XmlElement(name="Name")
	Stirng name;		//name

	@XmlElement(name="Currently")
	String currently;	//Currently

	@XmlElement(name="First_Bid")
	Stirng firstBid;	//First_Bid

	@XmlElement(name="Number_of_Bids")
	int numBids;		//Number_of_Bids

	@XmlElement(name="Location")
	String location;	//Location

	@XmlElement(name="Country")
	String country;		//Country

	@XmlElement(name="Started")
	String started;		//Started

	@XmlElement(name="Ends")
	String ends			//Ends

	//one special object for categories (array list)
	//one special object for Seller

	@XmlElement(name="Description")
	String description;

}