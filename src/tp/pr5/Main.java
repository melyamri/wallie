package tp.pr5;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import tp.pr5.cityLoader.*;
import tp.pr5.cityLoader.cityLoaderExceptions.WrongCityFormatException;
import tp.pr5.controller.ConsoleController;
import tp.pr5.controller.GUIController;
import tp.pr5.gui.*;
import tp.pr5.items.CodeCard;
import tp.pr5.items.Fuel;
import tp.pr5.items.Garbage;


/**
 * Initializes and launches the robot, 
 * it permits to choose between swing interface and console interface. the correct sequence is:
 * -h,--help    
 * -i,--interface <type>  (Being type "console" or "swing")
 * -m,--map <mapfile>     (Being mapfile the map route and name)
 * 
 * if the -h argument is used, it should not be used anyone more.
 * 
 * 
 * @author Ismael Gonjal & Meriem ElYamri
 * @y.exclude
 */
public class Main {
	//0 --> console, 1 --> swing 2-->both
	private static int isConsole;
	private static final int INTERFACE_CONSOLE = 0;
	private static final int INTERFACE_SWING = 1;
	private static final int INTERFACE_BOTH = 2;
	/**
	 * This method validates the correction of the arguments introduced into the main class
	 * @param args an argument array
	 * 
	 * @return a string with the map extracted from the arguments
	 */
	public static String validaArgs(String[] args){
		isConsole = 0;
		String fichero = "";
		
		if (args.length == 4){
			if(args[0].equals("-i") || args[0].equals("--interface")){
				if(args[1].equals("swing")){
					isConsole = Main.INTERFACE_SWING;
				}else if (args[1].equals("console")){
					isConsole = Main.INTERFACE_CONSOLE;
				}else if (args[1].equals("both")){
					isConsole = Main.INTERFACE_BOTH;
				}else{
					System.err.println("Wrong type of interface");
					System.exit(3);
				}
				if(!args[2].equals("-m") && !args[2].equals("--map")){
					System.err.println("Map file not specified");
					System.exit(1);
				}
				fichero = args[3];
			}else if(args[2].equals("-i") || args[2].equals("--interface")){
				if(args[3].equals("swing")){
					isConsole = Main.INTERFACE_SWING;
				}else if (args[3].equals("console")){
					isConsole = Main.INTERFACE_CONSOLE;
				}else if (args[3].equals("both")){
					isConsole = Main.INTERFACE_BOTH;
				}else{
					System.err.println("Wrong type of interface");
					System.exit(3);
				}
				if(!args[0].equals("-m") && !args[0].equals("--map")){
					System.err.println("Map file not specified");
					System.exit(1);
				}
				fichero = args[1];
			}
			
		}else if(args.length == 1){
			if(args[0].equals("-h") || args[0].equals("--help")){
				
				System.out.println("Execute this assignment with these parameters:\n" +
						"usage: tp.pr5.Main [-h] [-i <type>] [-m <mapfile>]\n" +
						" -h,--help               Shows this help message\n" +
						" -i,--interface <type>   The type of interface: console or swing\n" +
						" -m,--map <mapfile>      File with the description of the city");
				System.exit(0);
			}else{
				System.err.println("Interface not specified");
				System.exit(1);
			}
		}else if(args.length == 2){
			if(args[0].equals("-i") || args[0].equals("--interface"))
				System.err.println("Map file not specified");
			else if (args[0].equals("-m") || args[0].equals("--map"))
				System.err.println("Interface not specified");

			System.exit(1);
		}else{
			System.err.println("Map file not specified");
			System.exit(1);
		}
		return fichero;
	}
	/**
	 * Sets the observers and initialices the cityMapButtons
	 * @param engine
	 * @param mainWindow
	 * @param guiController
	 */
	private static void setObserversAndInitialice(RobotEngine engine, MainWindow mainWindow, GUIController guiController){
		//Sets the window in the controller so it can access to some parameters
		guiController.setView(mainWindow);
		
		//Adds the observer
		engine.addEngineObserver(mainWindow);
		engine.addEngineObserver(mainWindow.getRobotPanel());
		engine.addNavigationObserver(mainWindow.getNavigationPanel());
		engine.addItemContainerObserver(mainWindow.getRobotPanel());
		
		mainWindow.initCityMapButtons(engine.getModule().getCurrentPlace());
	}
	public static void main(String[] args)  {
		
	
		String fichero = validaArgs(args);
		CityLoaderFromTxtFile arch = new CityLoaderFromTxtFile();
		City ciudad;
		MainWindow mainWindow = null;

		try {
			FileInputStream file = new FileInputStream(fichero);
			ciudad = arch.loadCity(file);
			RobotEngine engine = new RobotEngine(ciudad, arch.getInitialPlace(),Direction.NORTH);
			
			
			
			if(isConsole == Main.INTERFACE_CONSOLE){
				ConsoleController consoleController = new ConsoleController(engine);
				engine.requestStart();
				consoleController.startEngine();
				
			} else if(isConsole == Main.INTERFACE_SWING){
				//swing
				GUIController guiController = new GUIController(engine);
				mainWindow = new MainWindow(guiController);
				
				setObserversAndInitialice(engine,mainWindow,guiController);
				

					//start
					engine.requestStart();

			}
			else if(isConsole == Main.INTERFACE_BOTH){
				GUIController guiController = new GUIController(engine);
				ConsoleController consoleController = new ConsoleController(engine);
				//both
				mainWindow = new MainWindow(guiController);
				
				setObserversAndInitialice(engine,mainWindow,guiController);
				//Sets the window in the controller so it can access to some parameters
				guiController.setView(mainWindow);
					
					//start
					engine.requestStart();
					//consoleController.startEngine();
			}
			
			
		} catch (WrongCityFormatException e) {
			System.err.println(e.getMessage());
			System.exit(2);
		} catch (FileNotFoundException e){
			//System.out.println(e.getMessage());
			System.err.println("Error reading the map file: " + fichero + " (No existe el fichero o el directorio)");
			System.exit(2);
		} finally {
			arch.closeFile();
		}
		
	}
	
}


