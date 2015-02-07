package tp.pr5;

import java.util.Observer;

import tp.pr5.gui.NavigationPanel;
/**
 * This interface should be implemented if a graphic interface is going to extend the class
 * Observable. Being the class or a subclass of NavigationModule.
 * 
 * It provides accesor methods and the capacity of knowing if the place has 
 * changed.
 * 
 * It provides also the Navigation panel which is associated to the NavigationModule.
 *  
 * @author Ismael Gonjal & Meriem El Yamri
 * 
 * @see NavigationModule
 * @see Observable
 */
public interface NavigationObserver extends Observer{
	/**
	 * Sets the new heading of the navigationModule
	 * @param newHeading the new heading
	 */
	public void headingChanged(Direction newHeading);
	/**
	 * Sets the initial place of the observer
	 * @param initialPlace
	 */
	public void initNavigationModule(Direction heading, PlaceInfo initialPlace);
	/**
	 * Changes the place to a new one, only observed and not mutable
	 * @param placeDescription t
	 */
	public void placeHasChanged(PlaceInfo placeDescription);
	/**
	 * Gets a description to this method to scan the place
	 * @param placeDescription
	 */
	public void placeScanned(PlaceInfo placeDescription);
	/**
	 * When the place changes, displays a message telling that robot has updated it's place
	 * @param heading
	 * @param place
	 */
	public void robotArrivesAtPlace(Direction heading, PlaceInfo place);
	
	
	
	
}
