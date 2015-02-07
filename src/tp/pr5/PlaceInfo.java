package tp.pr5;

import tp.pr5.items.ItemContainer;

/**
 * This class will implement the observable and immutable methods.
 * It will provide the basic methods to observe in a constant way any 
 * variable had in a place.
 * 
 * @author Ismael Gonjal & Meriem El Yamri
 *
 */
public interface PlaceInfo {
	/**
	 * It will return the name of the place which implements this
	 * @return the id of the place
	 */
	public String getName();
	/**
	 * It will return the description of the place which implements this
	 * @return
	 */
	public String getDescription();
	/**
	 * It will return the list of items which are in the place
	 * @return a list of items
	 */
	public boolean isSpaceship();

}
