package pickup;

import city.cs.engine.*;
import game.GameLevel;
import player.Stan;

// GoldCoin subclass
public class GoldCoin extends Pickups {
    public GoldCoin(GameLevel level) {
        super(level, new BoxShape(0.5f,0.5f), "data/Misc/GoldCoin.gif", 1f);
    }

    @Override
    public void collide(Stan player) {
        playPickupSound("data/SoundFX/MenuUp.wav");
        player.setGold(player.getGold() + 10);
        this.destroy();
    }
}