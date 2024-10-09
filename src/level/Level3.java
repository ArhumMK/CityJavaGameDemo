package level;

import city.cs.engine.*;
import city.cs.engine.Shape;
import java.awt.*;

import enemy.*;
import game.*;
import org.jbox2d.common.Vec2;
import pickup.*;
import player.Stan;

/**
 * Represents the third and final level of the game.
 */
public class Level3 extends GameLevel {

    /**
     * Constructs Level3 object.
     * @param game The game object associated with this level.
     */
    public Level3(Game game) {
        super(game);

        // World variables

        /**
         * The friction of the world. At 50, there's no sliding around.
         */
        int WORLD_FRICTION = 50;

        /**
         * Represents a transparent color.
         */
        Color TRANSPARENT = new Color(0,0,0,0);

        // Actor variables

        /**
         * The starting point on the x-coordinate.
         */
        int STARTING_POINT = -32;

        /**
         * The y-coordinate of the ground level.
         */
        int GROUND_LEVEL = -4;

        // Set level background music
        startLevelBGM("data/Music/Level3.wav");

        // Actors spawn
        //Stan (player)
        getPlayer().setPosition(new Vec2(STARTING_POINT,GROUND_LEVEL-4));

        //Alucard (boss)
        new Alucard(this).setPosition(new Vec2(38,GROUND_LEVEL-4));

        //torches
        int torchPos = -28;
        for (int i = 0; i < 4; i++) {
            new Torch(this).setPosition(new Vec2(torchPos + 12 * i, -1));
        }

        //level complete orb spawn
        StaticBody chair = new StaticBody(this, new BoxShape(1,1.6f));
        chair.setPosition(new Vec2(41,GROUND_LEVEL-1));
        chair.setFillColor(TRANSPARENT);
        chair.setLineColor(TRANSPARENT);
        chair.setAlwaysOutline(false);
        new CompleteOrb(this, game).setPosition(new Vec2(41,GROUND_LEVEL+2));

        //sensor to destroy projectiles
        //create sensor to destroy boss projectile
        StaticBody projectileSensor = new StaticBody(this);
        projectileSensor.setPosition(new Vec2(13.84f,GROUND_LEVEL));
        Sensor s = new Sensor(projectileSensor, new BoxShape(1, 3));

        /* LEVEL STRUCTURE DATA, KEEP THIS LAST */

        // Invisible Walls
        //setup
        Shape barrierShape = new BoxShape(10f,11);
        //invisible barrier level 1-1
        StaticBody invisibleWall_1 = new StaticBody(this,barrierShape);
        SolidFixture invisibleWallNoFric1 = new SolidFixture(invisibleWall_1,barrierShape);
        //invisible barrier level 1-3
        StaticBody invisibleWall_2 = new StaticBody(this,barrierShape);
        SolidFixture invisibleWallNoFric2 = new SolidFixture(invisibleWall_2,barrierShape);

        //settings
        //invisible wall 1-1
        invisibleWallNoFric1.setFriction(0);    // 0 to prevent 'climbing' up walls
        invisibleWall_1.setPosition(new Vec2(-45,GROUND_LEVEL));
        invisibleWall_1.setFillColor(TRANSPARENT);
        invisibleWall_1.setLineColor(TRANSPARENT);
        //invisible wall 1-1
        invisibleWallNoFric2.setFriction(0);    // 0 to prevent 'climbing' up walls
        invisibleWall_2.setPosition(new Vec2(56,GROUND_LEVEL));
        invisibleWall_2.setFillColor(TRANSPARENT);
        invisibleWall_2.setLineColor(TRANSPARENT);

        // Level 3 - Castle

        // castle grounds
        //setup
        Shape castleGroundShape = new BoxShape(50, 3);

        //height of the image mapped to the ground platform
        float groundTextureHeight = 30f;

        //level 3-1
        BodyImage castleGround_1 = new BodyImage("data/Environments/Level-3-1.png",groundTextureHeight);
        StaticBody castleGroundCol_1 = new StaticBody(this, castleGroundShape);
        SolidFixture castleGroundFixture_1 = new SolidFixture(castleGroundCol_1,castleGroundShape);

        //level 3-2
        BodyImage castleGround_2 = new BodyImage("data/Environments/Level-3-2.png",groundTextureHeight);
        StaticBody castleGroundCol_2 = new StaticBody(this, castleGroundShape);
        SolidFixture castleGroundFixture_2 = new SolidFixture(castleGroundCol_2,castleGroundShape);

        //settings

        //offset for adjusting the ground image to the platform
        Vec2 groundTextureCoords = new Vec2(-2,12.1f);

        //level 3-1
        castleGroundFixture_1.setFriction(WORLD_FRICTION);
        castleGroundCol_1.setPosition(new Vec2(0f, -9));
        castleGroundCol_1.addImage(castleGround_1).setOffset(groundTextureCoords);

        //level 3-2
        castleGroundFixture_2.setFriction(WORLD_FRICTION);
        castleGroundCol_2.setPosition(new Vec2(94f, -9));
        castleGroundCol_2.addImage(castleGround_2).setOffset(groundTextureCoords);
    }

    /**
     * Gets the player character for this level.
     * @return The player character object.
     */
    public Stan getPlayer(){
        return player;
    }

    //level complete flag
    /**
     * Checks if the level is complete.
     * @return True if the level is complete, false otherwise.
     */
    @Override
    public boolean isComplete() {
        if (player.collectedOrb()) {
            return true;
        } else {
            return false;
        }
    }
}
