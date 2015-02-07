package tp.pr5.instructions;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.exceptions.*;
import tp.pr5.items.Item;
import tp.pr5.items.ItemContainer;
/** 
* <p>This instruction tries to pick an Item from the current place and puts it the robot inventory. 
* This instruction works if the user writes PICK id or COGER id</p>
* 
* @author Ismael Gonjal & Meriem El Yamri
*
* @see NavigationModule
* @see ItemContainer
* @see RobotEngine
*/
public class PickInstruction implements Instruction {
	//Local variables
	private NavigationModule navigation;
	private RobotEngine engine;
	private ItemContainer robotContainer;
	private String id;
	
	//Constructors
	/**
	 * the default constructor
	 */
	public PickInstruction(){
		this.id = "";
		this.robotContainer = null;
		this.navigation = null;
		this.engine = null;
	}
	/**
	 * this constructor allows to include an id for the item that will be
	 * picked
	 * @param id an string for the item.
	 */
	public PickInstruction(String id){
		this.robotContainer = null;
		this.navigation = null;
		this.engine = null;
		this.id = id;
	}
	
	//Methods
	/**
	 * Gets the item id
	 * @return the item id
	 */
	public String getId(){
		return this.id;
	}
	/**
	 * <p>Configuration of the context for this instruction</p>
	 * 
	 * @param engine - The robot engine
	 * @param navigation - The information about the game, i.e., the places, current direction and current heading to navigate
	 * @param robotContainer - The inventory of the robot
	 * 
	 */
	public void configureContext(tp.pr5.RobotEngine engine,
										  tp.pr5.NavigationModule navigation,
										  tp.pr5.items.ItemContainer robotContainer){
		this.navigation = navigation;
		this.engine = engine;
		this.robotContainer = robotContainer;
	}
	
	/**
	 * <p>The robot adds an item to its inventory from the current place, if it exists.<p>
	 * 
	 * @throws instructionExecutionException - When the place does not contain an item with this name or when there is another item with the same id in the robot inventory
	 * @throws InstructionExecutionException - if there exist any execution error.
	 */
	public void execute() throws InstructionExecutionException{
	//	engine.saveRobot();
		Item aux;
		if(this.navigation.getCurrentPlace().existItem(id)){
			aux = this.navigation.pickItemFromCurrentPlace(navigation.getCurrentPlace().getContainer().getItem(id).getId());
			if(!robotContainer.addItem(aux)){
				this.navigation.getCurrentPlace().addItem(aux);
				throw new InstructionExecutionException("Error, the object is already exists");
			}
			this.navigation.robotSays("WALL·E says: I am happy! Now I have " + id + '\n' );
			//RobotEngine.printMessage("WALL·E says: I am happy! Now I have " + id );
		}
		else
			throw new InstructionExecutionException("Error, the written object doesn't exist");
	}
	/**
	 * <p>Returns a description of the Instruction syntax. The string does not end with the line separator. It is 
	 * up to the caller adding it before printing.</p>
	 * 
	 * @return the command syntax PICK|COGER <id>
	 */
	public String getHelp(){
		return "PICK|COGER <ID>";
	}
	/**
	 * <p>Parses the String returning an instance of PickInstruction or throwing a WrongInstructionFormatException()</p>
	 * 
	 * @param cad - text String to parse
	 * @return Instruction Reference to an instance of PickInstruction
	 * 
	 * @throws WrongInstructionFormatException - if the String is not PICK|COGER <id>
	 */
	public Instruction parse(String cad) throws WrongInstructionFormatException{
		String aux[] = cad.split(" ");
		if ((aux[0].toLowerCase().equals("pick") || (aux[0].toLowerCase().equals("coger"))) && (aux.length ==2)){
			this.id = aux[1];
		} else {
			throw new WrongInstructionFormatException("Wrong format");
		}
		
		return this;
	}
	
}
