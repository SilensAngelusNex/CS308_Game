import java.util.Vector;

public class Party {
	Vector<Character> chars;
	Vector<Item> convoy;
	int gold;
	
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