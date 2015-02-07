package tp.pr5;

import java.util.Observer;

import tp.pr5.items.Item;
import tp.pr5.items.ItemContainer;
/**
 * This interface should be implemented if the RobotEngine is Observed, it
 * provides the methods to get the necessary information of that class.
 * 
 * It gets if the robot has ended its beautiful travel, gets it's tracking
 * information (such as current fuel or recycledMaterial) the NavigationModule
 * associated to the Robot, the inventory, or gets an Item contained into the Inventory
 * 
 * @author Ismael Gonjal & Meriem El Yamri
 * @see RobotEngine
 * @see NavigationModule
 * @see ItemContainer
 * @see Item
 */
public interface RobotEngineObserver extends Observer{
	/**
	 * Informs that the comunication has been completed
	 */
	public void communicationCompleted();
	/**
	 * shows the help message
	 * @param help
	 */
	public void communicationHelp(String help);
	/**
	 * Sets off the engine
	 * @param atShip 
	 */
	public void engineOff(boolean atShip);
	/**
	 * throws an error
	 * @param msg
	 */
	public void raiseError(String msg);
	/**
	 * print a message
	 * @param message
	 */
	public void robotSays(String message);
	/**
	 * Updates the fuel and recycledMaterial of the robot
	 * @param fuel
	 * @param recycledMaterial
	 */
	public void robotUpdate(int fuel, int recycledMaterial);
	
	
	
}
