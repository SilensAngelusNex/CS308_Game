import java.util.Vector;

public class Inventory {
	public Vector<ItemWeapon> weapons;
	public Vector<Item> items;
	public EquipmentSet equipment;
	
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
	
	public void equipOnHand(int i){
		ItemWeapon toEquip = weapons.get(i);
		
		//Unequip the current onHand weapon
		unEquipOnHand();
		
		//Don't allow equipping the same weapon to both hands.
		if (toEquip.equals(equipment.offHand)){
			unEquipOffHand();
		}
		
		//Put new onHand weapon at the start of the weapons list
		weapons.remove(toEquip);
		weapons.add(0, toEquip);
		
		//Equip the new onHand weapon. 
		toEquip.equipped = true;
		equipment.onHand = toEquip;
		
	}
	
	public void equipOffHand(int i){
		ItemWeapon toEquip = weapons.get(i);
		
		//Unequip the current offHand weapon
		unEquipOffHand();
		
		//Don't allow equipping the same weapon to both hands.
		if (toEquip.equals(equipment.onHand)){
			unEquipOnHand();
		}
		
		//Put new offHand weapon at the start of the weapons list, after the onHand weapon
		
		weapons.remove(toEquip);
		if (weapons.get(0).equipped){
			weapons.add(1, toEquip);
		} else {
			weapons.add(0, toEquip);
		}
		
		//Equip the new offHand weapon. 
		toEquip.equipped = true;
		equipment.offHand = toEquip;
	}
	
	public void equipArmor(int i){
		//Unequip the current armor.
		unEquipArmor();
		
		//Put new armor at the start of the items list
		ItemArmor armor = (ItemArmor) items.get(i);
		items.remove(i);
		items.add(0, (Item) armor);
		
		//Equip the new armor. 
		armor.equipped = true;
		equipment.armor = armor;
	}
	
	public void unEquipOnHand(){
		if (equipment.onHand != null){
			//If there's an offHand weapon equipped, it rises to to the top of the weapon list
			if (equipment.offHand != null){
				ItemWeapon onHand = equipment.onHand;
				weapons.remove(onHand);
				weapons.add(1, onHand);
			}
			
			//Then unequip the onHand weapon
			equipment.onHand.equipped = false;
			equipment.onHand = null;
		}
		
	}
	
	public void unEquipOffHand(){
		if (equipment.offHand != null){
			equipment.offHand.equipped = false;
			equipment.offHand = null;
		}
	}
	
	public void unEquipArmor(){
		if (equipment.armor != null){
			equipment.armor.equipped = false;
			equipment.armor = null;
		}
	}
	
}
