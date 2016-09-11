import java.util.Map;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Character extends ImageView{
	private static final int NO_DMG_BATTLE_EXP = 1;
	
	public String name;
	public Util.CharacterClasses charClass;
	public Attributes attribs;
	private Point myLocation;
	private int myHeight;
	private int myWidth;
	public InfoLevel level;
	public Inventory inv;
	public boolean boss;
	private int myMov;
	private InfoTurn myTurnInfo;
	private Map<Point, Integer> myValidMoves;
	private boolean isDead;
	
	
	
	
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
		
	
	public Character(String n, Util.CharacterClasses c, Attributes a, InfoLevel l, Inventory i){
		super();
		name = n;
		setImage(new Image(getClass().getClassLoader().getResourceAsStream(String.format("%s%s", name, ".png"))));
		charClass = c;
		attribs = a;
		level = l;
		inv = i;
		boss = false;
		myMov = 6;
		myTurnInfo = new InfoTurn();
		isDead = false;
	}
	
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
		
		ike.boss = true;
		
		ike.take(ItemWeapon.newBronzeSword());
		ike.inv.equipOnHand(0);
		ike.myLocation = p;
		
		ike.myHeight = height;
		ike.myWidth = width;
		
		ike.setFitHeight(height);
		ike.setFitWidth(width);
		ike.moveTo(p);
		
		return ike;
		
	}
	
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
		
		mia.boss = true;
		
		mia.take(ItemWeapon.newBronzeSword());
		mia.inv.equipOnHand(0);
		mia.myLocation = p;
		
		mia.myHeight = height;
		mia.myWidth = width;
		
		mia.setFitHeight(height);
		mia.setFitWidth(width);
		mia.moveTo(p);
		
		return mia;
		
	}
	
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
		mist.inv.equipOffHand(0);
		mist.myLocation = p;
		
		mist.myHeight = height;
		mist.myWidth = width;
		
		mist.setFitHeight(height);
		mist.setFitWidth(width);
		mist.moveTo(p);
		
		return mist;
		
	}
	
	public void moveTo(Point p){
		myLocation = p;
		setX(p.getX() * myHeight);
		setY(p.getY() * myWidth);
	}
	
	public void take(Item i){
		inv.add(i);
	}
	
	public boolean drop(Item i){
		return inv.remove(i);
	}
	
	
	public int avoid(){
		int bonuses = 0;
		//TODO-LongTerm: Add other bonuses
		
		return 2 * attribs.get(Util.AttributeType.SPD) + attribs.get(Util.AttributeType.SKL) + bonuses;
	}
	
	public int hit(){
		int bonuses = 0;
		//TODO-LongTerm: Add other bonuses
		
		return inv.getHit() + 2 * attribs.get(Util.AttributeType.SKL) + attribs.get(Util.AttributeType.SPD) + bonuses;
	}
	
	public int crit(){
		int bonuses = 0;
		//TODO-LongTerm: Add other bonuses
		
		return inv.getCrit() + attribs.get(Util.AttributeType.SKL) + bonuses;
	}
	
	public int dodge(){
		int bonuses = 0;
		//TODO-LongTerm: Add other bonuses
		
		return attribs.get(Util.AttributeType.SPD) + attribs.get(Util.AttributeType.SKL) / 2 + bonuses;
	}
	
	public int attack(){
		int bonuses = 0;
		//TODO-LongTerm: Add other bonuses
		
		if (inv.getWeaponsEquipped() == 0) {
			return 0;
		} else if (inv.getWeaponsEquipped() == 2){
			return (inv.getMight() + attribs.get(inv.getOnHand().wAttrib) + attribs.get(inv.getOffHand().wAttrib)) / 2 + bonuses;
		} else {
			if (inv.getOnHand() != null){
				return inv.getMight() + attribs.get(inv.getOnHand().wAttrib) + bonuses;
			} else {
				return inv.getMight() + attribs.get(inv.getOffHand().wAttrib) + bonuses;
			}
		}
	}
	
	private void die(){
		isDead = true;
		System.out.printf("%s is defeated!\n", name);
	}
	
	private int damage(int dmg) throws CharacterDeadException{
		//TODO-LongTerm: Check skills (Miracle, other damage reduction skills).
		if (dmg < 0){
			dmg = 0;
		}
		if (attribs.getCurrHP() <= 0){
			System.out.printf("Stop, stop! %s's alreay dead!\n", name);
			throw new CharacterDeadException(this, -1);
		}
		if (attribs.getCurrHP() <= dmg){
			dmg = attribs.getCurrHP();
			attribs.setCurrHP(0);
			System.out.printf("%s took %d damage.\n", name, dmg);
			throw new CharacterDeadException(this, dmg);
		}
		attribs.addCurrHP(-dmg);
		System.out.printf("%s took %d damage.\n", name, dmg);
		return dmg;
	}
	
	private int strike(Character enemy) throws CharacterDeadException{
		if (attack() == 0){
			return 0;
		}
		
		int hit = hit() - enemy.avoid();
		//TODO: Weapon Triangle bonuses
		
		if (Util.rand.nextInt(99) + Util.rand.nextInt(99) >= 2 * hit){
			//Miss, no damage.
			return -1;
		}
		
		//TODO-LongTerm: Check on hit skill activations.
		
		
		int crit = crit() - enemy.dodge();
		if (Util.rand.nextInt(99) < crit){
			//TODO-LongTerm: Check crit trigger skill activations.
			//Crit, deal extra damage equal to attack.
			return enemy.damage(2 * attack() - enemy.attribs.get(inv.getDamageType()));
		} else {
			//Normal attack
			return enemy.damage(attack() - enemy.attribs.get(inv.getDamageType()));
		}
		
		//TODO-LongTerm: Check after damage skill activations.
		
	}
	
	public void combat(Character enemy){
		if (attribs.getCurrHP() <= 0 || enemy.attribs.getCurrHP() <= 0){
			System.out.println("One or more combatants are dead; they cannot fight.");
			return;
		}
		
		//TODO-LongTerm: pre-combat skill triggers (vantage, wrath), etc.
		
		int mySpdAdv = attribs.get(Util.AttributeType.SPD) - enemy.attribs.get(Util.AttributeType.SPD);
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
	
	private void gainExp(int i){
		int levelsToGain = level.gainExp(i);
		
		while (levelsToGain > 0){
			attribs.levelUp();
			levelsToGain--;
		}
	}
	
	private void cmbtExp(Character enemy){
		int levelDiff = level.totalLevel() - enemy.level.totalLevel();
		//10 + (LD * abs(LD)) / 4
		int toGain = 10 - (levelDiff * ((levelDiff < 0) ? -levelDiff : levelDiff)) / 4;
		if (toGain < 5){
			toGain = 5;
		}
		
		gainExp(toGain);
	}
	
	private void killExp(Character enemy){
		int levelDiff = level.totalLevel() - enemy.level.totalLevel();
		int tierDiff = level.getTier() - enemy.level.getTier();
		int bossMod = (enemy.boss) ? 40 : 0;
		
		int toGain = 15 - levelDiff - 5 * tierDiff + bossMod;
		
		if (toGain < 5 + bossMod / 4){
			toGain = 5 + bossMod / 4;
		}
		
		gainExp(toGain);
	}
	
	private int heal(int amount) throws CharacterDeadException{
		//TODO: Check healing skills.
		if (amount < 0){
			amount = 0;
		}
		
		if (attribs.getCurrHP() <= 0){
			System.out.printf("%s's dead. Healers aren't gods.\n", name);
			throw new CharacterDeadException(this, -1);
		}
		
		int maxHP = attribs.get(Util.AttributeType.CON);
		int curHP = attribs.getCurrHP();
		
		if (maxHP <= amount + curHP){
			amount = maxHP - curHP;
		}
		
		attribs.setCurrHP(curHP + amount);
		System.out.printf("%s was healed %d points.\n", name, amount);
		
		return amount;
	}
	
	private void staffExp(){
		//TODO: Make this depend on staff rank.
		gainExp(20);
	}
	
	public void staffHeal(Character ally){
		if (attribs.getCurrHP() <= 0 || ally.attribs.getCurrHP() <= 0){
			System.out.println("Dead people can't heal or be healed.");
			return;
		}
		
		if(inv.getOffHand().wType != Util.WeaponType.STAFF){
			System.out.println("You can't heal without a staff equipped.");
			return;
		}
		
		int amount = ((ItemWeaponStaff) inv.getOffHand()).baseHeal + attribs.get(Util.AttributeType.MAG);
		
		try {
			ally.heal(amount);
		} catch (CharacterDeadException e) {
			System.out.println("I told you not to try to bring back the dead, asshole.");
			e.printStackTrace();
		}
		
		((ItemWeaponStaff) inv.getOffHand()).use();
		staffExp();
		
		
	}
	
	public int getMov(){
		return myMov - myTurnInfo.getMoveTaken();
	}
	
	private void printChar(){
		System.out.printf("%s\t%d\t%d/%d\n", name, level.totalLevel(), attribs.getCurrHP(), attribs.get(Util.AttributeType.CON));
		System.out.println(charClass.toString());
		attribs.getRawAttribs().printAttributes();
	}

	
	private void printCmbt(Character e){
		System.out.printf("\tAttack\tHit\tCrit\tDefense\tAvoid\tDodge\tSpeed\n");
		System.out.printf("%s\t%d\t%d\t%d\t%d\t%d\t%d\t%d\n", name, attack(), hit(), crit(), attribs.get(Util.AttributeType.DEF), avoid(), dodge(), attribs.get(Util.AttributeType.SPD));
		System.out.printf("%s\t%d\t%d\t%d\t%d\t%d\t%d\t%d\n", e.name, e.attack(), e.hit(), e.crit(), e.attribs.get(Util.AttributeType.DEF), e.avoid(), e.dodge(), e.attribs.get(Util.AttributeType.SPD));
		System.out.printf("\t%d\t%d\t%d\t%d\t%d\t%d\t%d\n", attack()-e.attribs.get(Util.AttributeType.DEF), hit()-e.avoid(), crit()-e.dodge(), e.attack()-attribs.get(Util.AttributeType.DEF), e.hit()-avoid(), e.crit()-dodge(), attribs.get(Util.AttributeType.SPD)-e.attribs.get(Util.AttributeType.SPD));
	}
	
	public String toString(){
		return String.format("%s\t%d/%dHP\nEXP %d", name, attribs.getCurrHP(), attribs.get(Util.AttributeType.CON), level.getExp());
	}
	
	public int getRange(){
		return inv.getRange();
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
		
		c.inv.add(ItemWeapon.newBronzeSword());
		c.inv.equipOnHand(0);
		
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
		
		e.inv.add(new ItemWeaponStaff(
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
		
		e.inv.equipOffHand(0);
		
		
		c.printChar();
		c.gainExp(500);
		c.printChar();
		System.out.println();
		
		Character d = newIke(new Point(0, 0), 50, 50);
		d.printChar();
		d.inv.add(ItemWeapon.newBronzeSword());
		d.inv.equipOnHand(0);
		
		e.printChar();
		c.printCmbt(d);
		
		c.combat(d);
		System.out.println(c.level.getExp());
		System.out.println(d.level.getExp());
		System.out.println(e.level.getExp());
		
		e.staffHeal(d);
		
		
		d.combat(c);
		
		System.out.println(c.level.getExp());
		System.out.println(d.level.getExp());
		System.out.println(e.level.getExp());
		
		c.printChar();
		d.printChar();
		e.printChar();
		
		System.out.println(c);
		System.out.println(d);
		System.out.println(e);
		
	}

	public int getStaffRange() {
		Point staffRange = inv.getStaffRange();
		if (staffRange.getY() == 0){
			return staffRange.getX();
		}
		return staffRange.getX() + staffRange.getY() * attribs.get(Util.AttributeType.MAG);
	}
	
	public Point getLoc(){
		return myLocation;
	}

	public void setValidMoves(Map<Point, Integer> movesMap) {
		myValidMoves = movesMap;
		
	}
	
	public Map<Point, Integer> getValidMoves(){
		return myValidMoves;
	}

	public boolean hasMove() {
		return !isDead && myTurnInfo.getMoveTaken() < myMov;
	}
	
	public boolean hasAction(){
		return !isDead && myTurnInfo.getActionRemaining();
	}

	public void finalizeMove(Point endSpace) {
		myTurnInfo.move(myMov - myTurnInfo.getMoveTaken() - myValidMoves.get(endSpace));
		myValidMoves = null;
	}
	public void finalizeAction(){
		myTurnInfo.takeAction();
		myTurnInfo.move(myMov - myTurnInfo.getMoveTaken());
		myValidMoves = null;
	}
	
	public void nextTurn(){
		myTurnInfo.reset();
	}

	public boolean isBoss() {
		return boss;
	}

	public boolean isDead() {
		return isDead;
	}
	
}
