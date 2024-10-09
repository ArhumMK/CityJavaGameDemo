package enemy;

import city.cs.engine.*;
import game.GameLevel;
import player.Stan;
import org.jbox2d.common.Vec2;

/**
 * Represents a goblin enemy in the game.
 */
public class Goblin extends Enemy {

    //initialize goblin dimensions
    private static final float goblinWidth = 1.2f;
    private static final float goblinHeight = 1.5f;
    private static final Shape goblShape = new BoxShape(goblinWidth,goblinHeight/2f);

    //goblin sprites
    public BodyImage goblImage = new BodyImage("data/Enemies/GoblinWalk.gif", goblinHeight+0.5f);

    //goblin stats
    private final int SPEED = 10;
    private float left, right;

    /**
     * Constructs a Goblin object and adds it to the current level.
     * @param currentLevel The current level of the game.
     */
    public Goblin(GameLevel currentLevel) {
        super(currentLevel, goblShape);
        addImage(goblImage);
        startWalking(SPEED);
    }

    /**
     * Sets the position of the goblin.
     * @param position The new position of the goblin.
     */
    @Override
    public void setPosition(Vec2 position) {
        super.setPosition(position);
        int RANGE = 6;
        left = position.x- RANGE;
        right = position.x+ RANGE;
    }

    /**
     * Handles actions to be taken before each step.
     * @param stepEvent Information about the step event.
     */
    @Override
    public void preStep(StepEvent stepEvent) {
        if (getPosition().x > right){
            jump(12);
            startWalking(-SPEED);
        }
        if (getPosition().x < left){
            jump(12);
            startWalking(SPEED);
        }
    }

    /**
     * Handles actions to be taken after each step.
     * @param stepEvent Information about the step event.
     */
    @Override
    public void postStep(StepEvent stepEvent) {
        // No action needed
    }

    /**
     * Deals damage to the player. and additional logic handling to animate and move hurt player
     * @param player The player object.
     */
    @Override
    public void dealDamage(Stan player) {
        player.setLife(player.getLife() - 1);
        player.setLinearVelocity(new Vec2(-10, 0)); // Knockback effect of -10
        // Animate player getting hurt
        player.removeAllImages();
        player.addImage(player.stanHurt);
    }

    @Override
    public void move() {
        // to do
    }

    @Override
    public void attack() {
        //none, melee based
    }

    @Override
    public void die(Stan player) {
        this.destroy();
        playEnemySound("data/SoundFX/Hit2.wav");
        player.setGold(player.getGold() + 25);
    }
}
