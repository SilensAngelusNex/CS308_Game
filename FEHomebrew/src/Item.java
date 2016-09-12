/**
 * Superclass for all Items. Contains some fields and basic functionality.
 * @author Weston
 *
 */
public class Item {
	protected String name;
	protected int maxValue;
	
	/**
	 * Cojnstructs a new Item.
	 * @param n
	 * @param value
	 */
	public Item(String n, int value) {
		name = n;
		maxValue = value;
	}
	
	/**
	 * Returns the value the item has when it hasn't lost any uses.
	 * @return
	 */
	public int getMaxValue(){
		return maxValue;
	}
	
	/**
	 * Returns Item's name.
	 */
	public String toString(){
		return name;
	}

}