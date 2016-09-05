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
		//Unequip the current onHand weapon
		unEquipOnHand();
		
		//Don't allow equipping the same weapon to both hands.
		if (weapons.get(i).equals(equipment.offHand)){
			unEquipOffHand();
		}
		
		//Put new onHand weapon at the start of the weapons list
		ItemWeapon onHand = weapons.get(i);
		weapons.remove(i);
		weapons.add(0, onHand);
		
		//Equip the new onHand weapon. 
		onHand.equipped = true;
		equipment.onHand = onHand;
		
	}
	
	public void equipOffHand(int i){
		//Unequip the current offHand weapon
		unEquipOffHand();
		
		//Don't allow equipping the same weapon to both hands.
		if (weapons.get(i).equals(equipment.onHand)){
			unEquipOnHand();
		}
		
		//Put new offHand weapon at the start of the weapons list, after the onHand weapon
		ItemWeapon offHand = weapons.get(i);
		weapons.remove(i);
		if (weapons.get(0).equipped){
			weapons.add(1, offHand);
		} else {
			weapons.add(0, offHand);
		}
		
		//Equip the new offHand weapon. 
		offHand.equipped = true;
		equipment.offHand = offHand;
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
	
	public void unEquipOffHand(){
		equipment.offHand.equipped = false;
		equipment.offHand = null;
	}
	
	public void unEquipArmor(){
		equipment.armor.equipped = false;
		equipment.armor = null;
	}
	
}
