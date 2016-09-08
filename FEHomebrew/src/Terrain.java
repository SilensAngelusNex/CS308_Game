
public class Terrain {
	String name;
	boolean passable;
	int movementCost;
	int healPerTurn;
	int avoidBoost;
	ListAttribute aBonuses;
	
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
}
