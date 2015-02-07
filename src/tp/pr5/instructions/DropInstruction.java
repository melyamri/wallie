package tp.pr5.instructions;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.exceptions.*;
import tp.pr5.items.Item;
import tp.pr5.items.ItemContainer;
/**
 * <p>This instruction drops an Item from the current place and puts it the robot inventory. 
 * This instruction works if the user writes DROP or SOLTAR</p>
 * 
 * @author Ismael Gonjal & Meriem El Yamri
 *
 * @see NavigationModule
 * @see ItemContainer
 * @see RobotEngine
 */
public class DropInstruction implements Instruction {
	//Local variables
	private NavigationModule navigation;
	private RobotEngine engine;
	private ItemContainer robotContainer;
	private String id;
	
	//Constructors
	/**
	 * Empty constructor, generates a clear DropInstruction
	 */
	public DropInstruction(){
		this.id = "";
		this.robotContainer = null;
		this.navigation = null;
		this.engine = null;
	}
	/**
	 * This constructor is initialized with an id of the item that will be dropped
	 * @param id the id of the item.
	 */
	public DropInstruction(String id){
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
	 * <p>Set the execution context. The method receives the entire engine (engine, navigation and the robot 
	 * container) even though the actual implementation of execute() may not require it.</p>
	 * 
	 * @param engine The robot engine
	 * @param navigation The information about the game, i.e., the places, current direction and current heading to navigate
	 * @param robotContainer The inventory of the robot
	 */
	public void configureContext(RobotEngine engine, NavigationModule navigation, ItemContainer robotContainer){
		this.navigation = navigation;
		this.engine = engine;
		this.robotContainer = robotContainer;
	}
	
	/**
	 * <p>The robot drops an Item from its inventory in the current place, if the item exists</p>
	 *
	 * @throws InstructionExecutionException The robot drops an Item from its inventory in the current place, if the item exists
	 */
	public void execute()  throws InstructionExecutionException{
		//engine.saveRobot();
		Item aux = this.robotContainer.pickItem(id);
		if(aux != null){
			if(!navigation.getCurrentPlace().existItem(id)){
				
				this.navigation.dropItemAtCurrentPlace(aux);
				this.navigation.robotSays("Great! I have dropped " + id + '\n');
				
				//this.engine.printMessage("Great! I have dropped " + id);
			}else
				throw new InstructionExecutionException("Error, there is one " + id + " in this place." + '\n');
		}else{
			throw new InstructionExecutionException("You do not have any " + id + "." + '\n');
		}
	}
	
	/**
	 * <p>Returns a description of the Instruction syntax. The string does not end with the line separator. It is 
	 * up to the caller adding it before printing.</p>
	 */
	public String getHelp(){
		return "DROP|SOLTAR <id>";
	}
	
	/**
	 * <p>Parses the String returning an instance of DropInstruction or throwing a WrongInstructionFormatException()</p>
	 * 
	 * @param cad - text String to parse
	 * @return Instruction Reference to an instance of DropInstruction
	 * @throws WrongInstructionFormatException - When the String is not DROP <id>
	 * 
	 */
	public Instruction parse(String cad) throws WrongInstructionFormatException{
		
		String aux[] = cad.split(" ");
		if ((aux.length == 2) && ((aux[0].toLowerCase().equals("drop")) || (aux[0].toLowerCase().equals("soltar")))){
				this.id = aux[1];
		}else{
			throw new WrongInstructionFormatException("Wrong format");			
		}
		return this;
	}
}
