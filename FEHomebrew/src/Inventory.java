import java.util.Vector;

/**
 * This class is how characters store Items. There are two Vectors, one for weapons, and one for not weapons. Inventory
 * also keeps track of which weapons a character has equipped.
 * @author Weston
 *
 */
public class Inventory {
	private Vector<ItemWeapon> weapons;
	private Vector<Item> items;
	private EquipmentSet equipment;
	
	/**
	 * Constructs empty inventory.
	 */
	public Inventory(){
		weapons = new Vector<ItemWeapon>(4);
		items = new Vector<Item>(4);
		equipment = new EquipmentSet();
	}
	
	/**
	 * Adds item into the inventory
	 * @param item
	 * @return -1 if no space, 0 if exact space, 1 else
	 */
	public int add(Item item){
		if (item instanceof ItemWeapon){
			return addWeapon((ItemWeapon) item);
		} else {
			return addItem(item);
		}
	}

	/**
	 * Adds weapon into the inventory
	 * @param item
	 * @return -1 if no space, 0 if exact space, 1 else
	 */
	private int addWeapon(ItemWeapon weapon){
		if (weapons.size() > 4){
			return -1;
		} else if (weapons.size() == 4){
			weapons.add(weapon);
			return 0;
		} else {
			weapons.add(weapon);
			return 1;
		}
	}
	
	/**
	 * Adds non-weapon into the inventory
	 * @param item
	 * @return -1 if no space, 0 if exact space, 1 else
	 */
	private int addItem(Item item){
		if (items.size() > 4){
			return -1;
		} else if (items.size() == 4){
			items.add(item);
			return 0;
		} else {
			items.add(item);
			return 1;
		}
	}
	
	/**
	 * Removes item from inventory.
	 * @param item
	 * @return true iff successful
	 */
	public boolean remove(Item item){
		if (item instanceof ItemWeapon){
			return items.remove((ItemWeapon) item);
		} else {
			return items.remove(item);
		}
	}
	
	/**
	 * Equips the weapon in slot i to the onHand
	 * @param i
	 */
	public void equipOnHand(int i){
		ItemWeapon toEquip = weapons.get(i);
		
		//Unequip the current onHand weapon
		unEquipOnHand();
		
		//Don't allow equipping the same weapon to both hands.
		if (toEquip.equals(equipment.getOffHand())){
			unEquipOffHand();
		}
		
		//Put new onHand weapon at the start of the weapons list
		weapons.remove(toEquip);
		weapons.add(0, toEquip);
		
		//Equip the new onHand weapon. 
		equipment.setOnHand(toEquip);
		
	}
	
	/**
	 * Equips the weapon in slot i to the offHand
	 * @param i
	 */
	public void equipOffHand(int i){
		ItemWeapon toEquip = weapons.get(i);
		
		//Unequip the current offHand weapon
		unEquipOffHand();
		
		//Don't allow equipping the same weapon to both hands.
		if (toEquip.equals(equipment.getOnHand())){
			unEquipOnHand();
		}
		
		//Put new offHand weapon at the start of the weapons list, after the onHand weapon
		
		weapons.remove(toEquip);
		if (weapons.size() > 1 && weapons.get(0).isEquipped){
			weapons.add(1, toEquip);
		} else {
			weapons.add(0, toEquip);
		}
		
		//Equip the new offHand weapon. 
		equipment.setOffHand(toEquip);
	}
	
	/**
	 * Equips the armor in slot i
	 * @param i
	 */
	public void equipArmor(int i){
		//Unequip the current armor.
		unEquipArmor();
		
		//Put new armor at the start of the items list
		ItemArmor armor = (ItemArmor) items.get(i);
		items.remove(i);
		items.add(0, (Item) armor);
		
		//Equip the new armor. 
		equipment.setArmor(armor);
	}
	
	/**
	 * Unequips the onHand weapon
	 */
	public void unEquipOnHand(){
		if (equipment.getOnHand() != null){
			//If there's an offHand weapon equipped, it rises to to the top of the weapon list
			if (equipment.getOffHand()!= null){
				ItemWeapon onHand = equipment.getOnHand();
				weapons.remove(onHand);
				weapons.add(1, onHand);
			}
			
			//Then unequip the onHand weapon
			equipment.setOnHand(null);
		}
		
	}
	
	/**
	 * Unequips the offHand weapon
	 */
	public void unEquipOffHand(){
		equipment.setOffHand(null);
	}
	/**
	 * Doffs arrmor
	 */
	public void unEquipArmor(){
		equipment.setArmor(null);
	}
	
	/**
	 * @return onHand weapon
	 */
	public ItemWeapon getOnHand(){
		return equipment.getOnHand();
	}
	/**
	 * @return offHand weapon
	 */
	public ItemWeapon getOffHand(){
		return equipment.getOffHand();
	}
	/**
	 * @return armor
	 */
	public ItemArmor getArmor(){
		return equipment.getArmor();
	}
	/**
	 * @return equipment's hit bonus
	 */
	public int getHit(){
		return equipment.hit();
	}
	/**
	 * @return equipment's crit bonus
	 */
	public int getCrit(){
		return equipment.crit();
	}
	/**
	 * @return equipment's attack bonus
	 */
	public int getMight(){
		return equipment.might();
	}
	/**
	 * @return equipment's dmg reduction type
	 */
	public Util.AttributeType getDamageType(){
		return equipment.damageType();
	}
	/**
	 * @return the number of weapons equipped (max 2)
	 */
	public int getWeaponsEquipped(){
		return equipment.weaponsEquipped();
	}
	/**
	 * @return the number of armors equipped (max 1)
	 */
	public int getArmorEquipped(){
		return equipment.armorEquipped();
	}
	/**
	 * @return equipment's range
	 */
	public int getRange() {
		return equipment.getRange();
	}
	/**
	 * @return equipment's staff range
	 */
	public Point getStaffRange() {
		return equipment.getStaffRange();
	}
	
}
