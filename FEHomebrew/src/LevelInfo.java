
public class LevelInfo {
	public int tier;
	public int level;
	public int exp;
	
	public LevelInfo(){
		tier = 1;
		level = 1;
		exp = 0;
	}
	
	public int totalLevel(){
		return level + 20 * (tier - 1);
	}
	
	public int levelUp(){
		return level++;
	}
}
