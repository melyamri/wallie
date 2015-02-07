package tp.pr5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import tp.pr5.instructions.*;
import tp.pr5.instructions.exceptions.*;


/**
 * <p>The interpreter is in charge of converting the user input in an instruction for the robot. Up to now, 
 * the valid instructions are:</p>
		 * <p>&#8226MOVE </p>
		 * <p>&#8226TURN { LEFT | RIGHT } </p>
		 * <p>&#8226PICK &#60;ITEM&#62; </p>
		 * <p>&#8226SCAN [ &#60;ITEM&#62; ] </p>
		 * <p>&#8226OPERATE &#60;ITEM&#62; </p>
		 * <p>&#8226RADAR </p>
		 * <p>&#8226DROP &#60;ITEM&#62; </p>
		 * <p>&#8226HELP </p>
		 * <p>&#8226QUIT </p>
		 * <p>&#822UNDO</p>
 * @author Ismael Gonjal & Meriem El Yamri
 * @see tp.pr5.instructions
 * @see tp.pr5.instructions.exceptions
 */
public class Interpreter {
	// Attributes
	@SuppressWarnings("unused")
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	private static final String[] tipos = {"HELP","MOVE","QUIT","SCAN","RADAR","TURN","PICK","OPERATE", "DROP", "UNDO"};
	private static final Instruction[] instrucciones = {new HelpInstruction(), new MoveInstruction(), new QuitInstruction(),
														new ScanInstruction(), new  RadarInstruction(), new TurnInstruction(),
														new PickInstruction(), new OperateInstruction(), new DropInstruction(), 
														new UndoInstruction()};
	/* Due of the imperative of using the ArrayLists, we maintain the array of Instructions because is more
	 * maintainable and legible.
	 */
	private final static List<Instruction> instructions = new ArrayList<Instruction>(Arrays.asList(instrucciones));;
	
	//Methods
		/**
		 * <p>Generates a new instruction according to a string introduced. the instruction can be:</p>
		 * 
		 * <p>HELP, MOVE, QUIT, SCAN, RADAR, TURN, PICK OPERATE, DROP, UNDO</p>
		 * 
		 * <p>The string introduced will ignore the case differences, so Undo, undo, UNDO, UnDo, etc, will be the same</p>
		 * 
		 * @param line A string with the user input
		 * @return The instruction read from the given line. If the instruction is not correct, then it throws an exception.
		 * @throws WrongInstructionFormatException when the first word of the string is not one of the accepted ones.
		 */
		public static Instruction generateInstruction(String line) throws WrongInstructionFormatException{
			
		
			
			java.util.Iterator<Instruction> itr = instructions.iterator();		
			Instruction ret = null;
			String[] aux = line.split(" ");
			int i = 0;
			
			while(itr.hasNext() && ret == null){
				/* the iterator will get the next, but it only will be saved if a coincidence 
				 * has been found with the tipos string
				 */
				if(aux[0].equalsIgnoreCase(tipos[i]))
						ret = itr.next();
				else
					itr.next();
				i++;
			}

			if (ret == null){
				throw new WrongInstructionFormatException("Error al introducir la instrucción.");
			}
			return ret;
		}
	/**
	 * <p>It returns information about all the instructions that the robot understands</p>
	 * 
	 * @return A string with the information about all the available instructions
	 */
	public static String interpreterHelp(){
		Iterator<Instruction> itr = instructions.iterator();
		String ret = "The valid instructions for WALL-E are: " + '\n';
		boolean isDone = false;
		while(itr.hasNext() && !isDone){
			ret += "   " + itr.next().getHelp() + '\n';
		}
		
		return ret;
	}
}