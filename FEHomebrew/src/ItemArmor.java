/**
 * Unused, but implemented. Intended function is to allow characters to equip armor to boost their attributes.
 * @author Weston
 *
 */
public class ItemArmor extends Item {
	protected boolean equipped;
	protected ListAttribute aBonuses;
	
	/**
	 * Creates an ItemArmor from the required attributes.
	 * @param n
	 * @param value
	 * @param bonuses
	 */
	public ItemArmor(String n, int value, ListAttribute bonuses) {
		super(n, value);
		aBonuses = bonuses;
	}
	
	/**
	 * Gets the armor's equipped state.
	 * @return
	 */
	public boolean getEquipped(){
		return equipped;
	}
	/**
	 * Sets the armor's equipped state to b.
	 * @param b
	 */
	public void setEquipped(boolean b){
		equipped = b;
	}
	/**
	 * Returns the armor's attribute bonuses for equipping.
	 * @return
	 */
	public ListAttribute getBonuses(){
		return aBonuses;
	}

}
