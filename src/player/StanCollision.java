package player;

import city.cs.engine.*;
import enemy.Enemy;
import pickup.Pickups;
import projectile.AluBat;

/**
 * Class responsible for handling player collision detection (with other objects)
 */
public class StanCollision implements CollisionListener {

    public Stan player;

    /**
     * Constructs a StanCollision object with the given player.
     * @param player The player object for collision detection.
     */
    public StanCollision(Stan player) {
        this.player = player;
    }

    /**
     * Method invoked when a collision occurs.
     * @param e The collision event.
     */
    @Override
    public void collide(CollisionEvent e) {
        // player collision
        if (e.getOtherBody() instanceof Pickups p) {
            p.collide(player);
        } else if (e.getOtherBody() instanceof Enemy en) {
            en.dealDamage(player);
        } else if (e.getOtherBody() instanceof AluBat bat) {
            bat.collide(player);
        }
    }
}
