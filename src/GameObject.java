import java.awt.*;

/**
 * @author Amelie KOLLAN
 * @author Lena Wiedenhöft
 * @author Nicklas AMLINGER
 *
 * GameObject is a super class for all Objects of the game, defining the properties all objects share,
 * like positions, sizes and color
 */
public class GameObject
{
    protected java.awt.Color color;
    protected int xPosition;
    protected int yPosition;
    protected int xSize;
    protected int ySize;
    protected GameLogic gameLogic;

    /**
     * Constructor. Sets all the objects properties, that were defined by this super class
     * @param gameLogic the corresponding gameLogic
     * @param color the color in which the game object should be presented
     * @param xPosition the objects position along the x-axis
     * @param yPosition the objects position along the y-axis
     * @param xSize the objects width
     * @param ySize the objects height
     */
    public GameObject(GameLogic gameLogic, Color color, int xPosition, int yPosition, int xSize, int ySize) {
        this.gameLogic = gameLogic;
        this.color = color;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.xSize = xSize;
        this.ySize = ySize;
    }

    /**
     * Sets the objects position
     * @param xPosition the position along the x-axis
     * @param yPosition the position along the y-axis
     */
    public void setPosition(int xPosition, int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    /**
     * Gets the current position along the x-axis
     * @return the current position along the x-axis
     */
    public int getX() {
        return xPosition;
    }

    /**
     * Gets the current position along the y-axis
     * @return the current position along the y-axis
     */
    public int getY() {
        return yPosition;
    }

    /**
     * Gets the current width
     * @return the current width
     */
    public int getXSize() {
        return xSize;
    }

    /**
     * Gets the current height
     * @return the current height
     */
    public int getYSize() {
        return ySize;
    }

    /**
     * Getter that creates a rectangle containing the objects size and position properties
     * @return new rectangle with the objects size and position
     */
    public java.awt.Rectangle getHitBox() {
        return new Rectangle(xPosition, yPosition, xSize, ySize);
    }
}
