
/**
 * Contains information about a character's current level and experience status.
 * @author Weston
 *
 */
public class InfoLevel {
	private int tier;
	private int level;
	private int exp;
	
	/**
	 * Constructs an InfoLevel for a level 1 character.
	 */
	public InfoLevel(){
		tier = 1;
		level = 1;
		exp = 0;
	}
	/**
	 * Constructs an InfoLevel for a level i character.
	 * @param i
	 */
	public InfoLevel(int i){
		tier = 1;
		level = i;
		exp = 0;
	}
	
	/**
	 * Returns the character's level, plus 20 for each time they've class changed. (Class Change unimplemented.) 
	 * @return
	 */
	public int totalLevel(){
		return level + 20 * (tier - 1);
	}
	
	/**
	 * Resets EXP and adds 1 to level.
	 * @return new level
	 */
	public int levelUp(){
		exp = 0;
		return ++level;
	}
	
	/**
	 * Gains gains EXP, checking for level ups on the way.
	 * @param gains
	 * @return number of levels gained
	 */
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
	
	/**
	 * Gets tier.
	 * @return
	 */
	public int getTier(){
		return tier;
	}
	/**
	 * Gets current EXP.
	 * @return
	 */
	public int getExp(){
		return exp;
	}
}
