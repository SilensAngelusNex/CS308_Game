
public class Attributes {
	public ListAttribute rawAttribs;
	public ListAttribute growths;
	public ListWeapon wepMastery;
	public int currentHp;

	//Might not need this.
	public ListAttribute bonuses;

	public Attributes(int cHp, int[] r, int[] g, int[] w){
		currentHp = cHp;
		rawAttribs = new ListAttribute(r);
		growths = new ListAttribute(g);
		wepMastery = new ListWeapon(w);
		bonuses = new ListAttribute(0,0,0,0,0,0,0,0);
		
	}

	public ListAttribute levelUp(){
		ListAttribute increased = new ListAttribute(0,0,0,0,0,0,0,0);

		for (GameMap.AttributeType i : GameMap.AttributeType.values()){

			int growth = growths.get(i);
			//TODO: Make adjustment for growth > 100.

			//Increment the characters attribute growth% of the time.
			if (GameMap.rand.nextInt(99) <= growth){
				increased.set(i, 1);
			}
		}
		rawAttribs = rawAttribs.add(increased);
		return increased;
	}

	public int get(GameMap.AttributeType a){
		switch (a){
		case STR:
			return rawAttribs.STR + bonuses.STR;
		case MAG:
			return rawAttribs.MAG + bonuses.MAG;
		case SKL:
			return rawAttribs.SKL + bonuses.SKL;
		case SPD:
			return rawAttribs.SPD + bonuses.SPD;
		case CHA:
			return rawAttribs.CHA + bonuses.CHA;
		case CON:
			return rawAttribs.CON + bonuses.CON;
		case DEF:
			return rawAttribs.DEF + bonuses.DEF;
		case RES:
			return rawAttribs.RES + bonuses.RES;
		default:
			 throw new UnsupportedOperationException(a.toString());
	}
	}

}
