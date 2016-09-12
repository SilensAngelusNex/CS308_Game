
/**
 * ItemWeapons are wielded by Characters to use for attacking.
 * @author Weston
 *
 */
public class ItemWeapon extends Item {
	protected Util.WeaponType wType;
	protected Util.AttributeType wDmgBonusAttrib;
	protected Util.AttributeType wDmgMitigationType;
	protected boolean isEquipped;
	
	protected ListAttribute attributeBonuses;
	//TODO: Weapon Weight (to modify attack speed)
	protected int wRange;
	protected int wCrit;
	protected int wHit;
	protected int wMT;

	/**
	 * Constructs an ItemWeapon from all the required fields.
	 * @param name
	 * @param value
	 * @param weaponType
	 * @param dmgAttrib
	 * @param dmgType
	 * @param attribBonuses
	 * @param might
	 * @param hit
	 * @param crit
	 * @param range
	 */
	public ItemWeapon(String name, int value, Util.WeaponType weaponType, Util.AttributeType dmgAttrib,
			Util.AttributeType dmgType, ListAttribute attribBonuses, int might, int hit, int crit, int range){
		super(name, value);
		wType = weaponType;
		wDmgBonusAttrib = dmgAttrib;
		wDmgMitigationType = dmgType;
		attributeBonuses = attribBonuses;
		wCrit = crit;
		wHit = hit;
		wMT = might;
		wRange = range;
	}
	
	
	/**
	 * Makes and returns a deep copy of the weapon w.
	 */
	public ItemWeapon(ItemWeapon w){
		this(
				w.name,
				w.maxValue,
				w.wType,
				w.wDmgBonusAttrib,
				w.wDmgMitigationType,
				new ListAttribute(w.attributeBonuses),
				w.wMT,
				w.wHit,
				w.wCrit,
				w.wRange);
	}
	
	/**
	 * Returns a new Bronze Sword.
	 * @return
	 */
	public static ItemWeapon newBronzeSword(){
		return new ItemWeapon(
				"Bronze Sword",
				500,
				Util.WeaponType.SWORD,
				Util.AttributeType.STR,
				Util.AttributeType.DEF,
				new ListAttribute(0,0,0,0,0,0,0,0),
				5,
				90,
				0,
				1
				);
	}
	
	/**
	 * Returns a new Bronze Lance.
	 * @return
	 */
	public static ItemWeapon newBronzeLance(){
		return new ItemWeapon(
				"Bronze Lance",
				500,
				Util.WeaponType.LANCE,
				Util.AttributeType.STR,
				Util.AttributeType.DEF,
				new ListAttribute(0,0,0,0,0,0,0,0),
				6,
				85,
				0,
				1
				);
	}
	
	/**
	 * Returns a new Bronze Axe.
	 * @return
	 */
	public static ItemWeapon newBronzeAxe(){
		return new ItemWeapon(
				"Bronze Axe",
				500,
				Util.WeaponType.LANCE,
				Util.AttributeType.STR,
				Util.AttributeType.DEF,
				new ListAttribute(0,0,0,0,0,0,0,0),
				7,
				75,
				5,
				1
				);
	}
	
	/**
	 * Sets the Weapon's equipped state to b.
	 * @param b
	 */
	public void setEquipped(boolean b){
		isEquipped = b;
	}
	/**
	 * Gets the Weapon's equipped state.
	 * @return
	 */
	public boolean getEquipped(){
		return isEquipped;
	}
	/**
	 * Gets the Weapon's range.
	 * @return
	 */
	public int getRange(){
		return wRange;
	}

}
