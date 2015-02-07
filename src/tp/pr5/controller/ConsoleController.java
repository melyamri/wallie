package tp.pr5.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Scanner;

import tp.pr5.Direction;
import tp.pr5.Interpreter;
import tp.pr5.NavigationObserver;
import tp.pr5.Place;
import tp.pr5.PlaceInfo;
import tp.pr5.RobotEngine;
import tp.pr5.RobotEngineObserver;
import tp.pr5.gui.MainWindow;
import tp.pr5.instructions.Instruction;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.InventoryObserver;
import tp.pr5.items.Item;
/**
 * This class controlles the execution of the robot in a console way, in order to avoid
 * the creation of another class, the console is used here too
 * 
 * @author Ismael Gonjal & Meriem El Yamri
 *
 */
public class ConsoleController  implements RobotEngineObserver, NavigationObserver, InventoryObserver {

	private RobotEngine engine;
	private Scanner read;
	
	/**
	 * This constructor saves the RobotEngine, if it needs
	 * to be launched, the method start must be called
	 * @param engine a valid RobotEngine
	 * @see this.start()
	 */
	public ConsoleController(RobotEngine engine){
		this.engine = engine;
		this.addObservers();
	}

	/**
	 * Sets this class as engine observer
	 */
	public void addObservers(){
		engine.addEngineObserver(this);
		engine.addItemContainerObserver(this);
		engine.addNavigationObserver(this);
	}
	
	/**
	 * This method is in charge of the complete bucle of the console engine. It is in charge of starting the engine, parsing the command
	 * written by the user and calling the corresponding method in the RobotEngine to execute it.
	 */
	public void startEngine(){
		
		//Interpreter interpreter = new Interpreter();
		Place placeBeforeInstruction = engine.getModule().getCurrentPlace();
		String line;
		
		Instruction instruction = null;// new Instruction();		
		read = new Scanner(System.in); 

		//The prompt and the initial info is showed
		//this.engine.saySomething(engine.getModule().getCurrentPlace().toString());
		//this.robotSays(engine.getModule().getCurrentPlace().toString() + '\n');
		//this.robotSays("WALL·E is looking at direction " + engine.getModule().getCurrentHeading().toString() + '\n');
		//this.engine.saySomething("WALL·E is looking at direction " + engine.getModule().getCurrentHeading().toString());
		//showFuelGarbage();
		//this.robotSays("WALL·E> ");
		//While not in spaceship, there is fuel remaining and not requested the end, the simulation continues
		while (!engine.getModule().getCurrentPlace().isSpaceship() && engine.getFuel() > 0 && !engine.getQuit()){
				
			line = read.nextLine();
			//Remove the character "\n"
			line = line.substring(0, line.length());
			
			try{
				//Generates the instruction (Can throw WrongInstructionFormatException)
				instruction = Interpreter.generateInstruction(line);	
				//If the instruction needs to keep a word (Like an id) it does
				instruction.parse(line);
				//Executes the instruction
				engine.communicateRobot(instruction);
				
				if (engine.getModule().getCurrentPlace() != placeBeforeInstruction){
					//showMessage();
					placeBeforeInstruction = engine.getModule().getCurrentPlace();
				}
				if(!placeBeforeInstruction.isSpaceship()){
					this.robotSays("WALL·E> ");
				}
			}catch(WrongInstructionFormatException ex){
				this.raiseError(ex.getMsg());
			}	
		}
		
		//If at the end of the simulation, the place is spaceship
		if (engine.getModule().getCurrentPlace().isSpaceship()){
			this.robotSays("WALL·E says: I am at my spaceship. Bye bye" + '\n');
			System.exit(0);
		}else if(engine.getFuel() <=0){
			//If the fuel was reduced to 0 or lower
			this.robotSays("WALL·E says: I run out of fuel. I cannot move. Shutting down..." + '\n');
			System.exit(0);
		}else{
			//If the Quit Instruction was used
			this.robotSays("WALL·E says: I have communication problems. Bye bye" + '\n');
			System.exit(0);
		}
		
	}
	/**
	 * Prints a generic message used often into the aplication
	 */
	private void showMessage(){
    	//Place description
		this.robotSays(this.engine.getModule().getCurrentPlace().toString() + '\n');
		//Current fuel and garbage
		//showFuelGarbage();
	}
	/**
	 * Prints the fuel and Garbage 
	 */
	private void showFuelGarbage(){		
		this.robotSays("      * My power is " + this.engine.getFuel() + '\n'+"      * My reclycled material is " + this.engine.getRecycledMaterial() + '\n');
	}
	
	/**
	 * this method updates the state of the robot receiving an arrayList which first element
	 * must be an String with the words 
	 * "quit" without other params in the ArrayList 
	 * "checkEnd" without other params in the ArrayList
	 * "raiseError"  with another param being an string
	 * "talk" with another param being an String
	 */
	@Override
	public void update(Observable o, Object arg) {
		ArrayList<Object> array = (ArrayList<Object>)arg;
		
		switch((String) array.get(0)){
		case "quit": this.communicationCompleted(); break;
		case "checkEnd": this.engineOff(((PlaceInfo) array.get(1)).isSpaceship()); break;
		case "raiseError": this.raiseError((String) array.get(1)); break;
		case "talk": this.robotSays((String) array.get(1)); break;
		case "rotate": this.headingChanged((Direction)array.get(1)); break; //this.showFuelGarbage(); break;
		case "move":  this.robotArrivesAtPlace((Direction) array.get(1),(PlaceInfo) array.get(2)); 
						//this.showFuelGarbage();
						break;
		case "updateLog":  //this.headingChanged((Direction)array.get(1));
						this.initNavigationModule((Direction) array.get(1),(PlaceInfo)array.get(2)); 
						this.showFuelGarbage(); 
						this.robotSays("WALL·E> ");
						break;
					 // this.updateLog((PlaceInfo) array.get(2)); break;
		case "init": 	break; //this.initCityMapButtons((PlaceInfo) array.get(2)); break;
		case "dropItem": this.placeHasChanged((PlaceInfo) array.get(1)); break;
		case "pickFromPlace": this.placeHasChanged((PlaceInfo) array.get(1)); break;
		case "fuel": this.showFuelGarbage(); 
			break;
		}
		
	}
	private void updateLog(PlaceInfo placeInfo) {
		
	}
	/**
	 * Sets the new heading of the navigationModule
	 * @param newHeading the new heading
	 */
	@Override
	public void headingChanged(Direction newHeading) {
		this.robotSays("WALL·E is looking at direction "+ newHeading + '\n' );
		//this.showFuelGarbage();
	}
	/**
	 * Sets the initial place of the observer
	 * @param initialPlace
	 */
	@Override
	public void initNavigationModule(Direction heading, PlaceInfo initialPlace) {
		this.robotSays(initialPlace.toString() + '\n' + "WALL·E is looking at direction " + heading.toString() + '\n');
		//this.showFuelGarbage();
		
	}
	/**
	 * Changes the place to a new one, only observed and not mutable
	 * @param placeDescription t
	 */
	@Override
	public void placeHasChanged(PlaceInfo placeDescription) {
		//this.robotSays(placeDescription.getDescription());
		
	}
	/**
	 * Gets a description to this method to scan the place
	 * @param placeDescription
	 */
	@Override
	public void placeScanned(PlaceInfo placeDescription) {	
		
	}
	/**
	 * When the place changes, displays a message telling that robot has updated it's place
	 * @param heading
	 * @param place
	 */
	@Override
	public void robotArrivesAtPlace(Direction heading, PlaceInfo place) {
		this.robotSays(place.toString());//+ '\n' + "WALL·E is looking at direction " + heading.toString() + '\n');
		//this.showFuelGarbage();
		//this.robotSays("WALL·E> \n");
	}
	/**
	 * Informs that the comunication has been completed
	 */
	@Override
	public void communicationCompleted() {
		this.robotSays("WALL·E says: I have communication problems. Bye bye"+ '\n');
	}
	/**
	 * shows the help message
	 * @param help
	 */
	@Override
	public void communicationHelp(String help) {
		
	}
	/**
	 * Sets off the engine
	 * @param atShip 
	 */
	@Override
	public void engineOff(boolean atShip) {
		if (atShip) {
			this.robotSays("WALL·E says: I am at my space ship. Bye Bye" + '\n');
			System.exit(0);
		}
	}
	/**
	 * Raises an error
	 */
	@Override
	public void raiseError(String msg) {
		this.robotSays(msg);
	}
	/**
	 * Makes the robot telling something
	 */
	@Override
	public void robotSays(String message) {
		System.out.print(message);
		
	}
	/**
	 * Updates the robot state
	 */
	@Override
	public void robotUpdate(int fuel, int recycledMaterial) {
		this.showFuelGarbage();
		
	}
	/**
	 * Notifyies that the inventory has changed
	 * @param inventory
	 */
	@Override
	public void inventoryChange(List<Item> inventory) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Receives an order of scan and executes it
	 * @param inventoryDescription
	 */
	@Override
	public void inventoryScanned(String inventoryDescription) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * tells if the inventory is empty
	 * @param itemName
	 */
	@Override
	public void itemEmpty(String itemName) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Gets the scan of an item
	 * @param description
	 */
	@Override
	public void itemScanned(String description) {
		// TODO Auto-generated method stub
		
	}
}
