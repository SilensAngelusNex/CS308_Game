
public class ItemWeapon extends Item {
	public GameMap.WeaponType wType;
	public GameMap.AttributeType wAttrib;
	public GameMap.AttributeType wDmgType;
	public boolean equipped;
	
	public ListAttribute aBonuses;
	//TODO: Weapon Weight
	public int wCrit;
	public int wHit;
	public int wMT;

	public ItemWeapon(String n, int value, GameMap.WeaponType type, GameMap.AttributeType attrib,
			GameMap.AttributeType dmgType, ListAttribute bonus, int mt, int hit, int crit){
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
				GameMap.WeaponType.SWORD,
				GameMap.AttributeType.STR,
				GameMap.AttributeType.DEF,
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

}
