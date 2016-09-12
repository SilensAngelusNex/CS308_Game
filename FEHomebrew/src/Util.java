import java.util.Random;

/**
 * Contains some utilities, mostly enums, used throughout the project.
 * @author Weston
 *
 */
public class Util {
	
	/**
	 * 
	 */
	public static final Random random = new Random();
	
	/**
	 * An enumeration of the possible weapon types.
	 * @author Weston
	 */
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
	
	/**
	 * An enumeration of the attribute types.
	 * @author Weston
	 */
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
	
	/**
	 * Unused. Will be an enumeration of the types of usable items. (Healing, Attribute boosts, etc.)
	 * @author Weston
	 *
	 */
	public enum ItemTypeCode{
		HEALING
	}
	
	/**
	 * An enumeration of all the current (Tier 1) character classes.
	 * @author Weston
	 *
	 */
	public enum CharacterClasses{
		//TODO: Add T2 and T3 classes, possibly in different enums.
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
