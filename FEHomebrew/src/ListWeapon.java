
public class ListWeapon {
		public int SWORD;
		public int AXE;
		public int LANCE;
		public int KNIFE;
		public int BOW;
		public int FIRE;
		public int WIND;
		public int LIGHTNING;
		public int LIGHT;
		public int DARK;
		public int STAFF;
		public int BREATH;
		
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
		
		public void printAttributes(){
			System.out.print("SWORD\tAXE\tLANCE\tKNIFE\tBOW\tFIRE\tWIND\tLIGHTNING\tLIGHT\tDARK\tSTAFF\tBREATH\n");
			System.out.printf("%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t\t%d\t%d\t%d\t%d\n", SWORD, AXE, LANCE, KNIFE, BOW, FIRE, WIND, LIGHTNING, LIGHT, DARK, STAFF, BREATH);
		}

		public int get(GameMap.WeaponType w){
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
		public void set(GameMap.WeaponType w, int b){
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
		
		public static void main(String[] args){
			ListWeapon a = new ListWeapon(0,0,0,0,0,0,0,0,0,0,0,0);
			a.STAFF++;
			a.printAttributes();
			
			ListWeapon b = new ListWeapon(1,0,45,0,6,0,0,0,0,0,0,0);
			b.printAttributes();
			
		}

	}