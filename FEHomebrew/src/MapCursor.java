import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MapCursor extends ImageView{
	private Point myLocation;
	private int myMapSize;
	private int myMapWidth;
	private int myMapHeight;
	
	public MapCursor(int width, int height, int mapSize, Image image){
		super(image);
		myLocation = new Point(0, 0);
		myMapWidth = width;
		myMapHeight = height;
		myMapSize = mapSize;
		
		setFitHeight(myMapHeight / myMapSize);
		setFitWidth(myMapWidth / myMapSize);
		
		updateLoc();
	}
	
	private void updateLoc(){
		myLocation.putInside(myMapSize);
		setX(myLocation.getX() * myMapHeight / myMapSize);
		setY(myLocation.getY() * myMapWidth / myMapSize);
	}
	
	public void right(){
		myLocation = myLocation.right();
		updateLoc();
	}
	public void left(){
		myLocation = myLocation.left();
		updateLoc();
	}
	public void up(){
		myLocation = myLocation.down();
		updateLoc();
	}
	public void down(){
		myLocation = myLocation.up();
		updateLoc();
	}
	
	public Point getLocation(){
		return myLocation;
	}
}
