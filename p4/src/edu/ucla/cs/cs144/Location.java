package edu.ucla.cs.cs144;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.XmlAccessorType;
/**
 * Represents Location element in our XML data
**/
@XmlRootElement(name="Location")
public class Location {
	private String location;
	private String latitude;
	private String longitude;

	//setters
	public void setLocation(String l) {
		this.location = l;
	}
	public void setLatitude(String lat) {
		this.latitude = lat;
	}
	public void setLongitude(String lon) {
		this.longitude = lon;
	} 

	//getters
	@XmlValue
	public String getLocation() {
		return this.location;
	}
	@XmlAttribute(name="Latitude")
	public String getLatitude() {
		return this.latitude;
	}
	@XmlAttribute(name="Longitude") 
	public String getLongitude() {
		return this.longitude;
	}
}