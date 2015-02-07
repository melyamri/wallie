package tp.pr5.instructions;



import tp.pr5.*;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.ItemContainer;
/** 
 * 
 * <p>Shows the game help with all the instructions that the robot can execute. 
 * This instruction works if the user writes HELP or AYUDA</p>
* @author Ismael Gonjal & Meriem El Yamri
*
* @see NavigationModule
* @see ItemContainer
* @see RobotEngine
*/
public class HelpInstruction implements Instruction {
	//Local variables
	private RobotEngine engine;
	//Constructors
	/**
	 * the default helpInstruction constructor
	 */
	public HelpInstruction(){
		this.engine = null;
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
		this.engine = engine;
	}
	
	/**
	 * <p>Prints the help string of every instruction. It delegates to the Interpreter class.</p>
	 * 
	 * @throws InstructionExecutionException - if there exist any execution error.
	 */
	public void execute()  throws InstructionExecutionException{
		engine.requestHelp();
	}
	/**
	 * Returns a description of the Instruction syntax. The string does not end with the line separator. It is 
	 * up to the caller adding it before printing.
	 */
	public String getHelp(){
		return "HELP|AYUDA";
	}
	/**
	 * <p>Parses the String returning an instance of PickInstruction or throwing a WrongInstructionFormatException()</p>
	 * 
	 * @param cad - text String to parse
	 * @return Instruction Reference to an instance of HelpInstruction
	 * 
	 * @throws WrongInstructionFormatException - When the String is not HELP
	 */
	public Instruction parse(String cad) throws WrongInstructionFormatException{
		if (!cad.toLowerCase().equals("help") && !cad.toLowerCase().equals("ayuda"))
			throw new WrongInstructionFormatException("Wrong format");

		return this;
	}
}
