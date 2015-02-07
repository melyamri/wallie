package tp.pr5.gui;

import tp.pr5.*;

import javax.swing.*;
/**
 * Contains a button that contains the place, this class extends the JButton class
 * 
 * @author Ismael Gonjal y Meriem El Yamri
 * @see JButton
 */
public class PlaceCell extends JButton{
	
	private PlaceInfo place;
	/**
	 * <p>Button that represents a place in the city</p>
	 */
	public PlaceCell(){
		this.setText("");
		this.place = null;
	}
	
	/**
	 * Gets the place
	 * @return the place
	 */
	public String getPlace(){
		return this.place.getName();
	}
	
	/**
	 * Sets a place
	 * @param place the place
	 */
	public void setPlace (PlaceInfo place){
		this.place = place;
		this.setText(this.place.getName());
	}
	

}
