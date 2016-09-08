
public class Item {
	protected String name;
	protected int maxValue;
	
	public Item(String n, int value) {
		name = n;
		maxValue = value;
	}
	
	public int getMaxValue(){
		return maxValue;
	}
	
	public String toString(){
		return name;
	}

}