package tp.pr5.items;

import tp.pr5.NavigationModule;
import tp.pr5.Place;
import tp.pr5.RobotEngine;
/**
 * <p>An item that represents fuel. This item can be used at least 
 * once and it provides power energy to the robot. When the item is used the configured number of times, then it must be removed from the robot inventory</p>
 * 
 * 
 * @author Ismael Gonjal & Meriem ElYamri
 * @see Item
 *
 */
public class Fuel extends Item {
	private int power;
	private int times;
	
	//Constructora
	/**
	 * <p>Fuel constructor</p>
	 * 
	 * @param id Item id
	 * @param name Item description
	 * @param power The amount of power energy that this item provides the robot
	 * @param times Number of times the robot can use the item
	 */
	public Fuel(String id, String name, int power, int times){
		super(id, name);
		this.power = power;
		this.times = times;
	}
	/**
	 * <p>Fuel can be used as many times as it was configured</p>
	 * 
	 * @return true if the item still can be used
	 */
	public boolean canBeUsed(){
		return (times > 0);
	}
	/**
	 * <p>Using the fuel provides energy to the robot (if it can be used)</p>
	 * 
	 * @param r the robot that is going to use the fuel
	 * @param nav the place where the fuel is going to be used
	 * @return true if the fuel has been used
	 */
	public boolean use(RobotEngine r, NavigationModule nav){
		
		boolean aux = false;
		//If the fuel can be used, it is used and it's quantity gets reduced
		if (this.times > 0){
			r.addFuel(this.power);
			this.times -= 1;
			aux = true;
		}
		
		return aux;
	}
	/**
	 * <p>Generates a string with the item description</p>
	 * 
	 * @return the Fuel and its quantity
	 */
	public String toString(){
		return this.id + ": " + this.description + " // power = " + this.power + ", times = " + this.times;
	}
	/**
	 * Gets the power of the item
	 * @return the power
	 */
	public int getPower() {
		return power;
	}
	/**
	 * Gets the number of times that this item can be used
	 * @return the times of usage of the item.
	 */
	public int getTimes() {
		return times;
	}
	
}
