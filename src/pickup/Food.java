package pickup;

import city.cs.engine.*;
import game.GameLevel;
import player.Stan;

// Food subclass
public class Food extends Pickups {
    public Food(GameLevel level) {
        super(level, new BoxShape(1,1), "data/Misc/Food.png", 2f);
    }

    @Override
    public void collide(Stan player) {
        playPickupSound("data/SoundFX/Pickup.wav");
        // Do not increase player health beyond the max life.
        int MAX_LIFE = 4;
        if (player.getLife() < MAX_LIFE) {
            player.setLife(player.getLife() + 1);
        } else {
            player.setGold(player.getGold() + 5);
        }
        this.destroy();
    }
}