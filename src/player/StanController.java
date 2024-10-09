package player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The Main Class responsible for controlling player actions through keyboard input.
 */
public class StanController implements KeyListener {
    private Stan player;
    private StanAnimation playerAnim;

    /**
     * Constructs a StanController object with the given player.
     * @param player The player object to control.
     */
    public StanController(Stan player) {
        this.player = player;
    }

    /**
     * Updates the player object. Used in level change to ensure player controls still work.
     * @param newPlayer The new player object.
     */
    public void updatePlayer(Stan newPlayer) {
        this.player = newPlayer;
    }

    /**
     * Sets the player animation.
     * @param playerAnim The player animation object.
     */
    public void setStanAnimation(StanAnimation playerAnim) {
        this.playerAnim = playerAnim;
    }

    //keyboard input configuration
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_UP:
                player.setReadying(true);
                readySubWeapon();
                break;
            case KeyEvent.VK_DOWN:
                player.setKneeling(true);
                crouch();
                break;
            case KeyEvent.VK_RIGHT:
                if (!player.isAttacking()) {
                    player.setWalking(true);
                    moveRight();
                }
                break;
            case KeyEvent.VK_LEFT:
                if (!player.isAttacking()) {
                    player.setWalking(true);
                    moveLeft();
                }
                break;
            case KeyEvent.VK_Z:
                playerJump();
                break;
            case KeyEvent.VK_X:
                attack();
                break;
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT:
                player.setWalking(false);
                player.stopWalking();
                standing();
                break;
            case KeyEvent.VK_UP:
                player.setReadying(false);
                standing();
                break;
            case KeyEvent.VK_DOWN:
                player.setKneeling(false);
                standing();
                if (player.isWalking()) {
                    playerAnim.updateAnimation("walking");
                }
                break;
        }
    }

    /**
     * Initiates the attack action of the player.
     * Checks if the player is not already attacking, then sets the attacking flag to true.
     * Stops the player from walking, plays the attack animation, and spawns the whip hitbox after a delay.
     * After the attack animation finishes, resumes walking if the player was walking during the attack.
     * this is to synchronise the duration of the attack via the whip with player stopping
     */
    void attack() {
        if (!player.isAttacking()) { // Check if not already attacking
            player.setAttacking(true); // Set attacking flag to true

            // Stop the player from walking
            player.stopWalking();

            // Animation
            if (player.isKneeling()) {
                playerAnim.updateAttackAnimation("attackingKneeling");
            } else {
                playerAnim.updateAttackAnimation("attacking");
            }

            //create a Swing Timer to spawn the whip hitbox after a delay
            Timer timer = new Timer(280, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Spawn the whip hitbox for the attack
                    player.spawnWhipHitBox();
                    // Stop the timer
                    ((Timer) e.getSource()).stop();
                }
            });
            timer.setRepeats(false);
            timer.start();

            // create a new swing timer to resume walking after finishing attack
            Timer resetTimer = new Timer(320, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // if the player is still walking while attacking
                    // resume walking
                    if (player.isWalking()) {
                        if (player.isFacingRight()) {
                            moveRight();
                        } else {
                            moveLeft();
                        }
                    }
                    // Stop the timer
                    ((Timer) e.getSource()).stop();
                }
            });
            resetTimer.setRepeats(false);
            resetTimer.start();
        }
    }

    /**
     * Sets the player to a standing position.
     * If the player is not walking, updates the animation to the standing state.
     */
    void standing() {
        if (!player.isWalking()) {
            player.setStanding(true);
            playerAnim.updateAnimation("standing");
        } else {
            player.setStanding(false);
        }
    }

    /**
     * Initiates the action of readying the sub-weapon.
     * Updates the player animation accordingly.
     */
    void readySubWeapon() {
        playerAnim.updateAnimation("readying");
    }

    /**
     * Moves the player to the right.
     * Sets the player's facing direction to right, starts walking, and updates the animation.
     */
    void moveRight() {
        player.setFacingRight(true);
        player.startWalking(10);
        playerAnim.updateAnimation("walking");
    }

    /**
     * Moves the player to the left.
     * Sets the player's facing direction to left, starts walking, and updates the animation.
     */
    void moveLeft() {
        player.setFacingRight(false);
        player.startWalking(-10);
        playerAnim.updateAnimation("walking");
    }

    /**
     * Initiates the crouch action of the player.
     * If the player is not walking, sets the player to a kneeling position and updates the animation.
     */
    void crouch() {
        if (!player.isWalking()) {
            player.setKneeling(true);
            playerAnim.updateAnimation("kneeling");
        } else {
            player.setKneeling(false);
        }
    }

    /**
     * Initiates the player's jumping action.
     * Uses the kneeling animation for jumping and performs the jump action.
     */
    void playerJump() {
        //kneeling animation is reused for jumping
        playerAnim.updateAnimation("kneeling");
        player.jump(14);
    }
}