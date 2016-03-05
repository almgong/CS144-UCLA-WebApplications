package edu.ucla.cs.cs144;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Represents the category element in our XML data
**/
@XmlRootElement(name="Category")
public class Category {
	private String cat;

	@XmlValue
	public String getCategory() {
		return this.cat;
	}

	public void setCategory(String c) {
		this.cat = c;
	}
}