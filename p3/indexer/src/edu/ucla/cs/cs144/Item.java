package edu.ucla.cs.cs144;

/**
 * Class that represents an Item as described in CS144, project 2.
**/
public class Item {

	private int id;
	private String name;
	private String categories;			//space separated string
	private String description;			//description


	//cstrs
	public Item(int i, String n, String c, String d) {
		this.name = n;
		this.id=i;
		this.categories=c;
		this.description = d;
	}

	//getters
	public String getName() {
		return this.name;
	}	
	public int getID() {
		return this.id;
	}
	public String getCategories() {
		return this.categories;
	}
	public String getDescription() {
		return this.description;
	}

	//setters
	public void setCategories(String newCat) {
		this.categories = newCat;
	}

}