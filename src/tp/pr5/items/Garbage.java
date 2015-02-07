package tp.pr5.items;

import tp.pr5.NavigationModule;
import tp.pr5.Place;
import tp.pr5.RobotEngine;
/**
 * <p>The garbage is a type of item that generates recycled material after using it. 
 * The garbage can be used only once. 
 * After using it, it must be removed from the robot inventory</p>
 * 
 * @author Ismael Gonjal & Meriem ElYamri
 */
public class Garbage extends Item {
	 private int recycledMaterial;
	
	//Constructor
	/**
	 * <p>Garbage constructor</p>
	 * 
	 * @param id Item id
	 * @param description Item description
	 * @param recycledMaterial The amount of recycled material that the item generates
	 */
	public Garbage(String id, String description,int recycledMaterial){
		super(id, description);
		this.recycledMaterial = recycledMaterial;
	}
	//Methods
	/**
	 * <p>Garbage can be used only once</p>
	 * 
	 * @return true if the item has not been used yet
	 * 
	 */
	public boolean canBeUsed(){
		if (this.recycledMaterial > 0){
			return true;
		}
		return false;
	}
	
	/**
	 * <p>The garbage generates recycled material for the robot that uses it</p>
	 * 
	 * 
	 * @param r the robot engine
	 * @param The navigation module
	 * 
	 * @return true if the garbage was transformed in recycled material
	 */
	public boolean use(RobotEngine r, NavigationModule nav){
		if (this.recycledMaterial > 0 && canBeUsed()){
			r.addRecycledMaterial(this.recycledMaterial);
			this.recycledMaterial = 0;
			return true;
		}
		return false;
	}
	/**
	 * Gets the quantity of recycled material that produces this Garbage
	 * @return the recycled Material
	 */
	public int getRecycledMaterial() {
		return recycledMaterial;
	}
}
