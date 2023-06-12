import javax.swing.*;
/**
 * @author Amelie KOLLAN
 * @author Lena Wiedenhöft
 * @author Nicklas AMLINGER
 *
 * Breakout is the main class that sets up the JFrame and calls the GameLogic
 */

public class Breakout {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("Break Out");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        // create game logic object
        GameLogic gameLogic = new GameLogic();
        // add panel to window
        frame.add(gameLogic);
        frame.pack();
        frame.setVisible(true);
        gameLogic.start();
    }
}