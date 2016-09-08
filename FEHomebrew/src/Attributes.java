
public class Attributes {
	private ListAttribute rawAttribs;
	private ListAttribute growths;
	private ListWeapon wepMastery;
	private int currentHp;
	//TODO: movement

	//Might not need this.
	private ListAttribute bonuses;

	public Attributes(int cHp, int[] r, int[] g, int[] w){
		currentHp = cHp;
		rawAttribs = new ListAttribute(r);
		growths = new ListAttribute(g);
		wepMastery = new ListWeapon(w);
		bonuses = new ListAttribute(0,0,0,0,0,0,0,0);
		
	}

	public ListAttribute levelUp(){
		ListAttribute increased = new ListAttribute(0,0,0,0,0,0,0,0);

		for (Util.AttributeType i : Util.AttributeType.values()){

			int growth = growths.get(i);
			//TODO-LongTerm: Make adjustment for growth > 100.

			//Increment the characters attribute growth% of the time.
			if (Util.rand.nextInt(99) <= growth){
				increased.set(i, 1);
			}
		}
		rawAttribs = rawAttribs.add(increased);
		return increased;
	}

	public int get(Util.AttributeType a){
		switch (a){
		case STR:
			return rawAttribs.get(Util.AttributeType.STR) + bonuses.get(Util.AttributeType.STR);
		case MAG:
			return rawAttribs.get(Util.AttributeType.MAG) + bonuses.get(Util.AttributeType.MAG);
		case SKL:
			return rawAttribs.get(Util.AttributeType.SKL) + bonuses.get(Util.AttributeType.SKL);
		case SPD:
			return rawAttribs.get(Util.AttributeType.SPD) + bonuses.get(Util.AttributeType.SPD);
		case CHA:
			return rawAttribs.get(Util.AttributeType.CHA) + bonuses.get(Util.AttributeType.CHA);
		case CON:
			return rawAttribs.get(Util.AttributeType.CON) + bonuses.get(Util.AttributeType.CON);
		case DEF:
			return rawAttribs.get(Util.AttributeType.DEF) + bonuses.get(Util.AttributeType.DEF);
		case RES:
			return rawAttribs.get(Util.AttributeType.RES) + bonuses.get(Util.AttributeType.RES);
		default:
			 throw new UnsupportedOperationException(a.toString());
		}
	}
	
	public ListAttribute getRawAttribs(){
		return rawAttribs;
	}
	public ListAttribute getBonuses(){
		return bonuses;
	}
	public ListWeapon getWepMastery(){
		return wepMastery;
	}
	public int getCurrHP(){
		return currentHp;
	}
	public void setCurrHP(int i){
		currentHp = i;
	}
	public void addCurrHP(int i){
		currentHp += i;
	}

}
