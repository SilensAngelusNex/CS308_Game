/**
 * Attributes say everything about a character's inherent skills and abilities, including their raw Attributes,
 * attribute growth rates, and weapon mastery.
 * @author Weston
 *
 */
public class Attributes {
	private ListAttribute myRawAttribs;
	private ListAttribute myGrowths;
	private ListWeapon myWeaponMastery;
	private int myCurrentHp;
	//TODO: add movement

	//Might not need this. Never, used it, but it works if I want it.
	private ListAttribute bonuses;

	/**
	 * Constructs Attributes from currentHp, raw attibutes, growths, and weapon mastery.
	 * @param currentHp
	 * @param rawAttributes
	 * @param growths
	 * @param weaponMastery
	 */
	public Attributes(int currentHp, int[] rawAttributes, int[] growths, int[] weaponMastery){
		myCurrentHp = currentHp;
		myRawAttribs = new ListAttribute(rawAttributes);
		myGrowths = new ListAttribute(growths);
		myWeaponMastery = new ListWeapon(weaponMastery);
		bonuses = new ListAttribute(0,0,0,0,0,0,0,0);
		
	}

	/**
	 * Levels up, giving each raw attribute that attribute's growth rate % chance to increase by 1.
	 * @return a ListAttribute of the increased attributes
	 */
	public ListAttribute levelUp(){
		ListAttribute increased = new ListAttribute(0,0,0,0,0,0,0,0);

		for (Util.AttributeType i : Util.AttributeType.values()){

			int growth = myGrowths.get(i);
			//TODO-LongTerm: Make adjustment for growth > 100.

			//Increment the characters attribute growth% of the time.
			if (Util.random.nextInt(99) <= growth){
				increased.set(i, 1);
			}
		}
		myRawAttribs = myRawAttribs.add(increased);
		return increased;
	}
	
	/**
	 * Given an AttributeType, returns that raw attribute plus that attribute's bonus.
	 * @param a
	 * @return
	 */
	public int get(Util.AttributeType a){
		switch (a){
		case STR:
			return myRawAttribs.get(Util.AttributeType.STR) + bonuses.get(Util.AttributeType.STR);
		case MAG:
			return myRawAttribs.get(Util.AttributeType.MAG) + bonuses.get(Util.AttributeType.MAG);
		case SKL:
			return myRawAttribs.get(Util.AttributeType.SKL) + bonuses.get(Util.AttributeType.SKL);
		case SPD:
			return myRawAttribs.get(Util.AttributeType.SPD) + bonuses.get(Util.AttributeType.SPD);
		case CHA:
			return myRawAttribs.get(Util.AttributeType.CHA) + bonuses.get(Util.AttributeType.CHA);
		case CON:
			return myRawAttribs.get(Util.AttributeType.CON) + bonuses.get(Util.AttributeType.CON);
		case DEF:
			return myRawAttribs.get(Util.AttributeType.DEF) + bonuses.get(Util.AttributeType.DEF);
		case RES:
			return myRawAttribs.get(Util.AttributeType.RES) + bonuses.get(Util.AttributeType.RES);
		default:
			 throw new UnsupportedOperationException(a.toString());
		}
	}
	
	/**
	 * @return raw Attributes
	 */
	public ListAttribute getRawAttribs(){
		return myRawAttribs;
	}
	/**
	 * @return Attribute bonuses
	 */
	public ListAttribute getBonuses(){
		return bonuses;
	}
	/**
	 * @return weapon mastery values
	 */
	public ListWeapon getWepMastery(){
		return myWeaponMastery;
	}
	/**
	 * @return current HP
	 */
	public int getCurrHP(){
		return myCurrentHp;
	}
	/**
	 * Sets current HP to i.
	 * @param i
	 */
	public void setCurrHP(int i){
		myCurrentHp = i;
	}
	/**
	 * Adds i to current HP.
	 * @param i
	 */
	public void addCurrHP(int i){
		myCurrentHp += i;
	}

}
