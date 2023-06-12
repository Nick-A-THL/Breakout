import java.awt.*;

/**
 * @author Amelie KOLLAN
 * @author Lena Wiedenhöft
 * @author Nicklas AMLINGER
 *
 * Ball is a subclass of GameObject, extending its properties by the destroyed property
 */
public class Brick extends GameObject
{
    private boolean destroyed;

    /**
     * Constructor. Sets all the bricks properties, that were defined by its super class
     * @param gameLogic the corresponding gameLogic
     * @param color the color in which the brick should be presented
     * @param xPosition the bricks position along the x-axis
     * @param yPosition the bricks position along the y-axis
     * @param xSize the bricks width
     * @param ySize the bricks height
     */
    public Brick(GameLogic gameLogic, Color color, int xPosition, int yPosition, int xSize, int ySize) {
        super(gameLogic, color, xPosition, yPosition, xSize, ySize);
        this.destroyed = false;
    }

    /**
     * Returns if the brick is currently destroyed
     * @return whether the brick is currently destroyed
     */
    public boolean isDestroyed() {
        return destroyed;
    }

    /**
     * Sets if the object has been destroyed
     */
    public void setDestroyed() {
        this.destroyed = true;
    }

    /**
     * Sets if the object is intact
     */
    public void setIntact() {
        this.destroyed = false;
    }

    /**
     * Renders the brick on the Frame
     * @param graphics needed to render the brick
     */
    public void render(Graphics graphics) {
        graphics.setColor(color);
        graphics.fillRect(xPosition - xSize / 2, yPosition - ySize / 2, xSize, ySize);
    }
}
