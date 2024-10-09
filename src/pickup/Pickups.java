package pickup;

import city.cs.engine.*;
import game.GameLevel;
import player.Stan;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 * An abstract class representing pickups in the game. For pickups to be extended from this
 */
public abstract class Pickups extends DynamicBody {
    private SoundClip pickup;

    /**
     * Constructs a pickup object with the specified attributes.
     * @param level The GameLevel object in which the pickup exists.
     * @param shape The shape of the pickup.
     * @param imagePath The path to the image file representing the pickup.
     * @param imageSize The size of the image representing the pickup.
     */
    public Pickups(GameLevel level, Shape shape, String imagePath, float imageSize) {
        super(level, shape);
        BodyImage image = new BodyImage(imagePath, imageSize);
        addImage(image);
    }

    /**
     * Plays the sound associated with picking up the object.
     * @param pickupPath The path to the sound file to be played.
     */
    public void playPickupSound(String pickupPath) {
        try {
            pickup = new SoundClip(pickupPath);   // Open an audio input stream
            pickup.setVolume(0.7); //may vary, adjust so that sound effects can be heard
            pickup.play();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            //code in here will deal with any errors
            //that might occur while loading/playing sound
            System.out.println(e);
        }
    }

    /**
     * Abstract method to handle collision with the player.
     * @param player The player object colliding with the pickup. This called in StanCollision.java.
     */
    public abstract void collide(Stan player);
}
