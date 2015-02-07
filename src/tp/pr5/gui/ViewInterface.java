package tp.pr5.gui;

import java.util.EventListener;
import java.util.Observer;
/**
 * The observer of the viez. Sets the controller in order to fix the eventListener
 * 
 * @author Ismael Gonjal & Meriem El Yamri
 *
 */
public interface ViewInterface extends Observer{
	/**
	 * Method that fixes the controller for each window
	 * @param controller the controller
	 */
	public void fixController(EventListener controller);

}
