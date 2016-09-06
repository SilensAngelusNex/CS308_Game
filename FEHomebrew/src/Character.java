

public class Character {
	public String name;
	public GameMap.CharacterClasses charClass;
	public Attributes attribs;
	public LevelInfo level;
	public Inventory inv;
	public boolean boss;
	
	private class CharacterDiesException extends Exception{
		private static final long serialVersionUID = -3374130461047276835L;
		Character dead;
		int finishingDmg;
		
		protected CharacterDiesException(Character c, int d){
			super();
			dead = c;
			finishingDmg = d;
		}
	}
	
	
	/*
	 * The default character constructor makes PoR Ike
	 */
	public Character(){
		this(
				"Ike",
				GameMap.CharacterClasses.Swordsman,
				new Attributes(19,
						new int[] {5, 1, 6, 7, 5, 19, 5, 0},
						new int[] {50, 20, 50, 55, 35, 75, 40, 40},
						new int[] {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}),
				new LevelInfo(),
				new Inventory()
				);
	}
		
	
	public Character(String n, GameMap.CharacterClasses c, Attributes a, LevelInfo l, Inventory i){
		name = n;
		charClass = c;
		attribs = a;
		level = l;
		inv = i;
		boss = false;
	}
	
	
	public int avoid(){
		int bonuses = 0;
		//TODO: Add other bonuses
		
		return 2 * attribs.get(GameMap.AttributeType.SPD) + attribs.get(GameMap.AttributeType.SKL) + bonuses;
	}
	
	public int hit(){
		int bonuses = 0;
		//TODO: Add other bonuses
		
		return inv.equipment.hit() + 2 * attribs.get(GameMap.AttributeType.SKL) + attribs.get(GameMap.AttributeType.SPD) + bonuses;
	}
	
	public int crit(){
		int bonuses = 0;
		//TODO: Add other bonuses
		
		return inv.equipment.crit() + attribs.get(GameMap.AttributeType.SKL) + bonuses;
	}
	
	public int dodge(){
		int bonuses = 0;
		//TODO: Add other bonuses
		
		return attribs.get(GameMap.AttributeType.SPD) + attribs.get(GameMap.AttributeType.SKL) / 2 + bonuses;
	}
	
	public int attack(){
		int bonuses = 0;
		//TODO: Add other bonuses
		
		if (inv.equipment.weaponsEquipped() == 0) {
			return 0;
		} else if (inv.equipment.weaponsEquipped() == 2){
			return (inv.equipment.might() + attribs.get(inv.equipment.onHand.wAttrib) + attribs.get(inv.equipment.onHand.wAttrib)) / 2 + bonuses;
		} else {
			if (inv.equipment.onHand != null){
				return inv.equipment.might() + attribs.get(inv.equipment.onHand.wAttrib) + bonuses;
			} else {
				return inv.equipment.might() + attribs.get(inv.equipment.offHand.wAttrib) + bonuses;
			}
		}
	}
	
	public ListAttribute levelUp(){
		level.levelUp();
		return attribs.levelUp();
	}
	
	private void die(){
		System.out.printf("%s is defeated!\n", name);
	}
	
	public int damage(int dmg) throws CharacterDiesException{
		//TODO: Check skills (Miracle, other damage reduction skills).
		if (dmg < 0){
			dmg = 0;
		}
		if (attribs.currentHp <= 0){
			System.out.printf("Stop, stop! %s's alreay dead!\n", name);
			throw new CharacterDiesException(this, -1);
		}
		if (attribs.currentHp <= dmg){
			dmg = attribs.currentHp;
			attribs.currentHp = 0;
			System.out.printf("%s took %d damage.\n", name, dmg);
			throw new CharacterDiesException(this, dmg);
		}
		attribs.currentHp -= dmg;
		System.out.printf("%s took %d damage.\n", name, dmg);
		return dmg;
	}
	
	public int strike(Character enemy) throws CharacterDiesException{
		if (attack() == 0){
			return 0;
		}
		
		int hit = hit() - enemy.avoid();
		//TODO: Weapon Triangle bonuses
		
		if (GameMap.rand.nextInt(99) + GameMap.rand.nextInt(99) >= 2 * hit){
			//Miss, no damage.
			return -1;
		}
		
		//TODO: Check on hit skill activations.
		
		
		int crit = crit() - enemy.dodge();
		if (GameMap.rand.nextInt(99) < crit){
			//TODO: Check crit trigger skill activations.
			//Crit, deal extra damage equal to attack.
			return enemy.damage(2 * attack() - enemy.attribs.get(inv.equipment.damageType()));
		} else {
			//Normal attack
			return enemy.damage(attack() - enemy.attribs.get(inv.equipment.damageType()));
		}
		
		//TODO: Check after damage skill activations.
		
	}
	
	public void combat(Character enemy){
		if (attribs.currentHp <= 0 || enemy.attribs.currentHp <= 0){
			System.out.println("One or more combatants are dead; they cannot fight.");
			return;
		}
		
		//TODO: pre-combat skill triggers (vantage, wrath), etc.
		
		int mySpdAdv = attribs.get(GameMap.AttributeType.SPD) - enemy.attribs.get(GameMap.AttributeType.SPD);
		int dmg = 0;
		int enemyDmg = 0;
		boolean dead = false;
		boolean enemyDead = false;
		
		try{
			//|Speed difference| < 3 gives one strike each; 3 < x < 8 gives 2 hits to faster; > 8 gives 3.
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
		} catch (CharacterDiesException e) {
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
			System.out.println(dmg);
			System.out.println(enemyDmg);
			if (!dead){
				if (dmg > 0){
					cmbtExp(enemy);
				} else {
					gainExp(1);
				}
			}
			
			if (!enemyDead){
				if (enemyDmg > 0){
					enemy.cmbtExp(this);
				} else {
					enemy.gainExp(1);
				}
			}
		}
	}
	
	private void gainExp(int i){
		while (i > 0){
			if (100 - level.exp <= i){
				i -= 100 - level.exp;
				level.exp += 100 - level.exp;
				level.levelUp();
				attribs.levelUp();
			} else {
				level.exp += i;
				i = 0;
			}
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
		int tierDiff = level.tier - enemy.level.tier;
		int bossMod = (enemy.boss) ? 40 : 0;
		
		int toGain = 15 - levelDiff - 5 * tierDiff + bossMod;
		
		if (toGain < 5 + bossMod / 4){
			toGain = 5 + bossMod / 4;
		}
		
		gainExp(toGain);
	}
	
	private void printChar(){
		System.out.printf("%s\t%d\t%d/%d\n", name, level.totalLevel(), attribs.currentHp, attribs.get(GameMap.AttributeType.CON));
		System.out.println(charClass.toString());
		attribs.rawAttribs.printAttributes();
	}
	private void printCmbt(){
		System.out.printf("Attack\tHit\tCrit\tDefense\tAvoid\tDodge\n");
		System.out.printf("%d\t%d\t%d\t%d\t%d\t%d\n", attack(), hit(), crit(), attribs.get(GameMap.AttributeType.DEF), avoid(), dodge());
	}
	
	private void printCmbt(Character e){
		System.out.printf("\tAttack\tHit\tCrit\tDefense\tAvoid\tDodge\tSpeed\n");
		System.out.printf("%s\t%d\t%d\t%d\t%d\t%d\t%d\t%d\n", name, attack(), hit(), crit(), attribs.get(GameMap.AttributeType.DEF), avoid(), dodge(), attribs.get(GameMap.AttributeType.SPD));
		System.out.printf("%s\t%d\t%d\t%d\t%d\t%d\t%d\t%d\n", e.name, e.attack(), e.hit(), e.crit(), e.attribs.get(GameMap.AttributeType.DEF), e.avoid(), e.dodge(), e.attribs.get(GameMap.AttributeType.SPD));
	}
	
	
	public static void main(String[] args){
		Character c = new Character(
				"Mia",
				GameMap.CharacterClasses.Swordsman,
				new Attributes(17,
						new int[] {4, 1, 6, 9, 5, 17, 3, 0},
						new int[] {45, 20, 60, 70, 20, 50, 25, 45},
						new int[] {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}),
				new LevelInfo(),
				new Inventory()
				);
		
		
		c.printChar();
		c.gainExp(1000);
		c.printChar();
		System.out.println();
		
		Character d = new Character();
		d.printChar();
		
		c.inv.add(new ItemWeapon());
		c.inv.equipOnHand(0);
		d.inv.add(new ItemWeapon());
		d.inv.equipOnHand(0);
		c.printCmbt();
		d.printCmbt();
		c.printCmbt(d);
		

		
		
		c.combat(d);
		System.out.println(c.level.exp);
		System.out.println(d.level.exp);
		d.combat(c);
		
		System.out.println(c.level.exp);
		System.out.println(d.level.exp);
		
	}
	
}
