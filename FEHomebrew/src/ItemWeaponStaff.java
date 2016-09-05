
public class ItemWeaponStaff extends ItemWeapon {
	public int usesRemaining;
	public static int maxUses;
	
	/*
	 * Returns the current value of the item so it can be sold.
	 */
	public int valueRemaining(){
		return maxValue * usesRemaining / maxUses;
	}
	
	
	/*
	 * Using an item decrements the number of remaining uses. 
	 */
	public void use(){
		usesRemaining -= 1;
		return;
	}
	/*
	 * Not sure if this one will be used, but it decrements the number of uses 
	 * by an integer, for when you use multiple uses at once.
	 */
	public void use(int x){
		usesRemaining -= x;
		return;
	}
}
