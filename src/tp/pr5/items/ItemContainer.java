package tp.pr5.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import tp.pr5.instructions.DropInstruction;
import tp.pr5.instructions.MoveInstruction;
import tp.pr5.instructions.OperateInstruction;
import tp.pr5.instructions.PickInstruction;
/**
 * <p>A container of items. It can be employed by any class that stores items.
 * A container cannot store two items with the same identifier.
 * It provides methods to add new items, access them and remove them from the container.</p>
 * 
 * @author Ismael Gonjal y Meriem El Yamri
 * @see ArrayList
 */
public class ItemContainer extends Observable{
	
	private List<Item> items;
	
	/**
	 * Defult constructor for itemContainer
	 */
	public ItemContainer(){
		this.items = new ArrayList<Item>();
	}
	
	/**
	 * <p>Adds an item into the arraylist, ordering it lexicographically by it's id</p>
	 * 
	 * @param item the item to add
	 * @return true if it has been included in the arraylist. Duplications are not allowed.
	 */
	public boolean addItem(Item item){	
		
		
		boolean isDone = false;
		Item comp = new GenericItemForSearch(item.getId());
		int index = Collections.binarySearch(this.items, comp);
		if(index < 0){
			index = (index+1) * -1;
			this.items.add(index, item);
			List<Object> args = new ArrayList<Object>();
			args.add("addItem");
			args.add(this.items);
			this.informObservers(args);
			isDone = true;
		}
		return isDone;
	}
	
	/**
	 * 
	 * </p>Returns the item from the container according to the item name</p>
	 * 
	 * @param id Item name
	 * @return Item with that name or null if the container does not store an item with that name.
	 */
	public Item getItem(String id){
		Item comp = new GenericItemForSearch(id);
		int index = Collections.binarySearch(this.items, comp);
		Item aux = null;
		if(index >=0){
			aux = this.items.get(index);
		}
		return aux;
	}
	
	/**
	 * <p>Returns the container's size</p>
	 * 
	 * @return The number of items in the container
	 */
	
	public int numberOfItems(){
		return this.items.size();
	}
	
	/**
	 * <p>Returns and deletes an item from the inventory. This operation can fail, returning null</p>
	 * 
	 * @param id Name of the item
	 * @return An item if and only if the item identified by id exists in the inventory. Otherwise it returns null
	 */
	
	public Item pickItem(String id){
		Item comp = new GenericItemForSearch(id);
		int index = Collections.binarySearch(this.items, comp);
		Item aux = null;
		if(index >= 0 ){
			aux = this.items.get(index);
			this.items.remove(index);
			List<Object> args = new ArrayList<Object>();
			args.add("pickItem");
			args.add(this.items);
			this.informObservers(args);
		}
		return aux;
	}
	
	/**
	 * <p>Generates a String with information about the items contained in the container. 
	 * Note that the items must appear sorted but the item name.</p>
	 * 
	 * @return a string with the itemcontainer content
	 */
	public String toString(){
		Iterator<Item> itr = items.iterator();
		String aux = "";
		boolean isDone = false;
		while(itr.hasNext() && !isDone){
			aux += "   " + itr.next().getId() + '\n';
		}

		return aux;
	}
	
	/**
	 * this method search on the list and tells if the item iddentified by it's id is into the list
	 * @param id the id of the item
	 * @return true if the item is into the list false otherwise
	 */
	public boolean containsItem(String id){
		return (this.getItem(id) != null);
	}
	/**
	 * this method returns the complete list of items
	 * @return the list of items
	 */
	public List<Item> getItems() {
		return this.items;
	}
	
	/**
	 * Sets the items into the itemContainer list. Is is used for the UNDO action. If the undo action is executed,
	 * it makes a copy of the object and returns all the parameters it had at first.
	 * @param newitems the items list
	 */
	/*public void setItems(List<Item> newitems) {
	
		Iterator<Item> itr = newitems.iterator();
		//Read item is the item picked from the iteratod
		Item readItem;
		//toBeAdded is the item that will be added into the arrayList
		Item toBeAdded = null;
		
		//This while casts the item for it's classes and adds them into the list
		while(itr.hasNext()){
			readItem = itr.next();
			if(readItem.getClass() == Fuel.class){
				toBeAdded = new Fuel(readItem.getId(), readItem.getDescription(), ((Fuel) readItem).getPower(),((Fuel) readItem).getTimes());
			}else if(readItem.getClass() == CodeCard.class){
				toBeAdded = new CodeCard(readItem.getId(), readItem.getDescription(),((CodeCard)readItem).getCode());
			}else if(readItem.getClass() == Garbage.class){
				toBeAdded = new Garbage(readItem.getId(),readItem.getDescription(),((Garbage)readItem).getRecycledMaterial());
			}
			this.items.add(toBeAdded);
		}
		
	}
	*/
	private void informObservers(List<Object> args){
        setChanged(); // establece que ha habido un cambio.
        notifyObservers(args); // notifica a los observadores.	
        // notifyObservers(new Double(alto)); // se podría informa mandandoles un Objeto con la información necesaria
    }
}
