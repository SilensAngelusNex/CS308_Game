public class ListAttribute {
		private int STR;
		private int MAG;
		private int SKL;
		private int SPD;
		private int CHA;
		private int CON;
		private int DEF;
		private int RES;
		
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
		
		public void printAttributes(){
			System.out.print("STR\tMAG\tSKL\tSPD\tCHA\tCON\tDEF\tRES\n");
		System.out.printf("%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\n", STR, MAG, SKL, SPD, CHA, CON, DEF, RES);
	}

}