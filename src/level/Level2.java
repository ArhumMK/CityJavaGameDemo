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
 * Represents the second level of the game.
 */
public class Level2 extends GameLevel {

    /**
     * Constructs Level2 object.
     * @param game The game object associated with this level.
     */
    public Level2(Game game) {
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
        int GROUND_LEVEL = 0;

        // Set level background music
        startLevelBGM("data/Music/Level2.wav");

        // Actors spawn
        //Stan (player)
        getPlayer().setPosition(new Vec2(STARTING_POINT,GROUND_LEVEL-4));

        //enemies
        new Goblin(this).setPosition(new Vec2(0,GROUND_LEVEL-1));
        new Goblin(this).setPosition(new Vec2(20,GROUND_LEVEL-1));
        new Goblin(this).setPosition(new Vec2(34,GROUND_LEVEL-1));
        new Goblin(this).setPosition(new Vec2(58,GROUND_LEVEL-1));
        new Goblin(this).setPosition(new Vec2(88,GROUND_LEVEL-1));

        //level reward spawn
        int foodPos = 125;
        for (int i = 0; i < 3; i++) {
            new Food(this).setPosition(new Vec2(foodPos + 3 * i, GROUND_LEVEL));
            new GoldBag(this).setPosition(new Vec2(foodPos + 3 * i, GROUND_LEVEL+1));
        }

        //level complete orb spawn
        new CompleteOrb(this, game).setPosition(new Vec2(140,GROUND_LEVEL));

        // individual chunks of cave ground
        //setup
        Shape caveGroundChunkShape = new BoxShape(9.7f,3);
        BodyImage caveGroundChunk = new BodyImage("data/Environments/caveGround.png",6f);

        StaticBody caveGroundChunkCol_1 = new StaticBody(this,caveGroundChunkShape);
        SolidFixture caveGroundChunkFixture_1 = new SolidFixture(caveGroundChunkCol_1,caveGroundChunkShape);

        StaticBody caveGroundChunkCol_2 = new StaticBody(this,caveGroundChunkShape);
        SolidFixture caveGroundChunkFixture_2 = new SolidFixture(caveGroundChunkCol_2,caveGroundChunkShape);

        StaticBody caveGroundChunkCol_3 = new StaticBody(this,caveGroundChunkShape);
        SolidFixture caveGroundChunkFixture_3 = new SolidFixture(caveGroundChunkCol_3,caveGroundChunkShape);

        //settings
        caveGroundChunkFixture_1.setFriction(WORLD_FRICTION);
        caveGroundChunkCol_1.setPosition(new Vec2(85f,-9));
        caveGroundChunkCol_1.addImage(caveGroundChunk);

        caveGroundChunkFixture_2.setFriction(WORLD_FRICTION);
        caveGroundChunkCol_2.setPosition(new Vec2(115f,-9));
        caveGroundChunkCol_2.addImage(caveGroundChunk);

        caveGroundChunkFixture_3.setFriction(WORLD_FRICTION);
        caveGroundChunkCol_3.setPosition(new Vec2(130.5f,-5.7f));
        caveGroundChunkCol_3.addImage(caveGroundChunk);

        /* LEVEL STRUCTURE DATA, KEEP THIS LAST */

        // Kill pits
        //add them to the positions set in the method
        new DeadPits(this).setPosition(new Vec2(69.5f,-10));
        new DeadPits(this).setPosition(new Vec2(99,-10));

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
        //invisible wall 2-1
        invisibleWallNoFric1.setFriction(0);    // 0 to prevent 'climbing' up walls
        invisibleWall_1.setPosition(new Vec2(-45,GROUND_LEVEL));
        invisibleWall_1.setFillColor(TRANSPARENT);
        invisibleWall_1.setLineColor(TRANSPARENT);
        //invisible wall 2-2
        invisibleWallNoFric2.setFriction(0);    // 0 to prevent 'climbing' up walls
        invisibleWall_2.setPosition(new Vec2(150,-6));
        invisibleWall_2.setFillColor(TRANSPARENT);
        invisibleWall_2.setLineColor(TRANSPARENT);

        // Level 2 - Caves

        // Stone grounds
        //setup
        Shape caveGroundShape_1 = new BoxShape(45.5f, 3);
        Shape caveGroundShape_2 = new BoxShape(10,3);

        //height of the image mapped to the ground platform
        float groundTextureHeight = 30f;

        //level 1-1
        BodyImage caveGround_1 = new BodyImage("data/Environments/Level-2-1.png",groundTextureHeight);
        StaticBody caveGroundCol_1 = new StaticBody(this, caveGroundShape_1);
        SolidFixture caveGroundFixture_1 = new SolidFixture(caveGroundCol_1, caveGroundShape_1);
        //level 1-2
        BodyImage caveGround_2 = new BodyImage("data/Environments/Level-2-2.png",groundTextureHeight);
        StaticBody caveGroundCol_2 = new StaticBody(this, caveGroundShape_2);
        SolidFixture caveGroundFixture_2 = new SolidFixture(caveGroundCol_2, caveGroundShape_2);

        //settings

        //offset for adjusting the ground image to the platform
        Vec2 groundTextureCoords = new Vec2(-2,12.1f);

        //level 1-1
        caveGroundFixture_1.setFriction(WORLD_FRICTION);
        caveGroundCol_1.setPosition(new Vec2(0f, -9));
        caveGroundCol_1.addImage(caveGround_1).setOffset(groundTextureCoords);
        //level 1-2
        caveGroundFixture_2.setFriction(WORLD_FRICTION);
        caveGroundCol_2.setPosition(new Vec2(55f, -9));
        caveGroundCol_2.addImage(caveGround_2).setOffset(new Vec2(38,12.1f));
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
