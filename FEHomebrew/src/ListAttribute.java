/**
 * This class holds an integer value for each of the eight attributes.
 * @author Weston
 *
 */
public class ListAttribute {
	private int STR;
	private int MAG;
	private int SKL;
	private int SPD;
	private int CHA;
	private int CON;
	private int DEF;
	private int RES;
		
	/**
	 * Constructs a ListAttribute from eight int values.
	 * @param str
	 * @param mag
	 * @param skl
	 * @param spd
	 * @param cha
	 * @param con
	 * @param def
	 * @param res
	 */
	public ListAttribute(int str, int mag, int skl, int spd, int cha, int con, int def, int res){
		STR = str;
		MAG = mag;
		SKL = skl;
		SPD = spd;
		CHA = cha;
		CON = con;
		DEF = def;
		RES = res;
	}
	
	/**
	 * Construct a ListAttribute from an int[8]. Throws an IllegalArgumentException if the length of the int array
	 * is not 8.
	 * @param i
	 */
	public ListAttribute(int[] i){
		if (i.length != 8){
			throw new IllegalArgumentException("ListAttributes have eight fields.");
		}
		STR = i[0];
		MAG = i[1];
		SKL = i[2];
		SPD = i[3];
		CHA = i[4];
		CON = i[5];
		DEF = i[6];
		RES = i[7];
	}
	
	/**
	 * Copies a LisAttribute.
	 * @param l
	 */
	public ListAttribute(ListAttribute l){
		STR = l.STR;
		MAG = l.MAG;
		SKL = l.SKL;
		SPD = l.SPD;
		CHA = l.CHA;
		CON = l.CON;
		DEF = l.DEF;
		RES = l.RES;
	}
	
	/**
	 * Given an AttributeType a, returns the int value for that AttributeType.
	 * @param a
	 * @return
	 */
	public int get(Util.AttributeType a){
		switch (a){
			case STR:
				return STR;
			case MAG:
				return MAG;
			case SKL:
				return SKL;
			case SPD:
				return SPD;
			case CHA:
				return CHA;
			case CON:
				return CON;
			case DEF:
				return DEF;
			case RES:
				return RES;
			default:
				 throw new UnsupportedOperationException(a.toString());	
		}
	}
	
	/**
	 * Given an AttributeType a, sets the int value for that AttributeType to b.
	 * @param a
	 * @param b
	 */
	public void set(Util.AttributeType a, int b){
		switch (a){
			case STR:
				STR = b;
				break;
			case MAG:
				MAG = b;
				break;
			case SKL:
				SKL = b;
				break;
			case SPD:
				SPD = b;
				break;
			case CHA:
				CHA = b;
				break;
			case CON:
				CON = b;
				break;
			case DEF:
				DEF = b;
				break;
			case RES:
				RES = b;
				break;
			default:
				 throw new UnsupportedOperationException(a.toString());	
		}
	}
	
	/**
	 * Adds two ListAttributes together.
	 * @param that
	 * @return
	 */
	public ListAttribute add(ListAttribute that){

		return new ListAttribute(
				STR + that.STR,
				MAG + that.MAG,
				SKL + that.SKL,
				SPD + that.SPD,
				CHA + that.CHA,
				CON + that.CON,
				DEF + that.DEF,
				RES + that.RES);
	}
	
	/**
	 * Returns a String representation of the ListAttribute.
	 */
	public String toString(){
		return String.format(
				"STR\tMAG\tSKL\tSPD\tCHA\tCON\tDEF\tRES\n%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\n",
				STR, MAG,SKL, SPD, CHA, CON, DEF, RES);
	}

}