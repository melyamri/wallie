package tp.pr5.gui;
import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.Observable;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import tp.pr5.*;
import tp.pr5.controller.GUIController;
import tp.pr5.items.Item;

/**
 * This class extends java.awt.JPanel. Is the panel which will represent the world where wall-e lives. 
 * 
 * @author Ismael Gonjal y Meriem El Yamri
 * @see JPanel
 * @see PlaceCell
 */
public class NavigationPanel extends JPanel implements NavigationObserver{

	private final int numRows = 11;
	private final int numColumns = 11;
	
	private PlaceCell[][] matrix;
	private JLabel iconLabel;
	private JTextArea textArea;
	private int iCoord;
	private int jCoord;
	private MainWindow mainWindow;
	private JLabel label;
	/**
	 * <p>Window section that contains the city map, the WALLIE heading icon and the log that shows information about the place.
	 * Is is in charge of getting user action about the city panel to update the place information in the log panel, and also update
	 * the window.</p>
	 * 
	 * @param container the MainWindow
	 */
	public NavigationPanel(MainWindow container){
		this.mainWindow =  container;
		iCoord = 5;
		jCoord = 5;
		container.add(this, BorderLayout.CENTER);
		
		//Creating a group layout to set the localization of the panels
		GroupLayout layout = new GroupLayout(this);
		 this.setLayout(layout);
		
		//Panel that contains the city map and WALLIE icon
		JPanel northPanel = new JPanel();
		//Border Layout
		northPanel.setLayout(new BorderLayout());
		
		//Icon that contains the WALLIE icon with the correct heading direction, when the program starts, it's NORTH
		ImageIcon image = new ImageIcon(getClass().getResource("../headingIcons/walleNorth.png"));
		iconLabel = new JLabel(image);
		//The icon is added in the WEST part of the layout
		northPanel.add(iconLabel, BorderLayout.WEST);
		
		//Panel that has the matrix of PlaceCell button. It represents the city map		
		JPanel cityMap = new JPanel();
		//Grid Layout
		cityMap.setLayout(new GridLayout(numRows,numColumns));
		//Border
		cityMap.setBorder(new TitledBorder("City Map"));
		//The city map panel is added int CENTER part of the layout
		northPanel.add(cityMap, BorderLayout.CENTER);
		
		//Matrix that contains 121 cells and a bucle to create it
		matrix = new PlaceCell[numRows][numColumns];
		for (int i = 0; i < numRows; i++){
			for (int j = 0; j < numColumns; j++){
				matrix[i][j] = new PlaceCell();
				String istring, jstring;
				if (i > 9) istring = new String ("" + i);
				else istring = new String ("0" + i);
				if (j > 9) jstring = new String ("" + j);
				else jstring = new String ("0" + j);
				
				cityMap.add(matrix[i][j]);
				
			}
		}
		
		cityMap.doLayout();//Updating Layout to show all cells created before
		
		//Panel that has the description of the current place
		JPanel logPanel = new JPanel();
		//Border Layout
		logPanel.setLayout(new BorderLayout());
		//Border
		logPanel.setBorder(new TitledBorder("Log"));
		
		//Text Area in the log panel, it shows the place description
		this.textArea = new JTextArea();
		//It is not editable by the user
		textArea.setEditable(false);
		//Size
		textArea.setRows(5);
		//The text area is added in the CENTER part of the layout
		logPanel.add(textArea, BorderLayout.CENTER);
		
		label = new JLabel("WALL·E");
		label.setHorizontalAlignment((int) JLabel.CENTER_ALIGNMENT);
		
		//General organisation of the group layout, VERTICAL
		layout.setVerticalGroup(layout.createSequentialGroup()
			     .addComponent(northPanel, 250, 250, 800)
			     .addComponent(logPanel, 100, 100, 800)
			     .addComponent(label, 30, 30, 800)
		);
		
		//HORIZONTAL

		layout.setHorizontalGroup(layout.createSequentialGroup()
				 .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			                .addComponent(northPanel, 250, 250, 800)
			                .addComponent(label, 30, 30, 800)
			                .addComponent(logPanel, 100, 100, 800))
			                
		);
		
	}
	
	public JLabel getLabel() {
		return label;
	}

	public void setLabel(JLabel label) {
		this.label = label;
	}

	/**
	 * <p>Updates the image icon according to the correct heading direction</p>
	 * 
	 * @param direction the current direction of the robot
	 */
	public void updateImage(Direction direction){
		
		ImageIcon image = null;
		
		switch (direction){
		case NORTH: image = new ImageIcon(getClass().getResource("../headingIcons/walleNorth.png")); break;
		
		case WEST:image = new ImageIcon(getClass().getResource("../headingIcons/walleWest.png")); break;
		
		case SOUTH:image = new ImageIcon(getClass().getResource("../headingIcons/walleSouth.png")); break;
	
		case EAST:image = new ImageIcon(getClass().getResource("../headingIcons/walleEast.png")); break;
		
		}
		
		iconLabel.setIcon(image);
	}
	/**
	 * Updates the text area that contains the place information
	 * @param navigationModule the navigation module
	 */
	public void updateLog(PlaceInfo place){
		this.textArea.setText(place.toString());
		this.repaint();
	}
	/**
	 * Updates the city map matrix, it turns to green the current place, and to gray the visit places.
	 * @param module the navigation module
	 */
	public void updateCityMapButtons(PlaceInfo place, Direction dir){
		//Turns last place visited to gray color
		matrix[iCoord][jCoord].setBackground(Color.GRAY);
		switch (dir){
		case NORTH:
			if (iCoord>0) iCoord -= 1; break;
		case WEST:
			if (jCoord>0) jCoord -= 1; break;
		case SOUTH:
			if (iCoord<11) iCoord += 1; break;
		case EAST:
			if (jCoord<11) jCoord += 1; break;
		default:
			break;
		}
		//Turns the current place to green color
		matrix[iCoord][jCoord].setText(place.getName());
		matrix[iCoord][jCoord].setBackground(Color.GREEN);
		
		
	}
	
	/**
	 * Initiates the first place in the coordinates (5,5)
	 * @param navigation the Navigation Module
	 */
	public void initCityMapButtons(PlaceInfo place){
		matrix[iCoord][jCoord].setText(place.getName());
		matrix[iCoord][jCoord].setBackground(Color.GREEN);
		
	}

	/**
	 * Changes the heading
	 */
	@Override
	public void headingChanged(Direction newHeading) {
		this.updateImage(newHeading);
		this.mainWindow.robotSays("I'm looking at direction " + newHeading);
	}
	/**
	 * Initialices the navigation module buttons
	 */
	@Override
	public void initNavigationModule(Direction heading, PlaceInfo initialPlace) {
		this.initCityMapButtons(initialPlace);
		
	}
	/**
	 * Changes the place
	 */
	@Override
	public void placeHasChanged(PlaceInfo placeDescription) {
		this.updateLog(placeDescription);
		
	}
	/**
	 * Scans the place
	 */
	@Override
	public void placeScanned(PlaceInfo placeDescription) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Updates the cityMapButtons
	 */
	@Override
	public void robotArrivesAtPlace(Direction heading, PlaceInfo place) {
		this.updateCityMapButtons(place, heading);
		
	}
	/**
	 * This method receives an arrayList, it's first position is a String with
	 * the word "rotate", "move", "init", "dropItem", "pickItemFromPlace", "raiseError" or
	 * "talk" telling what order it will be executed. Each instruction has it's parameter.
	 * "rotate" must have a valid direction
	 * "move" must have a Direction and a PlaceInfo
	 * "init" must have a Direction and two PlaceInfo
	 * "dropItem" must have a PlaceInfo
	 * "pickFromPlace" must have a PlaceInfo
	 * "raiseError" must have a string
	 * "talk" must have a string
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		ArrayList<Object> array = (ArrayList<Object>)arg1;
		switch((String) array.get(0)){
		case "rotate": this.headingChanged((Direction)array.get(1)); break;
		case "move":  this.robotArrivesAtPlace((Direction) array.get(1),(PlaceInfo) array.get(2)); 
					  this.updateLog((PlaceInfo) array.get(2)); break;
		case "updateLog": 
						this.updateLog((PlaceInfo) array.get(2)); break;
		case "init": 	this.initCityMapButtons((PlaceInfo) array.get(2)); break;
		case "dropItem": this.placeHasChanged((PlaceInfo) array.get(1)); break;
		case "pickFromPlace": this.placeHasChanged((PlaceInfo) array.get(1)); break;
		case "raiseError": mainWindow.raiseError((String) array.get(1)); break; 
		case "talk": this.mainWindow.robotSays((String) array.get(1)); break;
		
		}
		
		
	}
	
}
