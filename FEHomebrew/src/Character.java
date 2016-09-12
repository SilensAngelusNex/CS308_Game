import java.util.Map;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Character extends ImageView{
	private static final int NO_DMG_BATTLE_EXP = 1;
	private static final int DEFAULT_MOV = 6;
	
	private String myName;
	private Util.CharacterClasses myClass;
	private Attributes myAttribs;
	private Point myLocation;
	private int myHeight;
	private int myWidth;
	private InfoLevel myLevel;
	private Inventory myInventory;
	private boolean isBoss;
	private int myMov;
	private InfoTurn myTurnInfo;
	private Map<Point, Integer> myValidMoves;
	private boolean isDead;
	
	
	
	/**
	 * Used when characters die in the middle of combat to stop the combat.
	 * @author Weston
	 *
	 */
	private class CharacterDeadException extends Exception{
		private static final long serialVersionUID = -3374130461047276835L;
		Character dead;
		int finishingDmg;
		
		protected CharacterDeadException(Character c, int d){
			super();
			dead = c;
			finishingDmg = d;
		}
	}
		
	/**
	 * Construct a character from the required fields.
	 * @param name
	 * @param charClass
	 * @param attribs
	 * @param level
	 * @param inv
	 */
	public Character(String name, Util.CharacterClasses charClass, Attributes attribs, InfoLevel level, Inventory inv){
		super();
		myName = name;
		setImage(new Image(getClass().getClassLoader().getResourceAsStream(String.format("%s%s", myName, ".png"))));
		myClass = charClass;
		myAttribs = attribs;
		myLevel = level;
		myInventory = inv;
		isBoss = false;
		myMov = DEFAULT_MOV;
		myTurnInfo = new InfoTurn();
		isDead = false;
	}
	
	/**
	 * @param p
	 * @param height
	 * @param width
	 * @return new correctly sized Ike
	 */
	public static Character newIke(Point p, int height, int width){
		Character ike = new Character(
				"Ike",
				Util.CharacterClasses.Swordsman,
				new Attributes(19,
						new int[] {5, 1, 6, 7, 5, 19, 5, 0},
						new int[] {50, 20, 50, 55, 35, 75, 40, 40},
						new int[] {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}),
				new InfoLevel(),
				new Inventory()
				);
		
		ike.isBoss = true;
		
		ike.take(ItemWeapon.newBronzeSword());
		ike.myInventory.equipOnHand(0);
		ike.myLocation = p;
		
		ike.myHeight = height;
		ike.myWidth = width;
		
		ike.setFitHeight(height);
		ike.setFitWidth(width);
		ike.moveTo(p);
		
		return ike;
		
	}
	
	/**
	 * @param p
	 * @param height
	 * @param width
	 * @return new correctly sized Boyd
	 */
	public static Character newBoyd(Point p, int height, int width){
		Character boyd = new Character(
				"Boyd",
				Util.CharacterClasses.Warrior,
				new Attributes(20,
						new int[] {6, 0, 9, 6, 4, 20, 3, 1},
						new int[] {60, 5, 50, 45, 35, 75, 25, 25},
						new int[] {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}),
				new InfoLevel(),
				new Inventory()
				);
		
		boyd.take(ItemWeapon.newBronzeAxe());
		boyd.myInventory.equipOnHand(0);
		boyd.myLocation = p;
		
		boyd.myHeight = height;
		boyd.myWidth = width;
		
		boyd.setFitHeight(height);
		boyd.setFitWidth(width);
		boyd.moveTo(p);
		
		return boyd;
		}
	
	/**
	 * @param p
	 * @param height
	 * @param width
	 * @return new correctly sized Mia
	 */
	public static Character newMia(Point p, int height, int width){
		Character mia = new Character(
				"Mia",
				Util.CharacterClasses.Swordsman,
				new Attributes(19,
						new int[] {5, 0, 8, 10, 4, 19, 6, 1},
						new int[] {40, 30, 45, 60, 45, 50, 20, 25},
						new int[] {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}),
				new InfoLevel(),
				new Inventory()
				);
		
		mia.isBoss = true;
		
		mia.take(ItemWeapon.newBronzeSword());
		mia.myInventory.equipOnHand(0);
		mia.myLocation = p;
		
		mia.myHeight = height;
		mia.myWidth = width;
		
		mia.setFitHeight(height);
		mia.setFitWidth(width);
		mia.moveTo(p);
		
		return mia;
		
	}
	
	/**
	 * @param p
	 * @param height
	 * @param width
	 * @return new correctly sized Diana
	 */
	public static Character newDiana(Point p, int height, int width){
		Character diana = new Character(
				"Diana",
				Util.CharacterClasses.Soldier,
				new Attributes(19,
						new int[] {6, 4, 7, 8, 5, 19, 7, 2},
						new int[] {45, 30, 60, 55, 75, 55, 30, 25},
						new int[] {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}),
				new InfoLevel(),
				new Inventory()
				);
		
		diana.isBoss = true;
		
		diana.take(ItemWeapon.newBronzeLance());
		diana.myInventory.equipOnHand(0);
		diana.myLocation = p;
		
		diana.myHeight = height;
		diana.myWidth = width;
		
		diana.setFitHeight(height);
		diana.setFitWidth(width);
		diana.moveTo(p);
		
		return diana;
		
	}
	
	/**
	 * @param p
	 * @param height
	 * @param width
	 * @return new correctly sized Rin
	 */
	public static Character newRin(Point p, int height, int width){
		Character rin = new Character(
				"Rin",
				Util.CharacterClasses.Priest,
				new Attributes(12,
						new int[] {4, 6, 4, 7, 6, 12, 2, 7},
						new int[] {40, 50, 60, 45, 25, 40, 20, 45},
						new int[] {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}),
				new InfoLevel(),
				new Inventory()
				);
		
		rin.take(ItemWeaponStaff.newHealStaff());
		rin.myInventory.equipOffHand(0);
		rin.myLocation = p;
		
		rin.myHeight = height;
		rin.myWidth = width;
		
		rin.setFitHeight(height);
		rin.setFitWidth(width);
		rin.moveTo(p);
		
		return rin;
	}
	
	/**
	 * @param p
	 * @param height
	 * @param width
	 * @return new correctly sized Ruddy
	 */
	public static Character newRuddy(Point p, int height, int width){
		Character ruddy = new Character(
				"Ruddy",
				Util.CharacterClasses.Knight,
				new Attributes(25,
						new int[] {7, 1, 8, 3, 7, 25, 5, 1},
						new int[] {0, 0, 0, 0, 0, 0, 0, 0},
						new int[] {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}),
				new InfoLevel(3),
				new Inventory()
				);
		
		ruddy.isBoss = true;
		
		ruddy.take(ItemWeapon.newBronzeSword());
		ruddy.myInventory.equipOnHand(0);
		ruddy.myLocation = p;
		
		ruddy.myHeight = height;
		ruddy.myWidth = width;
		
		ruddy.setFitHeight(height);
		ruddy.setFitWidth(width);
		ruddy.moveTo(p);
		
		return ruddy;
		
	}
	
	/**
	 * @param p
	 * @param height
	 * @param width
	 * @return new correctly sized Sword Mook
	 */
	public static Character newSwordMook(Point p, int height, int width){
		Character mook = new Character(
				"Swordsman",
				Util.CharacterClasses.Swordsman,
				new Attributes(15,
						new int[] {3, 0, 10, 8, 4, 15, 4, 0},
						new int[] {0, 0, 0, 0, 0, 0, 0, 0},
						new int[] {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}),
				new InfoLevel(),
				new Inventory()
				);
		
		mook.take(ItemWeapon.newBronzeSword());
		mook.myInventory.equipOnHand(0);
		mook.myLocation = p;
		
		mook.myHeight = height;
		mook.myWidth = width;
		
		mook.setFitHeight(height);
		mook.setFitWidth(width);
		mook.moveTo(p);
		
		return mook;
	}
	
	/**
	 * @param p
	 * @param height
	 * @param width
	 * @return new correctly sized Lance Mook
	 */
	public static Character newLanceMook(Point p, int height, int width){
		Character mook = new Character(
				"Soldier",
				Util.CharacterClasses.Soldier,
				new Attributes(18,
						new int[] {5, 0, 6, 6, 4, 18, 5, 3},
						new int[] {0, 0, 0, 0, 0, 0, 0, 0},
						new int[] {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}),
				new InfoLevel(),
				new Inventory()
				);
		
		mook.take(ItemWeapon.newBronzeLance());
		mook.myInventory.equipOnHand(0);
		mook.myLocation = p;
		
		mook.myHeight = height;
		mook.myWidth = width;
		
		mook.setFitHeight(height);
		mook.setFitWidth(width);
		mook.moveTo(p);
		
		return mook;
	}
	
	/**
	 * @param p
	 * @param height
	 * @param width
	 * @return new correctly sized Axe Mook
	 */
	public static Character newAxeMook(Point p, int height, int width){
		Character mook = new Character(
				"Warrior",
				Util.CharacterClasses.Warrior,
				new Attributes(22,
						new int[] {7, 0, 4, 7, 4, 22, 4, 1},
						new int[] {0, 0, 0, 0, 0, 0, 0, 0},
						new int[] {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}),
				new InfoLevel(),
				new Inventory()
				);
		
		mook.take(ItemWeapon.newBronzeAxe());
		mook.myInventory.equipOnHand(0);
		mook.myLocation = p;
		
		mook.myHeight = height;
		mook.myWidth = width;
		
		mook.setFitHeight(height);
		mook.setFitWidth(width);
		mook.moveTo(p);
		
		return mook;
	}
	
	/**
	 * @param p
	 * @param height
	 * @param width
	 * @return new correctly sized Mist
	 */
	public static Character newMist(Point p, int height, int width){
		Character mist = new Character(
				"Mist",
				Util.CharacterClasses.Priest,
				new Attributes(16,
						new int[] {1, 4, 4, 7, 6, 16, 2, 7},
						new int[] {35, 50, 25, 40, 60, 50, 15, 40},
						new int[] {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}),
				new InfoLevel(),
				new Inventory()
				);
		
		mist.take(ItemWeaponStaff.newHealStaff());
		mist.myInventory.equipOffHand(0);
		mist.myLocation = p;
		
		mist.myHeight = height;
		mist.myWidth = width;
		
		mist.setFitHeight(height);
		mist.setFitWidth(width);
		mist.moveTo(p);
		
		return mist;
		
	}
	
	/**
	 * move (temporarily) to Point p
	 * @param p
	 */
	public void moveTo(Point p){
		myLocation = p;
		setX(p.getX() * myHeight);
		setY(p.getY() * myWidth);
	}
	
	/**
	 * Put i into the inventory
	 * @param i
	 */
	public void take(Item i){
		myInventory.add(i);
	}
	
	/**
	 * Remove i from the inventory
	 * @param i
	 */
	public boolean drop(Item i){
		return myInventory.remove(i);
	}
	
	/**
	 * 
	 * @return Avoid with bonuses
	 */
	public int avoid(){
		int bonuses = 0;
		//TODO-LongTerm: Add other bonuses
		
		return 2 * myAttribs.get(Util.AttributeType.SPD) + myAttribs.get(Util.AttributeType.SKL) + bonuses;
	}
	
	/**
	 * 
	 * @return Hit with bonuses
	 */
	public int hit(){
		int bonuses = 0;
		//TODO-LongTerm: Add other bonuses
		
		return myInventory.getHit() + 2 * myAttribs.get(Util.AttributeType.SKL) + myAttribs.get(Util.AttributeType.SPD) + bonuses;
	}
	
	/**
	 * 
	 * @return Crit with bonuses
	 */
	public int crit(){
		int bonuses = 0;
		//TODO-LongTerm: Add other bonuses
		
		return myInventory.getCrit() + myAttribs.get(Util.AttributeType.SKL) + bonuses;
	}
	
	/**
	 * 
	 * @return Dodge with bonuses
	 */
	public int dodge(){
		int bonuses = 0;
		//TODO-LongTerm: Add other bonuses
		
		return myAttribs.get(Util.AttributeType.SPD) + myAttribs.get(Util.AttributeType.SKL) / 2 + bonuses;
	}
	
	/**
	 * 
	 * @return Attack with bonuses
	 */
	public int attack(){
		int bonuses = 0;
		//TODO-LongTerm: Add other bonuses
		
		if (myInventory.getWeaponsEquipped() == 0) {
			return 0;
		} else if (myInventory.getWeaponsEquipped() == 2){
			return (myInventory.getMight() + myAttribs.get(myInventory.getOnHand().wDmgBonusAttrib) + myAttribs.get(myInventory.getOffHand().wDmgBonusAttrib)) / 2 + bonuses;
		} else {
			if (myInventory.getOnHand() != null){
				return myInventory.getMight() + myAttribs.get(myInventory.getOnHand().wDmgBonusAttrib) + bonuses;
			} else {
				return myInventory.getMight() + myAttribs.get(myInventory.getOffHand().wDmgBonusAttrib) + bonuses;
			}
		}
	}

	
	/**
	 * Full combat with enemy
	 * @param enemy
	 */
	public void combat(Character enemy){
		if (myAttribs.getCurrHP() <= 0 || enemy.myAttribs.getCurrHP() <= 0){
			//System.out.println("One or more combatants are dead; they cannot fight.");
			return;
		}
		
		//TODO-LongTerm: pre-combat skill triggers (vantage, wrath), etc.
		
		int mySpdAdv = myAttribs.get(Util.AttributeType.SPD) - enemy.myAttribs.get(Util.AttributeType.SPD);
		int dmg = 0;
		int enemyDmg = 0;
		boolean dead = false;
		boolean enemyDead = false;
		
		try{
			/*
			 * |Speed difference| < 3 gives one strike each 1-1
			 * 3 < |Speed difference| < 8 gives 2 hits to faster unit 1-1-1
			 * |Speed difference| > 8 gives 3 hits to faster unit 2-1-1
			 * Aggressor goes first
			 */ 
			dmg += strike(enemy);
			
			if (mySpdAdv > 8){
				 dmg += strike(enemy);
			}
			
			enemyDmg += enemy.strike(this);
			
			if (mySpdAdv > 3){
				dmg += strike(enemy);
			}
			
			if (mySpdAdv < -3){
				enemyDmg += enemy.strike(this);
			}
			
			if (mySpdAdv < -8){
				enemyDmg += enemy.strike(this);
			}
		} catch (CharacterDeadException e) {
			//If anyone dies, stop the combat and grant kill EXP to the victor
			if (e.dead.equals(this)){
				if (e.finishingDmg > 0){
					enemy.killExp(this);
					die();
					dead = true;
				}
				enemyDmg += e.finishingDmg;
			}
			if (e.dead.equals(enemy)){
				if (e.finishingDmg > 0){
					killExp(enemy);
					enemyDead = true;
					enemy.die();
				}
				dmg += e.finishingDmg;
				
			}
		} finally {
			//Grant experience to both units from this combat, if they're still alive.
			if (!dead){
				if (dmg > 0){
					cmbtExp(enemy);
				} else {
					gainExp(NO_DMG_BATTLE_EXP);
				}
			}
			
			if (!enemyDead){
				if (enemyDmg > 0){
					enemy.cmbtExp(this);
				} else {
					enemy.gainExp(NO_DMG_BATTLE_EXP);
				}
			}
		}
	}
	
	public void gainExp(int i){
		int levelsToGain = myLevel.gainExp(i);
		
		while (levelsToGain > 0){
			myAttribs.levelUp();
			levelsToGain--;
		}
	}
	
	private void cmbtExp(Character enemy){
		int levelDiff = myLevel.totalLevel() - enemy.myLevel.totalLevel();
		//10 + (LD * abs(LD)) / 4
		int toGain = 10 - (levelDiff * ((levelDiff < 0) ? -levelDiff : levelDiff)) / 4;
		if (toGain < 5){
			toGain = 5;
		}
		
		gainExp(toGain);
	}
	
	private void killExp(Character enemy){
		int levelDiff = myLevel.totalLevel() - enemy.myLevel.totalLevel();
		int tierDiff = myLevel.getTier() - enemy.myLevel.getTier();
		int bossMod = (enemy.isBoss) ? 40 : 0;
		
		int toGain = 15 - levelDiff - 5 * tierDiff + bossMod;
		
		if (toGain < 5 + bossMod / 4){
			toGain = 5 + bossMod / 4;
		}
		
		gainExp(toGain);
	}
	
	public int heal(int amount){
		//TODO: Check healing skills.
		if (amount < 0){
			amount = 0;
		}
		
		if (myAttribs.getCurrHP() <= 0){
			//System.out.printf("%s's dead. Healers aren't gods.\n", myName);
			return -1;
		}
		
		int maxHP = myAttribs.get(Util.AttributeType.CON);
		int curHP = myAttribs.getCurrHP();
		
		if (maxHP <= amount + curHP){
			amount = maxHP - curHP;
		}
		
		myAttribs.setCurrHP(curHP + amount);
		//System.out.printf("%s was healed %d points.\n", myName, amount);
		
		return amount;
	}
	
	private void staffExp(){
		//TODO: Make this depend on staff rank.
		gainExp(20);
	}
	
	public void staffHeal(Character ally){
		if (myAttribs.getCurrHP() <= 0 || ally.myAttribs.getCurrHP() <= 0){
			//System.out.println("Dead people can't heal or be healed.");
			return;
		}
		
		if(myInventory.getOffHand().wType != Util.WeaponType.STAFF){
			//System.out.println("You can't heal without a staff equipped.");
			return;
		}
		
		int amount = ((ItemWeaponStaff) myInventory.getOffHand()).baseHeal + myAttribs.get(Util.AttributeType.MAG);
		
		ally.heal(amount);
		
		((ItemWeaponStaff) myInventory.getOffHand()).use();
		staffExp();
		
		
	}
	
	public int getMov(){
		return myMov - myTurnInfo.getMoveTaken();
	}
	
	public String verboseToString(){
		String line1 = String.format("%s\tLVL %d\t%d/%d\n", myName, myLevel.totalLevel(), myAttribs.getCurrHP(), myAttribs.get(Util.AttributeType.CON));
		String line2 = String.format("%s\n%s\n", myClass.toString(), myAttribs.getRawAttribs().toString());
		String line3 = "Attack\tHit\tCrit\tDefense\tAvoid\tDodge\tSpeed\n";
		String line4 = String.format("%d\t\t%d\t%d\t%d\t\t%d     \t%d\t\t%d\n", attack(), hit(), crit(), myAttribs.get(Util.AttributeType.DEF), avoid(), dodge(), myAttribs.get(Util.AttributeType.SPD));
		String line5 = String.format("OnHand:\t%s\nOffHand:\t%s\n", myInventory.getOnHand(), myInventory.getOffHand());
		return String.format("%s%s%s%s\n%s", line1, line2, line3, line4, line5);
	}


	
	/**
	 * Return name, hp, class, exp and level
	 */
	public String toString(){
		return String.format("%s\t%d/%dHP\nEXP %d\tLVL %d", myName, myAttribs.getCurrHP(), myAttribs.get(Util.AttributeType.CON), myLevel.getExp(), myLevel.totalLevel());
	}
	
	/**
	 * 
	 * @return attack range
	 */
	public int getRange(){
		return myInventory.getRange();
	}
	
	
	public static void main(String[] args){
		Character c = new Character(
				"Mia",
				Util.CharacterClasses.Swordsman,
				new Attributes(17,
						new int[] {4, 1, 6, 9, 5, 17, 3, 0},
						new int[] {45, 20, 60, 70, 20, 50, 25, 45},
						new int[] {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}),
				new InfoLevel(),
				new Inventory()
				);
		
		c.myInventory.add(ItemWeapon.newBronzeSword());
		c.myInventory.equipOnHand(0);
		
		Character e = new Character(
				"Mist",
				Util.CharacterClasses.Priest,
				new Attributes(15,
						new int[] {2, 4, 5, 6, 12, 15, 2, 5},
						new int[] {45, 20, 60, 70, 20, 50, 25, 45},
						new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0}),
				new InfoLevel(),
				new Inventory()
				);
		
		e.myInventory.add(new ItemWeaponStaff(
					"Heal",
					800,
					Util.WeaponType.STAFF,
					Util.AttributeType.STR,
					Util.AttributeType.DEF,
					new ListAttribute(0,0,0,0,0,0,0,0),
					0,
					70,
					0,
					40,
					2,
					1,
					0
					)
				);
		
		e.myInventory.equipOffHand(0);
		
		
		c.gainExp(500);
		System.out.println();
		
		Character d = newIke(new Point(0, 0), 50, 50);
		d.myInventory.add(ItemWeapon.newBronzeSword());
		d.myInventory.equipOnHand(0);

		c.combat(d);
		System.out.println(c.myLevel.getExp());
		System.out.println(d.myLevel.getExp());
		System.out.println(e.myLevel.getExp());
		
		e.staffHeal(d);
		
		
		d.combat(c);
		
		System.out.println(c.myLevel.getExp());
		System.out.println(d.myLevel.getExp());
		System.out.println(e.myLevel.getExp());
		
		System.out.println(c);
		System.out.println(d);
		System.out.println(e);
		
	}

	/**
	 * 
	 * @return staff range
	 */
	public int getStaffRange() {
		Point staffRange = myInventory.getStaffRange();
		if (staffRange.getY() == 0){
			return staffRange.getX();
		}
		return staffRange.getX() + staffRange.getY() * myAttribs.get(Util.AttributeType.MAG);
	}
	
	/**
	 * 
	 * @return character current grid location
	 */
	public Point getLoc(){
		return myLocation;
	}

	/**
	 * set myValidMoves so it doesn't have to be recalculated if we can help it.
	 * @param movesMap
	 */
	public void setValidMoves(Map<Point, Integer> movesMap) {
		myValidMoves = movesMap;
		
	}
	
	/**
	 * 
	 * @return valid moves
	 */
	public Map<Point, Integer> getValidMoves(){
		return myValidMoves;
	}

	/**
	 * 
	 * @return true iff character can still move this turn
	 */
	public boolean hasMove() {
		return !isDead && myTurnInfo.getMoveTaken() < myMov;
	}
	
	/**
	 * 
	 * @return true iff character can still act this turn
	 */
	public boolean hasAction(){
		return !isDead &&  myTurnInfo.getActionRemaining();
	}

	/**
	 * Permanently (for the turn) move to endSpace
	 * @param endSpace
	 */
	public void finalizeMove(Point endSpace) {
		myTurnInfo.move(myMov - myTurnInfo.getMoveTaken() - myValidMoves.get(endSpace));
		myValidMoves = null;
	}
	
	/**
	 * Character has done all it can for the turn. Don't let it do anything else.
	 */
	public void finalizeAction(){
		myTurnInfo.takeAction();
		myTurnInfo.move(myMov - myTurnInfo.getMoveTaken());
		myValidMoves = null;
	}
	
	/**
	 * Turn is over, reset turn info
	 */
	public void nextTurn(){
		myTurnInfo.reset();
	}

	/**
	 * 
	 * @return isBoss
	 */
	public boolean isBoss() {
		return isBoss;
	}

	/**
	 * 
	 * @return isDead
	 */
	public boolean isDead() {
		return isDead;
	}

	/**
	 * 
	 * @param enemy
	 * @return the % of enemy's HP this would deal in a combat (that this fully survived)
	 */
	public Double percentDamage(Character enemy) {
		int spdAdv = myAttribs.get(Util.AttributeType.SPD) - enemy.myAttribs.get(Util.AttributeType.SPD);
		double dmg = ((double) (attack() - enemy.myAttribs.get(myInventory.getDamageType()))) / ((double) enemy.myAttribs.get(Util.AttributeType.CON));
		
		if (spdAdv > 8)
			return 3 * dmg;
		else if (spdAdv > 3)
			return 2 * dmg;
		else
			return 1 * dmg;
	}

	
	/**
	 * Set the character to be "ded. Leik so ded."
	 */
	private void die(){
		isDead = true;
		//System.out.printf("%s is defeated!\n", myName);
	}
	
	/**
	 * Reduce Characters HP by up to dmg
	 * @param dmg
	 * @return amount of dmg taken
	 * @throws CharacterDeadException
	 */
	private int damage(int dmg) throws CharacterDeadException{
		//TODO-LongTerm: Check skills (Miracle, other damage reduction skills).
		if (dmg < 0){
			dmg = 0;
		}
		if (myAttribs.getCurrHP() <= 0){
			//System.out.printf("Stop, stop! %s's alreay dead!\n", myName);
			throw new CharacterDeadException(this, -1);
		}
		if (myAttribs.getCurrHP() <= dmg){
			dmg = myAttribs.getCurrHP();
			myAttribs.setCurrHP(0);
			//System.out.printf("%s took %d damage.\n", myName, dmg);
			throw new CharacterDeadException(this, dmg);
		}
		myAttribs.addCurrHP(-dmg);
		//System.out.printf("%s took %d damage.\n", myName, dmg);
		return dmg;
	}
	
	/**
	 * One strike of combat
	 * @param enemy
	 * @return dmg done
	 * @throws CharacterDeadException
	 */
	private int strike(Character enemy) throws CharacterDeadException{
		if (attack() == 0){
			return 0;
		}
		
		int hit = hit() - enemy.avoid();
		//TODO: Weapon Triangle bonuses
		
		if (Util.random.nextInt(99) + Util.random.nextInt(99) >= 2 * hit){
			//Miss, no damage.
			return -1;
		}
		
		//TODO-LongTerm: Check on hit skill activations.
		
		
		int crit = crit() - enemy.dodge();
		if (Util.random.nextInt(99) < crit){
			//TODO-LongTerm: Check crit trigger skill activations.
			//Crit, deal extra damage equal to attack.
			return enemy.damage(2 * attack() - enemy.myAttribs.get(myInventory.getDamageType()));
		} else {
			//Normal attack
			return enemy.damage(attack() - enemy.myAttribs.get(myInventory.getDamageType()));
		}
		
		//TODO-LongTerm: Check after damage skill activations.
		
	}
}
