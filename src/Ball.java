import java.awt.*;

/**
 * @author Amelie KOLLAN
 * @author Lena Wiedenhöft
 * @author Nicklas AMLINGER
 *
 * Ball is a subclass of GameObject, extending its properties by velocity along both axis
 */
public class Ball extends GameObject
{
    private int xVelocity;
    private int yVelocity;

    /**
     * Constructor. Sets all the balls properties, that were defined by its super class
     * @param gameLogic the corresponding gameLogic
     * @param color the color in which the ball should be presented
     * @param xPosition the balls position along the x-axis
     * @param yPosition the balls position along the y-axis
     * @param xSize the balls width
     * @param ySize the balls height
     */
    public Ball(GameLogic gameLogic, Color color, int xPosition, int yPosition, int xSize, int ySize) {
        super(gameLogic, color, xPosition, yPosition, xSize, ySize);
        this.xVelocity = (Math.random() <= 0.5) ? 1 : -1;
        this.yVelocity = 2;
    }

    /**
     * Gets the balls current velocity along the x-axis
     * @return the current velocity along the x-axis
     */
    public int getXVelocity() {
        return xVelocity;
    }


    /**
     * Gets the balls current velocity along the y-axis
     * @return the current velocity along the y-axis
     */
    public int getYVelocity() {
        return yVelocity;
    }

    /**
     * Sets the balls velocity along both axis
     * @param xVelocity the velocity along the x-axis
     * @param yVelocity the velocity along the y-axis
     */
    public void setVelocity(int xVelocity, int yVelocity) {
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
    }

    /**
     * Renders the ball on the Frame
     * @param graphics needed to render the ball
     */
    public void render(Graphics graphics) {
        graphics.setColor(color);
        graphics.fillOval(xPosition - xSize / 2, yPosition - ySize / 2, xSize, ySize);
    }

    /**
     * Moves the ball along both axis and handles bouncing off of walls
     */
    public void move() {
        xPosition += xVelocity;
        if (xPosition < 0) {
            xVelocity = -xVelocity;
        } else if (xPosition >= gameLogic.getWidth()) {
            xVelocity = -xVelocity;
        }
        yPosition += yVelocity;
        if (yPosition < 0) {
            yVelocity = -yVelocity;
        } else if (yPosition >= gameLogic.getHeight()) {
            yVelocity = -yVelocity;
        }
    }

    /**
     * Resets the balls position
     * @param xPosition the position where to set the ball along the x-axis
     * @param yPosition the position where to set the ball along the y-axis
     */
    public void resetPosition(int xPosition, int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        setVelocity((Math.random() <= 0.5) ? 1 : -1,2);
    }
}
