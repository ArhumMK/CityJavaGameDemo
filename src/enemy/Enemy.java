package enemy;

import city.cs.engine.*;
import game.GameLevel;
import player.Stan;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 * Abstract class representing an enemy entity in the game.
 */
public abstract class Enemy extends Walker implements StepListener {

    private SoundClip enemy;

    /**
     * Constructs an enemy object with the given shape and adds it to the current level.
     * @param currentLevel The current level of the game.
     * @param shape The shape of the enemy object.
     */
    public Enemy(GameLevel currentLevel, Shape shape) {
        super(currentLevel, shape);
        currentLevel.addStepListener(this);
        setGravityScale(2);
    }

    /**
     * Plays the sound associated with the enemy.
     * @param enemyPath The path to the sound file.
     */
    public void playEnemySound(String enemyPath) {
        try {
            enemy = new SoundClip(enemyPath);   // Open an audio input stream
            enemy.setVolume(0.7); //may vary, adjust so that sound effects can be heard
            enemy.play();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            //code in here will deal with any errors
            //that might occur while loading/playing sound
            System.out.println(e);
        }
    }

    /**
     * Deals damage to the player.
     * @param player The player object.
     */
    public abstract void dealDamage(Stan player);

    /**
     * Moves the enemy.
     */
    public abstract void move();

    /**
     * Initiates an attack.
     */
    public abstract void attack();

    /**
     * Handles the death of the enemy.
     * @param player The player object.
     */
    public abstract void die(Stan player);
}
