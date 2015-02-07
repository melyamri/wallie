package tp.pr5;

import tp.pr5.items.Item;
import tp.pr5.items.ItemContainer;
/**
 * <p>It represents a place in the city. Places are connected by streets according to the 4 compass directions, 
 * North, East, South and West. Every place has a name and a textual description about itself. This description 
 * is displayed when the robot arrives at the place.</p>
 * 
 * <p>A place can represent the spaceship where the robot is safe. When the robot arrives at this place, the
 *  application is over.</p>
 * 
 * @author Ismael Gonjal & Meriem ElYamri
 * @see Street
 * @See ItemContainer
 * 
 *
 */
public class Place implements PlaceInfo{
	//Attributes
	private String name;
	private boolean isSpaceShip;
	private String description;
	private ItemContainer container;
	
	//Constructor
	/**
	 * <p>Creates the place</p>
	 * 
	 * @param name the name of the place
	 * @param isSpaceShip Is it a spaceship?
	 * @param description Place description
	 */
	public Place(String name, boolean isSpaceShip, String description){
		this.name = name;
		this.isSpaceShip = isSpaceShip;
		this.description = description;
		this.container = new ItemContainer();
	}
	/**
	 * Constructor that creates a new place based on a given one
	 * @param place the given place
	 */
	public Place(Place place){
		this.name = place.name;
		this.isSpaceShip = place.isSpaceShip;
		this.description = place.description;
		this.container = new ItemContainer();
		//this.setContainer(place.getContainer());
	}
	//Methods
	/**
	 * gets the name of the place
	 * @return the place name
	 */
	public String getName(){
		return this.name;
	}
	/**
	 * <p>Tries to add an item to the place. The operation can fail (if the place already contains 
	 * an item with the same name)</p>
	 * 
	 * @param it the item to be added
	 * @return true if and only if the item can be added to the place, i.e., the place does not contain an item with the same name
	 */
	public boolean addItem(Item it){
		return container.addItem(it);
	}
	/**
	 * <p>is this place the space ship?</p>
	 * 
	 * @return true if the place represents a space ship
	 */
	public boolean isSpaceship(){
		return this.isSpaceShip;
	}
	/**
	 * <p>Tries to pick an item characterized by a given identifier from the place.
	 *  If the action was completed the item must be removed from the place.</p>
	 * 
	 * @param id the identifier of the item
	 * @return the item whose identifier is id if exists in the place, otherwise the method returns null
	 */
	public Item pickItem(String id){
		return this.container.pickItem(id); 
	}
	
	/**
	 * <p>Checks if an item is in this place</p>
	 * 
	 * @param id Identifier of an item
	 * @return true if and only if the place contains the item identified by id
	 */
	public boolean existItem(String id){
		return (this.container.getItem(id) != null);
	}
	/**
	 * <p>Overrides toString method. Returns the place name, its description and the list of items contained in the place</p>
	 * 
	 * @overrides toString in class java.lang.Object
	 * @see Object.toString()
	 */
	public String toString(){
		String description;
		if(this.container.numberOfItems() != 0){
		description = this.name + '\n' +this.description +'\n' + "The place contains these objects:" +  '\n'  +
							container.toString();
		}else{
			description = this.name + '\n' +this.description +'\n' + "The place is empty. There are no objects to pick" ;
		}
		return description;
	} 
	
	/**
	 * <p>Drop an item in this place. The operation can fail, returning false</p>
	 * 
	 * @param it the Item dropped
	 * @return true if and only if the item is dropped in the place, an item with the same identifier can not be dropped in the place
	 */
	public boolean dropItem(Item it){
		return this.addItem(it);
	}
	/**
	 * It will return the list of items which are in the place
	 * @return a list of items
	 */
	public ItemContainer getContainer() {
		return container;
	}
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return this.description;
	}
	
}
