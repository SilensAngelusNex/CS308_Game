import java.util.Vector;

public class Inventory {
	private Vector<ItemWeapon> weapons;
	private Vector<Item> items;
	private EquipmentSet equipment;
	
	public Inventory(){
		weapons = new Vector<ItemWeapon>(4);
		items = new Vector<Item>(4);
		equipment = new EquipmentSet();
	}
	
	public int add(Item item){
		if (item instanceof ItemWeapon){
			return addWeapon((ItemWeapon) item);
		} else {
			return addItem(item);
		}
	}

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
	
	public boolean remove(Item item){
		if (item instanceof ItemWeapon){
			return items.remove((ItemWeapon) item);
		} else {
			return items.remove(item);
		}
	}
	
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
		if (weapons.size() > 1 && weapons.get(0).equipped){
			weapons.add(1, toEquip);
		} else {
			weapons.add(0, toEquip);
		}
		
		//Equip the new offHand weapon. 
		equipment.setOffHand(toEquip);
	}
	
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
	
	public void unEquipOffHand(){
		equipment.setOffHand(null);
	}
	public void unEquipArmor(){
		equipment.setArmor(null);
	}
	
	public ItemWeapon getOnHand(){
		return equipment.getOnHand();
	}
	public ItemWeapon getOffHand(){
		return equipment.getOffHand();
	}
	public ItemArmor getArmor(){
		return equipment.getArmor();
	}
	public int getHit(){
		return equipment.hit();
	}
	public int getCrit(){
		return equipment.crit();
	}
	public int getMight(){
		return equipment.might();
	}
	public Util.AttributeType getDamageType(){
		return equipment.damageType();
	}
	public int getWeaponsEquipped(){
		return equipment.weaponsEquipped();
	}
	public int getArmorEquipped(){
		return equipment.armorEquipped();
	}

	public int getRange() {
		return equipment.getRange();
	}

	public Point getStaffRange() {
		return equipment.getStaffRange();
	}
	
}
