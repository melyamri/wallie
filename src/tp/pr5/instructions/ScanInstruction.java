package tp.pr5.instructions;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.ItemContainer;
/** 
* <p>The execution of this instruction shows the information of the inventory of the robot or the complete description about the item with identifier id contained in the inventory 
* This Instruction works if the player writes SCAN or ESCANEAR (id is not mandatory)</p>
* 
* @author Ismael Gonjal & Meriem El Yamri
*
* @see NavigationModule
* @see ItemContainer
* @see RobotEngine
*/
public class ScanInstruction implements Instruction {
	//Local variables
	ItemContainer robotContainer;
	RobotEngine engine;
	NavigationModule navigation;
	String id;
	
	//Constructors
	/**
	 * the default ScanInstruction constructor
	 */
	public ScanInstruction(){
		this.id = "";
		this.robotContainer = null;
		this.navigation = null;
		this.engine = null;
	}
	//Methods;
	/**
	 * <p>Set the execution context. The method receives the entire engine (engine, navigation and the robot 
	 * container) even though the actual implementation of execute() may not require it.</p>
	 * 
	 * @param engine The robot engine
	 * @param navigation The information about the game, i.e., the places, current direction and current heading to navigate
	 * @param robotContainer The inventory of the robot
	 */
	public void configureContext(tp.pr5.RobotEngine engine,
										  tp.pr5.NavigationModule navigation,
										  tp.pr5.items.ItemContainer robotContainer){
		this.robotContainer = robotContainer;
		this.navigation = navigation;
		this.engine = engine;
	}
	
	/**
	 *<p>Prints the description of a concrete item or the complete inventory of the robot</p>
	 *
	 *@throws InstructionExecutionException - When the robot does not contain the item to be scanned
	 */
	public void execute() throws InstructionExecutionException{
		if (this.id.equals("")){
			if (this.robotContainer.numberOfItems() > 0)
				//this.engine.printMessage("WALL·E says: I am carrying the following items" + '\n' + this.robotContainer.toString());
				this.navigation.robotSays("WALL·E says: I am carrying the following items" + '\n' + this.robotContainer.toString());
			else this.navigation.robotSays("What a pitty, I am carring nothing!");//this.engine.printMessage("What a pitty, I am carring nothing!");
		}else if(this.robotContainer.getItem(id) != null){
			//this.engine.printMessage(this.robotContainer.getItem(id).toString() + '\n');
			this.navigation.robotSays(this.robotContainer.getItem(id).toString() + '\n');
			
		}else{
			throw new InstructionExecutionException("Error en ScanInstruction");
		}
	}
	/**
	 * <p>Returns a description of the Instruction syntax. The string does not end with the line separator. It is 
	 * up to the caller adding it before printing.</p>
	 * 
	 * @return the Instruction syntax of the instruction SCAN | ESCANEAR [id]
	 */
	public String getHelp(){
		return "SCAN|ESCANEAR <ID>";
	}
	/**
	 * <p>Parses the String returning an instance of DropInstruction or throwing a WrongInstructionFormatException()</p>
	 * 
	 * @param cad - text String to parse
	 * @return Instruction Reference to an instance of ScanInstruction
	 * @throws WrongInstructionFormatException - When the String is not SCAN|ESCANEAR <id>
	 * 
	 */
	public Instruction parse(String cad) throws WrongInstructionFormatException{
		String aux[] = cad.split(" ");
		if (aux[0].toLowerCase().equals("scan") || aux[0].toLowerCase().equals("escanear")){
				if( aux.length == 2){
					this.id = aux[1];
				}
		}else{
			throw new WrongInstructionFormatException("Wrong format");
		}
		
		return this;
	}
}
