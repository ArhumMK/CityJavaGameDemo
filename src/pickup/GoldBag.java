package pickup;

import city.cs.engine.*;
import game.GameLevel;
import player.Stan;

// GoldBag subclass
public class GoldBag extends Pickups {
    public GoldBag(GameLevel level) {
        super(level, new BoxShape(1,1), "data/Misc/GoldBag.png", 2f);
    }

    @Override
    public void collide(Stan player) {
        playPickupSound("data/SoundFX/Pickup2.wav");
        player.setGold(player.getGold() + 100);
        this.destroy();
    }
}
