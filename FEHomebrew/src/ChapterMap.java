import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * A ChapterMap instance contains the Parties fighting in that chapter (level), a representation of the space the
 * chapter takes place and where each character is in it, and keeps track of turns.
 * @author Weston
 *
 */
public class ChapterMap {
	private Vector<Party> myFactions;
	private Terrain[][] myTerrain;
	private Character[][] myCharacters;
	private int myTurnIndex;
	private int mapSize;
	
	//Variables to keep track of AI turns.
	private boolean player2AI;
	private boolean doneAI;
	private int indexAI;
	
	/**
	 * Unused. Creates a ChapterMap with a map i squares by i squares.
	 * @param i
	 */
	public ChapterMap(int i){
		myTurnIndex = 0;
		mapSize = i;
		myTerrain = new Terrain[i][i];
		myCharacters = new Character[i][i];
		myFactions = new Vector<Party>();
		doneAI = false;
		indexAI = 0;
	}
	
	/**
	 * Creates a ChapterMap from an existing Terrain array.
	 * @param m
	 */
	public ChapterMap(Terrain[][] m){
		myTurnIndex = 0;
		myTerrain = m;
		myFactions = new Vector<Party>();
		mapSize = myTerrain.length;
		myCharacters = new Character[mapSize][mapSize];
		doneAI = false;
		indexAI = 0;
	}
	
	/**
	 * @return The ChapterMap used for the initial state of multiplayer mode.
	 */
	public static ChapterMap newMultiplayer(){
		Terrain p = Terrain.newPlain();
		Terrain f = Terrain.newForest();
		Terrain r = Terrain.newRocks();
		
		Terrain[][] tMap = new Terrain[][]
				{
			{f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f},
			{f, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p},
			{f, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p},
			{f, p, p, p, p, p, p, p, p, p, p, p, r, p, p, p, p, p, p, p, p, p},
			{f, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p},
			{f, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p},
			{f, p, p, p, f, f, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p},
			{f, p, p, p, f, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p},
			{r, p, p, p, p, f, p, p, p, p, p, p, p, p, p, p, p, r, p, p, p, p},
			{f, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p},
			{f, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p},
			{f, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p},
			{f, p, p, p, p, p, p, p, p, p, r, p, p, p, p, p, p, p, p, p, p, p},
			{f, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p},
			{f, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p},
			{f, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p},
			{f, p, p, p, p, r, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p},
			{f, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p, p},
			{f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f},
			{f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f}
				};
		
		ChapterMap result = new ChapterMap(tMap);
		
		result.myFactions.add(new Party("Greil Mercenaries (P1)"));
		result.myFactions.add(new Party("Mia's Edgelords (P2)"));


		result.addCharacter(Character.newIke(new Point(12, 6), 40, 40), 0);
		result.addCharacter(Character.newMist(new Point(12, 8), 40, 40), 0);
		result.addCharacter(Character.newBoyd(new Point(14, 5), 40, 40), 0);
		
		result.addCharacter(Character.newMia(new Point(9, 7), 40, 40), 1);
		result.addCharacter(Character.newDiana(new Point(8, 6), 40, 40), 1);
		result.addCharacter(Character.newRin(new Point(9, 10), 40, 40), 1);
		
		return result;
	}
	
	/**
	 * @return The ChapterMap used for the initial state of campaign mode.
	 */
	public static ChapterMap newCampaignLvl1(){
		Terrain p = Terrain.newPlain();
		Terrain f = Terrain.newForest();
		Terrain r = Terrain.newRocks();
		
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
		result.player2AI = true;
		result.myFactions.add(new Party("Fly Honeys"));
		result.myFactions.add(new Party("Rude Dudes"));
		
		result.addCharacter(Character.newMia(new Point(18, 7), 40, 40), 0);
		result.addCharacter(Character.newDiana(new Point(17, 6), 40, 40), 0);
		result.addCharacter(Character.newRin(new Point(19, 5), 40, 40), 0);
		
		result.addCharacter(Character.newRuddy(new Point(0, 19), 40, 40), 1);
		result.addCharacter(Character.newAxeMook(new Point(4, 17), 40, 40), 1);
		result.addCharacter(Character.newSwordMook(new Point(2, 16), 40, 40), 1);
		result.addCharacter(Character.newLanceMook(new Point(1, 12), 40, 40), 1);
		
		return result;
	}
	
	/**
	 * Returns a Vector of appropriatly sized ImageViews to add to the scene to represent all the tiles of the map.
	 * @param width
	 * @param height
	 * @return
	 */
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
	
	/**
	 * Returns a Vector of appropriatly sized ImageViews to add to the scene to represent all the Characters on the map.
	 * @param width
	 * @param height
	 * @return
	 */
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
	
	/**
	 * Finds a TreeMap of all the spaces the character on start can move to, and the move cost to get to each.
	 * @param start
	 * @return
	 */
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
        toMove.setValidMoves(result);
		return result;
	}
	
	/**
	 * Moves a charactee from Point start to Point end.
	 * @param start
	 * @param end
	 */
	public void move(Point start, Point end){
		if (myCharacters[start.getX()][start.getY()] != null && !start.equals(end)){
			myCharacters[start.getX()][start.getY()].moveTo(end);
			
			myCharacters[end.getX()][end.getY()] = myCharacters[start.getX()][start.getY()];
			myCharacters[start.getX()][start.getY()] = null;
		}
	}
	
	/**
	 * Spawns an appropriatly sized enemy on square p and returns an ImageView of him.
	 * @param p
	 * @param height
	 * @param width
	 * @return
	 */
	public Character spawnEnemy(Point p, int height, int width){
		Character spawned = null;
		
		Random r = new Random();
		int code = r.nextInt(3);
		switch (code){
			case 0:
				spawned = Character.newSwordMook(p, height/mapSize, width/mapSize);
				break;
			case 1:
				spawned = Character.newAxeMook(p, height/mapSize, width/mapSize);
				break;
			case 2:
				spawned = Character.newLanceMook(p, height/mapSize, width/mapSize);
				break;
				/*
			default:
	
				spawned = Character.newSwordMook(p, height/mapSize, width/mapSize);
				break;
				*/
		}
		myCharacters[p.getX()][p.getY()] = spawned;
		int spawnedsFaction = (myTurnIndex + 1) % myFactions.size();
		myFactions.get(spawnedsFaction).addCharacter(spawned);
		return spawned;
		
	}
	
	//TODO: make Attack squares orange
	/**
	 * Returns ImageViews to shade all the spaces given in validMoves blue.
	 * @param validMoves
	 * @param width
	 * @param height
	 * @return
	 */
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
	
	/**
	 * Returns a Vector of dead characters to take out of the scene. Also takes the off the map.
	 * @return
	 */
	public Vector<Character> bringOutYourDead(){
		Vector<Character> result = new Vector<Character>();
		
		for (Party p : myFactions){
			result.addAll(p.bringOutYourDead());
		}
		
		for (Character c: result){
			if (getTerrain(c.getLoc()) != null)
				myCharacters[c.getLoc().getX()][c.getLoc().getY()] = null;
				c.moveTo(new Point(-1, -1));
		}
		
		return result;
	}
	
	/**
	 * @param loc
	 * @return true iff there is a Character at Point loc.
	 */
	public boolean hasCharacter(Point loc) {
		return myCharacters[loc.getX()][loc.getY()] != null;
	}

	/**
	 * @param loc
	 * @return the Character at Point loc.
	 */
	public Character getCharacter(Point loc) {
		return myCharacters[loc.getX()][loc.getY()];
	}

	/**
	 * Takes the movement to get to Point loc out of the character's movement for the turn.
	 * @param loc
	 */
	public void finalizeMove(Point loc) {
		myCharacters[loc.getX()][loc.getY()].finalizeMove(loc);
	}
	
	/**
	 * Uses the character's action for the turn.
	 * @param loc
	 */
	public void finalizeAction(Point loc){
		myCharacters[loc.getX()][loc.getY()].finalizeAction();
	}
	
	/**
	 * Counts the number of Characters that can still act or move in the active Party.
	 * @return
	 */
	public int countActionsLeft(){
		return myFactions.get(myTurnIndex).countActionsLeft();
	}

	/**
	 * @return Active Party's name
	 */
	public String currentTurn() {
		return myFactions.get(myTurnIndex).toString();
	}

	/**
	 * @return true iff this turn is controlled by the AI
	 */
	public boolean currentTurnAI() {
		return player2AI && myTurnIndex == 1;
	}
	
	/**
	 * @param p
	 * @return true iff the current turn belongs to the faction of the Character on Point p 
	 */
	public boolean isOnOwnTurn(Point p){
		return getFaction(getCharacter(p)) == myTurnIndex;
	}
	
	/**
	 * @return true iff the AI has no moves left to make
	 */
	public boolean doneAI(){
		return doneAI;
	}

	/**
	 * Get the name of the winning faction, or return null if there isn't a winner yet.
	 * @return
	 */
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
	
	/**
	 * Pick the next action for the computer controlled team.
	 */
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
			indexAI++;
		}
		
	}
	
	/**
	 * Reset Characters turn info and make the next faction the active one.
	 */
	public void endTurn(){
		if (player2AI)
			resetAI();
		myFactions.get(myTurnIndex).nextTurn();
		myTurnIndex = (myTurnIndex + 1) % myFactions.size();
	}

	/**
	 * Add a Party p to the faction list.
	 * @param p
	 */
	public void addParty(Party p){
		myFactions.add(p);
	}
	
	/**
	 * @param p
	 * @return true iff the Character at Point p has move left for the turn.
	 */
	public boolean canMove(Point p){
		return hasCharacter(p) && myFactions.get(myTurnIndex).contains(getCharacter(p)) && getCharacter(p).hasMove();
	}
	
	/**
	 * @param p
	 * @return true iff the Character at Point p has an action left for the turn.
	 */
	public boolean canAct(Point p){
		return hasCharacter(p) && myFactions.get(myTurnIndex).contains(getCharacter(p)) && getCharacter(p).hasAction();
	}
	
	/**
	 * Add a character to the partyIndex-th party in myFactions.
	 * @param toAdd
	 * @param partyIndex
	 */
	public void addCharacter(Character toAdd, int partyIndex){
		if (myCharacters[toAdd.getLoc().getX()][toAdd.getLoc().getY()] == null){
			myFactions.get(partyIndex).addCharacter(toAdd);
			myCharacters[toAdd.getLoc().getX()][toAdd.getLoc().getY()] = toAdd;
		}
		
	}
	
	/**
	 * @param p
	 * @return the terrain on space p
	 */
	public Terrain getTerrain(Point p){
		if (p == null || p.getX() >= mapSize || p.getY() >= mapSize || p.getX() < 0 || p.getY() < 0){
			return null;
		} else {
			return myTerrain[p.getX()][p.getY()];
		}
	}
	
	/**

	 * @param p
	 * @return the  movement cost of the terrain on space p
	 */
	public int getSpaceMoveCost(Point p){
		return getTerrain(p).moveCost();
	}
	
	/**
	 * @return size of the chapter map
	 */
	public int size(){
		return mapSize;
	}

	/**
	 * Helper for nextMoveAI. Attacks a character the given character can kill, or if none, the one it does the most %HP
	 * damage to, or if it can't attack any characters, moves randomly.
	 * @param attacking
	 */
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
	
	/**
	 * Helper function for attackSpaces.
	 * @param c
	 * @return
	 */
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
	
	//TODO: Make this remember the path taken?
	/**
	 * Helper function for possibleMove.
	 * @param start
	 * @param c
	 * @param spacesToGo
	 * @param result
	 */
	private void possibleMove(Point start, Character c, int spacesToGo, Map<Point, Integer> result){

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
	
	/**
	 * Helper for attackSpaces. Finds all spaces within dist range of Point p;
	 * @param p
	 * @param dist
	 * @return
	 */
	private Map<Point, Integer> pointsInRectDist(Point p, int dist){
		Map<Point, Integer> result = new TreeMap<Point, Integer>();	
		result.put(p, dist);
		pointsInRectDist(p, dist, result);
		return result;
	}
	
	/**
	 * Helper for pointsInRectDist
	 * @param p
	 * @param dist
	 * @param result
	 * @return 
	 */
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
	
	/**
	 * @param c
	 * @return a Vector containing all the characters c can attack
	 */
	private Vector<Character> canAttack(Character c){
		Vector<Character> result = new Vector<Character>();
		for (Point attack : attackSpaces(c)){
			if (enemyOn(c, attack)){
				result.add(getCharacter(attack));
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @param c
	 * @return faction index of Character c
	 */
	private int getFaction(Character c){
		int index = 0;
		
		for (Party p : myFactions){
			if (p.contains(c)){
				return index;
			}
			index++;
		}
		return -1;
	}
	
	/**
	 * 
	 * @param c
	 * @param p
	 * @return true iff there is an enemy of c on Point p.
	 */
	private boolean enemyOn(Character c, Point p){
		return hasCharacter(p) && getFaction(c) != getFaction(getCharacter(p));
	}

	/**
	 * Resets AI for next turn.
	 */
	private void resetAI(){
		doneAI = false;
		indexAI = 0;
	}
	
}
	

