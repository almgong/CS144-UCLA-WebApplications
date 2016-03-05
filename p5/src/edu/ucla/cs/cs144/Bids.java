package edu.ucla.cs.cs144;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.XmlAccessorType;

import java.util.ArrayList;
/**
 * Confusing class name, but represents the root Bids element
**/

@XmlRootElement(name="Bids") 
public class Bids {
	private ArrayList<Bid> bids;

	//setters
	public void setBids(ArrayList<Bid> b) {
		this.bids = b;
	}

	//getters
	@XmlElement(name="Bid")
	public ArrayList<Bid> getBids() {
		return this.bids;
	}
}