
public class ItemWeapon extends Item {
	protected Util.WeaponType wType;
	protected Util.AttributeType wAttrib;
	protected Util.AttributeType wDmgType;
	protected boolean equipped;
	
	protected ListAttribute aBonuses;
	//TODO: Weapon Weight (attack speed)
	protected int wCrit;
	protected int wHit;
	protected int wMT;

	public ItemWeapon(String n, int value, Util.WeaponType type, Util.AttributeType attrib,
			Util.AttributeType dmgType, ListAttribute bonus, int mt, int hit, int crit){
		super(n, value);
		wType = type;
		wAttrib = attrib;
		wDmgType = dmgType;
		aBonuses = bonus;
		wCrit = crit;
		wHit = hit;
		wMT = mt;
	}
	
	public ItemWeapon(){
		this(
				"Bronze Sword",
				500,
				Util.WeaponType.SWORD,
				Util.AttributeType.STR,
				Util.AttributeType.DEF,
				new ListAttribute(0,0,0,0,0,0,0,0),
				5,
				90,
				0
				);
	}
	
	/*
	 * Makes and returns a deep copy of the weapon w.
	 */
	public ItemWeapon(ItemWeapon w){
		this(
				w.name,
				w.maxValue,
				w.wType,
				w.wAttrib,
				w.wDmgType,
				new ListAttribute(w.aBonuses),
				w.wMT,
				w.wHit,
				w.wCrit);
	}
	
	public void setEquipped(boolean b){
		equipped = b;
	}

}
