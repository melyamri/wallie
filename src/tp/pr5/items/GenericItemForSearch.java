package tp.pr5.items;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;

public class GenericItemForSearch extends Item {
	/**
	 * This generates a generic item which only should be used in order to search 
	 * another items in Lists whatever it's type would be.
	 * 
	 * @param id the id of the item to search
	 */
	public GenericItemForSearch(String id) {
		super(id, "");
		// TODO Auto-generated constructor stub
	}
	/**
	 * This method should not me used, it will always returns false
	 */
	@Override
	public boolean canBeUsed() {
		return false;
	}
	/**
	 * This method should not be used, it will always returns false, and will do nothing
	 */
	@Override
	public boolean use(RobotEngine robot, NavigationModule nav) {
		return false;
	}
	

}
