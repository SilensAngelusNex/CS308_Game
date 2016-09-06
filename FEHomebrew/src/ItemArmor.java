
public class ItemArmor extends Item {
	public boolean equipped;
	public ListAttribute aBonuses;
	
	public ItemArmor(String n, int value, ListAttribute bonuses) {
		super(n, value);
		aBonuses = bonuses;
	}
}
