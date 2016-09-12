
/**
 * Unused; partially implemented. The intention is to allow for characters to use items for healing, stat boosts, etc.
 * @author Weston
 *
 */
public class ItemOther extends Item {
	protected Util.ItemTypeCode code;
	protected int usesRemaining;
	protected int maxUses;
	
	/**
	 * Constructs an ItemOther from the required fields.
	 * @param name
	 * @param value
	 * @param uses
	 */
	public ItemOther(String name, int value, int uses){
		super(name, value);
		usesRemaining = uses;
		maxUses = uses;
	}
	
	/**
	 * Returns the current value of the item so it can be sold.
	 * @return
	 */
	public int valueRemaining(){
		return maxValue * usesRemaining / maxUses;
	}
	
	
	/**
	 * Using an item decrements the number of remaining uses. 
	 */
	public void use(){
		usesRemaining -= 1;
		return;
	}
	/**
	 * Unused. Decrements the number of uses by an integer, for when you use multiple uses at once.
	 * @param x
	 */
	public void use(int x){
		usesRemaining -= x;
	}
	
	//TODO: make it break at 0 uses left.
}
