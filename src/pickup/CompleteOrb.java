package pickup;

import city.cs.engine.*;
import game.Game;
import game.GameLevel;
import player.Stan;

public class CompleteOrb extends Pickups {
    private final Game game;

    public CompleteOrb(GameLevel level, Game game) {
        super(level, new BoxShape(1,1), "data/Misc/CompleteOrb.png", 2f);
        this.game = game;
    }

    @Override
    public void collide(Stan player) {

        // Reward player for finishing level
        player.setGold(player.getGold() + 1000);

        playPickupSound("data/SoundFX/MenuConfirm.wav");
        // Set that the player has collected the orb
        player.setOrb(true);

        // Move to the next level
        game.goToNextLevel();
        player.setOrb(false);
    }
}
