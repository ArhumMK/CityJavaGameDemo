package player;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 * Represents the whip object that is spawned each time the player attacks.
 * Uses a sensor to detect objects that are to be attacked. (in WhipHandler class)
 */
public class Whip extends DynamicBody {
    private final Sensor sensor;
    private SoundClip w;

    /**
     * Constructs a Whip object in the given world.
     * @param world The world in which the Whip exists.
     */
    public Whip(World world) {
        super(world);

        this.sensor = new Sensor(this, new BoxShape(3.2f, 0.5f), 0);
        this.setGravityScale(0);
        this.getPosition();
    }

    /**
     * Adds a SensorListener to the sensor of the whip.
     * @param listener The SensorListener to be added.
     */
    public void addSensorListener(SensorListener listener) {
        sensor.addSensorListener(listener);
    }

    /**
     * Plays the sound effect associated with the whip.
     * If an error occurs during sound playback, it is printed to the console.
     */
    public void playWhipSound() {
        try {
            w = new SoundClip("data/SoundFX/Fwoosh.wav");
            w.setVolume(0.5);
            w.play();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }
}
