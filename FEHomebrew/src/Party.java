import java.util.Vector;

/**
 * This class represents a Party: a group of allied characters and some of their shared resources.
 * @author Weston
 *
 */
public class Party {
	private String myName;
	private Vector<Character> myChars;
	
	//The functionality of the following isn't yet implemented.
	private Vector<Item> myConvoy;
	private int myGold;
	
	/**
	 * Makes an empty party.
	 * @param name
	 */
	public Party(String name){
		myName = name;
		myGold = 0;
		myConvoy = new Vector<Item>();
		myChars = new Vector<Character>();
	}
	
	/**
	 * Adds the Character toAdd to the Party.
	 * @param toAdd
	 */
	public void addCharacter(Character toAdd){
		myChars.add(toAdd);
	}
	
	/**
	 * Checks if the party contains Character c.
	 * @param c
	 * @return boolean
	 */
	public boolean contains(Character c){
		return myChars.contains(c);
	}
	
	/**
	 * Not used. Gives an Item stored in the convoy to a character in the party.
	 * @param c
	 * @param i
	 */
	public void takeFromConvoy(Character c, Item i){
		if (myChars.contains(c)){
			myConvoy.remove(i);
			c.take(i);
		}
	}
	
	/**
	 * Not used. Gives an Item stored in a character in the Party's inventory to the convoy.
	 * @param c
	 * @param i
	 */
	public void putInConvoy(Character c, Item i){
		if (myChars.contains(c)){
			myConvoy.add(i);
			c.drop(i);
		}
	}

	/**
	 * Counts the number of characters in the party who still can move or act this turn.
	 * @return number of characters (int)
	 */
	public int countActionsLeft() {
		int result = 0;
		
		for (Character c : myChars){
			if (c.hasMove() || c.hasAction())
				result++;
		}
		return result;
	}

	/**
	 * Returns a Vector containing the Party's dead. 
	 * @return
	 */
	public Vector<Character> bringOutYourDead() {
		Vector<Character> result = new Vector<Character>();
		
		for (Character c : myChars){
			if (c.isDead()){
				result.add(c);
			}
		}
		return result;
	}
	
	/**
	 * Checks if any of the important "boss" characters are dead. If no bosses are in the Party, it instead checks if
	 * the Party is routed. (If all characters in the Party are dead.)
	 * @return boolean
	 */
	public boolean bossDefeated() {
		for (Character c : myChars){
			if (c.isBoss() && c.isDead()){
				return true;
			}
		}
		return routed();
	}

	/**
	 * Checks if the Party has been routed. (If all characters in the Party are dead.)
	 * @return boolean
	 */
	public boolean routed() {
		for (Character c : myChars){
			if (!c.isDead()){
				return false;
			}
		}
		return true;
	}
	
	/**
	 *Resets the movement and action counters of all characters in the party (so they will be able to act and move next
	 *turn).
	 */
	public void nextTurn() {
		for (Character c : myChars){
			c.nextTurn();
		}
	}

	/**
	 * Returns the index-th Character in the Party.
	 * @param index
	 * @return
	 */
	public Character getCharacter(int index) {
		return myChars.get(index);
	}
	
	/**
	 * Returns the number of characters in the Party.
	 * @return
	 */
	public int size(){
		return myChars.size();
	}
	
	/**
	 * Returns the Party's name.
	 */
	public String toString(){
		return myName;
	}
}
