package tp.pr5;
/**
 * <p>An enumerated type that represents the compass directions 
 * (north, east, south and west) plus a value that represents 
 * an unknown direction.</p>
 * 
 * @author Ismael Gonjal & Meriem El Yamri
 *
 */
public enum Direction {
	NORTH, SOUTH, EAST, WEST, UNKNOWN;

	/**
	 * <p>Return the opposite direction in the compass</p>
	 * 
	 * @param dir A Direction from the Enum
	 * @return the opposite direction of the given one.
	 */
	public static Direction opposite(Direction dir){
		Direction ret;
		 switch(dir){
		 	case NORTH: ret = SOUTH;break;
		 	case SOUTH: ret = NORTH;break;
		 	case EAST:  ret = WEST;break;
		 	case WEST:  ret = EAST;break;
		 	default:	ret = UNKNOWN;
		 }
		 return ret;
	}
	/**
	 * <p>This methods receives a direction and a rotation. If the rotation or the direction are its
	 * UNKNOWN value, the return will be unknown</p>
	 * 
	 * @param rot A Left, right or unknown rotation
	 * @param dir A North, south, east, west or unknown parameter
	 * @return The new direction after the rotation if direction != UNKNOWN. Otherwise, UNKNOWN
	 */
	public static Direction rotate(Rotation rot, Direction dir){
		Direction fin = UNKNOWN;
		// always rotates at left
		if(dir.equals(Direction.EAST)){
			fin = Direction.NORTH;
		}else if(dir.equals(Direction.WEST)){
			fin = Direction.SOUTH;
		}else if(dir.equals(Direction.NORTH)){
			fin = Direction.WEST;
		}else if(dir.equals(Direction.SOUTH)){
			fin = Direction.EAST;
		}
		// if the rotation goes right the direction is the opposite, this implementation saves lines of code
		if (rot.equals(Rotation.RIGHT)){
			fin = opposite(fin);
		}else if(rot.equals(Rotation.UNKNOWN)){
			fin = Direction.UNKNOWN;
		}
		return fin;
	}
	/**
	 * Converts a string into a Direction, the case difference doesn't mind, 
	 * SOUTH south and SoUth are considered as the same string and will return a valid direction
	 * 
	 * @param line the string
	 * @return A direction coinciding with the introduced into the line, UNKNOWN if the string has no match.
	 */
	public static Direction fromString(String line){
		Direction direccion = Direction.UNKNOWN;
		switch (line.toLowerCase()){
			case "north":
				direccion = Direction.NORTH;
			break;
			case "south":
				direccion = Direction.SOUTH;
			break;
			case "east":
				direccion = Direction.EAST;
			break;
			case "west":
				direccion = Direction.WEST;
			break;	
		}
		return direccion;
	}
}
