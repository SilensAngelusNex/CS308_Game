import java.util.Map;
import java.util.Vector;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;


/**
 * Separate the game code from some of the boilerplate code.
 * 
 * @author Robert C. Duvall
 */
class ExampleGame {
    public static final String TITLE = "Example JavaFX";
    public static final int KEY_INPUT_SPEED = 5;
    private static final int CURSOR_SIZE = 20;

    private Rectangle myTopBlock;
    private Rectangle myBottomBlock;
    
    private Scene myScene;
    private Group myRoot;    
    private GameState myState;
    private int myWidth;
    private int myHeight;
    
    //Used in splash state
    private ImageView mySplash;
	private Cursor myMenuCursor;
	
	//Used in MapMenu state
	private MapCursor myMapCursor;
	private ChapterMap myChapterMap;
	
    //Used in MoveMenu state
	private Map<Point, Integer> myValidMoves;
	private Point myMoveStart;
	private Point myMoveEnd;
	private Vector<ImageView> myMoveImages;
	
	//Used in action menu
	//private Character myCharacter;

	
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
    	EXAMINE
    	
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
        // make some shapes and set their properties

        
        
        myTopBlock = new Rectangle(width / 2 - 12, height / 2 - 100, 24, 100);
        myTopBlock.setFill(Color.RED);
        myBottomBlock = new Rectangle(width / 2 - 25, height / 2 + 50, 50, 50);
        myBottomBlock.setFill(Color.BISQUE);

        // x and y represent the top left corner, so center it

        
        // order added to the group is the order in which they are drawn
        
		myRoot.getChildren().add(myTopBlock);
		myRoot.getChildren().add(myBottomBlock);
		
		enterSplash();
        
        // respond to input
        myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        //myScene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
        myScene.setOnMouseClicked(e -> handleMouseInput((MouseEvent) e));
        return myScene;
    }

    /**
     * Change properties of shapes to animate them
     * 
     * Note, there are more sophisticated ways to animate shapes,
     * but these simple ways work too.
     */
    public void step (double elapsedTime) {
        // update attributes
        myTopBlock.setRotate(myTopBlock.getRotate() - 1);
        myBottomBlock.setRotate(myBottomBlock.getRotate() + 1);
        
        // check for collisions
        // with shapes, can check precisely
        Shape intersect = Shape.intersect(myTopBlock, myBottomBlock);
        if (intersect.getBoundsInLocal().getWidth() != -1) {
            myTopBlock.setFill(Color.MAROON);
        }
        else {
            myTopBlock.setFill(Color.RED);
        }
        // with images can only check bounding box
        if (mySplash != null && myBottomBlock.getBoundsInParent().intersects(mySplash.getBoundsInParent())) {
            myBottomBlock.setFill(Color.BURLYWOOD);
        }
        else {
            myBottomBlock.setFill(Color.BISQUE);
        }
        
        if (myChapterMap != null){
        	if (myChapterMap.countActionsLeft() == 0){
        		myChapterMap.endTurn();
        	}
        }
    }



    // What to do each time a key is pressed
    private void handleKeyInput (KeyCode code) {
    	switch (myState){
	        case SPLASH:
	        	splashKeyHandler(code);
	        	break;
	        case STARTMENU:
	        	startMenuHandler(code);
	        	break;
	        case MAPMENU:
	        	mapMenuKeyHandler(code);
	        	break;
	        case MOVEMENU:
	        	moveMapKeyHandler(code);
	        	break;
	        case ACTIONMENU:
	        	actionMenuKeyHandler(code);
	        	break;
	        case ATTACKMENU:
	        	attackMenuKeyHandler(code);
	        	break;
	        case ANIMATION:
	        	//Do nothing.
	        	break;
	        case VICTORY:
	        	//victoryKeyHandler(code);
	        	break;
	        case EXAMINE:
	        	//examineKeyHandler(code);
	        	break;
	        default:
	        	break;
    	}
    }
    
    private void splashKeyHandler(KeyCode code){
    	switch (code){
	        case RIGHT:
	        	//Do nothing
	            myTopBlock.setX(myTopBlock.getX() + KEY_INPUT_SPEED);
	            break;
	        case LEFT:
	        	//Do nothing
	            myTopBlock.setX(myTopBlock.getX() - KEY_INPUT_SPEED);
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
	        		//Exit splash and init multiplayer
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
	        	characterMousOver();
	        	break;
	        case R:
	        	switch (myMenuCursor.getPos()){
	        		case 0:
	        			startMenuExit();
	        			enterTurnChangeSplash();
	        	}
	        case W:
	        	System.out.printf("Examine %s\t", myMapCursor.getLocation().toString());
	        	System.out.println(myChapterMap.getTerrain(myMapCursor.getLocation()));
	        	System.out.println(myChapterMap.getCharacter(myMapCursor.getLocation()));
	        case Q:
	        	startMenuEnter();
	        default:
	            // do nothing
    }
    
    private void mapMenuKeyHandler(KeyCode code){
    	switch (code){
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
	        	System.out.printf("Examine %s\t", myMapCursor.getLocation().toString());
	        	System.out.println(myChapterMap.getTerrain(myMapCursor.getLocation()));
	        	System.out.println(myChapterMap.getCharacter(myMapCursor.getLocation()));
	        case Q:
	        	//Enter start menu
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
	        	if (	myValidMoves.keySet().contains(myMapCursor.getLocation()) &&
	        			(myChapterMap.getCharacter(myMapCursor.getLocation()) == null ||
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
	        			//Switch to attack? menu for staves
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
    	
    	
    	Image splashImage = new Image(getClass().getClassLoader().getResourceAsStream("giphy.gif"));
    	mySplash = new ImageView(splashImage);
    	myRoot.getChildren().add(mySplash);
    	
        mySplash.setX(myWidth / 2 - mySplash.getBoundsInLocal().getWidth() / 2);
        mySplash.setY(myHeight / 2  - mySplash.getBoundsInLocal().getHeight() / 2);
        
    	
    	myMenuCursor = new Cursor(400, 600, CURSOR_SIZE, 100, 2);
        myMenuCursor.setFill(Color.RED);
    	myRoot.getChildren().add(myMenuCursor);	
    }

    private void exitSplash(){
    	mySplash.setImage(null);
    	myRoot.getChildren().remove(mySplash);
    	myRoot.getChildren().remove(myMenuCursor);
    	myMenuCursor = null;
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
    	System.out.println("Exit");
    	myState = GameState.MAPMENU;
    	
    	myRoot.getChildren().removeAll(myMoveImages);
    	myMoveImages = null;
    	myValidMoves = null;
    	myMoveStart = null;
    	myMoveEnd = null;
    	
    }
    
    private void enterActionMenu(){
    	myState = GameState.ACTIONMENU;
    	
    	myMenuCursor = new Cursor(600, 100, 20, 30, 4);
    	myRoot.getChildren().add(myMenuCursor);
    }
    
    private void exitActionMenu(){
    	myState = GameState.MOVEMENU;
    	
    	myRoot.getChildren().remove(myMenuCursor);
    	myMenuCursor = null;
    	
    }
    
    private void enterAttackMenu(boolean staff){
    	myState = GameState.ATTACKMENU;
    	
    	myStaffBool = staff;
    }
    
    private void exitAttackMenu(){
    	myState = GameState.ACTIONMENU;
    	
    }
    
    // What to do each time a key is pressed
    /*
    private void handleMouseInput (double x, double y) {
        if (myBottomBlock.contains(x, y)) {
            myBottomBlock.setScaleX(myBottomBlock.getScaleX() * GROWTH_RATE);
            myBottomBlock.setScaleY(myBottomBlock.getScaleY() * GROWTH_RATE);
        }
    }
    */
    private void handleMouseInput(MouseEvent e){
    	//Ignore mouse inputs
    }
}
