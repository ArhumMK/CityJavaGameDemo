package projectile;

import city.cs.engine.*;
import enemy.Alucard;
import game.GameLevel;
import org.jbox2d.common.Vec2;
import player.Stan;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 * Represents the main projectile of the boss class Alucard.
 * Fires a bat object and manages the bat object's properties.
 */
public class AluBat extends DynamicBody {

    private final Alucard alucard; // Reference to the Alucard(bosse) that spawned this bat
    private final Timer selfDestructTimer; // time to self-destruct the bat object (if player dodges it)
    private SoundClip soundclip;

    private static final float batSize = 1f;
    private static final Shape batShape = new CircleShape(batSize);
    private static final BodyImage BAT_IMAGE = new BodyImage("data/Enemies/Bat.gif", 2 * batSize);
    private static final int SELF_DESTRUCT_DELAY = 3000; // Delay in ms for self-destruction

    /**
     * Constructs a new AluBat projectile associated with the given Alucard boss.
     * @param level The GameLevel in which the bat exists.
     * @param alucard The Alucard boss that spawned this bat.
     */
    public AluBat(GameLevel level, Alucard alucard) {
        super(level, batShape);
        this.alucard = alucard;
        addImage(BAT_IMAGE);
        setGravityScale(0);
        setBullet(true); // Make the projectile a bullet for continuous collision detection

        // Initialize and start the self-destruct timer
        selfDestructTimer = new Timer(SELF_DESTRUCT_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Destroy the bat object when the timer triggers
                destroy();
            }
        });
        selfDestructTimer.setRepeats(false); // Set the timer to trigger only once
        selfDestructTimer.start(); // Start the timer
    }

    /**
     * Throws the bat projectile from the specified position with the given velocity.
     * @param position The position from which the bat is thrown.
     * @param velocity The velocity of the bat.
     */
    public void throwBat(Vec2 position, float velocity) {
        setPosition(position);
        setLinearVelocity(new Vec2(velocity, 0)); // Negative velocity for leftward movement
        playBatSound("data/SoundFX/MonsterShriek.wav");
    }

    /**
     * Destroys the bat projectile. When hit or destroyed by player or after a set time
     */
    public void destroyBat() {
        playBatSound("data/SoundFX/DoorOpen.wav");
        this.destroy();
    }

    /**
     * Handles collision with the player.
     * @param player The player that collides with the bat.
     */
    public void collide(Stan player) {
        //animate hurting player
        player.removeAllImages();
        player.addImage(player.stanHurt);

        //actually hurt player
        player.setLife(player.getLife() - 1);
        player.setLinearVelocity(new Vec2(-20,0));

        //destroy projectile after that
        destroyBat();
    }

    /**
     * Plays the bat sound.
     * @param filepath The filepath of the sound to play.
     */
    public void playBatSound(String filepath) {
        try {
            soundclip = new SoundClip(filepath);   // Open an audio input stream
            soundclip.setVolume(0.7); //may vary, adjust so that sound effects can be heard
            soundclip.play();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            //code in here will deal with any errors
            //that might occur while loading/playing sound
            System.out.println(e);
        }
    }
}
