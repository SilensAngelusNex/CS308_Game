/**
 * Contains information about a character's remaining movement and actions on the current turn. Needs to be reset when
 * ending a turn (or really just before their next turn starts).
 * @author Weston
 *
 */
public class InfoTurn {
	private int moveTaken;
	private boolean actionRemaining;
	
	/**
	 * Constructs an InfoTurn for a character that hasn't moved or acted yet.
	 */
	public InfoTurn(){
		moveTaken = 0;
		actionRemaining = true;
	}
	/**
	 * Returns the amount of movement the character has already used this turn.
	 * @return
	 */
	public int getMoveTaken(){
		return moveTaken;
	}
	
	/**
	 * Adds i spaces moved.
	 * @param i
	 */
	public void move(int i){
		moveTaken += i;
	}
	
	/**
	 * Uses the action for the turn.
	 */
	public void takeAction(){
		actionRemaining = false;
	}
	
	/**
	 * Checks if the action is still available.
	 * @return
	 */
	public boolean getActionRemaining(){
		return actionRemaining;
	}
	
	/**
	 * Resets the InfoTurn to it's starting state for the next turn.
	 */
	public void reset(){
		moveTaken = 0;
		actionRemaining = true;
	}
}
