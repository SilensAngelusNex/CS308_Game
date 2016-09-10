import javafx.scene.image.Image;

public class Terrain {
	protected String name;
	protected Image myImage;
	protected boolean passable;
	protected int movementCost;
	protected int healPerTurn;
	protected int avoidBoost;
	protected ListAttribute aBonuses;
	
	public boolean passable(){
		return  passable;
	}
	
	public int moveCost(){
		return movementCost;
	}
	
	public boolean canMoveTo(int moveRemaining){
		return passable && (movementCost <= moveRemaining);
	}
	
	public String toString(){
		return name;
	}
	
	public static Terrain newForest(){
		return new Terrain(
				"Forest",
				true,
				2,
				0,
				10,
				new ListAttribute(new int[] {0,0,0,0,0,0,0,0}));
	}
	
	public static Terrain newPlain(){
		return new Terrain(
				"Plain",
				true,
				1,
				0,
				0,
				new ListAttribute(new int[] {0,0,0,0,0,0,0,0}));
	}
	
	public static Terrain newRocks(){
		return new Terrain(
				"Rocks",
				false,
				1,
				0,
				0,
				new ListAttribute(new int[] {0,0,0,0,0,0,0,0}));
	}

	public Terrain(String n, boolean pass, int cost, int heal, int avo, ListAttribute bonus) {
		name = n;
		myImage = new Image(getClass().getClassLoader().getResourceAsStream(String.format("%s%s", name, ".png")));
		passable = pass;
		movementCost = 1;
		healPerTurn = heal;
		avoidBoost = avo;
		aBonuses = bonus;
	}

	public Image getImage() {
		return myImage;
	}

}
