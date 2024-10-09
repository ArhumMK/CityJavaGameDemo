package player;

import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import game.GameView;
import org.jbox2d.common.Vec2;

/**
 * A class responsible for tracking the player's position and making the "camera" follow the player.
 */
public class StanTracker implements StepListener {

    private final GameView view;
    private final Stan player;

    /**
     * Constructs a StanTracker with the given GameView and Stan player.
     * @param view The GameView to update.
     * @param player The Stan player to track.
     */
    public StanTracker(GameView view, Stan player) {
        this.view = view;
        this.player = player;
    }

    @Override
    public void preStep(StepEvent stepEvent) {

    }

    @Override
    public void postStep(StepEvent stepEvent) {
        Vec2 position = player.getPosition();
        //int camY = 4; //adjust camera height. 4 is about the same as CV3
        // (position.y+camY) makes the camera vertically off-centre by adding to position.y WHEN TRACKING Y
        Vec2 newPos = new Vec2(position.x, (4));
        view.setCentre(newPos);
    }
}
