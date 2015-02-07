package tp.pr5.items;

import java.util.List;
import java.util.Observer;
/**
 * This class represents the observer of the inventory
 * 
 * @author Ismael Gonjal & Meriem El Yamri
 *
 */
public interface InventoryObserver extends Observer {
	/**
	 * Notifyies that the inventory has changed
	 * @param inventory
	 */
	public void inventoryChange(List<Item> inventory);
	/**
	 * Receives an order of scan and executes it
	 * @param inventoryDescription
	 */
	public void inventoryScanned(String inventoryDescription);
	/**
	 * tells if the inventory is empty
	 * @param itemName
	 */
	public void itemEmpty(String itemName);
	/**
	 * Gets the scan of an item
	 * @param description
	 */
	public void itemScanned(String description);
	

}
