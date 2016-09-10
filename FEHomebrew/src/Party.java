import java.util.Vector;

public class Party {
	private Vector<Character> chars;
	private Vector<Item> convoy;
	private int gold;
	
	public Party(){
		gold = 0;
		convoy = new Vector<Item>();
		chars = new Vector<Character>();
	}
	
	public void takeFromConvoy(Character c, Item i){
		if (chars.contains(c)){
			convoy.remove(i);
			c.take(i);
		}
	}
	
	public void takeFromConvoy(Character c, int i){
		if (chars.contains(c)){
			 Item toTake = convoy.remove(i);
			c.take(toTake);
		}
	}
	
	public void putInConvoy(Character c, Item i){
		if (chars.contains(c)){
			convoy.add(i);
			c.drop(i);
		}
	}
	
}
