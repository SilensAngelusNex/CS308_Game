
public class ItemWeapon extends Item {
	protected Util.WeaponType wType;
	protected Util.AttributeType wAttrib;
	protected Util.AttributeType wDmgType;
	protected boolean equipped;
	
	protected ListAttribute aBonuses;
	//TODO: Weapon Weight (attack speed)
	protected int myRange;
	protected int wCrit;
	protected int wHit;
	protected int wMT;

	public ItemWeapon(String n, int value, Util.WeaponType type, Util.AttributeType attrib,
			Util.AttributeType dmgType, ListAttribute bonus, int mt, int hit, int crit, int range){
		super(n, value);
		wType = type;
		wAttrib = attrib;
		wDmgType = dmgType;
		aBonuses = bonus;
		wCrit = crit;
		wHit = hit;
		wMT = mt;
		myRange = range;
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
				w.wCrit,
				w.myRange);
	}
	
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
	
	public void setEquipped(boolean b){
		equipped = b;
	}
	public boolean getEquipped(){
		return equipped;
	}
	
	public int getRange(){
		return myRange;
	}

}
