package tp.pr5.controller;

import java.awt.Component;
import java.awt.event.ActionEvent;

import tp.pr5.*;
import tp.pr5.instructions.*;
import java.awt.event.ActionListener;
import java.util.EventObject;

import tp.pr5.gui.*;

/**
 * This class is the controller of the interface of this program. It provides the
 * functionality to the complete application, the controller will work with the window "MainWindow"
 * and it will connect that class with RobotEngine, the nucleus of the game.  
 * 
 * @author Ismael Gonjal & Meriem El Yamri
 * @see MainWindow
 * @see Robotengine
 */
public class GUIController implements ActionListener{

	MainWindow mainWindow;
	tp.pr5.RobotEngine engine;
	
	/**
	 * Default constructor
	 */
	public GUIController(){
		this(null);
	}
	
	/**
	 * Constructor
	 * @param engine the RobotEngine model
	 */
	public GUIController(RobotEngine engine) {
		this.setModel(engine);
	}
	
	/**
	 * Sets the model in the controller so that it can make changes on it
	 * @param engine the model
	 */
	public void setModel(RobotEngine engine){
		this.engine = engine;
	}
	/**
	 * Adds the observers
	 */
	public void addObservers(){
		engine.addEngineObserver(mainWindow);
		engine.addItemContainerObserver(mainWindow.getRobotPanel());
		engine.addNavigationObserver(mainWindow.getNavigationPanel());
	}
	/**
	 * Sets the main window so that it can read some changes (The combobox and the textfield)
	 * @param mainWindow the main window
	 */
	public void setView(MainWindow mainWindow){
		this.mainWindow = mainWindow;
	}
	
	/**
	 * Gets the robot engine, the model
	 * @return the model
	 */
	public RobotEngine getEngine(){
		return this.engine;
	}
	/**
	 * Method that is in charge of changing the model depending on the event
	 * The view will update from the observers
	 * 
	 * @param source the one that requested a model change
	 */
	public void doChange(Component source){
		switch(source.getName()){
		case "move": 	engine.communicateRobot(new MoveInstruction()); break;
		case "quit":	System.exit(0); break;
		case "turn":	engine.communicateRobot(new TurnInstruction(mainWindow.getRotation())); break;
		case "pick":	engine.communicateRobot(new PickInstruction(mainWindow.getIdOfPickableItem()));	break;
		case "drop":	//If there is an item in the table selected, it drops it
					if(mainWindow.getRobotPanel().getTable().getSelectedRow()!= -1){
						String idDrop = (String) mainWindow.getRobotPanel().getTable().getValueAt(mainWindow.getRobotPanel().getTable().getSelectedRow(), 0);
						engine.communicateRobot(new DropInstruction(idDrop));
					}break;
		case "operate":	//If there is an item in the table selected, it operates it
					if(mainWindow.getRobotPanel().getTable().getSelectedRow()!= -1){
						String idOperate = (String) mainWindow.getRobotPanel().getTable().getValueAt(mainWindow.getRobotPanel().getTable().getSelectedRow(), 0);
						engine.communicateRobot(new OperateInstruction(idOperate));
					}break;
					
		}

	}
	
	/**
	 * Method in charge of treating the event in a generic way
	 * @param e the event
	 */
	public void treatGenericEvent(EventObject e){
		Component source = (Component) e.getSource(); // the one who generated the event
        doChange(source);
	}
	
	/**
	 * In this method the ActionEvents are treated, informing the model and the view when it concerns
	 * @param arg0 the Action event
	 */
	public void actionPerformed(ActionEvent arg0) {
		this.treatGenericEvent(arg0);
	}
	

}
