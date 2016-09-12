import javafx.scene.image.Image;

/**
 * This class represents the terrain on one grid square.
 * @author Weston
 *
 */
public class Terrain {
	private String myName;
	private Image myImage;
	private boolean passable;
	private int movementCost;
	
	//The use for following three isn't yet implemented. 
	private int healPerTurn;
	private int avoidBoost;
	private ListAttribute aBonuses;
	
	/**
	 * 
	 * @param name
	 * @param canPass
	 * @param movCost
	 * @param heal
	 * @param avo
	 * @param bonus
	 */
	public Terrain(String name, boolean canPass, int movCost, int heal, int avo, ListAttribute bonus) {
		myName = name;
		myImage = new Image(getClass().getClassLoader().getResourceAsStream(String.format("%s%s", myName, ".png")));
		passable = canPass;
		movementCost = movCost;
		healPerTurn = heal;
		avoidBoost = avo;
		aBonuses = bonus;
	}
	
	/**
	 * 
	 * @return a new Terrain object, initialized to be a forest
	 */
	public static Terrain newForest(){
		return new Terrain(
				"Forest",
				true,
				2,
				0,
				10,
				new ListAttribute(new int[] {0,0,0,0,0,0,0,0}));
	}
	
	/**
	 * 
	 * @return a new Terrain object, initialized to be a plain
	 */
	public static Terrain newPlain(){
		return new Terrain(
				"Plain",
				true,
				1,
				0,
				0,
				new ListAttribute(new int[] {0,0,0,0,0,0,0,0}));
	}
	
	/**
	 * 
	 * @return a new Terrain object, initialized to be a rocks
	 */
	public static Terrain newRocks(){
		return new Terrain(
				"Rocks",
				false,
				1,
				0,
				0,
				new ListAttribute(new int[] {0,0,0,0,0,0,0,0}));
	}
	
	/**
	 * Checks if a unit can move through or end a turn on this terrain.
	 * @return 
	 * true if terrain is passable, false if not
	 */
	public boolean passable(){
		return  passable;
	}
	
	/**
	 * Returns the cost for a unit to move from an adjacent space onto a space woth this terrain.
	 * @return int
	 */
	public int moveCost(){
		return movementCost;
	}
	
	/**
	 * Checks if a unit with remaining movement moveRemaining can move onto this space.
	 * @param moveRemaining
	 * @return true if the unit can move onto the space, false if not.
	 */
	public boolean canMoveTo(int moveRemaining){
		return passable && (movementCost <= moveRemaining);
	}
	
	/**
	 * @return string representation of terrain's name
	 */
	public String toString(){
		return myName;
	}
	
	/**
	 * Fetches the Image associated with this terrain
	 * @return an Image to display
	 */
	public Image getImage() {
		return myImage;
	}

}
