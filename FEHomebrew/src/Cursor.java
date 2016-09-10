import javafx.scene.shape.Polygon;

public class Cursor extends Polygon {
	private static final double myWidth = Math.sqrt(3);
	private double mySize;
	private double mySpacing;
	private double myX;
	private double myY;
	private int myCurrPos;
	private int myTotalPositions;
	
	public Cursor(double x, double y, double size, double spacing, int positions){
		super(x, y, x, y + size, x + size * myWidth, y + size / 2);
		myX = x;
		myY = y;
		mySize = size;
		mySpacing = spacing;
		myTotalPositions = positions;
	}
	
	public void moveUp(int i){
		while (i > 0){
			myCurrPos--;
			System.out.println(myCurrPos);
			myCurrPos = (myCurrPos + myTotalPositions) % myTotalPositions;
			System.out.println(myCurrPos);
			i--;
		}
		remakePoly();

	}
	
	public void moveDown(int i){
		while (i > 0){
			myCurrPos++;
			System.out.println(myCurrPos);
			myCurrPos = (myCurrPos + myTotalPositions) % myTotalPositions;
			System.out.println(myCurrPos);
			i--;
		}
		remakePoly();
	}
	
	public void remakePoly(){
		super.getPoints().clear();
		double yCurr = myY + mySpacing * myCurrPos;
		super.getPoints().addAll(myX, yCurr, myX, yCurr + mySize, myX + mySize * myWidth, yCurr + mySize / 2);
	}
	
	public int getPos(){
		return myCurrPos;
	}
	
}
