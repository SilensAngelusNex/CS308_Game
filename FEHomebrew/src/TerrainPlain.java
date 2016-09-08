
public class TerrainPlain extends Terrain{
	
	public TerrainPlain(){
		name = "P";
		passable = true;
		movementCost = 1;
		healPerTurn = 0;
		avoidBoost = 0;
		aBonuses = new ListAttribute(new int[] {0,0,0,0,0,0,0,0});
	}

}
