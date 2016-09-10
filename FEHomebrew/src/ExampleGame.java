import java.util.Vector;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;


/**
 * Separate the game code from some of the boilerplate code.
 * 
 * @author Robert C. Duvall
 */
class ExampleGame {
    public static final String TITLE = "Example JavaFX";
    public static final int KEY_INPUT_SPEED = 5;
    private static final double GROWTH_RATE = 1.1;
    private static final int BOUNCER_SPEED = 30;
    private static final int CURSOR_SIZE = 20;

    private int myWidth;
    private int myHeight;
    private Scene myScene;
    private Group myRoot;
    private ImageView mySplash;
    private Rectangle myTopBlock;
    private Rectangle myBottomBlock;
    private Cursor myCursor;
    private GameState myState;


    /**
     * Returns name of the game.
     */
    public String getTitle () {
        return TITLE;
    }
    
    private enum GameState{
    	SPLASH,
    	MAPMENU,
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
    }



    // What to do each time a key is pressed
    private void handleKeyInput (KeyCode code) {
    	switch (myState){
	        case SPLASH:
	        	splashKeyHandler(code);
	        	break;
	        case MAPMENU:
	        	//mapMenuKeyHandler(code);
	        	break;
	        case ATTACKMENU:
	        	//attackMenuKeyHandler(code);
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
	            myCursor.moveUp(1);
	            break;
	        case DOWN:
	        	myCursor.moveDown(1);
	        	break;
	        case R:
	        	if (myCursor.getPos() == 0){
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
    
    private void enterSplash(){
    	myState = GameState.SPLASH;
    	
    	
    	Image splashImage = new Image(getClass().getClassLoader().getResourceAsStream("giphy.gif"));
    	mySplash = new ImageView(splashImage);
    	myRoot.getChildren().add(mySplash);
    	
        mySplash.setX(myWidth / 2 - mySplash.getBoundsInLocal().getWidth() / 2);
        mySplash.setY(myHeight / 2  - mySplash.getBoundsInLocal().getHeight() / 2);
    	
    	myCursor = new Cursor(400, 600, CURSOR_SIZE, 100, 2);
        myCursor.setFill(Color.RED);
    	myRoot.getChildren().add(myCursor);	
    }

    private void exitSplash(){
    	mySplash.setImage(null);
    	myRoot.getChildren().remove(mySplash);
    	myRoot.getChildren().remove(myCursor);
    	myCursor = null;
    }
    
    private void enterMap(ChapterMap cMap){
    	myState = GameState.MAPMENU;

    	Vector<ImageView> imagesToAdd = cMap.getTileImages(myWidth, myHeight);
    	myRoot.getChildren().addAll(imagesToAdd);
    	
    	imagesToAdd = cMap.getCharacterImages(myWidth, myHeight);
    	myRoot.getChildren().addAll(imagesToAdd);
    	
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
