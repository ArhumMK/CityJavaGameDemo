package player;

import city.cs.engine.*;

/**
 * The main player character class. Extends city.cs.engine.Walker
 */
public class Stan extends Walker {
    private final WhipHandler whipHandler;
    private final ScoreManager scoreManager;

    //initialize player dimensions
    private static final float playerWidth = 1.4f; //1.4f player optimal width
    private static final int playerHeight = 4;
    private static final Shape stanShape = new BoxShape(playerWidth,playerHeight/2f);

    //player sprites
    public BodyImage stanIdle = new BodyImage("data/Player/StanIdle.png",playerHeight);
    public BodyImage stanKneel = new BodyImage("data/Player/StanKneel.png", playerHeight);
    public BodyImage stanHurt = new BodyImage("data/Player/StanHurt.png", playerHeight);
    public BodyImage stanDead = new BodyImage("data/Player/StanDead.png", playerHeight);
    public BodyImage stanReady = new BodyImage("data/Player/StanReady.png", playerHeight);

    //player animations (gif)
    public BodyImage stanWalk = new BodyImage("data/Player/StanWalk.gif", playerHeight);

    //player attack animations
    public BodyImage stanAttack1 = new BodyImage("data/Player/StanAttackWhip1.png", playerHeight);
    public BodyImage stanAttack2 = new BodyImage("data/Player/StanAttackWhip2.png", playerHeight);
    public BodyImage stanAttack3 = new BodyImage("data/Player/StanAttackWhip3.png", playerHeight);
    public BodyImage stanKneelAttack1 = new BodyImage("data/Player/StanKneelAttackWhip1.png", playerHeight);
    public BodyImage stanKneelAttack2 = new BodyImage("data/Player/StanKneelAttackWhip2.png", playerHeight);
    public BodyImage stanKneelAttack3 = new BodyImage("data/Player/StanKneelAttackWhip3.png", playerHeight);


    //initialize player stats
    private int life = 4; //4 HP by default
    private boolean hasOrb = false; //has not collected completion orb by default

    /**
     * Constructs the player character.
     * @param world The world in which the player exists.
     * @param scoreManager The ScoreManager object for managing the player's score.
     */
    public Stan(World world, ScoreManager scoreManager) {
        super(world, stanShape);
        addImage(stanIdle);
        setGravityScale(2);
        whipHandler = new WhipHandler(this);
        this.scoreManager = scoreManager;
    }

    //player orientation and states
    private boolean facingRight = true; // facing right by default
    private boolean kneeling = false; // not kneeling by default
    private boolean readying = false; // not ready by default
    private boolean jumping = false; // idle by default
    private boolean attacking = false; //idle by default
    private boolean walking = false;
    private boolean standing = true;

    /**
     * Checks if the player character is facing right.
     * @return True if the player is facing right, false otherwise.
     */
    public boolean isFacingRight() {
        return facingRight;
    }

    /**
     * Checks if the player character is currently kneeling.
     * @return True if the player is kneeling, false otherwise.
     */
    public boolean isKneeling() {
        return kneeling;
    }

    /**
     * Checks if the player character is currently in a ready stance.
     * @return True if the player is in a ready stance, false otherwise.
     */
    public boolean isReady() {
        return readying;
    }

    /**
     * Checks if the player character is currently jumping.
     * @return True if the player is jumping, false otherwise.
     */
    public boolean isJumping() {
        return jumping;
    }

    /**
     * Checks if the player character is currently attacking.
     * @return True if the player is attacking, false otherwise.
     */
    public boolean isAttacking() {
        return attacking;
    }

    /**
     * Checks if the player character is currently walking.
     * @return True if the player is walking, false otherwise.
     */
    public boolean isWalking() {
        return walking;
    }

    /**
     * Checks if the player character is currently standing.
     * @return True if the player is standing, false otherwise.
     */
    public boolean isStanding() {
        return standing;
    }

    /**
     * Sets the orientation of the player character.
     * @param facingRight True if the player should face right, false if left.
     */
    public void setFacingRight(boolean facingRight) {
        this.facingRight = facingRight;
    }

    /**
     * Sets the kneeling state of the player character.
     * @param kneeling True if the player is kneeling, false otherwise.
     */
    public void setKneeling(boolean kneeling) {
        this.kneeling = kneeling;
    }

    /**
     * Sets the ready stance state of the player character.
     * @param readying True if the player is in a ready stance, false otherwise.
     */
    public void setReadying(boolean readying) {
        this.readying = readying;
    }

    /**
     * Sets the jumping state of the player character.
     * @param jumping True if the player is jumping, false otherwise.
     */
    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    /**
     * Sets the attacking state of the player character.
     * @param attacking True if the player is attacking, false otherwise.
     */
    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    /**
     * Sets the walking state of the player character.
     * @param walking True if the player is walking, false otherwise.
     */
    public void setWalking(boolean walking) {
        this.walking = walking;
    }

    /**
     * Sets the standing state of the player character.
     * @param standing True if the player is standing, false otherwise.
     */
    public void setStanding(boolean standing) {
        this.standing = standing;
    }

    /**
     * Spawns the whip hitbox for the player.
     */
    public void spawnWhipHitBox() {
        whipHandler.spawnWhipHitBox();
    }

    /**
     * Destroys the player character when their life reaches zero.
     */
    public void killHim() {
        int health = getLife();
        if (health < 1) {
            destroy();
        }
    }

    //player mutators,accessors
    // LIFE
    /**
     * Retrieves the current health/life of the player character.
     * @return The current health/life of the player character.
     */
    public int getLife() {
        return life;
    }

    /**
     * Sets the health/life of the player character.
     * @param life The new health/life value.
     */
    public void setLife(int life) {
        this.life = life;
        killHim();
    }

    // GOLD
    /**
     * Sets the amount of gold the player character has.
     * @param gold The amount of gold to set.
     */
    public void setGold(int gold) {
        scoreManager.setGold(gold);
    }

    /**
     * Retrieves the amount of gold the player character has.
     * @return The amount of gold the player character has.
     */
    public int getGold() {
        return scoreManager.getGold();
    }

    // LEVEL COMPLETION ORB
    /**
     * Checks if the player character has collected the level completion orb.
     * @return True if the player has collected the level completion orb, false otherwise.
     */
    public boolean collectedOrb() {
        return hasOrb;
    }

    /**
     * Sets whether the player character has collected the level completion orb.
     * @param hasOrb True if the player has collected the orb, false otherwise.
     */
    public void setOrb(boolean hasOrb) {
        this.hasOrb = hasOrb;
    }
}
