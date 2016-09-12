import javafx.scene.shape.Polygon;

/**
 * This class implements a cursor for use in linear menus.
 * @author Weston
 *
 */
public class MenuCursor extends Polygon {
	private static final double myWidth = Math.sqrt(3);
	private double mySize;
	private double mySpacing;
	private double myX;
	private double myY;
	private int myCurrPos;
	private int myTotalPositions;
	
	/**
	 * Makes a MenuCursor from a location, a height and width, the spacing between positions and the number of
	 * positions available.
	 * @param x
	 * @param y
	 * @param size
	 * @param spacing
	 * @param positions
	 */
	public MenuCursor(double x, double y, double size, double spacing, int positions){
		super(x, y, x, y + size, x + size * myWidth, y + size / 2);
		myX = x;
		myY = y;
		mySize = size;
		mySpacing = spacing;
		myTotalPositions = positions;
	}
	
	/**
	 * Moves the MenuCursor up i positions. Will wrap to the bottom.
	 * @param i
	 */
	public void moveUp(int i){
		while (i > 0){
			myCurrPos--;
			//Next line is myCurrPos = myCurrPos (mod myTotalPositions). Weird adding keeps it positive.
			myCurrPos = (myCurrPos + myTotalPositions) % myTotalPositions;
			i--;
		}
		remakePoly();

	}
	
	/**
	 * Moves the MenuCursor down i positions. Will wrap to the top.
	 * @param i
	 */
	public void moveDown(int i){
		while (i > 0){
			myCurrPos++;
			//Next line is myCurrPos = myCurrPos (mod myTotalPositions). Weird adding keeps it positive.
			myCurrPos = (myCurrPos + myTotalPositions) % myTotalPositions;
			i--;
		}
		remakePoly();
	}
	
	/**
	 * Sync the Image of the MenuCursor with the cursor's location.
	 */
	private void remakePoly(){
		super.getPoints().clear();
		double yCurr = myY + mySpacing * myCurrPos;
		super.getPoints().addAll(myX, yCurr, myX, yCurr + mySize, myX + mySize * myWidth, yCurr + mySize / 2);
	}
	
	/**
	 * @return cursor's current position in the menu.
	 */
	public int getPos(){
		return myCurrPos;
	}
	
}
