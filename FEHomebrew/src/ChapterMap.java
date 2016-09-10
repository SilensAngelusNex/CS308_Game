import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;
import java.util.Map;
import java.util.Vector;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ChapterMap {
	private Vector<Party> factions;
	private Terrain[][] myTerrain;
	private Character[][] myCharacters;
	private int mapSize;
	
	public ChapterMap(int i){
		mapSize = i;
		myTerrain = new Terrain[i][i];
		myCharacters = new Character[i][i];
		factions = new Vector<Party>();
	}
	
	public ChapterMap(Terrain[][] m){
		myTerrain = m;
		factions = new Vector<Party>();
		mapSize = myTerrain.length;
		myCharacters = new Character[mapSize][mapSize];
	}

	public void addParty(Party p){
		factions.add(p);
	}
	
	public int size(){
		return mapSize;
	}
	
	public Terrain getTerrain(Point p){
		if (p.getX() >= mapSize || p.getY() >= mapSize || p.getX() < 0 || p.getY() < 0){
			return null;
		} else {
			return myTerrain[p.getX()][p.getY()];
		}
	}
	
	public int getSpaceMoveCost(Point p){
		return getTerrain(p).moveCost();
	}
	
	
	//TODO: Make this remember the path taken?
	public void possibleMove(Point start, int spacesToGo, Map<Point, Integer> result){

		Point[] nextSpaces = {start.up(), start.down(), start.left(), start.right()};
		for (Point s: nextSpaces){
			if (s != null && getTerrain(s) != null && getTerrain(s).canMoveTo(spacesToGo)){
				if (s.equals(new Point(12, 0))){
					System.out.println(spacesToGo);
				}
				//I haven't been here before, or I found a shorter path to get here.
				if (!result.containsKey(s) || result.get(s).compareTo(spacesToGo - getSpaceMoveCost(s)) < 0){
					result.put(s, spacesToGo - getSpaceMoveCost(s));
					possibleMove(s, spacesToGo - getSpaceMoveCost(s), result);
				}
			}
		}
	}
	
	public Map<Point, Integer> possibleMove(Point start){
		Map<Point, Integer> result = new TreeMap<Point, Integer>();
			if (myCharacters[start.getX()][start.getY()] != null){
				int spacesToGo = myCharacters[start.getX()][start.getY()].getMov();
				result.put(start, spacesToGo);
				possibleMove(start, spacesToGo, result);
			}
		return result;
	}
	
	public Vector<ImageView> getTileImages(int width, int height){
		Vector<ImageView> result = new Vector<ImageView>();
		
		for (int i = 0; i < mapSize; i++){
			for (int j = 0; j < mapSize; j++){
				ImageView image = new ImageView(myTerrain[i][j].getImage());
				
				image.setFitHeight(height / mapSize - 1);
				image.setFitWidth(width / mapSize - 1);
				
				image.setX(i * height / mapSize);
				image.setY(j * width / mapSize);
				
				result.add(image);
			}
		}
		return result;
	}
	
	public Vector<ImageView> getCharacterImages(int width, int height){
		Vector<ImageView> result = new Vector<ImageView>();
		
		for (int i = 0; i < mapSize; i++){
			for (int j = 0; j < mapSize; j++){
				if (myCharacters[i][j] != null){
					result.add(myCharacters[i][j]);
				}
			}
		}
		
		return result;
	}
	
	public Vector<ImageView> getMoveSquares(Map<Point, Integer> validMoves, int width, int height){
		Vector<ImageView> result = new Vector<ImageView>();
		
		Image validSpace = new Image(getClass().getClassLoader().getResourceAsStream("MoveSquare.png"));
		
		for (Point p: validMoves.keySet()){
			ImageView image = new ImageView(validSpace);
			image.setFitHeight(height / mapSize);
			image.setFitWidth(width / mapSize);
			
			image.setX(p.getX() * height / mapSize);
			image.setY(p.getY() * width / mapSize);
			
			result.add(image);
		}
		return result;
	}
	
	public void move(Point start, Point end){
		if (myCharacters[start.getX()][start.getY()] != null){
			myCharacters[start.getX()][start.getY()].moveTo(end);
			
			myCharacters[end.getX()][end.getY()] = myCharacters[start.getX()][start.getY()];
			myCharacters[start.getX()][start.getY()] = null;
		}
		
	}
	
	public static ChapterMap newCampaignLvl1(){
		Terrain p = Terrain.newPlain();
		Terrain f = Terrain.newForest();
		
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
		
		ChapterMap result = new ChapterMap(tMap);
		
		result.myCharacters[12][6] = Character.newIke(new Point(12, 6), 40, 40);
		
		return result;
	}
	
	public static void main(String[] args){

		ChapterMap c = ChapterMap.newCampaignLvl1();
		
		Point start = new Point(12, 6);
		Map<Point, Integer> o = c.possibleMove(start);
		ArrayList<Point> a = new ArrayList<Point>(o.keySet());
		
		Collections.sort(a);
		System.out.println(o);
		System.out.println(a.size());
				
		
	}

	public boolean hasCharacter(Point loc) {
		return myCharacters[loc.getX()][loc.getY()] != null;
	}
}
	

