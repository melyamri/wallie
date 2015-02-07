package tp.pr5;
/**
 * <p>An enumerated type that represents in which direction the 
 * robot rotates (left or right) plus a value that represents 
 * an unknown direction.</p>
 * 
 * @author Ismael Gonjal & Meriem ElYamri
 *
 */
public enum Rotation {
	LEFT, RIGHT, UNKNOWN;


public static  Rotation toRot(String s){
	Rotation rot = Rotation.UNKNOWN;
	if (s.equalsIgnoreCase("LEFT") ) rot = Rotation.LEFT;
	else rot = Rotation.RIGHT;
	
	return rot;
		
}

}