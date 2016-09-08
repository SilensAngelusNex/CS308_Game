
public class ItemArmor extends Item {
	protected boolean equipped;
	protected ListAttribute aBonuses;
	
	public ItemArmor(String n, int value, ListAttribute bonuses) {
		super(n, value);
		aBonuses = bonuses;
	}
	
	public boolean getEquipped(){
		return equipped;
	}
	public void setEquipped(boolean b){
		equipped = b;
	}
	public ListAttribute getBonuses(){
		return aBonuses;
	}

}
