import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
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
    private Timer timer;

    /**
     * Constructor. Sets ball count, score, game state, a new paddle and ball, all with data from the Configuration.
     * Also creates an empty new Hashset for the bricks, with presumably will be set in a later exercise
     */
    public GameLogic() {
        setFocusable(true);
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
            new Color(0,  100, 0),
            Configuration.BALL_X_Position,
            Configuration.BALL_Y_Position,
            Configuration.BALL_X_SIZE,
            Configuration.BALL_Y_SIZE);
        this.bricks = createLevel1();

        // set panel size
        setPreferredSize(new Dimension(Configuration.FIELD_X_SIZE, Configuration.FIELD_Y_SIZE));
        addKeyListener(new BreakoutKeyAdapter());
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

    /**
     * Paints the panel and all game objects
     * @param graphics the <code>Graphics</code> object to protect
     */
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

    /**
     * Generates bricks for level 1
     * @return the set of bricks for level 1
     */
    private Set<Brick> createLevel1() {
        int colCount = 4;
        int rowCount = 3;

        return createLevel(colCount, rowCount);
    }

    /**
     * Generates bricks for any level
     * @param colCount the number of columns the level has
     * @param rowCount the number of rows the level has
     * @return the levels set of bricks
     */
    private Set<Brick> createLevel(int colCount, int rowCount) {
        HashSet<Brick> newBricks = new HashSet<Brick>();
        int xMargin = 1;
        int yMargin = 10;
        int defaultXPos = Configuration.FIELD_X_SIZE - (colCount * Configuration.BRICK_X_SIZE + (Configuration.BRICK_Y_SIZE + xMargin) / 2);
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

    /**
     * Starts the game
     */
    public void start() {
        gameState = GameState.RUNNING;
        timer = new Timer(Configuration.LOOP_PERIOD, new GameLoop());
        timer.start();
    }

    /**
     * Manages movements, hitBoxes, rules, gameState, removing bricks, etc.
     */
    private void onTick() {
        // move paddle and ball
        paddle.move();
        ball.move();

        // check physics and rules
        if (ball.getHitBox().intersects(paddle.getHitBox())) { // ball hits paddle
            ball.setVelocity(ball.getXVelocity(), -ball.getYVelocity());
        } else if (ball.getY() >= Configuration.FIELD_Y_SIZE) {
            // ball is lost
            // reduce number of balls
            --ballCount;
            if (ballCount <= 0) { // no balls left
                gameState = GameState.GAME_OVER;
                System.out.printf("Game over: You lost. Score = %d%n", score);
                System.exit(-1);
            } else { // at least one ball left, continue level
                restartWithNewBall();
            }
        }

        Rectangle ballHitBox = ball.getHitBox();
        Rectangle nextX = new Rectangle(ballHitBox);
        nextX.setLocation(nextX.x + ball.getXVelocity(), nextX.y);
        Rectangle nextY = new Rectangle(ballHitBox);
        nextY.setLocation(nextY.x, nextY.y + ball.getYVelocity());

        Brick hitBrick = null;
        for (Brick brick : bricks) {
            if (brick.getHitBox().intersects(nextX)) { // hit in the west or east
                ball.setVelocity(-ball.getXVelocity(), ball.getYVelocity());
                hitBrick = brick;
                break;
            }
            if (brick.getHitBox().intersects(nextY)) { // hit in the north or south
                ball.setVelocity(ball.getXVelocity(), -ball.getYVelocity());
                hitBrick = brick;
                break;
            }
        }
        if (hitBrick != null) { // if hit brick then remove it and score
            bricks.remove(hitBrick);
            score += Configuration.BRICK_SCORE;
        }

        if(bricks.isEmpty()) {
            gameState = GameState.GAME_OVER;
            System.out.printf("Game over: You won! Score = %d%n", score);
            System.exit(-1);

        }


        repaint();
    }

    /**
     * Restarts the game with a new ball and reduces ballCount
     */
    private void restartWithNewBall() {
        this.ballCount--;
        System.out.printf("Ball lost. Score = %d. Balls left = %d%n", score, ballCount);
        paddle.resetPosition(Configuration.PADDLE_X_POSITION);
        ball.resetPosition(Configuration.BALL_X_Position, Configuration.BALL_Y_Position);
    }

    /**
     * GameLoop to call onTick
     */
    private class GameLoop implements ActionListener
    {
        /**
         * Calls onTick every loop
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            onTick();
        }
    }

    /**
     * Checks if a key is pressed or released
     */
    private class BreakoutKeyAdapter extends KeyAdapter {

        /**
         * Checks if a key is released
         */
        @Override
        public void keyReleased(KeyEvent event) {
            onKeyReleased(event);
        }

        /**
         * Checks if a key is released
         */
        @Override
        public void keyPressed(KeyEvent event) {
            onKeyPressed(event);
        }
    }

    /**
     * Moves the panel when a key is pressed
     * @param event the keyEvent for the pressed key
     */
    private void onKeyPressed(KeyEvent event) {
        int key = event.getKeyCode();
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
            paddle.setVelocity(-Configuration.PADDLE_VELOCITY);
        }
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            paddle.setVelocity(Configuration.PADDLE_VELOCITY);
        }
    }

    /**
     * Stops moving the panel when the key is released
     * @param event the keyEvent for the released key
     */
    private void onKeyReleased(KeyEvent event) {
        int key = event.getKeyCode();
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_A || key == KeyEvent.VK_D) {
            paddle.setVelocity(0);
        }
    }
}
