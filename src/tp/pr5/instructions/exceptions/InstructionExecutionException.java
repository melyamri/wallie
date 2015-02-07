package tp.pr5.instructions.exceptions;

/**
 * <p>Exception thrown when a instruction execution fails. The exception has a user-friendly message with an explanation about the error. 
 * This class has many different constructors, one for every constructor of the base class.</p>
 * 
 * @author Ismael Gonjal & Meriem El Yamri
 *
 */
public class InstructionExecutionException extends java.lang.Exception {
	String msg;
	/**
	 * <p>Constructor without parameters</p>
	 */
	public InstructionExecutionException(){
		this.msg = "Error, the instruction could not be executed.";
	}
	/**
	 * <p>The exception thrown is created with a problem message.</p>
	 * @param msg - User-friendly string that explains the error.
	 */
	public InstructionExecutionException(String msg){
		this.msg = msg;
	}
	/**
	 * gets the exception message
	 * @return the message
	 */
	public String getMsg(){
		return this.msg;
	}
	
}
