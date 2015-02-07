package tp.pr5.instructions;

import javax.swing.JOptionPane;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.ItemContainer;
/**
 * <p>This instruction undoes the last instruction done by the user. It works with MOVE, PICK, DROP, OPERATE and TURN
 * This instruction works if the user writes UNDO or DESHACER</p>
 * 
 * @author Ismael Gonjal & Meriem El Yamri
 *
 * @see NavigationModule
 * @see ItemContainer
 * @see RobotEngine
 */
public class UndoInstruction implements Instruction{
	//Local variables
	private RobotEngine engine;
	private NavigationModule navigation;
	private ItemContainer robotContainer;

	//Constructors
	/**
	 * The default constructor for UndoInstruction
	 */
	public UndoInstruction(){
		this.robotContainer = null;
		this.navigation = null;
		this.engine = null;
	}
	
	//Methods
	@Override
	/**
	 * <p>Set the execution context. The method receives the entire engine (engine, navigation and the robot 
	 * container) even though the actual implementation of execute() may not require it.</p>
	 * 
	 * @param engine The robot engine
	 * @param navigation The information about the game, i.e., the places, current direction and current heading to navigate
	 * @param robotContainer The inventory of the robot
	 */
	public void configureContext(RobotEngine engine,
			NavigationModule navigation, ItemContainer robotContainer) {
		this.engine = engine;
		this.navigation = navigation;
		this.robotContainer = robotContainer;
	}

	/**
	 * <p>The robot undoes the last instruction done returning to a previous state</p>
	 *
	 * 
	 */
	public void execute() {
		//if (engine.getBackup() != null)
		//	engine.loadRobot();
		
	}

	/**
	 * <p>Returns a description of the Instruction syntax. The string does not end with the line separator. It is 
	 * up to the caller adding it before printing.</p>
	 */
	public String getHelp() {
		return "UNDO|DESHACER";
	}

	/**
	 * <p>Parses the String returning an instance of UndoInstruction or throwing a WrongInstructionFormatException()</p>
	 * 
	 * @param cad - text String to parse
	 * @return Instruction Reference to an instance of DropInstruction
	 * @throws WrongInstructionFormatException - When the String is not UNDO or DESHACER <id>
	 * 
	 */
	public Instruction parse(String cad) throws WrongInstructionFormatException {
		if (!cad.toLowerCase().equals("undo") && !cad.toLowerCase().equals("deshacer"))
			throw new WrongInstructionFormatException("Error de formato");
	
	return this;
	}

}
