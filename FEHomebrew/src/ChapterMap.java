import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.TreeMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class ChapterMap {
	Vector<Party> factions;
	Terrain[][] map;
	int mapSize;
	
	public ChapterMap(int i){
		map = new Terrain[i][i];
		factions = new Vector<Party>();
	}
	
	public ChapterMap(Terrain[][] m){
		map = m;
		factions = new Vector<Party>();
		mapSize = map.length;
	}

	public void addParty(Party p){
		factions.add(p);
	}
	
	public Terrain getTerrain(Point p){
		if (p.getX() >= mapSize || p.getY() >= mapSize || p.getX() < 0 || p.getY() < 0){
			return null;
		} else {
			return map[p.getX()][p.getY()];
		}
	}
	
	public int getSpaceMoveCost(Point p){
		return getTerrain(p).moveCost();
	}
	
	
	//TODO: Make this remember the path taken?
	public void possibleMove(Point start, int spacesToGo, Map<Point, Integer> result){

		Point[] nextSpaces = {start.up(), start.down(), start.left(), start.right()};
		for (Point s: nextSpaces){
			if (s != null && getTerrain(s).canMoveTo(spacesToGo)){
				//I haven't been here before, or I found a shorter path to get here.
				if (!result.containsKey(s) || result.get(s).compareTo(spacesToGo - getSpaceMoveCost(s)) < 0){
					result.put(s, spacesToGo - getSpaceMoveCost(s));
					possibleMove(s, spacesToGo - getSpaceMoveCost(s), result);
				}
			}
		}
	}
	
	public Map<Point, Integer> possibleMove(Point start, int spacesToGo){
		Map<Point, Integer> result = new TreeMap<Point, Integer>();
		result.put(start, spacesToGo);
		possibleMove(start, spacesToGo, result);
		return result;
	}
	
	public static void main(String[] args){
		Terrain p = new TerrainPlain();
		Terrain f = new TerrainForest();
		
		Terrain[][] tMap = new Terrain[][]
				{
			{f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f},
			{f, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p},
			{f, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p},
			{f, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p},
			{f, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p},
			{f, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p},
			{f, p, p, p, f, f, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p},
			{f, p, p, p, f, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p},
			{f, p, p, p, p, f, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p},
			{f, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p},
			{f, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p},
			{f, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p},
			{f, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p},
			{f, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p},
			{f, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p},
			{f, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p},
			{f, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p},
			{f, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p},
			{f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f},
			{f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f}
				};
				
		ChapterMap c = new ChapterMap(tMap);
		
		Point start = new Point(7, 5);
		Map<Point, Integer> o = c.possibleMove(start, 2);
		ArrayList<Point> a = new ArrayList<Point>(o.keySet());
		
		Collections.sort(a);
		System.out.println(o);
		System.out.println(a.size());
				
		
	}
}
	

