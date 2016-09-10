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
	
}
