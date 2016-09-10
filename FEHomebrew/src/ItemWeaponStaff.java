
public class ItemWeaponStaff extends ItemWeapon {
	protected int usesRemaining;
	protected int maxUses;
	protected int baseHeal;
	protected Point myRange;
	
	public ItemWeaponStaff(String n, int value, Util.WeaponType type, Util.AttributeType attrib,
			Util.AttributeType dmgType, ListAttribute bonus, int mt, int hit, int crit, int uses, int heal, int baseRange, int scaleRange){
		super(n, value, type, attrib, dmgType, bonus, mt, hit, crit, 1);
		usesRemaining = uses;
		maxUses = uses;
		baseHeal = heal;
		myRange = new Point(baseRange, scaleRange);
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

	public Point staffRange() {
		return myRange;
	}

	public static ItemWeaponStaff newHealStaff() {
		return new ItemWeaponStaff(
				"Heal",
				800,
				Util.WeaponType.STAFF,
				Util.AttributeType.STR,
				Util.AttributeType.DEF,
				new ListAttribute(0,0,0,0,0,0,0,0),
				0,
				80,
				0,
				40,
				5,
				1,
				0);
	}
}
