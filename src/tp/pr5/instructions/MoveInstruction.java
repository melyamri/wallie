package tp.pr5.instructions;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.ItemContainer;
/** 
* 
* <p>Its execution moves the robot from one place to the next one in the current direction. This instruction works if the user writes MOVE or MOVER </p>
* @author Ismael Gonjal & Meriem El Yamri
*
* @see NavigationModule
* @see ItemContainer
* @see RobotEngine
*/
public class MoveInstruction implements Instruction {
	//Local variables
	private NavigationModule navigation;
	private RobotEngine engine;
	//ItemContainer robotContainer;
	
	//Constructors
	/**
	 * the default MoveInstruction constructor.
	 */
	public MoveInstruction(){
		this.navigation = null;
		this.engine = null;
	}
	
	//Methods
	/**
	 * <p>Set the execution context. 
	 * The method receives the entire engine (engine, navigation and the robot container) even though the actual implementation of execute() may not require it.</p>
	 * 
	 * @param engine - The robot engine
	 * @param navigation - The information about the game, i.e., the places, current direction and current heading to navigate
	 * @param robotContainer - The inventory of the robot
	 */
	public void configureContext(tp.pr5.RobotEngine engine,
										  tp.pr5.NavigationModule navigation,
										  tp.pr5.items.ItemContainer robotContainer){
		this.navigation = navigation;
		this.engine = engine;
		//this.robotContainer = robotContainer;
	}
	
	/**
	 * 
	 * <p>Moves from the current place to the next place on the current Direction. 
	 * An opened street must exist between both places to be moved</p>
	 * 
	 * @throws InstructionExecutionException - When the robot cannot go to other place (there is a wall, a closed street...)
	 * 
	 */
	public void execute()  throws InstructionExecutionException{
		//engine.saveRobot();
		this.navigation.move();
		this.engine.addFuel(-5);
	}
	/**
	 * <p>Returns a description of the Instruction syntax. The string does not end with the line separator. It is 
	 * up to the caller adding it before printing.</p>
	 * 
	 * @return the command syntax MOVE|MOVER
	 */
	public String getHelp(){
		return "MOVE|MOVER";
	}
	/**
	 * <p>Parses the String returning a MoveInstruction instance or throwing a WrongInstructionFormatException()</p>
	 * 
	 * @param cad - text String to parse
	 * @return Instruction Reference to an instance of MoveInstruction
	 * @throws WrongInstructionFormatException - When the String is not MOVE
	 */
	public Instruction parse(String cad) throws WrongInstructionFormatException {
		if (!cad.toLowerCase().equals("move") && !cad.toLowerCase().equals("mover"))
				throw new WrongInstructionFormatException("Wrong format");
		
		return this;
	}
}
