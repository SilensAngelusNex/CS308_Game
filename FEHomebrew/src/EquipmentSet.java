// This entire file is part of my masterpiece.
// WESTON CARVALHO

public class EquipmentSet {
	private ItemWeapon onHand;
	private ItemWeapon offHand;
	private ItemArmor armor;
	
	/**
	 * Construct empty equipment set
	 */
	public EquipmentSet(){
		onHand = null;
		offHand = null;
		armor = null;
	}
	
	/**
	 * @return attribute bonuses weapons grant
	 */
	public ListAttribute weaponBonus(){
		return onHand.attributeBonuses.add(offHand.attributeBonuses);
	}
	/**
	 * @return attribute bonuses armor grants
	 */
	public ListAttribute armorBonus(){
		return armor.getBonuses();
	}
	
	/**
	 * @return number of weapons equipped (max 2)
	 */
	public int weaponsEquipped(){
		if (onHand != null){
			if (offHand != null){
				return 2;
			}
			return 1;
		}
		return 0;
	}
	
	/**
	 * @return number of armors equipped (max 1)
	 */
	public int armorEquipped(){
		if (armor != null){
			return 1;
		}
		return 0;
	}
	
	/**
	 * @return attack bonus from equipment
	 */
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
	/**
	 * @return hit bonus from equipment
	 */
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
	/**
	 * @return crit bonus from equipment
	 */
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
	/**
	 * @return DmgMitigationType of onHand weapon
	 */
	public Util.AttributeType damageType(){
		if (onHand != null){
			return onHand.wDmgMitigationType;
		} else if (offHand != null){
			return offHand.wDmgMitigationType;
		}
		return Util.AttributeType.STR;
	}
	
	/**
	 * @return onHand weapon
	 */
	public ItemWeapon getOnHand(){
		return onHand;
	}
	/**
	 * @return offHand weapon
	 */
	public ItemWeapon getOffHand(){
		return offHand;
	}
	/**
	 * @return armor
	 */
	public ItemArmor getArmor(){
		return armor;
	}
	
	/**
	 * Set onHand to toEquip
	 * @param toEquip
	 */
	public void setOnHand(ItemWeapon toEquip){
		if (toEquip != null){
			toEquip.setEquipped(true);
		}
		onHand = toEquip;
	}
	/**
	 * Set offHand to toEquip
	 * @param toEquip
	 */
	public void setOffHand(ItemWeapon toEquip){
		if (toEquip != null){
			toEquip.setEquipped(true);
		}		offHand = toEquip;
	}
	/**
	 * Set armor to toEquip
	 * @param toEquip
	 */
	public void setArmor(ItemArmor toEquip){
		if (toEquip != null){
			toEquip.setEquipped(true);
		}		armor = toEquip;
	}

	/**
	 * 
	 * @return onHand weapon range
	 */
	public int getRange() {
		if (onHand != null)
			return onHand.getRange();
		else if (offHand != null)
			return offHand.getRange();
		else
			return 0;
	}

	/**
	 * 
	 * @return staff range
	 */
	public Point getStaffRange() {
		if (offHand != null && offHand instanceof ItemWeaponStaff)
			return ((ItemWeaponStaff) offHand).staffRange();
		else 
			return new Point(0, 0);
	}
	

}
