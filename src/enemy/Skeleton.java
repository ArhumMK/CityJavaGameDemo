package enemy;

import city.cs.engine.*;
import game.GameLevel;
import player.Stan;
import org.jbox2d.common.Vec2;

/**
 * Represents a skeleton enemy in the game.
 */
public class Skeleton extends Enemy {

    //initialize skeleton dimensions
    private static final float skeletonWidth = 1.3f;
    private static final int skeletonHeight = 4;
    private static final Shape skelShape = new BoxShape(skeletonWidth,skeletonHeight/2f);

    //skeleton sprites
    public BodyImage skelImage = new BodyImage("data/Enemies/SkeletonWalk.gif", skeletonHeight);

    //skeleton stats
    private final int SPEED = 10;
    private final int RANGE = 3;
    private float left, right;

    /**
     * Constructs a Skeleton enemy and adds it to the current level.
     * @param currentLevel The current level of the game.
     */
    public Skeleton(GameLevel currentLevel) {
        super(currentLevel, skelShape);
        addImage(skelImage);
        startWalking(SPEED);
    }

    /**
     * Sets the position of the skeleton, also can add patrol behavior on each similation step
     * @param position The new position of the skeleton.
     */
    @Override
    public void setPosition(Vec2 position) {
        super.setPosition(position);
        left = position.x-RANGE;
        right = position.x+RANGE;
    }

    /**
     * Handles actions to be taken before each step.
     * @param stepEvent Information about the step event.
     */
    @Override
    public void preStep(StepEvent stepEvent) {
        if (getPosition().x > right){
            startWalking(-SPEED);
        }
        if (getPosition().x < left){
            startWalking(SPEED);
        }
    }

    /**
     * Handles actions to be taken after each step.
     * @param stepEvent Information about the step event.
     */
    @Override
    public void postStep(StepEvent stepEvent) {

    }

    /**
     * Deals damage to the player and handle additional logic to animate and move the player via linear velocity when called.
     * @param player The player object.
     */
    @Override
    public void dealDamage(Stan player) {
        player.setLife(player.getLife() - 1);
        player.setLinearVelocity(new Vec2(-20, 0)); // Knockback effect of -40
        // Animate player getting hurt
        player.removeAllImages();
        player.addImage(player.stanHurt);
    }

    @Override
    public void move() {

    }

    @Override
    public void attack() {

    }

    @Override
    public void die(Stan player) {
        this.destroy();
        playEnemySound("data/SoundFX/Hit5.wav");
        player.setGold(player.getGold() + 50);
    }
}
