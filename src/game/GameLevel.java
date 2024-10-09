package game;

import city.cs.engine.*;

import player.ScoreManager;
import player.Stan;
import player.StanCollision;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 * Abstract class representing a level in the game.
 */
public abstract class GameLevel extends World {
    // Fields
    public Stan player; // The player character
    private ScoreManager scoreManager; // The score manager for the level
    private SoundClip gameMusic; // The background music for the level

    /**
     * Constructs a new GameLevel instance.
     * @param game The Game instance managing the game.
     */
    public GameLevel(Game game) {
        player = new Stan(this, game.getScoreManager());

        // Pickups
        StanCollision gbPickup = new StanCollision(getPlayer());
        getPlayer().addCollisionListener(gbPickup);
    }

    /**
     * Starts playing the background music for the level.
     * @param musicPath The file path to the background music.
     */
    public void startLevelBGM(String musicPath) {
        try {
            gameMusic = new SoundClip(musicPath);
            gameMusic.setVolume(0.2);
            gameMusic.loop();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    /**
     * Stops playing the background music for the level.
     */
    public void stopLevelBGM() {
        if (gameMusic != null) {
            gameMusic.stop();
        }
    }

    /**
     * Retrieves the player character for the level.
     * @return The player character.
     */
    public Stan getPlayer() {
        return player;
    }

    /**
     * Retrieves the score manager for the level.
     * @return The score manager.
     */
    public ScoreManager getScoreManager() {
        return scoreManager;
    }

    /**
     * Sets the score manager for the level.
     * @param scoreManager The score manager to be set.
     */
    public void setScoreManager(ScoreManager scoreManager) {
        this.scoreManager = scoreManager;
    }

    /**
     * Determines whether the level is complete.
     * @return True if the level is complete, otherwise false.
     */
    public abstract boolean isComplete();
}
