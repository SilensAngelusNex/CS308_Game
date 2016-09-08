
public class ItemWeaponStaff extends ItemWeapon {
	protected int usesRemaining;
	protected int maxUses;
	protected int baseHeal;
	
	public ItemWeaponStaff(String n, int value, Util.WeaponType type, Util.AttributeType attrib,
			Util.AttributeType dmgType, ListAttribute bonus, int mt, int hit, int crit, int uses, int heal){
		super(n, value, type, attrib, dmgType, bonus, mt, hit, crit);
		usesRemaining = uses;
		maxUses = uses;
		baseHeal = heal;
	}
	
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
