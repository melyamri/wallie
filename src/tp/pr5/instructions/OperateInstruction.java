package tp.pr5.instructions;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.exceptions.*;
import tp.pr5.items.*;
/** 
* <p>The Instruction for using an item. This Instruction works if the user writes OPERATE id or OPERAR id</p>
* 
* @author Ismael Gonjal & Meriem El Yamri
*
* @see NavigationModule
* @see ItemContainer
* @see RobotEngine
*/
public class OperateInstruction implements Instruction {
	//Local variables
	private NavigationModule navigation;
	private RobotEngine engine;
	private ItemContainer robotContainer;
	private String id;
	
	//constructors
	/**
	 * the default operateInstruction constructor
	 */
	public OperateInstruction(){
		this.id = "";
		this.robotContainer = null;
		this.navigation = null;
		this.engine = null;
	}
	/**
	 * this constructor allows to be created with an string of the object which will be used
	 * @param id
	 */
	public OperateInstruction(String id){
		this.id = id;
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
		this.robotContainer = robotContainer;
	}
	/**
	 * Gets the id of the item to operate with
	 * @return an string with the id stored into the instruction
	 */
	public String getId(){
		return this.id;
	}
	/**
	 * <p>The robot uses the requested item.</p>
	 * 
	 * @throws InstructionExecutionException - When the robot inventory does not contain an item with this name or when the item cannot be used
	 */
	public void execute() throws InstructionExecutionException{
		//engine.saveRobot();
		Item aux = this.robotContainer.getItem(id);
		if (aux != null){
			if(!aux.use(engine, navigation)){
				throw new InstructionExecutionException("Error executing OperateInstruction");
			}else{
				if(!aux.canBeUsed()){
					this.robotContainer.pickItem(id);
				}
			}
			
		}else{
			throw new InstructionExecutionException("Error en OperateInstruction");
		}
	}
	/**
	 * 
	 * <p>Returns a description of the Instruction syntax. 
	 * The string does not end with the line separator. It is up to the caller adding it before printing.</p>
	 * 
	 * @return the Instruction syntax OPERATE|OPERAR <ID>
	 */
	public String getHelp(){
		return "OPERATE|OPERAR <ID>";
	}
	/**
	 * <p>Parses the String returning an instance of OperateInstruction or throwing a WrongInstructionFormatException()</p>
	 * 
	 * @param cad - text String to parse
	 * @return Instruction Reference to an instance of OperateInstruction
	 * @throws WrongInstructionFormatException - When the String is not OPERATE|OPERAR <ID>
	 */
	public Instruction parse(String cad) throws WrongInstructionFormatException {
		
		String aux[] = cad.split(" ");
		if ((aux.length == 2) && ((aux[0].toLowerCase().equals("operate")) || (aux[0].toLowerCase().equals("operar")))){
				this.id = aux[1];
		}else{
			throw new WrongInstructionFormatException("Wrong format");			
		}
		
		return this;
	}
	
}
