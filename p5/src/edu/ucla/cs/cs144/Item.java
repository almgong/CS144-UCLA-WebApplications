package edu.ucla.cs.cs144;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;

import java.util.ArrayList;

/* For Representing and Parsing an XML String */

@XmlRootElement(name="Item")
public class Item {
	@XmlAttribute(name="ItemID")
	private String id;			//itemID attribute
	private String name;		//name
	private ArrayList<Category> categories;	//list of categories

	@XmlElement(name="Bids")
	private Bids bids;			//Bids ele

	@XmlElement(name="Currently")
	private String currently;	//Currently

	@XmlElement(name="First_Bid")
	private String firstBid;	//First_Bid

	@XmlElement(name="Number_of_Bids")
	private int numBids;		//Number_of_Bids

	@XmlElement(name="Location")
	private Location location;	//Location

	@XmlElement(name="Country")
	private String country;		//Country

	@XmlElement(name="Started")
	private String started;		//Started

	@XmlElement(name="Ends")
	private String ends;			//Ends

	//one special object for categories (array list)
	//one special object for Seller
	@XmlElement(name="Seller")
	private Seller seller;

	@XmlElement(name="Description")
	private String description;


	//setters
	private void setID(String id) {
		this.id = id;
	}
	private void setName(String n) {
		this.name = n;
	}
	private void setCategories(ArrayList<Category> c) {
		this.categories = c;
	}
	private void setCurrently(String c) {
		this.currently = c;
	}
	private void setFirstBid(String f) {
		this.firstBid = f;
	}
	private void setNumBids(int numBids) {
		this.numBids = numBids;
	}
	private void setBids(Bids b) {
		this.bids = b;
	}
	private void setLocation(Location l) {
		this.location = l;
	}
	private void setCountry(String c) {
		this.country = c;
	}
	private void setStarted(String s) {
		this.started =s;
	}
	private void setEnds(String e) {
		this.ends = e;
	}
	private void setSeller(Seller s) {
		this.seller = s;
	}
	private void setDescription(String description) {
		this.description = description;
	}



	//getters
	public String getID() {
		return this.id;
	}

	@XmlElement(name="Name")
	public String getName() {
		return this.name;
	}
	@XmlElement(name="Category")
	public ArrayList<Category> getCategories() {
		return this.categories;
	}
	public String getCurrently() {
		return this.currently;
	}
	public String getFirstBid() {
		return this.firstBid;
	}
	public int getNumBids() {
		return this.numBids;
	}
	public ArrayList<Bid> getBids() {
		return this.bids.getBids();	//note the delegation!
	}
	public Location getLocation() {
		return this.location;
	}
	public String getCountry() {
		return this.country;
	}
	public String getStarted() {
		return this.started;
	}
	public String getEnds() {
		return this.ends;
	}

	//seller stuff
	public String getSellerRating() {
		return this.seller.getRating();
	}
	public String getSellerUserId() {
		return this.seller.getUserId();
	}


	public String getDescription() {
		return this.description;
	}

}