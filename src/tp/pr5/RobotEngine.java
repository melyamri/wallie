package tp.pr5;
import java.util.*;

import javax.swing.JOptionPane;
import tp.pr5.instructions.*;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.InventoryObserver;
import tp.pr5.items.Item;
import tp.pr5.items.ItemContainer;
/**
 * <p>This class represents the robot engine. It controls robot movements by processing the instructions introduced with
 *  the keyboard. The engine stops when the robot arrives at the space ship, runs out of fuel or receives a quit
 *  instruction.</p>
 * <p>The robot engine is also responsible for updating the fuel level and the recycled material according to the 
 * actions that the robot performs in the city.</p>  
 * <p>The robot engine also contains an inventory where the robot stores the items that it collects from the city</p>
 * 
 * @author Ismael Gonjal & Meriem ElYamri
 *
 */
public class RobotEngine extends Observable{
	
	//Attributes
	private ItemContainer inventory;
	private int currentFuel;
	private int currentRecycledMaterial;
	private NavigationModule module;
	private boolean quit;
	private static boolean isConsole;
	
	//Constructor
	/**
	 * <p>Creates the robot engine in an initial place, facing an initial direction and with a city map. Initially the 
	 * robot has not any items or recycled material but it has an initial amount of fuel (50).</p>
	 * 
	 * @param cityMap The city where the robot wanders
	 * @param currentPlace The place where the robot starts
	 * @param direction The initial direction where the robot is facing
	 */
	public RobotEngine(City cityMap, Place currentPlace, Direction direction){
		
		this.inventory = new ItemContainer();
		this.currentFuel = 100;
		this.currentRecycledMaterial = 0;
		this.module = new NavigationModule(cityMap, currentPlace);
		this.quit = false;
		//isConsole = true;
		
		
	}

	/**
	 * Gets the module NavigationModule
	 * @return the navigation module
	 */
	public NavigationModule getModule(){
		 return this.module;
	}
	/**
	 * <p>Adds an amount of fuel to the robot (it can be negative)</p>
	 * 
	 * @param Amount of fuel added to the robot
	 */
	public void addFuel(int fuel){
		this.currentFuel += fuel;
		if(this.currentFuel < 0){
			this.currentFuel = 0;
		}
		List<Object> args = new ArrayList<Object>();
		args.add("fuel");
		args.add(new Integer(this.currentFuel));
		args.add(new Integer(this.currentRecycledMaterial));
		this.informObservers(args);
	}
	/**
	 * <p>Increases the amount of recycled material of the robot. It should be positive but is deliberately uncontrolled.</p>
	 * 
	 * @param weight Amount of recycled material
	 */
	public void addRecycledMaterial(int weight){
		this.currentRecycledMaterial += weight;
		List<Object> args = new ArrayList<Object>();
		args.add("recycledMaterial");
		args.add(new Integer(this.currentFuel));
		args.add(new Integer(this.currentRecycledMaterial));
		this.informObservers(args);	}
	/**
	 * <p>Returns the current fuel of the robot</p>
	 * 
	 * @return Fuel remaining
	 */
	public int getFuel(){
		return this.currentFuel;
	}

	/**
	 * Returns the current quantity of recycled material
	 * 
	 * @return the recycled material
	 */
	public int getRecycledMaterial(){
		return this.currentRecycledMaterial;
	}
	
	/**
	 * Requests the end of the simulation
	 */
	public void requestQuit(){
		this.quit = true;
		
		List<Object> args = new ArrayList<Object>();
		args.add("quit");
		this.informObservers(args);
	}
	/**
	 * Prints the information about all possible instructions
	 */
	public void requestHelp(){
		System.out.println(Interpreter.interpreterHelp()); 
		List<Object> args = new ArrayList<Object>();
		args.add("help");
		this.informObservers(args);
	}
	/**
	 * Gets the item according to the String "id"
	 * @param id the item name
	 * @return the item
	 */
	public Item getItem(String id){
		return this.inventory.getItem(id);
	}
	/**
	 * Gets the robot inventory
	 * @return the robot inventory
	 */
	public ItemContainer getInventory() {
		return inventory;
	}
	/**
	 * <p>It executes an instruction. The instruction must be configured with the context before executing it. It controls the end 
	 * of the simulation. If the execution of the instruction throws an exception, then the corresponding message is printed</p>
	 * 
	 * @param c The instruction to be executed
	 */
	public void communicateRobot(Instruction c){
		try{
			c.configureContext(this, this.module, this.inventory);
			c.execute();
			List<Object> args = new ArrayList<Object>();
			args.add("checkEnd");
			args.add(this.module.getCurrentPlace());
			this.informObservers(args);
		}catch(InstructionExecutionException ex){
			this.raiseError(ex.getMsg());
			/*if(isConsole)
				System.out.println(ex.getMsg());
			else
				JOptionPane.showMessageDialog(null, ex.getMsg());
				*/
		}
	}
	/**
	 * Raises an error with a message
	 * @param msg the message
	 */
	private void raiseError(String msg){
		ArrayList<Object> ls = new ArrayList<Object>();
		ls.add("raiseError");
		ls.add(msg);
		this.informObservers(ls);
	}
	/**
	 * makes the robot tell something
	 * @param msg the message that the robot has to tell.
	 */
	private void robotSays(String msg){
		ArrayList<Object> ls = new ArrayList<Object>();
		ls.add("talk");
		ls.add(msg);
		this.informObservers(ls);
	}
	/**
	 * Returns if the last order was quit. this is used in order to end the execution, it
	 * depends on if a instruction which class was QuitInstructionwas executed. 
	 * @return true or false depending of the last Instruction stored into the class 
	 */

	public boolean getQuit() {
		return this.quit;
	}

	/**
	 * this method informs the observers of the changes realized in order to
	 *  updating them
	 */
	private void informObservers(List<Object> args){
        setChanged(); // Establish that a change happened 
        notifyObservers(args); // Notify the observers
        // It could be used by sending an object with the necessary information 
    }
	/**
	 * sets if the RobotEngine is running in console mode
	 * @param isConsole true if the console mode is going to run, false otherwise
	 */
	public static void setConsole(boolean isConsole) {
		RobotEngine.isConsole = isConsole;
	}
	/**
	 * Adds a robotEngineObserver
	 * @param observer
	 */
	public void addEngineObserver(RobotEngineObserver observer){
		this.addObserver((Observer) observer);
	}
	/**
	 * Adds an Item Container observer
	 * @param observer the observer
	 */
	public void addItemContainerObserver(InventoryObserver observer){
		this.getInventory().addObserver((Observer)observer);
	}
	/**
	 * Adds a NavigationModule observer
	 * @param robotObserver
	 */
	public void addNavigationObserver(NavigationObserver robotObserver){
		this.getModule().addObserver((Observer) robotObserver);
	}
	/**
	 * Tells if the execution is over
	 * @return a boolean value
	 */
	public boolean isOver(){
		return this.quit;
	}
	/**
	 * This methor initialices the message of the place, it only calls the requestStart method in the
	 *  "NavigationModule" class
	 */
	public void requestStart() {
		this.module.requestStart();
		
	}
	
}