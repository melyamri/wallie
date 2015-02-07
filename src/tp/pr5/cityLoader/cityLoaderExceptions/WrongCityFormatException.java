package tp.pr5.cityLoader.cityLoaderExceptions;

public class WrongCityFormatException extends java.io.IOException {
	
	/**
	 * Exception thrown by the map loader when the file does not adhere to the file format.
	 * 
	 * @author Ismael Gonjal y Meriem El Yamri
	 */
	
	/**
	 * The message of error
	 */
	private String message;
	//Constructors
	/**
	 * this constructor creates a WrongCityFormatException with the message: 
	 * "Error de formato"
	 */
	public WrongCityFormatException(){
		this.message = "Error de formato";
	}
	/**
	 * this constructor will create a WrongCityFormatExcepton with the introduced 
	 * mesage
	 * @param msg the message that will store this exception
	 */
	public WrongCityFormatException(String msg){
		this.message = msg;
	}
	
	/**
	 * Gets the message that returns the instruction
	 * 
	 * @return the message that is thrown by the exception
	 */
	public String getMessage(){
		return this.message;
	}

}
