package tp.pr5;

import tp.pr5.items.CodeCard;
/**
 * <p>A street links two places A and B in one direction. If it is defined as Street(A,NORTH,B) it
 *  means that Place B is at NORTH of Place A. Streets are two-way streets, i.e. if B is at NORTH 
 *  of A then A is at SOUTH of B.</p>
 *  <p>Some streets are closed and a code (contained in a code card) is needed to open them</p>
 * 
 * @author Ismael Gonjal & Meriem ElYamri
 * @see Place
 * @see Direction
 * @see CodeCard
 */
public class Street implements Comparable<Street>{
	//Attributes
	private Place source;
	private Place target;
	private Direction direction;
	private boolean isOpen;
	private String code;
	
	//Constructors
	/**
	 * <p>Creates an open street and it have not any code to open or close it</p>
	 * 
	 * @param source Source place
	 * @param direction Represents How is placed the target place with respect to the source place.
	 * @param target Target place
	 */
	public Street(Place source, Direction direction, Place target){
		this.source = source;
		this.target = target;
		this.direction = direction;
		this.isOpen = true;
	}
	/**
	 * 
	 * <p>Creates a street that has a code to open and close it</p>
	 * 
	 * @param source Source place
	 * @param direction Represents how is placed the target place with respect to the source place.
	 * @param target Target place
	 * @param isOpen Determines if the street is open or is closed
	 * @param code The code that opens and closes the street
	 */
	public Street(Place source, Direction direction, Place target, boolean isOpen, String code){
		this.source = source;
		this.target = target;
		this.direction = direction;
		this.isOpen = isOpen;
		this.code = code;
	}
	
	//Methods
	/**
	 * <p>Tries to close a street using a code card. Codes must match in order to complete this action</p>
	 * 
	 * @param card A CodeCard to close the street
	 * @return true if the action has been completed or the street was already closed
	 */
	public boolean close(CodeCard card){
		if(card.getCode().equals(this.code)){
			this.isOpen = false;
		}
		return !this.isOpen;
	}
	/**
	 * <p>Checks if the street comes out from a place in a given direction. Remember that streets are two-way</p>
	 * <p>In other words, Tells if the place and direction provided belongs to this street</p>
	 * 
	 * @param place The place to check
	 * @param whichDirection The direction where we wish look at
	 * @return True if the street comes out from the input place.
	 */
	public boolean comeOutFrom(Place place, Direction whichDirection){
		
		 // true if the place provided is coincides with the target and the direction is the opposite of provided 
		 // this means place is target pointing to source 
		return (place.equals(this.target) && this.direction.equals(Direction.opposite(whichDirection))) 
				 //true if the place provided coincides with the source and the direction is the same
				 // this means place is source pointing to target
				|| (place.equals(this.source) && whichDirection.equals(this.direction));
	}
	/**
	 * <p>Checks if the street is open or closed</p>
	 * 
	 * @return true when the street is open and false when is closed 
	 */
	public boolean isOpen(){
		return this.isOpen;
	}
	
	/**
	 * <p>Tries to open a street using a code card. Codes must match in order to complete this action</p>
	 * 
	 * @param card A code card to open the street
	 * @return true if the action has been completed o the street was already open
	 */
	public boolean open(CodeCard card){
		if(card.getCode().equals(this.code)){
			this.isOpen = true;
		}
		return this.isOpen;
	}
	/**
	 * Gets the street source
	 * @return the source place
	 */
	public Place getSource() {
		return source;
	}
	/**
	 * sets a source place
	 * @param source the place to set
	 */
	public void setSource(Place source) {
		this.source = source;
	}
	/**
	 * Gets the target
	 * @return the target
	 */
	public Place getTarget() {
		return target;
	}
	/**
	 * Sets the target
	 * @param target
	 */
	public void setTarget(Place target) {
		this.target = target;
	}
	/**
	 * <p>Returns the place of the other side from the place whereAmI. This method does not consider whether the street is 
	 * open or closed.</p>
	 * 
	 * @param whereAmI the place where i am
	 * @return It returns the Place at the other side of the street (even if the street is closed). Returns null if whereAmI does not belong to the street.
	 */
	public Place nextPlace(Place whereAmI){
		Place whereIGo = null;
		//I am in target
		if(whereAmI.equals(this.target)){
			whereIGo = this.source;
		//I am in source
		}else if(whereAmI.equals(this.source)){
			whereIGo = this.target;
		}
		return whereIGo;
	}

	/**
	 * Returns true if the street introduced is the same (Source, direction and target)
	 * 
	 * @param st the street to compare
	 * @return true if it is equal at this street, normal or reversing it. Falce otherwise
	 */
	public boolean equals(Street st){
		return  (( this.direction.equals(st.direction) &&
				   this.source.equals(st.source) && 
				   this.target.equals(st.target) )
				   );/*||
				 ( this.direction.equals(Direction.opposite(st.direction)) &&
				   this.source.equals(st.target) &&
				   this.target.equals(st.source) )
				 );*/
	}
	/**
	 * Compares the sources, and returns the differences. If is the same, returns the target differences. 
	 * If source and target are the same, the difference will be 0.
	 * 
	 * @param st the street to compare.
	 * @return a negative number if st is higher, 0 if it's equal a positive number otherwise
	 */
	
	@Override
	public int compareTo(Street o) {
		int difference;

		if(this.source.getName().compareTo(o.source.getName()) == 0){
			difference = this.target.getName().compareTo(this.target.getName());
		}else{
			difference = this.source.getName().compareTo(o.source.getName());
		}
		return difference;
	}

}
