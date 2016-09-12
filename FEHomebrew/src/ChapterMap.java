import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ChapterMap {
	private Vector<Party> myFactions;
	private Terrain[][] myTerrain;
	private Character[][] myCharacters;
	private int myTurnIndex;
	private int mapSize;
	private boolean player2AI;
	private boolean doneAI;
	private int indexAI;
	
	public ChapterMap(int i){
		myTurnIndex = 0;
		mapSize = i;
		myTerrain = new Terrain[i][i];
		myCharacters = new Character[i][i];
		myFactions = new Vector<Party>();
		doneAI = false;
		indexAI = 0;
	}
	
	public ChapterMap(Terrain[][] m){
		myTurnIndex = 0;
		myTerrain = m;
		myFactions = new Vector<Party>();
		mapSize = myTerrain.length;
		myCharacters = new Character[mapSize][mapSize];
		doneAI = false;
		indexAI = 0;
	}
	
	public void endTurn(){
		//TODO: Reset moves and attacks
		myFactions.get(myTurnIndex).nextTurn();
		myTurnIndex = (myTurnIndex + 1) % myFactions.size();
	}

	public void addParty(Party p){
		myFactions.add(p);
	}
	
	public boolean canMove(Point p){
		return hasCharacter(p) && myFactions.get(myTurnIndex).contains(getCharacter(p)) && getCharacter(p).hasMove();
	}
	
	public boolean canAct(Point p){
		return hasCharacter(p) && myFactions.get(myTurnIndex).contains(getCharacter(p)) && getCharacter(p).hasAction();
	}
	
	public void addCharacter(Character toAdd, int partyIndex){
		if (myCharacters[toAdd.getLoc().getX()][toAdd.getLoc().getY()] == null){
			myFactions.get(partyIndex).addCharacter(toAdd);
			myCharacters[toAdd.getLoc().getX()][toAdd.getLoc().getY()] = toAdd;
		}
		
	}
	
	public int size(){
		return mapSize;
	}
	
	public Terrain getTerrain(Point p){
		if (p == null || p.getX() >= mapSize || p.getY() >= mapSize || p.getX() < 0 || p.getY() < 0){
			return null;
		} else {
			return myTerrain[p.getX()][p.getY()];
		}
	}
	
	public int getSpaceMoveCost(Point p){
		return getTerrain(p).moveCost();
	}
	
	
	//TODO: Make this remember the path taken?
	public void possibleMove(Point start, Character c, int spacesToGo, Map<Point, Integer> result){

		Point[] nextSpaces = {start.up(), start.down(), start.left(), start.right()};
		for (Point s: nextSpaces){
			if (s != null && getTerrain(s) != null && getTerrain(s).canMoveTo(spacesToGo) && ! enemyOn(c, s)){
				//I haven't been here before, or I found a shorter path to get here.
				if (!result.containsKey(s) || result.get(s).compareTo(spacesToGo - getSpaceMoveCost(s)) < 0){
					result.put(s, spacesToGo - getSpaceMoveCost(s));
					possibleMove(s, c, spacesToGo - getSpaceMoveCost(s), result);
				}
			}
		}
	}
	
	public Map<Point, Integer> possibleMove(Point start){
		Map<Point, Integer> result = new TreeMap<Point, Integer>();
		Character toMove = myCharacters[start.getX()][start.getY()];
		
		if (toMove != null){
			int spacesToGo = toMove.getMov();
			result.put(start, spacesToGo);
			possibleMove(start, toMove, spacesToGo, result);
		}
		
        Iterator<Point> iter = result.keySet().iterator();
        while (iter.hasNext()) {
            Point p = iter.next();
            if (hasCharacter(p) && p != start) {
                iter.remove();
            }
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
	
	public void nextMoveAI(){
		if (indexAI >= myFactions.get(myTurnIndex).size()){
			doneAI = true;
			indexAI = 0;
		} else {
			Character nextToAct = myFactions.get(myTurnIndex).getCharacter(indexAI);
			while (!nextToAct.hasAction()){
				nextToAct = myFactions.get(myTurnIndex).getCharacter(indexAI);
				indexAI++;
			}
			makeBestAttackAI(nextToAct);
		}
		
	}
	
	private void makeBestAttackAI(Character attacking) {
		Map<Point, Integer> moves = possibleMove(attacking.getLoc());
		Vector<Character> options = canAttack(attacking);
		ArrayList<Double> percent = new ArrayList<Double>();
		
		if (options.size() > 0){
		
			for (Character c: options){
				percent.add(attacking.percentDamage(c));
			}
			
			int option = -1;
			if (Collections.min(percent) < 0)
				option = percent.indexOf(Collections.min(percent));
			else 
				option = percent.indexOf(Collections.max(percent));
			
			//Find spaces we could move to and attack from.
			Character toAttack = options.get(option);
			Set<Point> inRange = pointsInRectDist(toAttack.getLoc(), attacking.getRange()).keySet();
			System.out.println(inRange);
			inRange.retainAll(moves.keySet());
			System.out.println(inRange);
			
			//Randomly pick a legal space within range that we can move to
			Vector<Point> moveList = new Vector<Point>(inRange);
			Collections.shuffle(moveList);
			move(attacking.getLoc(), moveList.get(0));
			
			attacking.combat(toAttack);

		} else {
			//Move to a legal space (randomly :p).
			Vector<Point> moveList = new Vector<Point>(moves.keySet());
			Collections.shuffle(moveList);
			
			move(attacking.getLoc(), moveList.get(0));
		}
		
		
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
		if (myCharacters[start.getX()][start.getY()] != null && !start.equals(end)){
			myCharacters[start.getX()][start.getY()].moveTo(end);
			
			myCharacters[end.getX()][end.getY()] = myCharacters[start.getX()][start.getY()];
			myCharacters[start.getX()][start.getY()] = null;
		}
		
	}
	
	public static ChapterMap newMultiplayer(){
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
		result.myFactions.add(new Party("Fly Honeys"));
		result.myFactions.add(new Party("Rude Dudes"));
		
		result.addCharacter(Character.newIke(new Point(12, 6), 40, 40), 0);
		result.addCharacter(Character.newMist(new Point(12, 8), 40, 40), 0);
		
		
		return result;
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
		result.myFactions.add(new Party("Greil Mercenaries"));
		result.myFactions.add(new Party("Mia's Edgelords"));
		
		result.addCharacter(Character.newIke(new Point(12, 6), 40, 40), 0);
		result.addCharacter(Character.newMist(new Point(12, 8), 40, 40), 0);
		result.addCharacter(Character.newMia(new Point(9, 7), 40, 40), 1);
		
		return result;
	}
	
	
	public String getVictor(){
		if (myFactions.size() == 1){
			return myFactions.get(0).toString();
		}
		//TODO: make getVictor account for number of parties != 2
		
		if (player2AI){
			if (myFactions.get(0).bossDefeated())
				return myFactions.get(1).toString();
			else if (myFactions.get(1).routed())
				return myFactions.get(0).toString();
			else
				return null;
		} else {
			if (myFactions.get(0).bossDefeated())
				return myFactions.get(1).toString();
			else if (myFactions.get(1).bossDefeated())
				return myFactions.get(0).toString();
			else
				return null;
		}
	}
	
	public Vector<Character> bringOutYourDead(){
		Vector<Character> result = new Vector<Character>();
		
		for (Party p : myFactions){
			result.addAll(p.bringOutYourDead());
		}
		
		for (Character c: result){
			myCharacters[c.getLoc().getX()][c.getLoc().getY()] = null;
		}
		
		return result;
	}
	
	private Map<Point, Integer> pointsInRectDist(Point p, int dist){
		Map<Point, Integer> result = new TreeMap<Point, Integer>();	
		result.put(p, dist);
		pointsInRectDist(p, dist, result);
		return result;
	}
	
	private Map<Point, Integer> pointsInRectDist(Point p, int dist, Map<Point, Integer> result){
		Point[] nextSpaces = {p.up(), p.down(), p.left(), p.right()};
		
		for (Point s: nextSpaces){
			if (s != null && getTerrain(s) != null && dist > 0){
				//I haven't been here before, or I found a shorter path to get here.
				if (!result.containsKey(s) || result.get(s).compareTo(dist - 1) < 0){
					result.put(s, dist - 1);
					pointsInRectDist(s, dist - 1, result);
				}
			}
		}
		return result;
	}
	
	private Set<Point> attackSpaces(Character c){
		Set<Point> result = new TreeSet<Point>();
		if (getCharacter(c.getLoc()) == c){
			Map<Point, Integer> validMoves = possibleMove(c.getLoc());
			
			for (Point move : validMoves.keySet()){
				result.addAll(pointsInRectDist(move, c.getRange()).keySet());
			}
		}
		return result;
	}
	
	private Vector<Character> canAttack(Character c){
		Vector<Character> result = new Vector<Character>();
		for (Point attack : attackSpaces(c)){
			if (enemyOn(c, attack)){
				result.add(getCharacter(attack));
			}
		}
		return result;
	}
	
	public boolean isOnOwnTurn(Point p){
		return getFaction(getCharacter(p)) == myTurnIndex;
	}
	
	private int getFaction(Character c){
		int index = 1;
		
		for (Party p : myFactions){
			if (p.contains(c)){
				return index;
			}
			index++;
		}
		return -1;
	}
	
	private boolean enemyOn(Character c, Point p){
		return hasCharacter(p) && getFaction(c) != getFaction(getCharacter(p));
	}

	public boolean hasCharacter(Point loc) {
		return myCharacters[loc.getX()][loc.getY()] != null;
	}

	public Character getCharacter(Point loc) {
		return myCharacters[loc.getX()][loc.getY()];
	}

	public void finalizeMove(Point loc) {
		myCharacters[loc.getX()][loc.getY()].finalizeMove(loc);
	}
	
	public void finalizeAction(Point loc){
		myCharacters[loc.getX()][loc.getY()].finalizeAction();
	}
	
	public int countActionsLeft(){
		return myFactions.get(myTurnIndex).countActionsLeft();
	}

	public String currentTurn() {
		return myFactions.get(myTurnIndex).toString();
	}

	public boolean currentTurnAI() {
		return player2AI && myTurnIndex == 2;
	}
	
	public boolean doneAI(){
		return doneAI;
	}

	
	public static void main(String[] args){

		ChapterMap c = ChapterMap.newCampaignLvl1();
		
		c.makeBestAttackAI(c.getCharacter(new Point(9, 7)));
		
		System.out.println(c.myFactions.get(1).getCharacter(0).getLoc());
	}
}
	

