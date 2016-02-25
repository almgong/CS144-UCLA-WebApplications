package edu.ucla.cs.cs144;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Represents the Bidder element in our XML data
**/
@XmlRootElement(name="Bidder")
public class Bidder {
	private String bidderRating;
	private String bidderId;
	private String location;
	private String country;

	//setters
	public void setBidderRating(String r) {
		this.bidderRating = r;
	}
	public void setBidderId(String i) {
		this.bidderId = i;
	}
	public void setLocation(String l) {
		this.location = l;
	}
	public void setCountry(String c) {
		this.country = c;
	}

	//getters
	@XmlAttribute(name="Rating")
	public String getBidderRating() {
		return this.bidderRating;
	}

	@XmlAttribute(name="UserID")
	public String getBidderId() {
		return this.bidderId;
	}

	@XmlElement(name="Location")
	public String getLocation() {
		return this.location;
	}

	@XmlElement(name="Country")
	public String getCountry() {
		return this.country;
	}
}