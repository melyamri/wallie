package tp.pr5.items;

import tp.pr5.NavigationModule;
import tp.pr5.Place;
import tp.pr5.RobotEngine;
/**
 * <p>A CodeCard can open or close the door placed in the streets. The card contains a code 
 * that must match the street code in order to perform the action.</p>
 * 
 * @author Ismael Gonjal & Meriem ElYamri
 * @extends Item
 * @see Street
 */
public class CodeCard extends Item {
	//Attributes
	private String code;
	
	// Constructor
	/**
	 * Code card Constructor
	 * 
	 * @param id CodeCard name
	 * @param description Description of the CodeCard
	 * @param code Secret code stored in the CodeCard 
	 */
	public CodeCard(String id, String description, String code){
		super(id, description);
		this.code = code;
	}
	
	// Methods
	/**
	 * <p>A code card always can be used. Since the robot has the code card it never loses it</p>
	 * 
	 * @inherits Item
	 * @return true because it always can be used
	 */
	public boolean canBeUsed(){
		return true;
	}
	
	/**
	 * <p>Gets the code stored in the CodeCard</p>
	 * 
	 * @return a String representing the secret code
	 */
	public String getCode() {
		return this.code;
	}
	
	/**
	 * <p>The method to use a code card. If the robot is in a place which contains a street in the 
	 * direction he is looking at, then the street is opened or closed if the street code and the 
	 * card code match.</p>
	 * 
	 * @param r the robotEngine employed to use the card
	 * @param nav the navigationModule to look for the street
	 * @return true If the code card can complete the action of opening or closing a street. Otherwise it returns false.
	 */
	public boolean use(RobotEngine r, NavigationModule nav){
		boolean moving = false;
		// If there is a street in that direction
		if (nav.getHeadingStreet() != null){
			// if the street is not open (Will try to open it)
			if(!nav.getHeadingStreet().isOpen()){  
				// If the street is opening
				if(nav.getHeadingStreet().open(this)){
					//System.out.println("Something changes...");
					moving = true;
				}
			// If the street is open (Will try to close it)
			}else if(nav.getHeadingStreet().isOpen()){
				// If the street is closing
				if(nav.getHeadingStreet().close(this)){
					//System.out.println("Something changes...");
					moving = true;
				}
			}
		}
		if (!moving){
			System.out.println("WALL·E says: I have problems using the object " + this.id.toLowerCase());
		}
		return moving;
	}
 
	
}
