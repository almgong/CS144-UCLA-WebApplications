package edu.ucla.cs.cs144;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;
/**
 * Represents the Bid element in our XML data
 **/
@XmlRootElement(name="Bid")
public class Bid {
	private Bidder bidder;
	private String time;
	private String amount;

	//setters
	public void setBidder(Bidder b) {
		this.bidder = b;
	}
	public void setTime(String t) {
		this.time = t;
	}
	public void setAmount(String a) {
		this.amount = a;
	}

	//getters
	@XmlElement(name="Bidder")
	public Bidder getBidder() {
		return this.bidder;
	}

	@XmlElement(name="Time")
	public String getTime() {
		return this.time;
	}

	@XmlElement(name="Amount")
	public String getAmount() {
		return this.amount;
	}
}