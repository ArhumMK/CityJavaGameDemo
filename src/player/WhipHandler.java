package player;

import city.cs.engine.*;
import enemy.Enemy;
import org.jbox2d.common.Vec2;
import pickup.Torch;
import projectile.AluBat;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controls the behavior of the spawned whip object used in the player's attack hitbox.
 * Extends the Whip class and implements SensorListener for collision detection.
 */
public class WhipHandler extends Whip implements SensorListener {
    private final Stan player;
    private Whip whip;
    private Timer whipTimer;

    /**
     * Constructs a WhipHandler object associated with the given player and his whip.
     * @param player The player associated with this WhipHandler.
     */
    public WhipHandler(Stan player) {
        super(player.getWorld());
        this.player = player;
        this.whip = null;
    }

    /**
     * Spawns the whip hitbox at the appropriate position based on the player's stance and facing direction.
     * this is what's used in the main attack method.
     * a timer is used to spawn and destroy the whiphitbox accordingly
     * the hitbox is a sensor so objects to be attacked can be managed
     */
    public void spawnWhipHitBox() {
        // Destroy any existing whip hitbox
        destroyWhipHitBox();

        // player is attacking
        player.setAttacking(true);

        // play the sound of the whip
        playWhipSound();

        // Get the current position and facing direction of the player
        Vec2 playerPos = player.getPosition();
        boolean facingRight = player.isFacingRight();

        // Calculate the position for the whip hitbox based on the player's position and facing direction
        float xOffset = facingRight ? 3.5f : -3.5f;
        float yOffset = -1f;
        Vec2 whipPos = new Vec2(playerPos.x + xOffset, playerPos.y + 0.5f);
        Vec2 whipPosKneeling = new Vec2(playerPos.x + xOffset, playerPos.y + yOffset);

        // Set the position of the whip hitbox based on player stance
        if (!player.isKneeling()) {
            whip = new Whip(player.getWorld());
            whip.setPosition(whipPos);
        } else {
            whip = new Whip(player.getWorld());
            whip.setPosition(whipPosKneeling);
        }

        // Register the SensorListener to this WhipHandler
        whip.addSensorListener(this);

        // Schedule a Swing Timer to destroy the whip hitbox after a delay
        whipTimer = new Timer(320, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //destroy whip after 320ms to match animation ending
                destroyWhipHitBox();
                whipTimer.stop();
            }
        });
        whipTimer.setRepeats(false);
        whipTimer.start();
    }

    /**
     * Destroys the whip hitbox.
     * Resets the player's attacking status.
     */
    public void destroyWhipHitBox() {
        if (whip != null) {
            whip.destroy();
            //player has stopped attacking
            player.setAttacking(false);
            whip = null;
        }
    }

    @Override
    public void beginContact(SensorEvent e) {
        if (e.getContactBody() instanceof Enemy enemy) {
            // Destroy the enemy/damage the boss
            enemy.die(player);
        } else if (e.getContactBody() instanceof Torch torch) {
            torch.destroyTorch();
        } else if (e.getContactBody() instanceof AluBat bat) {
            bat.destroy();
        }
    }
    @Override
    public void endContact(SensorEvent e) {
    }
}
