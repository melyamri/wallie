package tp.pr5.gui;


import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import tp.pr5.*;
import tp.pr5.controller.GUIController;
import tp.pr5.instructions.*;
/**
 * <p>It represents the main window of the game.It contains the robot panel, the instructions panel and the navigation panel.
 * It contains the action listeners and saves a RobotEngine reference to communicate and tell user actions. The class extends 
 * the java.awt.JFrame class.</p>
 * 
 * @author Ismael Gonjal y Meriem El Yamri
 * @see JFrame
 */
public class MainWindow extends JFrame implements RobotEngineObserver{

	//Windows size
	private final int windowX = 800;
	private final int windowY = 560;
	
	//Attributes
	private NavigationPanel navigationPanel;
	private RobotPanel robotPanel;
	private JPanel instructions;
	
	RobotEngineObserver engineObserver;
	
	//The combobox which contains the selected item
	private JComboBox<Rotation> rotationComboBox;
	//the JTextField which contains the id introduced
	private JTextField idTextField;
	
	
	//Top menu
	JMenuBar menu;
	//Top menu quit item
	private JMenuItem quitItem;
		
	
	//Constructor
	/**
	 * Creates an initialices the values of the main window, such as size, defaultCloseOperation, etc
	 * @param engine a valid robotEngine
	 */
	public MainWindow(GUIController controller){
		//Gets the screen dimension
		
		Dimension dim =super.getToolkit().getScreenSize(); 
		//Sets the window in the center of the screen
		this.setBounds((dim.width - windowX)/2, (dim.height - windowY)/2, windowX, windowY); 
		this.setVisible(true); 
		//The window can NOT be resized
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Border layout
		this.setLayout(new BorderLayout());
		
		// Creates the menu with the option "File" and menu Item "Quit"
		createMenu();
		
		//Panel that contains the instructions panel and the robot panel
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new BorderLayout());
		//Adds the northPanel in the NORTH part of the layout
		this.getContentPane().add(northPanel, BorderLayout.NORTH);
		
		
		//creates the instructions panel
		createInstructionsPanel(northPanel);
		
		//creates the robot panel
		robotPanel = new RobotPanel(this, northPanel);
		//robotPanel.updateRobotStateLabel(engineObserver);
		//creates the navigation panel
		navigationPanel = new NavigationPanel(this);
		
		//Sets the controller
		this.fixController(controller);
		
	}
	
	/**
	 * Gets the Navigation Panel	
	 * @return the Navigation Panel
	 */
	public NavigationPanel getNavigationPanel(){
		return this.navigationPanel;
	}
	/**
	 * <p> Creates the Menu "File" that contains the menu item "Quit" on the top of the window.</p>
	 */
	private void createMenu(){
		menu = new JMenuBar();
		this.setJMenuBar(menu);
		JMenu fileMenu = new JMenu("File");
		menu.add(fileMenu);
		quitItem = new JMenuItem("Quit");
		quitItem.setName("quit");
		//quitItem.setActionCommand("quit");
		//quitItem.addActionListener(this);
		fileMenu.add(quitItem);
		
		
	}
	/**
	 * <p>Creates the instructions panel, it contains buttons with the instructions: MOVE, OPERATE, QUIT, PICK and DROP.
	 * It also contains a combo box to select LEFT or RIGHT direction to turn, and a text area to write the id item to operate
	 * with. The instructions DROP and OPERATE will use the item that is selected in the robot inventory, which is shown in the 
	 * robot panel</p>
	 * 
	 * @param container
	 */
	private void createInstructionsPanel(Container container){
		
		instructions = new JPanel();
		//Grid layout
		instructions.setLayout(new GridLayout(4,2));
		//Border
		instructions.setBorder(new TitledBorder("Instructions"));
		//The instructions panel is added in the WEST part of the layout
		container.add(instructions, BorderLayout.WEST);
		
		//MOVE button
		JButton moveButton = new JButton("MOVE");
		//moveButton.setActionCommand("move");
		//moveButton.addActionListener(this);
		moveButton.setName("move");
		instructions.add(moveButton);
		
		//QUIT button
		JButton quitButton = new JButton("QUIT");
		quitButton.setName("quit");
		//quitButton.setActionCommand("quit");
		//quitButton.addActionListener(this);
		instructions.add(quitButton);
		
		//TURN button
		JButton turnButton = new JButton("TURN");
		turnButton.setName("turn");
		//turnButton.setActionCommand("turn");
		//turnButton.addActionListener(this);
		instructions.add(turnButton);
		
		//LEFT|RIGHT combo box
		JComboBox<Rotation> directionBox = new JComboBox<Rotation>();
		
		instructions.add(directionBox);
		
			directionBox.addItem(Rotation.LEFT);
			directionBox.addItem(Rotation.RIGHT);
		this.rotationComboBox = directionBox;
		//PICK button
		JButton pickButton = new JButton("PICK");
		pickButton.setName("pick");
		//pickButton.setActionCommand("pick");
		//pickButton.addActionListener(this);
		instructions.add(pickButton);
		
		//Item id text field
		JTextField idTextField = new JTextField();
		this.idTextField = idTextField;
		instructions.add(idTextField);
		
		//DROP button
		JButton dropButton = new JButton("DROP");
		dropButton.setName("drop");
		//dropButton.setActionCommand("drop");
		//dropButton.addActionListener(this);
		instructions.add(dropButton);
		
		//OPERATE button
		JButton operateButton = new JButton("OPERATE");
		operateButton.setName("operate");
		//operateButton.setActionCommand("operate");
		//operateButton.addActionListener(this);
		instructions.add(operateButton);
		
	}
	

	/**
	 * This method will return the direction selected into the comboBox 
	 * @return a valid direction
	 */
	public Rotation getRotation(){
		return  Rotation.toRot(this.rotationComboBox.getSelectedItem().toString());
	}
	
	/**
	 * This method will return the text introduced into the JTextArea prepared
	 * for introduce the id, if nothing was introduced, the returning string will
	 * be null. 
	 * 
	 * @return the text introduced into the JTextArea
	 */
	public String getIdOfPickableItem(){
		return this.idTextField.getText();
	}

	public void fixController(EventListener controller) {
		//JMenuBar item
		quitItem.addActionListener((ActionListener) controller);
		//MOVE
		((JButton) instructions.getComponent(0)).addActionListener((ActionListener) controller);
		//QUIT
		((JButton) instructions.getComponent(1)).addActionListener((ActionListener) controller);
		//TURN
		((JButton) instructions.getComponent(2)).addActionListener((ActionListener) controller);
		//PICK
		((JButton) instructions.getComponent(4)).addActionListener((ActionListener) controller);
		//DROP
		((JButton) instructions.getComponent(6)).addActionListener((ActionListener) controller);
		//OPERATE
		((JButton) instructions.getComponent(7)).addActionListener((ActionListener) controller);
	}

	/**
	 * Gets the robotPanel associated with the main window
	 * @return the robot panel
	 */
	public RobotPanel getRobotPanel() {
		return robotPanel;
	}
	/**
	 * This method sets the communication as completed, and finishes the 
	 * execution
	 */
	@Override
	public void communicationCompleted() {
		System.exit(0);
		
	}
	/**
	 * This method does nothing, because of in this short of interface
	 * the "HELP"command does not exist
	 */
	@Override
	public void communicationHelp(String help) {
		
	}
	/**
	 * The engine is over, so the robot says bye bye and closes the execution
	 *  if the boolean received is true. It does nothing otherwise
	 */
	@Override
	public void engineOff(boolean atShip) {
		if (atShip) {
			this.robotSays("I am at my space ship. Bye Bye");
			System.exit(0);
		}
		
	}
	/**
	 * this method raises a JOptionPane with the message received
	 * 
	 */
	@Override
	public void raiseError(String msg) {
		//JOptionPane.showMessageDialog(null, msg);
		
	}
	/**
	 * This method updates the label of the wall-e Words
	 */
	@Override
	public void robotSays(String message) {
		
		getNavigationPanel().getLabel().setText(message);
		
	}
	/**
	 * This method updates the fuel and recycledMaterial of the robot
	 */
	@Override
	public void robotUpdate(int fuel, int recycledMaterial) {
		//this.robotSays("Robot attributes has been updated: " + fuel + ", " + recycledMaterial);
		robotPanel.updateRobotStateLabel(fuel, recycledMaterial);
		if(fuel <= 0){
			this.robotSays("I ran out of fuel. I cannot move. Shutting down...");
			this.communicationCompleted();
		}
		
	}
	/**
	 * this method updates the state of the robot receiving an arrayList which first element
	 * must be an String with the words 
	 * "quit" without other params in the ArrayList 
	 * "checkEnd" without other params in the ArrayList
	 * "raiseError"  with another param being an string
	 * "talk" with another param being an String
	 */
	@Override
	public void update(Observable o, Object arg) {
		ArrayList<Object> array = (ArrayList<Object>)arg;
		switch((String) array.get(0)){
		case "quit": this.communicationCompleted(); break;
		case "checkEnd": this.engineOff(((PlaceInfo) array.get(1)).isSpaceship()); break;
		case "raiseError": this.raiseError((String) array.get(1)); break;
		case "talk": this.robotSays((String) array.get(1)); break;
		}
		
	}
	/**
	 * Initialices the NAvigationModule city Map buttons
	 * @param place
	 */
	public void initCityMapButtons(PlaceInfo place){
		this.navigationPanel.initCityMapButtons(place);
	}
	
	
}
