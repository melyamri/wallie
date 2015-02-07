package tp.pr5.instructions;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.ItemContainer;
/** 
*<p>Its execution request the robot to finish the simulation This Instruction works if the user writes QUIT or SALIR</p>
* @author Ismael Gonjal & Meriem El Yamri
*
* @see NavigationModule
* @see ItemContainer
* @see RobotEngine
*/
public class QuitInstruction implements Instruction {
	//Local variables
	private RobotEngine engine;
	//Constructors
	/**
	 * the default constructor for QuitInstruction
	 */
	public QuitInstruction(){
		this.engine = null;
	}
	
	//methods
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
		this.engine = engine;
	}
	
	/**
	 * <p>Request the robot to stop the simulation</p>
	 * 
	 * @throws InstructionExecutionException - if there exist any execution error.
	 */
	public void execute()  throws InstructionExecutionException{
		engine.requestQuit();
	}
	/**
	 * <p>Returns a description of the Instruction syntax. The string does not end with the line separator. It is 
	 * up to the caller adding it before printing.</p>
	 * 
	 * @return the Instruction syntax QUIT|SALIR
	 */
	public String getHelp(){
		return "QUIT|SALIR";
	}
	/**
	 * <p>Parses the String returning an instance of PickInstruction or throwing a WrongInstructionFormatException()</p>
	 * 
	 * @param cad - text String to parse
	 * @return Instruction Reference to an instance of QuitInstruction
	 * 
	 * @throws WrongInstructionFormatException - When the String is not QUIT|SALIR
	 */
	public Instruction parse(String cad) throws WrongInstructionFormatException {
		if (!cad.toLowerCase().equals("quit") && !cad.toLowerCase().equals("salir"))
				throw new WrongInstructionFormatException("Wrong Format");
		
		return this;
	}
}
