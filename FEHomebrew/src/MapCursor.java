import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class is used to keep track of cursor placement on a grid and display a cursor image.
 * @author Weston
 *
 */
public class MapCursor extends ImageView{
	private Point myLocation;
	private int myMapSize;
	private int myMapWidth;
	private int myMapHeight;
	
	/**
	 * Makes a MapCursor as big as one grid square. (height / myMapSize by width / mapSize
	 * @param width
	 * @param height
	 * @param mapSize
	 */
	public MapCursor(int width, int height, int mapSize){
		super();
		setImage(new Image(getClass().getClassLoader().getResourceAsStream("MapCursor.png")));
		myLocation = new Point(0, 0);
		myMapWidth = width;
		myMapHeight = height;
		myMapSize = mapSize;
		
		setFitHeight(myMapHeight / myMapSize);
		setFitWidth(myMapWidth / myMapSize);
		
		updateLoc();
	}

	/**
	 * Moves the MapCursor one space right.
	 */
	public void right(){
		myLocation = myLocation.right();
		updateLoc();
	}
	/**
	 * Moves the MapCursor one space left.
	 */
	public void left(){
		myLocation = myLocation.left();
		updateLoc();
	}
	/**
	 * Moves the MapCursor one space up.
	 */
	public void up(){
		myLocation = myLocation.down();
		updateLoc();
	}
	/**
	 * Moves the MapCursor one space down.
	 */
	public void down(){
		myLocation = myLocation.up();
		updateLoc();
	}
	
	/**
	 * Send the MapCursor to the grid square at Point p.
	 * @param p
	 */
	public void setLocation(Point p){
		myLocation = p;
		updateLoc();
	}
	
	/**
	 * @return MapCursor's current location
	 */
	public Point getLocation(){
		return myLocation;
	}
	
	/**
	 * Moves the image of the MapCursor to reflect any change in the MapCursor's location.
	 */
	private void updateLoc(){
		myLocation.putInside(myMapSize);
		setX(myLocation.getX() * myMapHeight / myMapSize);
		setY(myLocation.getY() * myMapWidth / myMapSize);
	}
}
