
public class EquipmentSet {
	public ItemWeapon onHand;
	public ItemWeapon offHand;
	public ItemArmor armor;
	
	public EquipmentSet(){
		onHand = null;
		offHand = null;
		armor = null;
	}
	
	public ListAttribute weaponBonus(){
		return onHand.aBonuses.add(offHand.aBonuses);
	}
	public ListAttribute armorBonus(){
		return armor.aBonuses;
	}
	
	public int weaponsEquipped(){
		if (onHand != null){
			if (offHand != null){
				return 2;
			}
			return 1;
		}
		return 0;
	}
	
	public int armorEquipped(){
		if (armor != null){
			return 1;
		}
		return 0;
	}
	
	public int might(){
		int result = 0;
		if (onHand != null){
			result += onHand.wMT;
		}
		if (offHand != null){
			result += offHand.wMT;
		}
		return result;
	}
	
	public int hit(){
		int result = 0;
		if (onHand != null){
			result += onHand.wHit;
		}
		if (offHand != null){
			result += offHand.wHit;
		}
		return result;
	}
	public int crit(){
		int result = 0;
		if (onHand != null){
			result += onHand.wCrit;
		}
		if (offHand != null){
			result += offHand.wCrit;
		}
		return result;
	}
	
	public Util.AttributeType damageType(){
		if (onHand != null){
			return onHand.wDmgType;
		} else if (offHand != null){
			return offHand.wDmgType;
		}
		return Util.AttributeType.STR;
	}

}
