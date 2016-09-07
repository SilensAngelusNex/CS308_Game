import java.util.Random;

public class Util {
	
	public static Random rand = new Random();
	
	public enum WeaponType{
		SWORD,
		AXE,
		LANCE,
		KNIFE,
		BOW,
		FIRE,
		WIND,
		LIGHTNING,
		LIGHT,
		DARK,
		STAFF,
		BREATH
	}
	
	public enum AttributeType{
		STR,
		MAG,
		SKL,
		SPD,
		CHA,
		CON,
		DEF,
		RES
	}
	
	public enum ItemTypeCode{
		HEALING
	}
	
	public enum CharacterClasses{
		Swordsman,
		Warrior,
		Soldier,
		Rogue,
		Priest,
		Mage,
		Cavalier,
		Knight
	}
		
}
