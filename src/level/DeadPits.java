package level;

import city.cs.engine.*;
import player.Stan;

/**
 * A class representing pits that instantly kill the player on contact.
 */
public class DeadPits extends StaticBody implements SensorListener {
    private Sensor s;

    /**
     * Constructs a DeadPits object.
     * @param world The world in which the dead pits exist.
     */
    public DeadPits(World world) {
        super(world);

        // Create a sensor shape for the kill zone
        BoxShape killSensorShape = new BoxShape(4f, 1f);

        // Initialize the sensor
        this.s = new Sensor(this, killSensorShape);
        s.addSensorListener(this);
    }

    /**
     * Triggered when the sensor makes contact with another object.
     * If the contact is with the player, the player's life is reduced by 5 (as player's max life is 4, instantly kills him).
     * @param e The sensor event that occurred.
     */
    @Override
    public void beginContact(SensorEvent e) {
        if (e.getContactBody() instanceof Stan player) {
            player.setLife(player.getLife() - 5);
        }
    }

    /**
     * Triggered when the sensor stops making contact with another object.
     * @param e The sensor event that occurred.
     */
    @Override
    public void endContact(SensorEvent e) {
        // No action needed for end contact
    }
}
