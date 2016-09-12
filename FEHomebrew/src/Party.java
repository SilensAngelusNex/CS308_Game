import java.util.Collection;
import java.util.Vector;

public class Party {
	private String myName;
	private Vector<Character> myChars;
	private Vector<Item> myConvoy;
	private int myGold;
	
	public Party(String name){
		myName = name;
		myGold = 0;
		myConvoy = new Vector<Item>();
		myChars = new Vector<Character>();
	}
	
	public void addCharacter(Character toAdd){
		myChars.add(toAdd);
	}
	
	public boolean contains(Character c){
		return myChars.contains(c);
	}
	
	public void takeFromConvoy(Character c, Item i){
		if (myChars.contains(c)){
			myConvoy.remove(i);
			c.take(i);
		}
	}
	
	public void takeFromConvoy(Character c, int i){
		if (myChars.contains(c)){
			 Item toTake = myConvoy.remove(i);
			c.take(toTake);
		}
	}
	
	public void putInConvoy(Character c, Item i){
		if (myChars.contains(c)){
			myConvoy.add(i);
			c.drop(i);
		}
	}
	
	public String toString(){
		return myName;
	}

	public int countActionsLeft() {
		int result = 0;
		
		for (Character c : myChars){
			if (c.hasMove() || c.hasAction())
				result++;
		}
		return result;
	}

	public void nextTurn() {
		for (Character c : myChars){
			c.nextTurn();
		}
	}

	public boolean bossDefeated() {
		
		for (Character c : myChars){
			if (c.isBoss() && c.isDead()){
				return true;
			}
		}
		return routed();
	}

	public boolean routed() {
		for (Character c : myChars){
			if (!c.isDead()){
				return false;
			}
		}
		return true;
	}

	public Vector<Character> bringOutYourDead() {
		Vector<Character> result = new Vector<Character>();
		
		for (Character c : myChars){
			if (c.isDead()){
				result.add(c);
			}
		}
		
		return result;
	}
	
	public int size(){
		return myChars.size();
	}

	public Character getCharacter(int index) {
		return myChars.get(index);
	}
	
}
