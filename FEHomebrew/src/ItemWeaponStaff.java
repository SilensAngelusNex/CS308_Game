
/**
 * Staves are used to "attack" allied units to heal them. They are a subset of weapons.
 * @author Weston
 *
 */
public class ItemWeaponStaff extends ItemWeapon {
	protected int usesRemaining;
	protected int maxUses;
	protected int baseHeal;
	protected Point myRange;
	
	/**
	 * Constructs an ItemWeaponStaff from all the required fields.
	 * @param name
	 * @param value
	 * @param weaponType
	 * @param damageAttribute
	 * @param dmgType
	 * @param attributeBonuses
	 * @param might
	 * @param hit
	 * @param crit
	 * @param uses
	 * @param heal
	 * @param baseRange
	 * @param scaleRange
	 */
	public ItemWeaponStaff(String name, int value, Util.WeaponType weaponType, Util.AttributeType damageAttribute,
			Util.AttributeType dmgType, ListAttribute attributeBonuses, int might, int hit, int crit, int uses,
			int heal, int baseRange, int scaleRange){
		super(name, value, weaponType, damageAttribute, dmgType, attributeBonuses, might, hit, crit, 1);
		usesRemaining = uses;
		maxUses = uses;
		baseHeal = heal;
		myRange = new Point(baseRange, scaleRange);
	}
	
	/**
	 * Returns a new Heal staff.
	 * @return
	 */
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
	
	/**
	 * Unused. Returns the current value of the item so it can be sold.
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
	 */
	public void use(int x){
		usesRemaining -= x;
		return;
	}

	/**
	 * Returns a Point (a, b) so that Character can calculate staffRange = a * MAG / b
	 * @return
	 */
	public Point staffRange() {
		return myRange;
	}
}
