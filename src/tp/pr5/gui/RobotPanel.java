package tp.pr5.gui;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.Observable;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import tp.pr5.*;
import tp.pr5.controller.GUIController;
import tp.pr5.items.InventoryObserver;
import tp.pr5.items.Item;
import tp.pr5.items.ItemContainer;
/**
 * This class extends JPanel. It will represent the robot state, having information like the quantity of fuel or garbage that it
 * currently has. It also has the information about the items which Wall-e has.
 * 
 * @author Ismael Gonjal y Meriem El Yamri
 *
 */
public class RobotPanel extends JPanel implements RobotEngineObserver, InventoryObserver{
	
	private JLabel robotStateLabel;
	private JTable inventoryTable;
	private MainWindow mainWindow;
	/**
	 * <p>Panel that contains the robot state, it shows the current fuel and garbage. It also shows the item that is in use and its 
	 * description </p>
	 * @param container the MainWindow that contains the panel
	 */
	public RobotPanel(MainWindow mw, Container container){
		container.add(this); //Sets the MainWindow container
		mainWindow = mw;
		//Robot panel size
		this.setPreferredSize(new Dimension(550, 100));
		
		//Grid Layout
		this.setLayout(new FlowLayout());
		//Border
		this.setBorder(new TitledBorder("Robot info"));
		
		//Label with the robot state: fuel and garbage
		robotStateLabel = new JLabel("Fuel:  Recycled: ");
		//Alignment
		robotStateLabel.setHorizontalAlignment((int) JLabel.CENTER_ALIGNMENT);
		robotStateLabel.setPreferredSize(new Dimension(550, 24));
		this.add(robotStateLabel);
		
		//Table that contains the item id and its description
		inventoryTable = new JTable();
		
		inventoryTable.setPreferredSize(new Dimension (600, 16));
		inventoryTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		//inventoryTable.set
		
		//Setting table model
		inventoryTable.setModel(new DefaultTableModel());
		//Setting number of columns: 2
		((DefaultTableModel)inventoryTable.getModel()).setColumnCount(2);
		//Setting number of rows: 2
		((DefaultTableModel)inventoryTable.getModel()).setRowCount(0);
		//inventoryTable.setBackground(this.getBackground());
		
		
		//Values of permanent fields in Table: "Id" at first field, and "Description" at second field
		((DefaultTableModel)inventoryTable.getModel()).setColumnIdentifiers(new String[] {"Id", "Description"});
		//New scrollable panel to make the table scrollable
		JScrollPane pane = new JScrollPane(inventoryTable);
		pane.setPreferredSize(new Dimension (600, 71));
		//No horizontal scroll
		pane.setHorizontalScrollBar(null);
		this.add(pane);
		
	}
	/**
	 * Gets the table that contains the inventory
	 * @return a Jtable
	 */
	public JTable getTable(){
		return this.inventoryTable;
	}
	/**
	 * <p>Updates the robot fuel and recycled material</p>
	 * 
	 * @param robotEngine the robot engine
	 */
	public void updateRobotStateLabel(int fuel, int recycledMaterial){
		robotStateLabel.setText("Fuel: " + fuel + " Recycled: " + recycledMaterial);
		
	}

	/**
	 * Erases and updates the inventoryTable from the beggining for the UNDO action
	 * @param inventory the robot inventory
	 */
	public void updateInventoryTable(List<Item> inventory){

		//Sets row count
		((DefaultTableModel)inventoryTable.getModel()).setRowCount(inventory.size());
		//Size
		inventoryTable.setPreferredSize(new Dimension(600, (inventory.size())*16));
		//Inserts the items
		for(int i = 0; i < inventory.size(); i++) {
			inventoryTable.getModel().setValueAt(inventory.get(i).getId(), i, 0);
			inventoryTable.getModel().setValueAt(inventory.get(i).toString(), i, 1);
		}
	}
	/**
	 * This method updatess the inventory with an inventory
	 */
	@Override
	public void inventoryChange(List<Item> inventory) {
		this.updateInventoryTable(inventory);
	}
	/**
	 * This method should show the inventory description but it does nothing 
	 * into the swing interface
	 */
	@Override
	public void inventoryScanned(String inventoryDescription) {
		
	}
	/**
	 * This method does nothing in the swing interface
	 */
	@Override
	public void itemEmpty(String itemName) {
		
	}
	/**
	 * This method show the inventory description, 
	 */
	@Override
	public void itemScanned(String description) {
		
	}
	/**
	 * This method gets out of the application
	 */
	@Override
	public void communicationCompleted() {
		System.exit(0);
		
	}
	/**
	 * This method shows the help command
	 */
	@Override
	public void communicationHelp(String help) {
		
	}
	/**
	 * This method gets out if the ship is at the spaceship. That is determinated by 
	 * the input parameter
	 */
	@Override
	public void engineOff(boolean atShip) {
		if (atShip) {
			System.exit(0);
			this.robotSays("I am at my space ship. Bye Bye");
		}
		
	}
	/**
	 * raises a JOptionPane with the error
	 */
	@Override
	public void raiseError(String msg) {
		this.mainWindow.raiseError(msg);
		//JOptionPane.showMessageDialog(null, msg);
	}
	/**
	 * Updates the label that represents the phrases that the robot tells 
	 */
	@Override
	public void robotSays(String message) {
		this.mainWindow.robotSays(message);
		//JOptionPane.showMessageDialog(null, message);
		
	}
	/**
	 * This method updates the fuel and the recycledMaterial of the
	 * robot. 
	 */
	@Override
	public void robotUpdate(int fuel, int recycledMaterial) {
		this.updateRobotStateLabel(fuel, recycledMaterial);
		if(fuel <= 0){
			this.robotSays("I ran out of fuel. I cannot move. Shutting down...");
			this.communicationCompleted();
		}
		
	}
	/**
	 * This method receives an ArrayList containing in first place a String
	 * with the instruction which has been executed, it must be:
	 * 
	 * "fuel" and another two parameters being integers
	 * "recycledMaterial" with two parameters being integers
	 * "pickItem" with a list with the items into the inventory
	 * "addItem" with a list with the items into the inventory
	 * "raiseError" with a string
	 * "talk" with a string
	 */
	@Override
	public void update(Observable o, Object arg) {
		ArrayList<Object> array = (ArrayList<Object>) arg;
		switch((String) array.get(0)){
		case "fuel": this.robotUpdate((Integer)array.get(1), (Integer)array.get(2)); break;
		case "recycledMaterial": this.robotUpdate((Integer)array.get(1), (Integer)array.get(2)); break;
		case "pickItem": this.inventoryChange((List<Item>) array.get(1)); break;
		case "addItem": this.inventoryChange((List<Item>)array.get(1)); break;
		case "raiseError":  this.mainWindow.raiseError((String) array.get(1));
		case "talk": this.mainWindow.robotSays((String) array.get(1));
		}
		
	}
		
	
}
