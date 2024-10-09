package game;

import level.Level1;
import level.Level2;
import level.Level3;
import misc.GiveFocus;
import player.*;

/**
 * The main class that initializes and manages the game.
 */
public class Game {
    // Fields
    private GameLevel currentLevel; // The current game level
    private final GameView view; // The game view
    private final StanController controller; // The controller for player input
    private final StanAnimation animation; // The animation for the player
    private final ScoreManager scoreManager; // The score manager for the game
    private final int DEFAULT_ZOOM = 25; // The default zoom level

    /**
     * The width of the game window. 800 recommended.
     */
    public static int resX = 800; // The width of the game window

    /**
     * The height of the game window. 600 recommended.
     */
    public static int resY = 600; // The height of the game window

    /**
     * Constructs a new Game instance.
     */
    public Game() {
        // Initialize score manager to preserve scores across levels
        scoreManager = new ScoreManager();

        // Initialize level 1
        currentLevel = new Level1(this);

        // Set up game view
        view = new GameView(currentLevel, resX, resY, "data/Environments/SkyBox1.png");
        view.setZoom(DEFAULT_ZOOM);
        view.setPlayer(currentLevel.getPlayer()); // Set player object in GameView

        // Add player camera tracking
        currentLevel.addStepListener(new StanTracker(view, currentLevel.getPlayer()));

        // Set up player input
        controller = new StanController(currentLevel.getPlayer());
        view.addKeyListener(controller);
        view.addMouseListener(new GiveFocus());

        // Set up player animation
        animation = new StanAnimation(currentLevel.getPlayer());
        controller.setStanAnimation(animation);

        // Set up the game frame
        new GameFrame(view);

        // Start world
        currentLevel.start();
    }

    /**
     * Switches to the next level.
     */
    public void goToNextLevel(){
        // Unload current level
        currentLevel.stop();
        // Stop current level music
        currentLevel.stopLevelBGM();

        // Load next level
        if (currentLevel instanceof Level1) {
            currentLevel = new Level2(this);
            view.setBgImagePath("data/Environments/SkyBox2.png");
        } else if (currentLevel instanceof Level2) {
            currentLevel = new Level3(this);
            view.setBgImagePath("data/Environments/SkyBox3.png");
        } else if (currentLevel instanceof Level3) {
            // Set game as completed and close it
            view.setGameWin(true);
            System.out.println("Well done! Game complete.");
        }

        // Get the new player instance for the current level
        Stan newPlayer = currentLevel.getPlayer();

        // Update view for the new player
        view.setWorld(currentLevel);
        view.setPlayer(newPlayer);
        view.setZoom(DEFAULT_ZOOM);

        // Update player controls for new level
        controller.updatePlayer(newPlayer);

        // Update animation with the new player
        animation.updatePlayer(newPlayer);

        // Set up player camera tracker for the new level
        currentLevel.addStepListener(new StanTracker(view, newPlayer));

        // Set the ScoreManager for the current level
        currentLevel.setScoreManager(scoreManager);

        // Start the new level
        currentLevel.start();
    }

    // Getter for ScoreManager
    public ScoreManager getScoreManager() {
        return scoreManager;
    }

    // Getter methods for resX and resY
    public static int getResX() {
        return resX;
    }
    public static int getResY() {
        return resY;
    }

    // Main method to start the game
    public static void main(String[] args) {
        new Game();
        System.out.println("Game Start!");
    }
}

