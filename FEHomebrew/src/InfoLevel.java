
public class InfoLevel {
	private int tier;
	private int level;
	private int exp;
	
	public InfoLevel(){
		tier = 1;
		level = 1;
		exp = 0;
	}
	public InfoLevel(int i){
		tier = 1;
		level = i;
		exp = 0;
	}
	
	public int totalLevel(){
		return level + 20 * (tier - 1);
	}
	
	public int levelUp(){
		exp = 0;
		return level++;
	}
	
	public int gainExp(int gains){
		int levelsGained = 0;
		
		while (gains > 0){
			if (gains >= 100 - exp){
				gains -= 100 - exp;
				exp = 100;
				levelsGained++;
				levelUp();
			} else {
				exp += gains;
				gains = 0;
			}
		}
		return levelsGained;
	}
	
	public int getTier(){
		return tier;
	}
	public int getExp(){
		return exp;
	}
	
}
