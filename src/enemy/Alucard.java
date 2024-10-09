package enemy;

import city.cs.engine.*;
import game.GameLevel;
import org.jbox2d.common.Vec2;
import player.Stan;
import projectile.AluBat;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents the boss enemy "Alucard" in the game.
 */
public class Alucard extends Enemy implements StepListener, DestructionListener {
    private AluBat bat;
    private Timer spawnTimer; // Timer for spawning bats

    // Boss Attributes
    private static final float ATTACK_RANGE = 20f;
    private int bossHP = 5;     // 5 HP by default
    private boolean isDead = false;     // he is not dead... yet.
    private static final int BAT_SPAWN_DELAY = 660;     // Delay in ms

    //initialize alucard dimensions
    private static final float bossWidth = 1.2f;
    private static final int bossHeight = 5;
    private static final Shape bossShape = new BoxShape(bossWidth,bossHeight/2f);

    //alucard sprites
    public BodyImage bossImage = new BodyImage("data/Enemies/Alucard.gif", bossHeight);
    public BodyImage bossAttackImage = new BodyImage("data/Enemies/AlucardAttack.png", bossHeight);

    /**
     * Constructs an Alucard boss object and adds it to the current level.
     * @param currentLevel The current level of the game.
     */
    public Alucard(GameLevel currentLevel) {
        super(currentLevel, bossShape);
        addImage(bossImage);
    }

    /**
     * Gets the current HP of the boss.
     * @return The current HP of the boss.
     */
    public int getBossHP() {
        return bossHP;
    }

    /**
     * Sets the HP of the boss.
     * @param hp The new HP of the boss.
     */
    public void setBossHP(int hp) {
        this.bossHP = hp;
    }

    /**
     * Checks if the boss is dead.
     * @return True if the boss is dead, otherwise false.
     */
    public boolean isDead() {
        return isDead;
    }

    /**
     * Deals damage to the player.
     * @param player The player object.
     */
    @Override
    public void dealDamage(Stan player) {
        player.setLife(player.getLife() - 2);
        player.setLinearVelocity(new Vec2(-40, 0)); // Knockback effect of -40
        // Animate player getting hurt
        player.removeAllImages();
        player.addImage(player.stanHurt);
    }

    @Override
    public void move() {

    }

    @Override
    public void attack() {
        // Check if the spawn timer is running and return if it is
        if (spawnTimer != null && spawnTimer.isRunning()) {
            return;
        }

        // Initialize and start the spawn timer
        spawnTimer = new Timer(BAT_SPAWN_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spawn a bat when the timer triggers
                if (bat == null && isPlayerInRange()) {
                    removeAllImages();
                    addImage(bossAttackImage);

                    bat = new AluBat((GameLevel) getWorld(), Alucard.this); // Spawn a new bat
                    Vec2 spawnPosition = getPosition().add(new Vec2(-1, 0)); // Adjust the position as needed
                    float velocity = -5; // Adjust the speed as needed
                    bat.throwBat(spawnPosition, velocity);
                    bat.addDestructionListener(Alucard.this); // Add this Alucard as a destruction listener for the bat
                }
            }
        });
        spawnTimer.setRepeats(false); // Set the timer to trigger only once
        spawnTimer.start(); // Start the timer

        removeAllImages();
        addImage(bossImage);
    }

    // Method to be called when bat is destroyed
    public void batDestroyed() {
        bat = null; // Reset the bat reference
    }

    /**
     * Checks if the player is within the attack range of the enemy.
     * @return true if the player is within the attack range, false otherwise
     */
    private boolean isPlayerInRange() {
        // Get a reference to the player object from the current game level
        Stan player = ((GameLevel) getWorld()).getPlayer();

        // Get the position of the player and the enemy
        Vec2 playerPosition = player.getPosition();
        Vec2 enemyPosition = getPosition();

        // Calculate the distance between the player and the enemy
        float distance = playerPosition.sub(enemyPosition).length();

        // Check if the distance is less than or equal to the attack range
        return distance <= ATTACK_RANGE;
    }

    @Override
    public void die(Stan player) {
        // Decrement bossHP by 1 using the setter
        setBossHP(getBossHP() - 1);
        // Kill boss once conditions are met and reward the player!
        if (getBossHP() <= 0) {
            player.setGold(player.getGold() + 5000);
            playEnemySound("data/SoundFX/LongExplosion.wav");
            isDead = true;
            this.destroy();
        }
    }


    @Override
    public void preStep(StepEvent stepEvent) {
        if (bat == null && isPlayerInRange()) {
            attack(); // Attack if no bat has been spawned and player is in range
        }
    }

    @Override
    public void postStep(StepEvent stepEvent) {
    }

    @Override
    public void destroy(DestructionEvent e) {
        batDestroyed(); // Handle bat destruction
    }
}
