package edu.ucla.cs.cs144;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Class for Seller element in XML
 **/
@XmlRootElement(name="Item")
public class Seller {
	private String rating;
	private String userId;

	//getters and setters
	public void setRating(String r) {
		this.rating = r;
	}
	@XmlAttribute(name="Rating")
	public String getRating() {
		return this.rating;
	}
	public void setUserId(String u) {
		this.userId = u;
	}
	@XmlAttribute(name="UserID")
	public String getUserId() {
		return this.userId;
	}

}