package tp.pr5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import tp.pr5.Street;

/**
 * <p>This class represents the city where the robot is wandering. It contains
 *  information about the streets and the places in the city</p>
 * 
 * @author Ismael Gonjal & Meriem ElYamri
 * @see Street
 */
public class City {
	// Attributes
	private List<Street> streetList;
	
	// Constructors
	/**
	 * Default constructor. Needed for testing purposes
	 */
	public City (){
		this.streetList = new ArrayList<Street>();

	}
	
	/**
	 * Creates a city using an array of streets
	 * 
	 * @param streets the streets which belongs to the city
	 */
	public City (Street streets[]){
		this.streetList = new ArrayList<Street>(Arrays.asList(streets));
	}
	
	// Methods
	/**
	 * <p>Adds an street to the city</p>
	 * 
	 * @param street the street to add
	 */
	public void addStreet(Street street){
		this.streetList.add(street);
	}
	
	
	/**
	 * <p>Looks for the street that starts from the given place in the given direction.</p>
	 * 
	 * @param currentPlace The place where to look for the street
	 * @param currentHeading The direction to look for the street
	 * @return the street that stars from the given place in the given direction. 
	 * It returns null if there is not any street in this direction from the given 
	 * place
	 */
	public Street lookForStreet(Place currentPlace,  Direction currentHeading){
		
		Street calle = null;
		Street calleAux = null;
		Iterator<Street> itr = this.streetList.iterator();
		
		//While the array has not been fully seek and the street hasn't been found
		while(itr.hasNext() && calle == null){
			calleAux = itr.next();
			if(calleAux.comeOutFrom(currentPlace, currentHeading)){
				calle = calleAux;
			}
		}

		return calle;
	}


	

}
