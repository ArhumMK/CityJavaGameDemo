package player;

/**
 * The score handling class for managing player gold and preserving it on level changes.
 */
public class ScoreManager {
    private int gold;

    /**
     * Constructs a ScoreManager object with initial gold set to zero.
     */
    public ScoreManager() {
        this.gold = 0;
    }

    /**
     * Retrieves the current amount of gold.
     * @return The current amount of gold.
     */
    public int getGold() {
        return gold;
    }

    /**
     * Sets the amount of gold.
     * @param gold The new amount of gold.
     */
    public void setGold(int gold) {
        this.gold = gold;
    }
}
