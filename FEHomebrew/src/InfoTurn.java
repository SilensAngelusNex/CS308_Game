
public class InfoTurn {
	private int moveTaken;
	private boolean actionRemaining;
	
	public InfoTurn(){
		moveTaken = 0;
		actionRemaining = true;
	}
	
	public int getMoveTaken(){
		return moveTaken;
	}
	
	public void move(int i){
		moveTaken += i;
	}
	public void takeAction(){
		actionRemaining = false;
	}
	
	public boolean getActionRemaining(){
		return actionRemaining;
	}
	
	public void reset(){
		moveTaken = 0;
		actionRemaining = true;
	}
}
