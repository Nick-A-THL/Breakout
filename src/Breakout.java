import javax.swing.*;

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
    }
}