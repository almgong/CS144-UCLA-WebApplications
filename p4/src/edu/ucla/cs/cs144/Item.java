package edu.ucla.cs.cs144;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;

/* For Representing and Parsing an XML String */

@XmlRootElement(name="Item")
public class Item {
	@XmlAttribute(name="ItemID")
	private String id;			//itemID attribute
	private String name;		//name

	@XmlElement(name="Currently")
	private String currently;	//Currently

	@XmlElement(name="First_Bid")
	private String firstBid;	//First_Bid

	@XmlElement(name="Number_of_Bids")
	private int numBids;		//Number_of_Bids

	@XmlElement(name="Location")
	private String location;	//Location

	@XmlElement(name="Country")
	private String country;		//Country

	@XmlElement(name="Started")
	private String started;		//Started

	@XmlElement(name="Ends")
	private String ends;			//Ends

	//one special object for categories (array list)
	//one special object for Seller

	@XmlElement(name="Description")
	private String description;


	//setters
	private void setDescription(String description) {
		this.description = description;
	}

	public void setName(String n) {
		this.name = n;
	}



	//getters
	public String getDescription() {
		return this.description;
	}

	@XmlElement(name="Name")
	public String getName() {
		return this.name;
	}
}