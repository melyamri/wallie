package tp.pr5.instructions;

import java.util.ArrayList;

import tp.pr5.Direction;
import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.Rotation;
import tp.pr5.instructions.exceptions.*;
import tp.pr5.items.ItemContainer;
/** 
 * 
 * This instruction allows the robot to turn. It must be parsed before the execution
 * 
 * @author Ismael Gonjal & Meriem El Yamri
 *
 * @see NavigationModule
 * @see ItemContainer
 * @see RobotEngine
 */
public class TurnInstruction implements Instruction {
	//Local variables
	private NavigationModule navigation;
	private RobotEngine engine;
	private Rotation rot;
	
	//constructors
	/**
	 * The default constructor for TurnInstruction
	 */
	public TurnInstruction(){
		this.navigation = null;
		this.engine = null;
		this.rot = Rotation.UNKNOWN;
	}
	/**
	 * this TurnInstruction constructor will allow a rotation that will be used into the execution
	 * @param rot
	 */
	public TurnInstruction(Rotation rot){
		this.navigation = null;
		this.engine = null;
		this.rot = rot;
	}
	
	//Methods
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
		this.navigation = navigation;
		this.engine = engine;
		
	}
	
	/**
	 * <p>Turns the robot left or right</p>
	 * 
	 * @throws InstructionExecutionException - When the rotation is unknown
	 */
	public void execute()  throws InstructionExecutionException{
		//engine.saveRobot();
		this.navigation.rotate(this.rot);
		this.engine.addFuel(-5);
		
	}
	/**
	 * <p>Returns a description of the Instruction syntax. The string does not end with the line separator. It is 
	 * up to the caller adding it before printing.</p>
	 * 
	 * @return the command syntax TURN | GIRAR < LEFT|RIGHT >
	 */
	public String getHelp(){
		return "TURN | GIRAR < LEFT|RIGHT >";
	}
	/**
	 * <p>Parses the String returning an instance of DropInstruction or throwing a WrongInstructionFormatException()</p>
	 * 
	 * @param cad - text String to parse
	 * @return Instruction Reference to an instance of TurnInstruction
	 * @throws WrongInstructionFormatException - When the String is not TURN | GIRAR < LEFT|RIGHT >
	 * 
	 */
	public Instruction parse(String cad) throws WrongInstructionFormatException{
		String aux[] = cad.split(" ");
		
		if (aux.length != 2){
			throw new WrongInstructionFormatException("Error de formato");
		}else if (aux[0].toLowerCase().equals("turn") || aux[0].toLowerCase().equals("girar")){
			if(aux[1].toLowerCase().equals("left")){
				this.rot = Rotation.LEFT;
			}else if(aux[1].toLowerCase().equals("right")){
				this.rot = Rotation.RIGHT;
			}else{
				this.rot = Rotation.UNKNOWN;
				throw new WrongInstructionFormatException("Format Error, Wrong rotation");
			}
		}else{
			throw new WrongInstructionFormatException("Wrong format");
		}
		return this;
	}
}
