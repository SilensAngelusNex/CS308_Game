
/**
 * This class holds an integer value for each of the twelve weapon type.
 * @author Weston
 *
 */
public class ListWeapon {
	private int SWORD;
	private int AXE;
	private int LANCE;
	private int KNIFE;
	private int BOW;
	private int FIRE;
	private int WIND;
	private int LIGHTNING;
	private int LIGHT;
	private int DARK;
	private int STAFF;
	private int BREATH;
	
	/**
	 * Construct a ListWeapon from twelve int values.
	 * @param sword
	 * @param axe
	 * @param lance
	 * @param knife
	 * @param bow
	 * @param fire
	 * @param wind
	 * @param lightning
	 * @param light
	 * @param dark
	 * @param staff
	 * @param breath
	 */
	public ListWeapon(	int sword, int axe, int lance, int knife, int bow,
						int fire, int wind, int lightning, int light, int dark,
						int staff, int breath){
		SWORD = sword;
		AXE = axe;
		LANCE = lance;
		KNIFE = knife;
		BOW = bow;
		FIRE = fire;
		WIND = wind;
		LIGHTNING = lightning;
		LIGHT = light;
		DARK = dark;
		STAFF = staff;
		BREATH = breath;
	}
	
	/**
	 * Construct a ListWeapon from an int[12]. Throws an IllegalArgumentException if the length of the int array is
	 * not 12.
	 * @param i
	 */
	public ListWeapon(int[] i){
		if (i.length != 12){
			throw new IllegalArgumentException("ListWeapons have twelve fields.");
		}
		SWORD = i[0];
		AXE = i[1];
		LANCE = i[2];
		KNIFE = i[3];
		BOW = i[4];
		FIRE = i[5];
		WIND = i[6];
		LIGHTNING = i[7];
		LIGHT = i[8];
		DARK = i[9];
		STAFF = i[10];
		BREATH = i[11];
		}
	
	/**
	 * Copies ListWeapon i.
	 * @param i
	 */
	public ListWeapon(ListWeapon i){
		SWORD = i.SWORD;
		AXE = i.AXE;
		LANCE = i.LANCE;
		KNIFE = i.KNIFE;
		BOW = i.BOW;
		FIRE = i.FIRE;
		WIND = i.WIND;
		LIGHTNING = i.LIGHTNING;
		LIGHT = i.LIGHT;
		DARK = i.DARK;
		STAFF = i.STAFF;
		BREATH = i.BREATH;
		}
	
	/**
	 * Returns a new ListWeapon that is the sum of the two given.
	 * @param that
	 * @return
	 */
	public ListWeapon add(ListWeapon that){
		return new ListWeapon(
				SWORD + that.SWORD,
				AXE + that.AXE,
				LANCE + that.LANCE,
				KNIFE + that.KNIFE,
				BOW + that.BOW,
				FIRE + that.FIRE,
				WIND + that.WIND,
				LIGHTNING + that.LIGHTNING,
				LIGHT + that.LIGHT,
				DARK + that.DARK,
				STAFF + that.STAFF,
				BREATH + that.BREATH);
	}
	
	/**
	 * Given a WeaponType w, returns the int value for that WeaponType.
	 * @param w
	 * @return
	 */
	public int get(Util.WeaponType w){
		switch (w){
			case SWORD:
				return SWORD;
			case AXE:
				return AXE;
			case LANCE:
				return LANCE;
			case KNIFE:
				return KNIFE;
			case BOW:
				return BOW;
			case FIRE:
				return FIRE;
			case WIND:
				return WIND;
			case LIGHTNING:
				return LIGHTNING;
			case LIGHT:
				return LIGHT;
			case DARK:
				return DARK;
			case STAFF:
				return STAFF;
			case BREATH:
				return BREATH;
			default:
				 throw new UnsupportedOperationException(w.toString());
		}			
	}
	
	/**
	 * Given a WeaponType w, sets w's int value to b.
	 * @param w
	 * @param b
	 */
	public void set(Util.WeaponType w, int b){
		switch (w){
			case SWORD:
				SWORD = b;
				break;
			case AXE:
				AXE = b;
				break;
			case LANCE:
				LANCE = b;
				break;
			case KNIFE:
				KNIFE = b;
				break;
			case BOW:
				BOW = b;
				break;
			case FIRE:
				FIRE = b;
				break;
			case WIND:
				WIND = b;
				break;
			case LIGHTNING:
				LIGHTNING = b;
				break;
			case LIGHT:
				LIGHT = b;
				break;
			case DARK:
				DARK = b;
				break;
			case STAFF:
				STAFF = b;
				break;
			case BREATH:
				BREATH = b;
				break;
			default:
				 throw new UnsupportedOperationException(w.toString());
		}			
	}
}