package game;

import javax.swing.*;

/**
 * The main frame class for the game, extending JFrame
 */
public class GameFrame extends JFrame {

    /**
     * Constructs a new GameFrame instance.
     * @param view The game view to be displayed in the frame.
     */
    public GameFrame(GameView view) {
        // Set window title
        super("CityGame Milestone 1 Demo");

        // Set window icon
        ImageIcon icon = new ImageIcon("data/UI/GameIcon.png");
        setIconImage(icon.getImage());

        // JFrame settings
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setFocusable(true);
        add(view);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        requestFocus();
    }
}
