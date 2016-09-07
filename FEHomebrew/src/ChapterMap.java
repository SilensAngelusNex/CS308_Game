import java.util.Vector;

public class ChapterMap {
	Vector<Party> factions;
	Terrain[][] map;
	
	public ChapterMap(int i){
		map = new Terrain[i][i];
		factions = new Vector<Party>();
	}
}
