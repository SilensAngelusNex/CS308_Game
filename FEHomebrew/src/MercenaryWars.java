import java.util.Map;
import java.util.Vector;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


/**
 * Separate the monstrosity that is the UI from the boilerplate Main code and the game's back end.
 * I feel like there should be a way to separate the different "States" out into different classes, each with there own
 * keyHandler, but I don't see a clear way to do it.
 * 
 * @author Weston Carvalho building on code by Robert Duvall
 */
class MercenaryWars {
    public static final String TITLE = "Mercenary Wars";
    public static final int KEY_INPUT_SPEED = 5;
    private static final int CURSOR_SIZE = 20;
    private static final int CURSOR_SPACING = 35;
    
    private static final int SPLASH_CURSOR_X = 190;
    private static final int SPLASH_CURSOR_Y = 625;
    private static final int SPLASH_CURSOR_SPACING = 75;
    
    private static final int ACTION_CURSOR_X = 660;
    private static final int ACTION_CURSOR_Y = 70;
    private static final int ACTION_MENU_X = 650;
    private static final int ACTION_MENU_Y = 50;
    
    private static final int START_CURSOR_X = 360;
    private static final int START_CURSOR_Y = 380;
    private static final int START_MENU_X = 350;
    private static final int START_MENU_Y = 360;
    
    
    private Scene myScene;
    private Group myRoot;    
    private GameState myState;
    private int myWidth;
    private int myHeight;
    
    //Used in splash menu states (splash, turnChange, actionMenu, startMenu)
    private ImageView mySplash;
	private MenuCursor myMenuCursor;
	private Text myMessage;
	
	//Used in MapMenu state
	private MapCursor myMapCursor;
	private ChapterMap myChapterMap;
	
    //Used in MoveMenu state
	private Map<Point, Integer> myValidMoves;
	private Point myMoveStart;
	private Point myMoveEnd;
	private Vector<ImageView> myMoveImages;

	
	//Used in attack menu
	private boolean myStaffBool;


    /**
     * Returns name of the game.
     */
    public String getTitle () {
        return TITLE;
    }
    
    private enum GameState{
    	SPLASH,
    	STARTMENU,
    	MAPMENU,
    	MOVEMENU,
    	ACTIONMENU,
    	ATTACKMENU,
    	ANIMATION,
    	VICTORY,
    	EXAMINE,
    	TURNCHANGE
    	}

    /**
     * Create the game's scene
     */
    public Scene init (int width, int height) {
    	myWidth = width;
    	myHeight = height;
        // create a scene graph to organize the scene
        myRoot = new Group();
        // create a place to see the shapes
        myScene = new Scene(myRoot, width, height, Color.BLACK);
		
		enterSplash();
        
        // respond to input
        myScene.setOnKeyPressed(e -> handleKeyInput(e));
        return myScene;
    }

    /**
     * Change properties of shapes to animate them
     * 
     * Note, there are more sophisticated ways to animate shapes,
     * but these simple ways work too.
     */
    public void step (double elapsedTime) {
        
        if (myChapterMap != null){
        	myRoot.getChildren().removeAll(myChapterMap.bringOutYourDead());
        	if (myState != GameState.VICTORY && myChapterMap.countActionsLeft() == 0){
            	if (myChapterMap.getVictor() != null)
            		enterVictorySplash(myChapterMap.getVictor());
            	else
            		enterTurnChangeSplash();
        	}
        	if (myChapterMap.currentTurnAI() && myState != GameState.TURNCHANGE){
        		if (!myChapterMap.doneAI()){
        			myChapterMap.nextMoveAI();
        		} else {
        			enterTurnChangeSplash();
        		}
        	}
        }
    }



    // What to do each time a key is pressed
    private void handleKeyInput (KeyEvent e) {
    	switch (myState){
	        case SPLASH:
	        	splashKeyHandler(e.getCode());
	        	break;
	        case STARTMENU:
	        	startMenuKeyHandler(e.getCode());
	        	break;
	        case MAPMENU:
	        	mapMenuKeyHandler(e);
	        	break;
	        case MOVEMENU:
	        	moveMapKeyHandler(e.getCode());
	        	break;
	        case ACTIONMENU:
	        	actionMenuKeyHandler(e.getCode());
	        	break;
	        case ATTACKMENU:
	        	attackMenuKeyHandler(e.getCode());
	        	break;
	        case TURNCHANGE:
	        	turnChangeKeyHandler(e.getCode());
	        case ANIMATION:
	        	//Do nothing.
	        	break;
	        case VICTORY:
	        	//Do nothing.
	        	break;
	        case EXAMINE:
	        	//TODO: implement examine
	        	//examineKeyHandler(e.getCode());
	        	break;
	        default:
	        	break;
    	}
    }
    
    private void splashKeyHandler(KeyCode code){
    	switch (code){
	        case RIGHT:
	        	//Do nothing
	            break;
	        case LEFT:
	        	//Do nothing
	            break;
	        case UP:
	            myMenuCursor.moveUp(1);
	            break;
	        case DOWN:
	        	myMenuCursor.moveDown(1);
	        	break;
	        case R:
	        	if (myMenuCursor.getPos() == 0){
	        		exitSplash();
	        		System.out.println("Campaign");
		        	enterMap(ChapterMap.newCampaignLvl1());
	        	} else {
	        		exitSplash();
	        		System.out.println("Multiplayer");
	        		enterMap(ChapterMap.newMultiplayer());
	        	}
	            break;
	        default:
	            // do nothing
    	}
    }
    
    private void startMenuKeyHandler(KeyCode code){
    	switch (code){
	        case UP:
	        	myMenuCursor.moveUp(1);
	            break;
	        case DOWN:
	        	myMenuCursor.moveDown(1);
	        	break;
	        case R:
	        	switch (myMenuCursor.getPos()){
	        		case 0:
	        			exitStartMenu();
	        			enterTurnChangeSplash();
	        			break;
	        		case 1:
	        			exitStartMenu();
	        			break;
	        	}
	        	break;
	        case E:
	        	exitStartMenu();
	        	break;
	        case Q:
	        	exitStartMenu();
	        	break;
	        default:
	            // do nothing
    	}
    }
    
    private void turnChangeKeyHandler(KeyCode code){
    	switch (code){
	        case R:
	        	exitTurnChangeSplash();
	        default:
	            // do nothing
    	}
    }
    
    private void mapMenuKeyHandler(KeyEvent e){
    	switch (e.getCode()){
	        case RIGHT:
	            myMapCursor.right();
	            characterMousOver();
	            break;
	        case LEFT:
	        	myMapCursor.left();
	        	characterMousOver();
	            break;
	        case UP:
	        	myMapCursor.up();
	        	characterMousOver();
	            break;
	        case DOWN:
	        	myMapCursor.down();
	        	characterMousOver();
	        	break;
	        case R:
	        	if (myChapterMap.canMove(myMapCursor.getLocation()) || myChapterMap.canAct(myMapCursor.getLocation()))
	        		enterMoveMenu();
	            break;
	        case W:
	        	System.out.println(myChapterMap.getCharacter(myMapCursor.getLocation()).verboseToString());
	        	break;
	        case Q:
	        	enterStartMenu();
	        	break;
	        case S:
	        	if (e.isControlDown() && !myChapterMap.hasCharacter(myMapCursor.getLocation()))	
	        		myRoot.getChildren().add(myChapterMap.spawnEnemy(myMapCursor.getLocation(), myHeight, myWidth));
	        	break;
	        case M:
	        	if (	e.isControlDown() &&
	        			myChapterMap.getCharacter(myMapCursor.getLocation()) != null &&
	        			myChapterMap.isOnOwnTurn(myMapCursor.getLocation())
	        			)
	        		myChapterMap.getCharacter(myMapCursor.getLocation()).nextTurn();
	        	break;
	        case H:
	        	if (	e.isControlDown() &&
	        			myChapterMap.getCharacter(myMapCursor.getLocation()) != null
	        			)
						myChapterMap.getCharacter(myMapCursor.getLocation()).heal(1000);
	        	break;
	        case X:
	        	if (	e.isControlDown() &&
	        			myChapterMap.getCharacter(myMapCursor.getLocation()) != null
	        			)
	        		myChapterMap.getCharacter(myMapCursor.getLocation()).gainExp(100);
	        	break;
	        default:
	            // do nothing
    	}
    }
    
    private void characterMousOver(){
    	//TODO: make this actually display something instead.
        if (myChapterMap.getCharacter(myMapCursor.getLocation()) != null){
        	System.out.println(myChapterMap.getCharacter(myMapCursor.getLocation()));
        }
    }
    
    private void moveMapKeyHandler(KeyCode code){
    	switch (code){
	        case RIGHT:
	            myMapCursor.right();
	            break;
	        case LEFT:
	        	myMapCursor.left();
	            break;
	        case UP:
	        	myMapCursor.up();
	            break;
	        case DOWN:
	        	myMapCursor.down();
	        	break;
	        case R:
	        	if (	(myValidMoves.keySet().contains(myMapCursor.getLocation()) &&
	        			(myChapterMap.getCharacter(myMapCursor.getLocation()) == null) ||
	        			myMapCursor.getLocation().equals(myMoveStart))){
	        		System.out.println(myMapCursor.getLocation());
	        		myMoveEnd = myMapCursor.getLocation();
	        		myChapterMap.move(myMoveStart, myMoveEnd);
	        		enterActionMenu();
	        	}
	        	break;
	        case E:
	        	myMapCursor.setLocation(myMoveStart);
	        	exitMoveMenu();
	            break;
	        default:
	            // do nothing
    	}
    }
    
    private void actionMenuKeyHandler(KeyCode code){
    	switch (code){
	        case RIGHT:
	        	//Do nothing
	            break;
	        case LEFT:
	        	//Do nothing
	            break;
	        case UP:
	            myMenuCursor.moveUp(1);
	            break;
	        case DOWN:
	        	myMenuCursor.moveDown(1);
	        	break;
	        case R:
	        	switch (myMenuCursor.getPos()){
	        		case 0:
	        			//Switch to attack menu
	        			if(myChapterMap.canAct(myMoveEnd)){
		        			enterAttackMenu(false);
		        			System.out.println("Attack");
	        			}
	        			break;
	        		case 1:
	        			//Switch to attack menu for staves
	        			if(myChapterMap.canAct(myMoveEnd)){
		        			enterAttackMenu(true);
		        			System.out.println("Staff");
	        			}
	        			break;
	        		case 2:
	        			//Switch back to map menu
	        			System.out.println("Wait");
	        			myChapterMap.finalizeMove(myMoveEnd);
	        			exitActionMenu();
	        			exitMoveMenu();
	        			break;
	        		case 3:
	        			exitActionMenu();
	        			myChapterMap.move(myMoveEnd, myMoveStart);
	        			
	        			myMapCursor.setLocation(myMoveStart);
	        	    	myChapterMap.getCharacter(myMoveStart).setValidMoves(null);
	        			exitMoveMenu();
	        			
	        	}
	            break;
	        case E:
	        	//Go back to move menu
	        	System.out.println("Back");
    			exitActionMenu();
    			myChapterMap.move(myMoveEnd, myMoveStart);
	        default:
	            // do nothing
    	}
    }
    private void attackMenuKeyHandler(KeyCode code){
    	switch (code){
	        case RIGHT:
	            myMapCursor.right();
	            break;
	        case LEFT:
	        	myMapCursor.left();
	            break;
	        case UP:
	        	myMapCursor.up();
	            break;
	        case DOWN:
	        	myMapCursor.down();
	        	break;
	        case R:
	        	if (myStaffBool){
	        		int staffRange = myChapterMap.getCharacter(myMoveEnd).getStaffRange();
	        		if(
	        				staffRange > 0 &&
	        				//TODO only heal allies
	        				staffRange >= myMoveEnd.rectDist(myMapCursor.getLocation()) &&
	        				myChapterMap.hasCharacter(myMapCursor.getLocation()) &&
	        				myChapterMap.canAct(myMoveEnd)
	        						){
	        			myChapterMap.getCharacter(myMoveEnd).staffHeal(myChapterMap.getCharacter(myMapCursor.getLocation()));
	        			myChapterMap.finalizeAction(myMoveEnd);
	    	        	exitAttackMenu();
	    	        	exitActionMenu();
	    	        	exitMoveMenu();
	        		}
	        	} else {
	        		int weaponRange = myChapterMap.getCharacter(myMoveEnd).getRange();
	        		if(
	        				weaponRange > 0 &&
	        				//TODO only attack enemies
	        				weaponRange >= myMoveEnd.rectDist(myMapCursor.getLocation()) &&
	        				myChapterMap.hasCharacter(myMapCursor.getLocation()) &&
	        				myChapterMap.canAct(myMoveEnd)
	        						){
	        			myChapterMap.getCharacter(myMoveEnd).combat(myChapterMap.getCharacter(myMapCursor.getLocation()));
	        			myChapterMap.finalizeAction(myMoveEnd);
			        	exitAttackMenu();
			        	exitActionMenu();
			        	exitMoveMenu();
		        	}
	        	}
	            break;
	        case E:
	        	exitAttackMenu();
	        default:
	            // do nothing
    	}
    }
    
    
    private void enterSplash(){
    	myState = GameState.SPLASH;
    	
    	
    	Image splashImage = new Image(getClass().getClassLoader().getResourceAsStream("Splash.png"));
    	mySplash = new ImageView(splashImage);
    	myRoot.getChildren().add(mySplash);
    	
    	mySplash.setFitHeight(myHeight);
    	mySplash.setFitWidth(myWidth);
    	
        mySplash.setX(myWidth / 2 - mySplash.getBoundsInLocal().getWidth() / 2);
        mySplash.setY(myHeight / 2  - mySplash.getBoundsInLocal().getHeight() / 2);
        
    	
    	myMenuCursor = new MenuCursor(SPLASH_CURSOR_X, SPLASH_CURSOR_Y, CURSOR_SIZE, SPLASH_CURSOR_SPACING, 2);
        myMenuCursor.setFill(Color.RED);
    	myRoot.getChildren().add(myMenuCursor);	
    }

    private void exitSplash(){
    	myRoot.getChildren().remove(mySplash);
    	myRoot.getChildren().remove(myMenuCursor);
    	
    	mySplash.setImage(null);
    	myMenuCursor = null;
    }
    
    private void enterStartMenu(){
    	myState = GameState.STARTMENU;
    	
    	mySplash = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("StartMenu.png")));
    	mySplash.setX(START_MENU_X);
    	mySplash.setY(START_MENU_Y);
    	myRoot.getChildren().add(mySplash);
    	
    	myMenuCursor = new MenuCursor(START_CURSOR_X, START_CURSOR_Y, CURSOR_SIZE, CURSOR_SPACING, 2);
    	myMenuCursor.setFill(Color.GOLD);
    	myRoot.getChildren().add(myMenuCursor);
    }
    
    private void exitStartMenu(){
    	myState = GameState.MAPMENU;
    	
    	myRoot.getChildren().remove(myMenuCursor);  
    	myRoot.getChildren().remove(mySplash);
    	myMenuCursor = null;
    	mySplash = null;
    }
    
    private void enterTurnChangeSplash(){    		
    	myState = GameState.TURNCHANGE;
    	
    	myChapterMap.endTurn();

    	mySplash = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("TurnChangeSplash.png")));
    	
    	mySplash.setFitHeight(myHeight);
    	mySplash.setFitWidth(myWidth);
    	
        mySplash.setX(myWidth / 2 - mySplash.getBoundsInLocal().getWidth() / 2);
        mySplash.setY(myHeight / 2  - mySplash.getBoundsInLocal().getHeight() / 2);
    	
    	myRoot.getChildren().add(mySplash);
    	
    	myMessage = new Text(String.format("%s' Turn", myChapterMap.currentTurn()));
    	myMessage.setFont(Font.font("Copperplate", 40));
    	myMessage.setFill(Color.RED);
    	
    	myMessage.setX(myWidth / 2 - myMessage.getBoundsInLocal().getWidth() / 2);
    	myMessage.setY(475);
    	
    	myRoot.getChildren().add(myMessage);

    }
    
    private void exitTurnChangeSplash(){
    	myRoot.getChildren().remove(myMessage);
    	myRoot.getChildren().remove(mySplash);
    	myMessage = null;
    	mySplash = null;
    	

	    myState = GameState.MAPMENU;
    }
    
    private void enterMap(ChapterMap cMap){
    	myState = GameState.MAPMENU;
    	myChapterMap = cMap;

    	Vector<ImageView> imagesToAdd = cMap.getTileImages(myWidth, myHeight);
    	imagesToAdd.addAll(cMap.getCharacterImages(myWidth, myHeight));
    	
    	myRoot.getChildren().addAll(imagesToAdd);
    	
    	
    	myMapCursor = new MapCursor(myWidth, myHeight, cMap.size());
    	myRoot.getChildren().add(myMapCursor);

    }
    
    private void enterMoveMenu(){
    	System.out.println("Enter");
    	myState = GameState.MOVEMENU;
    	
    	myMoveStart = myMapCursor.getLocation();
    	myMoveEnd = null;
    	
    	myValidMoves = myChapterMap.possibleMove(myMapCursor.getLocation());
    	myMoveImages = myChapterMap.getMoveSquares(myValidMoves, myWidth, myHeight);
    	myRoot.getChildren().addAll(myMoveImages);
    }
    
    private void exitMoveMenu(){
    	myState = GameState.MAPMENU;
    	
    	myRoot.getChildren().removeAll(myMoveImages);
    	myMoveImages = null;
    	myValidMoves = null;
    	myMoveStart = null;
    	myMoveEnd = null;
    	
    }
    
    private void enterActionMenu(){
    	myState = GameState.ACTIONMENU;
    	
    	mySplash = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("ActionMenu.png")));
    	mySplash.setX(ACTION_MENU_X);
    	mySplash.setY(ACTION_MENU_Y);
    	myRoot.getChildren().add(mySplash);
    	
    	myMenuCursor = new MenuCursor(ACTION_CURSOR_X, ACTION_CURSOR_Y, CURSOR_SIZE, CURSOR_SPACING, 4);
    	myMenuCursor.setFill(Color.GOLD);
    	myRoot.getChildren().add(myMenuCursor);
    }
    
    private void exitActionMenu(){
    	myState = GameState.MOVEMENU;
    	
    	myRoot.getChildren().remove(myMenuCursor);
    	myRoot.getChildren().remove(mySplash);
    	myMenuCursor = null;
    	mySplash = null;
    	
    }
    
    private void enterAttackMenu(boolean staff){
    	myState = GameState.ATTACKMENU;
    	
    	myStaffBool = staff;
    }
    
    private void exitAttackMenu(){
    	myState = GameState.ACTIONMENU;
    	
    }
    
    private void enterVictorySplash(String victor){
    	myState = GameState.VICTORY;
    	
    	mySplash = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("VictorySplash.png")));
    	myRoot.getChildren().add(mySplash);
    	
    	Text myMessage = new Text(String.format("%s are victorious!", victor));
    	myMessage.setFont(Font.font("Copperplate", 30));
    	myMessage.setFill(Color.RED);
    	
    	myMessage.setX(myWidth / 2 - myMessage.getBoundsInLocal().getWidth() / 2);
    	myMessage.setY(475);
    	
    	myRoot.getChildren().add(myMessage);
    	
    }
}
