import java.awt.*;

/**
 * @author Amelie KOLLAN
 * @author Lena Wiedenhöft
 * @author Nicklas AMLINGER
 *
 * Paddle is a subclass of GameObject, extending its properties by velocity along the x-axis
 */
public class Paddle extends GameObject
{
    private int xVelocity;

    /**
     * Constructor. Sets all the paddles properties, that were defined by its super class
     * @param gameLogic the corresponding gameLogic
     * @param color the color in which the paddle should be presented
     * @param xPosition the paddles position along the x-axis
     * @param yPosition the paddles position along the y-axis
     * @param xSize the paddles width
     * @param ySize the paddles height
     */
    public Paddle(GameLogic gameLogic, Color color, int xPosition, int yPosition, int xSize, int ySize) {
        super(gameLogic, color, xPosition, yPosition, xSize, ySize);
        this.xVelocity = 0;
    }

    /**
     * Default setter for the velocity
     * @param velocity the velocity to be set
     */
    public void setVelocity(int velocity) {
        this.xVelocity = velocity;
    }

    /**
     * Renders the paddle on the Frame
     * @param graphics needed to render the paddle
     */
    public void render(Graphics graphics) {
        graphics.setColor(color);
        graphics.fillRect(xPosition - xSize / 2, yPosition - ySize / 2, xSize, ySize);
    }

    /**
     * Moves the paddle along the x-axis and keeps in within bounds
     */
    public void move() {
        xPosition += xVelocity;
        int xHalf = xSize / 2;
        if (xPosition < xHalf) {
            xPosition = xHalf;
        } else if (xPosition >= gameLogic.getWidth() - xHalf) {
            xPosition = gameLogic.getWidth() - xHalf;
        }
    }

    /**
     * Resets the paddles position
     * @param xPosition the position where to set the paddle along the x-axis
     */
    public void resetPosition(int xPosition) {
        this.xVelocity = 0;
        this.xPosition = xPosition;
    }
}
