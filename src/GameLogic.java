import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Amelie KOLLAN
 * @author Lena Wiedenhöft
 * @author Nicklas AMLINGER
 *
 * GameLogic contains all objects and defines the logic of the game
 * (i.e. checking for collision, managing the current game state)
 */

public class GameLogic extends JPanel
{
    private int ballCount;
    private int score;
    private Paddle paddle;
    private Ball ball;
    private Set<Brick> bricks;
    private GameState gameState;

    /**
     * Constructor. Sets ball count, score, game state, a new paddle and ball, all with data from the Configuration.
     * Also creates an empty new Hashset for the bricks, with presumably will be set in a later exercise
     */
    public GameLogic() {
        this.ballCount = Configuration.BALL_COUNT_INITIAL;
        this.score = 0;
        this.gameState = GameState.SETUP;
        this.paddle = new Paddle(
            this,
            new Color(0, 0, 0),
            Configuration.PADDLE_X_POSITION,
            Configuration.PADDLE_Y_POSITION,
            Configuration.PADDLE_X_SIZE,
            Configuration.PADDLE_Y_SIZE
        );
        this.ball = new Ball(this,
            new Color(0, 0, 0),
            Configuration.BALL_X_Position,
            Configuration.BALL_Y_Position,
            Configuration.BALL_X_SIZE,
            Configuration.BALL_Y_SIZE);
        this.bricks = createLevel1();

        // set panel size
        setPreferredSize(new Dimension(Configuration.FIELD_X_SIZE, Configuration.FIELD_Y_SIZE));
    }

    /**
     * Returns the current ball count
     * @return current ball count
     */
    public int getBallCount() {
        return ballCount;
    }

    /**
     * Returns the current score
     * @return current score
     */
    public int getScore() {
        return score;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        // paint panel
        super.paintComponent(graphics);
        // configure rendering pipeline: Enable antialiasing and high render quality
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        // render bricks, paddle and ball
        for (Brick brick: bricks) {
            brick.render(graphics);
        }
        paddle.render(graphics);
        ball.render(graphics);
        // synchronize graphics state
        Toolkit.getDefaultToolkit().sync();
    }
    
    private Set<Brick> createLevel1() {
        int colCount = 4;
        int rowCount = 3;

        return createLevel(colCount, rowCount);
    }

    private Set<Brick> createLevel(int colCount, int rowCount) {
        HashSet<Brick> newBricks = new HashSet<Brick>();
        int xMargin = 1;
        int yMargin = 10;
        int defaultXPos = Configuration.FIELD_X_SIZE - (colCount * (Configuration.BRICK_X_SIZE + xMargin) + (Configuration.BRICK_Y_SIZE / 2));
        int xPos = defaultXPos;
        int yPos = 20 + Configuration.BALL_Y_SIZE / 2;

        for (int i = 0; i < (rowCount * colCount); i++) {
            if(i != 0) {
                if (i % colCount == 0) {
                    xPos = defaultXPos;
                    yPos += Configuration.BRICK_Y_SIZE + yMargin;
                } else {
                    xPos += Configuration.BRICK_X_SIZE + xMargin;
                }
            }

            newBricks.add(new Brick(this, new Color(255, 0 , 0), xPos, yPos, Configuration.BRICK_X_SIZE, Configuration.BRICK_Y_SIZE));
        }

        return newBricks;

    }
}
