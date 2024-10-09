package game;

import city.cs.engine.*;
import javax.swing.*;
import java.awt.*;
import misc.CustomFont; //loads my custom font handler
import player.Stan;

/**
 * The main view class for the game handling drawing graphics to the foreground/background, extending UserView.
 */
public class GameView extends UserView {

    private Stan player;
    private Image bg;
    private final ImageIcon lifeBar = new ImageIcon("data/UI/LifeBar.png");
    private final ImageIcon lifeBarTray = new ImageIcon("data/UI/LifeBarTray.png");

    //Fonts folder located within misc package
    public static final Font STATUS_FONT = CustomFont.loadFont("Fonts/CV2.ttf", 25f);
    public static final Font GAMEOVER_FONT = CustomFont.loadFont("Fonts/CV2.ttf", 40f);

    int windowWidth = Game.getResX();
    int windowHeight = Game.getResY();
    private String bgImagePath;
    private boolean gameWin;

    /**
     * Constructs a new GameView.
     * @param world The GameLevel associated with this view.
     * @param width The width of the view.
     * @param height The height of the view.
     * @param bgImagePath The path to the background image.
     */
    public GameView(GameLevel world, int width, int height, String bgImagePath) {
        super(world, width, height);
        bg = new ImageIcon(bgImagePath).getImage().getScaledInstance(windowWidth, windowHeight, Image.SCALE_DEFAULT);
    }

    // Method to handle game over logic
    public void handleGameOver() {
        Timer gameOverTimer = new Timer(3000, e -> System.exit(0)); // Timer to close the game after 3 seconds
        gameOverTimer.setRepeats(false); // Make sure the timer only fires once
        gameOverTimer.start(); // Start the timer
    }

    // Accept path to background image as string in other classes
    // So bg image can be changed for each level
    public void setBgImagePath(String bgImagePath) {
        this.bgImagePath = bgImagePath;
        bg = new ImageIcon(bgImagePath).getImage().getScaledInstance(windowWidth, windowHeight, Image.SCALE_DEFAULT);
    }

    @Override
    protected void paintBackground(Graphics2D g) {
        g.drawImage(bg,0, 0, this);
    }

    @Override
    protected void paintForeground(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setFont(STATUS_FONT);

        int lifeCount = player.getLife();
        int goldCount = player.getGold();

        //life bar dimensions
        int barWidth = 32; // Width of each life bar image
        int barHeight = 38; // Height of each life bar image
        int spacing = 1; // Spacing between life bars
        int startX = 170; // X coordinate to start drawing life bars

        //draw the tray the life bar is drawn on
        g.drawImage(lifeBarTray.getImage(), startX, 30, 140, 38, this);

        //draw complete life bar via for loop
        for (int i = 0; i < lifeCount; i++) {
            int x = startX + (barWidth + spacing) * i;
            g.drawImage(lifeBar.getImage(), x, 30, barWidth, barHeight, this);
        }
        // GOLD display
        //make x-position of GOLD adjust to any game window width
        g.drawString("GOLD " + goldCount, windowWidth-350, 60);

        // when health reach zero, LIFE -> DEAD + draw Game Over text
        if (lifeCount > 0) {
            g.drawString("LIFE", 50, 60);
        } else {
            g.drawString("DEAD", 50, 60);
            g.setFont(GAMEOVER_FONT);
            g.setColor(Color.RED);
            g.drawString("GAME OVER", windowWidth/3.5f,windowHeight/2f);

            // Call the method to handle game over logic
            handleGameOver();
        }

        // check if game is won
        if (isGameWin()) {
            player.destroy();
            g.setFont(GAMEOVER_FONT);
            g.setColor(Color.GREEN);
            g.drawString("YOU WIN!", windowWidth/3.4f,windowHeight/2f);

            // Call the method to handle game over logic
            handleGameOver();
        }
    }

    /**
     * Sets the player character for the view.
     * @param player The player character.
     */
    public void setPlayer(Stan player) {
        this.player = player;
    }

    /**
     * Sets the game win status.
     * @param gameWin true if the game is won, otherwise false.
     */
    public void setGameWin(boolean gameWin) {
        this.gameWin = gameWin;
    }

    /**
     * Checks if the game is won.
     * @return true if the game is won, otherwise false.
     */
    public boolean isGameWin() {
        return gameWin;
    }
}
