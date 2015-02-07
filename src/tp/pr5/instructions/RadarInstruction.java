package tp.pr5.instructions;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.ItemContainer;
/** 
* 
* <p>his Instruction shows the description of the current place and the items in it. 
* This Instruction works if the user writes RADAR</p>
* 
* @author Ismael Gonjal & Meriem El Yamri
*
* @see NavigationModule
* @see ItemContainer
* @see RobotEngine
*/
public class RadarInstruction implements Instruction {
	//Local variables
	private NavigationModule navigation;
	
	//Constructors
	/**
	 * The default RadarInstruction constructor
	 */
	public RadarInstruction(){
		this.navigation = null;
	}
	
	//Methods
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
	}
	
	/**
	 * <p>Shows the current place description.</p>
	 * 
	 * @throws InstructionExecutionException - if there exist any execution error.
	 */
	public void execute()  throws InstructionExecutionException{
		this.navigation.scanCurrentPlace();
	}
	/**
	 * <p>Returns a description of the Instruction syntax. The string does not end with the line separator. It is 
	 * up to the caller adding it before printing.</p>
	 * 
	 * @return the command syntax RADAR <id>
	 */
	public String getHelp(){
		return "RADAR";
	}
	/**
	 * <p>Parses the String returning an instance of PickInstruction or throwing a WrongInstructionFormatException()</p>
	 * 
	 * @param cad - text String to parse
	 * @return Instruction Reference to an instance of RadarInstruction
	 * 
	 * @throws WrongInstructionFormatException - if the String is not RADAR <id>
	 */
	public Instruction parse(String cad) throws WrongInstructionFormatException {
		if (!cad.toLowerCase().equals("radar"))
				throw new WrongInstructionFormatException("Wrong Format");
		
		return this;
	}
}
