package tp.pr5.items;

import tp.pr5.NavigationModule;
import tp.pr5.Place;
import tp.pr5.RobotEngine;
/**
 * <p>The superclass of every type of item. It contains the common information 
 * for all the items and it defines the interface that the items must match</p>
 * 
 * @author Ismael Gonjal & Meriem ElYamri
 * 
 */
public abstract class Item implements Comparable<Item>{
	//Attributes
	protected String id;
	protected String description;
		
	//Constructor
	/**
	 * <p>Builds an item from a given identifier and description</p>
	 * 
	 * @param id Item Identifier
	 * @param name Item description
	 */
	public Item(String id, String name){
			this.id = id;
			this.description = name;
	}
	
	// Abstract Methods
	/**
	 * <p>Checks if the item can be used. Subclasses must override this method</p>
	 * 
	 * @return true if the item can be used
	 */
	public abstract boolean canBeUsed();
	/**
	 * <p>Try to use the item with a robot in a given place. It returns whether the 
	 * action was completed. Subclasses must override this method</p>
	 * 
	 * @param robot The robot that uses the item
	 * @param nav The Place where the item is used
	 * @return true if the action was completed. Otherwise, it returns false
	 */
	public abstract boolean use(RobotEngine robot, NavigationModule nav);
	
	// Defined methods
	/**
	 * <p>Return the item identifier</p>
	 * 
	 * @return The item identifier
	 */
	public String getId(){
		return this.id;
	}
	/**
	 * <p>Generates a string with the item description</p>
	 * 
	 * @return the item description
	 */
	public String toString(){
		return this.id + ": " + this.description;
	}
	/**
	 * Gets the description
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * Compares two items
	 * @param it the item to compare
	 * @return true if they are equal, false otherwise
	 */
	public boolean equals(Item it){
		return (this.id.equalsIgnoreCase(it.id));
	}
	/** Compares two items and returns an integer value with it's
	 *  diference lexicography.
	 * @return the diference.
	 */
	public int compareTo(Item it){
		return this.id.compareToIgnoreCase(it.id);
	}


}
