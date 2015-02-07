package tp.pr5;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.JOptionPane;

import tp.pr5.gui.NavigationPanel;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.items.*;
/**
 * <p>This class is in charge of the robot navigation features. It contains the city where the robot looks 
 * for garbage, the current place where the robot is, and the current direction of the robot. It contains 
 * methods to handle the different robot movements and to pick and drop items at the current place.</p>
 * 
 * @author Ismael Gonjal & Meriem ElYamri
 * @see Place
 * @See Direction
 * @See City
 *
 */
public class NavigationModule extends Observable {
	// Attributes
	private Place currentPlace;
	private Direction direction;
	private City cityMap;
	private boolean hasMoved;
	//Constructor
	/**
	 * Navigation module constructor. It needs the city map and the initial place
	 * 
	 * @param aCity A city map
	 * @param initialPlace An initial place for the robot
	 */
	public NavigationModule(City aCity, Place initialPlace){
		this.cityMap = aCity;
		this.currentPlace = initialPlace;
		this.direction = Direction.NORTH;
		this.hasMoved = false;
		
		List<Object> args = new ArrayList<Object>();
		args.add("init");
		args.add(this.direction);
		args.add(this.currentPlace);
		this.informObservers(args);
	}
	//Methods
	/**
	 * Sets the current dierection where the NavigationModule is Pointing at.
	 * @param direction a valid or UNKNOWN direction
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	/**
	 * <p>Checks if the robot has arrived at a spaceship</p>
	 *  
	 * @return true if the current place is the spaceship
	 */
	public boolean atSpaceship(){
		return this.currentPlace.isSpaceship();
	}
	
	/**
	 * <p>Updates the current direction of the robot according to the rotation</p>
	 * 
	 * @param rotation left or right
	 */
	public void rotate(Rotation rotation){
		this.hasMoved = false;
		this.direction = Direction.rotate(rotation, this.direction);
		List<Object> args = new ArrayList<Object>();
		args.add("rotate");
		args.add(this.direction);
		this.informObservers(args);
	}
	/**
	 * Gets the direction where the robot is pointing at
	 * @return a valid direction.
	 */
	public Direction getDirection(){
		return this.direction;
	}
	/**
	 *<p>The method tries to move the robot following the current direction. If the movement is not 
	 *possible because there is no street, or there is a street which is closed, then it throws an 
	 *exception. Otherwise the current place is updated according to the movement</p>
	 * 
	 * @throws InstructionExecutionException
	 */
	public void move() throws InstructionExecutionException{
		this.hasMoved = false;
		Street street;
		street = this.getHeadingStreet();
		//if street would be null, there wouldn't be a street in the direction that wall·e is currently looking at
		if (street != null  && street.isOpen()){
			this.currentPlace = street.nextPlace(this.currentPlace);
			this.hasMoved = true;
			
			this.robotSays("WALL·E says: Moving in direction " + this.direction +'\n');
			
			List<Object> args = new ArrayList<Object>();
			args.add("move");
			args.add(this.direction);
			args.add(this.currentPlace);
			this.informObservers(args);
			
			
		}else if(street != null && street.comeOutFrom(this.currentPlace, this.direction) && !street.isOpen()){
			throw new InstructionExecutionException("WALL·E says: Arrggg, there is a street but it is closed!");
		}else{
			throw new InstructionExecutionException("I can't move in that direction");
		}
	}
	/**
	 * Updates the view of the fuel and garbage
	 */
	public void updateFuelGarbage(){
		List<Object> args = new ArrayList<Object>();
		args.add("updateFuelGarbage");
		this.informObservers(args);
	}
	
	/**
	 * This method creates the ArrayList that will be sent to the informObservers 
	 * method
	 * @param msg a message with the words that wall-e will say
	 */
	public void robotSays(String msg){
		ArrayList<Object> ls = new ArrayList<Object>();
		ls.add("talk");
		ls.add(msg);
		this.informObservers(ls);
	}
	/**
	 * <p>Drop an item in the current place. It assumes that there is no other item with the same name/id there. 
	 * Otherwise, the behavior is undefined.<p>
	 * 
	 * @param item The item to be dropped.
	 */
	public void dropItemAtCurrentPlace(Item item){
		this.currentPlace.addItem(item);
		List<Object> args = new ArrayList<Object>();
		args.add("dropItem");
		args.add(this.currentPlace);
		this.informObservers(args);
	}
	
	/**
	 * <p>Checks if there is an item with a given id in this place</p>
	 * 
	 * @param id The identifier of the item we are looking for
	 * @return if and only if an item with this id is in the current place
	 */
	public boolean findItemAtCurrentPlace(String id){
		this.hasMoved = false;
		return this.currentPlace.existItem(id);
		
	}
	
	/**
	 * <p>Returns the current place where the robot is (for testing purposes)</p>
	 * 
	 * @return The current place
	 */
	public Place getCurrentPlace(){
		return this.currentPlace;
	}
	
	/**
	 * <p>Initializes the current heading according to the parameter</p>
	 * 
	 * @param heading New direction for the robot
	 */
	public void initHeading(Direction heading){
		this.direction = heading;
	}
	
	/**
	 * <p>Tries to pick an item characterized by a given identifier from the current place. If the action
	 *  was completed the item is removed from the current place.</p>
	 * 
	 * @param id The identifier of the item
	 * @return The item of identifier id if it exists in the place. Otherwise the method returns null
	 */
	public Item pickItemFromCurrentPlace(String id){
		
		Item aux = this.currentPlace.pickItem(id);
		
		List<Object> args = new ArrayList<Object>();
		args.add("pickFromPlace");
		args.add(this.currentPlace);
		this.informObservers(args);
		
		return aux;
		
	}

	/**
	 * <p>Prints the information (description + inventory) of the current place</p>
	 */
	public void scanCurrentPlace(){
		this.robotSays(this.currentPlace.toString());
	}
	
	/**
	 * <p>Returns the robot heading</p>
	 * @return The direction where the robot is facing to
	 */
	public Direction getCurrentHeading(){
		return this.direction;
	}
	
	/**
	 * <p>Returns the street opposite the robot</p>
	 * 
	 * @return The street which the robot is facing, or null, if there is not any street in this direction
	 */
	public Street getHeadingStreet(){
		return cityMap.lookForStreet(this.currentPlace, this.direction);
	}
	/**
	 * Gets the complete CityMap which is currently using the NavigationModule
	 * @return A CityMap if it has one, null if it hasn't.
	 */
	public City getCityMap() {
		return cityMap;
	}
	/**
	 * Returns the NavigationPanel of the NavigationModule
	 * @return
	 */

	/**
	 * Notifys the classes which would be Observing that this class has
	 * changed and they must Update their content.
	 */
	private void informObservers(List<Object> args){
        setChanged(); // establece que ha habido un cambio.
        notifyObservers(args); // notifica a los observadores.	
       
    }
	/**
	 * Returns if the robot has moved
	 * @return true if the last modification to the NavigationModule has been a move order
	 */
	public boolean getHasMoved() {
		return this.hasMoved;
	}
	/**
	 * Inits the text into the views telling where the robot is
	 */
	public void requestStart() {
		ArrayList<Object> args = new ArrayList<Object>();
		args.add("updateLog");
		args.add(this.direction);
		args.add(this.currentPlace);
		this.informObservers(args);
		
		
	}

	
	
	
	
	
	

}
