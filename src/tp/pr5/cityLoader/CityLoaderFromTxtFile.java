package tp.pr5.cityLoader;

import java.io.EOFException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import tp.pr5.City;
import tp.pr5.Direction;
import tp.pr5.Place;
import tp.pr5.Street;
import tp.pr5.cityLoader.cityLoaderExceptions.WrongCityFormatException;
import tp.pr5.items.CodeCard;
import tp.pr5.items.Fuel;
import tp.pr5.items.Garbage;
import tp.pr5.items.Item;
/**
 * City loader from a txt file The mandatory format must be:
 
 BeginCity
 BeginPlaces
 place 0 Entrada Estamos_en_la_entrada._Comienza_la_aventura noSpaceShip
 place 1 Callao In_this_square... spaceShip
 ...
 EndPlaces
 BeginStreets
 street 0 place 0 south place 1 open
 street 1 place 1 east place 2 open
 street 2 place 2 north place 3 closed onetwothreefourfive
 ...
 EndStreets
 BeginItems
 fuel 0 Petrol from_olds_heatings 10 3 place 0
 fuel 1 Battery to_get_cracking -50 1 place 0
 codecard 2 Card The_key_is_too_easy onetwothreefourfive place 1
 garbage 3 Newspapers News_on_sport 30 place 2
 ...
 EndItems
 EndCity
 * 
 * 
 * @author Ismael Gonjal & Meriem ElYamri
 *
 */
public class CityLoaderFromTxtFile {
	//Variables
		Place places[];
		Street calles[];
		City ciudad;
		Scanner archivo;
		
		//constructor
		public CityLoaderFromTxtFile(){
			this.ciudad = new City();
			this.calles = new Street[0];
			this.places = new Place[0];
			
		}
		/**
		 * Gets the initial place in the city Map
		 * @return the initial place
		 */
		public Place getInitialPlace(){
			return this.places[0];
		}
		/**
		 * <p>Reads a line from the file content in scanner and returns an array of strings, where
		 * the splitting character is the white space or the character "_" if it existed.</p>
		 * <p>ATTENTION, if the line has both characters, " " and "_", only would be replaced the " "</p>
		 * 
		 * @param archivo the file to read
		 * @return A array of strings.  
		 */
		private String[] readLine(Scanner archivo) throws WrongCityFormatException{
			String[] ret;
			if(archivo.hasNextLine()){
				String aux = archivo.nextLine();
				aux = aux.replace("_", " ");
				ret = aux.split(" ");
			}
			else{
				throw new WrongCityFormatException("There is no next line");
			}
	
			return ret;
			
		}
		/** This method creates the places reading them from a file and saves them in the local array places[]
		 * 
		 * @param archivo A scanner object 
		 * @throws WrongCityFormatException
		 * @throws java.io.IOException
		 * @throws EOFException
		 */
		private void createPlaces(Scanner archivo) throws WrongCityFormatException{
			String[] linea;
			List<Place> placesList = new ArrayList<Place>(); 
			int i = 0;
			boolean isSpaceShip;
			//if the file has next, the line is read, else it throws an exception
			if (archivo.hasNext()) linea = this.readLine(archivo);
			else throw new WrongCityFormatException("Wrong file format.");
			//While the line read is not "EndPlaces" and only that 
			while(!linea[0].equals("EndPlaces") && linea.length != 1){
				//If the line has the proper format
				if(linea[0].equals("place") &&
				   linea[1].equals(new Integer(i).toString()) && 
				   (linea[linea.length-1].equals("spaceShip")|| linea[linea.length - 1].equals("noSpaceShip"))){
					if(linea[linea.length-1].equals("spaceShip"))isSpaceShip = true;
					else isSpaceShip = false;
					
					String aux = "";
					//Concats the description using only one String
					for (int j = 3;j < linea.length -2; j++)
						aux = aux + linea[j] + " ";
					aux = aux + linea[linea.length - 2];
					//Adds the place read into the arraylist
					placesList.add(new Place(linea[2],isSpaceShip, aux));
					
				} else throw new WrongCityFormatException("Error in the places format, place nº" + i);
				
				//Reads the next line
				linea = this.readLine(archivo);
				i++;
			}
			//saves the places
			this.places = placesList.toArray(this.places);
			
		}
		
		/**
		 * <p>Creates the streets. This method needs the places would be currently created</p>
		 * 
		 * @param archivo A scanner object with a valid  file currently open
		 * @throws WrongCityFormatException
		 * @throws java.io.IOException
		 * @throws EOFException
		 */
		private void createStreets(Scanner archivo) throws WrongCityFormatException{
			Direction direccion = Direction.UNKNOWN;
	    	//Place[] sourTarg = new Place[2];
	    	List<Street> streetList = new ArrayList<Street>(); 
	    	String [] linea = this.readLine(archivo);
	    	int i = 0;
	    	
	    	while(!linea[0].equals("EndStreets") && linea.length >= 8){
	    		
	    		if(linea[0].equals("street") &&
	    			linea[1].equals(new Integer(i).toString()) &&
	    			linea[2].equals("place") &&
	    			linea[3].matches("\\d*")&&
	    			linea[5].equals("place") &&
	    			linea[6].matches("\\d*") && // "\\d*" means a numeric character
	    			(linea.length == 8 || linea.length == 9)){
	    		
	    			
	    			//The pointing direction is saved
					direccion = Direction.fromString(linea[4]);
					//default:
						if (direccion.equals(Direction.UNKNOWN))
							throw new WrongCityFormatException("error in the direction of the street: " + i);
				
	    			
	    			int aux[] = {new Integer(linea[3]), new Integer(linea[6])};
	    			//The numbers of the places exist in the array of places
	    			if(this.places.length > aux[0] && this.places.length > aux[1]){
	    				//the street is open
	    				if(linea.length == 8 && linea[7].equals("open"))
	    					streetList.add(new Street(this.places[aux[0]],direccion,this.places[aux[1]]));
	    				//the street is closed
	    				else if (linea.length == 9 && linea[7].equals("closed"))
	    					streetList.add(new Street(this.places[aux[0]],direccion,this.places[aux[1]], false,linea[8]));
	    				//the street is open but has a code or is closed and it hasn't one
	    				else throw new WrongCityFormatException("Error in the street " + i + 
	    						", the data that tells if the street is open or closed is not well declared");
	    			//There are no such places
	    			}else throw new WrongCityFormatException("Error in the street " + i
	    					+", there are no place " + aux[0] + " or " + aux[1]);
	    			
	    				
	    		}else throw new WrongCityFormatException("Format error in streets, the fail is with the number " + i);
	    		
	    		
	    		i++;
	    		linea = this.readLine(archivo);
	    	}
	    	
	    	this.calles = streetList.toArray(this.calles);
		}
	/**
	 * <p>Creates the items and adds them into the streets</p>
	 * 
	 * @param archivo
	 * @throws WrongCityFormatException
	 * @throws EOFException
	 */
	    private void createItems(Scanner archivo) throws WrongCityFormatException{
	    	int i = 0;
	    	//ArrayList<Item> itemList = new ArrayList<Item>(); 
	    	String [] linea = this.readLine(archivo);
	    	int indexPlace = 0;
	    	while(!linea[0].equals("EndStreets") && linea.length != 1){
	    		
	    		//Checks the syntax of the line
	    		if(linea[1].equals(new Integer(i).toString()) &&
	    				linea[linea.length - 2].equals("place") &&
	    				linea[linea.length -1].matches("\\d*")){
	    			
	    			//picks the index of the place and chechs if it exist in the array
	    			indexPlace = new Integer(linea[linea.length-1]);
	    			if (indexPlace > this.places.length -1) throw new WrongCityFormatException("Error, a place does not exist for the item number " + i);
	    			
	    			String description = "";
	    			
	    			// Here a WrongNumberFormat could happen because of the porer can be negative and does not adapt at the \\d* wildcard character
	    			if(linea[0].equals("fuel") && //linea[linea.length -4].matches("\\d*") && 
	    					linea[linea.length -3].matches("\\d*")){
	    				for (int j = 2; j< linea.length - 4; j++){
	    					description += linea[j] + " ";
	    				}
	    				description = description.substring(0, description.length() - 1);
	    				//String id, String name, int power, int times
	    				this.places[indexPlace].addItem(new Fuel(linea[2], description, new Integer(linea[linea.length-4]), new Integer(linea[linea.length-3])));
	    				
	    			} else if (linea[0].equals("codecard")) {
	    				for (int j = 2; j< linea.length - 2; j++){
	    					description += linea[j] + " ";
	    				}
	    				description = description.substring(0, description.length() - 1);
	    				//String id, String description, String code
	    				this.places[indexPlace].addItem(new CodeCard(linea[2], description ,linea[linea.length-3]));
	    				
	    			} else if (linea[0].equals("garbage") && linea[linea.length -3].matches("\\d*")) {
	    				for (int j = 2; j< linea.length - 3; j++){
	    					description += linea[j] + " ";
	    				}
	    				description = description.substring(0, description.length() - 1);
	    				//String id, String description,int recycledMaterial
	    				this.places[indexPlace].addItem(new Garbage(linea[2], description, new Integer(linea[linea.length -3])));
	    
	    			} else throw new WrongCityFormatException("Error, the type of item does not exist or it's parameters arent right. item #" + i);
	    			
	    		}else throw new WrongCityFormatException("Error in the syntax of the Item " + i);

	    		linea = this.readLine(archivo);
	    		i++;
	    	}
	    }
		
		
	    /**
	     * Loads the city receiving an InputStream file
	     * 
	     * @param file An input stream
	     * @return A city if the syntax of the file is correct, it will throw a WrongCityFormatException Otherwise
	     * @throws WrongCityFormatException
	     */
		public City loadCity (java.io.InputStream file) throws WrongCityFormatException{
			//This code must be documented and improved, it will be done in the next code update
			archivo = new Scanner(file);
			//try {
	  		String linea = "";

		  	if (!archivo.hasNextLine()) 
		  		throw new WrongCityFormatException("Error, archivo vacío");
		  	linea = archivo.nextLine();
		  		
		  	if(!linea.equals("BeginCity")) 
		  		throw new WrongCityFormatException("Error, falta la línea BeginCity");
		  		
		  	if (!archivo.hasNextLine()) 
		  		throw new WrongCityFormatException("Error,fin del archivo inesperado");
		  	linea = archivo.nextLine();
		  		
		  	if (!linea.equals("BeginPlaces"))
		  		throw new WrongCityFormatException("Error, falta la línea Begin Places");
		  	this.createPlaces(archivo);
		  		
		  	if(!archivo.hasNextLine())
		  		throw new WrongCityFormatException("Error,fin del archivo inesperado");
		  	linea = archivo.nextLine();
		  		
		  	if (!linea.equals("BeginStreets"))
		  		throw new WrongCityFormatException("Error, falta la línea BeginStreets");						
			this.createStreets(archivo);
										
		  	if(!archivo.hasNextLine())
		  		throw new WrongCityFormatException("Error, fin del archivo inesperado");
		  	linea = archivo.nextLine();
		  		
		  	if (!linea.equals("BeginItems"))
		  		throw new WrongCityFormatException("Error, No aparece BeginItems");
		  	this.createItems(archivo);
		  		
		  	if(!archivo.hasNextLine())
		  		throw new WrongCityFormatException("Error, fin del archivo inesperado");
		  	linea = archivo.nextLine();
		  		
		  	//if (!linea.equals("EndCity")) throw new WrongCityFormatException("Error, falta la línea EndCity");  

		  		
		  	this.ciudad = new City(this.calles);
	  	
		  	return ciudad;
		}
		
		/**
		 * Closes the file from where it reads the city map. This method avoid losing information in the text file.
		 */
		public void closeFile(){
			archivo.close();
		}
}
