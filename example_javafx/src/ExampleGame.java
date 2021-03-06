import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
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
    private static final double GROWTH_RATE = 1.1;
    private static final int BOUNCER_SPEED = 30;

    private Scene myScene;
    private Group root;
    private ImageView myBouncer;
    private Rectangle myTopBlock;
    private Rectangle myBottomBlock;


    /**
     * Returns name of the game.
     */
    public String getTitle () {
        return TITLE;
    }

    /**
     * Create the game's scene
     */
    public Scene init (int width, int height) {
        // create a scene graph to organize the scene
        root = new Group();
        // create a place to see the shapes
        myScene = new Scene(root, width, height, Color.GREEN);
        // make some shapes and set their properties
        
        myTopBlock = new Rectangle(width / 2 - 12, height / 2 - 100, 24, 100);
        myTopBlock.setFill(Color.RED);
        myBottomBlock = new Rectangle(width / 2 - 25, height / 2 + 50, 50, 50);
        myBottomBlock.setFill(Color.BISQUE);
        
        Image image = new Image(getClass().getClassLoader().getResourceAsStream("giphy.gif"));
        myBouncer = new ImageView(image);
        // x and y represent the top left corner, so center it
        myBouncer.setX(width / 2 - myBouncer.getBoundsInLocal().getWidth() / 2);
        myBouncer.setY(height / 2  - myBouncer.getBoundsInLocal().getHeight() / 2);
        
        // order added to the group is the order in which they are drawn
		root.getChildren().add(myTopBlock);
		root.getChildren().add(myBottomBlock);

        root.getChildren().add(myBouncer);

        
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
        if (myBouncer != null && myBottomBlock.getBoundsInParent().intersects(myBouncer.getBoundsInParent())) {
            myBottomBlock.setFill(Color.BURLYWOOD);
        }
        else {
            myBottomBlock.setFill(Color.BISQUE);
        }
    }


    // What to do each time a key is pressed
    private void handleKeyInput (KeyCode code) {
        switch (code) {
            case RIGHT:
                myTopBlock.setX(myTopBlock.getX() + KEY_INPUT_SPEED);
                break;
            case LEFT:
                myTopBlock.setX(myTopBlock.getX() - KEY_INPUT_SPEED);
                break;
            case UP:
                myTopBlock.setY(myTopBlock.getY() - KEY_INPUT_SPEED);
                break;
            case DOWN:
                myTopBlock.setY(myTopBlock.getY() + KEY_INPUT_SPEED);
                break;
            case ENTER:
            	if (root.getChildren().contains(myBouncer)){
            		root.getChildren().remove(myBouncer);
            	} else {
            		root.getChildren().add(myBouncer);
            	}
                break;
            default:
                // do nothing
        }
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
    	if (e.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
    		if (e.getButton().equals(MouseButton.PRIMARY)){
    	        if (myBottomBlock.contains(e.getX(), e.getY())) {
    	            myBottomBlock.setScaleX(myBottomBlock.getScaleX() * GROWTH_RATE);
    	            myBottomBlock.setScaleY(myBottomBlock.getScaleY() * GROWTH_RATE);
    	        }
    		} else if (e.getButton().equals(MouseButton.SECONDARY)) {
	            myBottomBlock.setScaleX(myBottomBlock.getScaleX() / GROWTH_RATE);
	            myBottomBlock.setScaleY(myBottomBlock.getScaleY() / GROWTH_RATE);
    		}
    	}
    }
}
