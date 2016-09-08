
public class TerrainForest extends Terrain{
	
	public TerrainForest(){
		name = "Forest";
		passable = true;
		movementCost = 2;
		healPerTurn = 0;
		avoidBoost = 10;
		aBonuses = new ListAttribute(new int[] {0,0,0,0,0,0,0,0});
	}

}
