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
 * Represents the first level of the game.
 */
public class Level1 extends GameLevel {

    /**
     * Constructs Level1 object.
     * @param game The game object associated with this level.
     */
    public Level1(Game game) {
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
        startLevelBGM("data/Music/Level1.wav");

        // Actors spawn
        //Stan (player)
        getPlayer().setPosition(new Vec2(STARTING_POINT,GROUND_LEVEL));

        //enemies
        new Skeleton(this).setPosition(new Vec2(10,GROUND_LEVEL-1));
        new Skeleton(this).setPosition(new Vec2(23,GROUND_LEVEL-1));
        new Skeleton(this).setPosition(new Vec2(40,GROUND_LEVEL-1));
        new Skeleton(this).setPosition(new Vec2(64,GROUND_LEVEL-1));
        new Skeleton(this).setPosition(new Vec2(180,GROUND_LEVEL-1));
        new Skeleton(this).setPosition(new Vec2(194,GROUND_LEVEL-1));
        new Skeleton(this).setPosition(new Vec2(200,GROUND_LEVEL-1));

        //torches
        int torchPos = -28;
        for (int i = 0; i < 4; i++) {
            new Torch(this).setPosition(new Vec2(torchPos + 12 * i, -1));
        }
        new Torch(this).setPosition(new Vec2(180, -1));
        new Torch(this).setPosition(new Vec2(192, -1));
        new Torch(this).setPosition(new Vec2(200, -1));

        //more pickups
        new Food(this).setPosition(new Vec2(50,GROUND_LEVEL-1));
        new Food(this).setPosition(new Vec2(76,GROUND_LEVEL-1));
        new Food(this).setPosition(new Vec2(158.6f,GROUND_LEVEL-1));
        new Food(this).setPosition(new Vec2(162f,GROUND_LEVEL-1));


        //level reward spawn
        int foodPos = 210;
        for (int i = 0; i < 3; i++) {
            new Food(this).setPosition(new Vec2(foodPos + 3 * i, GROUND_LEVEL));
            new GoldBag(this).setPosition(new Vec2(foodPos + 3 * i, GROUND_LEVEL+1));
        }

        //level complete orb spawn
        new CompleteOrb(this, game).setPosition(new Vec2(220,GROUND_LEVEL)); // 220 final position
        // World Data

        // Gravestones
        //gravestone properties
        float graveWidth = 3;
        BodyImage graveStoneImage = new BodyImage("data/Environments/GraveStone.png", 3f);
        Shape graveStoneShape = new BoxShape(graveWidth / 2, 1.5f);
        float gposY = -4.5f; //initial height of gravestone

        // draw 3 gravestones using for loop
        for (int graveNum = 0; graveNum < 16; graveNum++) {
            // create gravestone
            StaticBody graveStoneCol = new StaticBody(this, graveStoneShape);
            SolidFixture graveStoneFixture = new SolidFixture(graveStoneCol, graveStoneShape);
            graveStoneFixture.setFriction(WORLD_FRICTION);
            graveStoneCol.addImage(graveStoneImage);

            // calculate position and spacing
            float posX = 30 + graveNum * 8; // spacing is set to 8
            graveStoneCol.setPosition(new Vec2(posX, gposY));
            new GoldCoin(this).setPosition(new Vec2(posX, gposY + 1));
        }

        // Floating platforms
        // Setup
        BodyImage platformFloatingImage = new BodyImage("data/Environments/FloatingPlatform.png", 2f);
        Shape platformFloatingShape = new BoxShape(4f, 1f);
        float fposY = -0.5f; // Initial Y position for all platforms

        // Draw 4 floating platforms with spacing
        for (int platformNum = 0; platformNum < 4; platformNum++) {
            //create floating platform
            StaticBody platformFloatingCol = new StaticBody(this, platformFloatingShape);
            SolidFixture platformFloatingFixture = new SolidFixture(platformFloatingCol, platformFloatingShape);
            platformFloatingFixture.setFriction(WORLD_FRICTION);
            platformFloatingCol.addImage(platformFloatingImage);

            //calculate X position for each platform with spacing
            float fposX = 110 + platformNum * 14; // Adjust spacing between platforms as needed
            //set position for the platform
            platformFloatingCol.setPosition(new Vec2(fposX, fposY));
            //spawn a skeleton on top of each gravestone
            new Skeleton(this).setPosition(new Vec2(fposX,fposY+2));
        }

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
        invisibleWall_2.setPosition(new Vec2(231f,GROUND_LEVEL));
        invisibleWall_2.setFillColor(TRANSPARENT);
        invisibleWall_2.setLineColor(TRANSPARENT);

        // Level 1 - Graveyard

        //setup
        Shape grassGroundShape = new BoxShape(50, 3);

        //height of the image mapped to the ground platform
        float groundTextureHeight = 30f;

        //level 1-1
        BodyImage grassGround_1 = new BodyImage("data/Environments/Level-1-1.png",groundTextureHeight);
        StaticBody grassGroundCol_1 = new StaticBody(this, grassGroundShape);
        SolidFixture grassGroundFixture_1 = new SolidFixture(grassGroundCol_1,grassGroundShape);
        //level 1-2
        BodyImage grassGround_2 = new BodyImage("data/Environments/Level-1-2.png",groundTextureHeight);
        StaticBody grassGroundCol_2 = new StaticBody(this, grassGroundShape);
        SolidFixture grassGroundFixture_2 = new SolidFixture(grassGroundCol_2,grassGroundShape);
        //level 1-3
        BodyImage grassGround_3 = new BodyImage("data/Environments/Level-1-3.png",groundTextureHeight);
        StaticBody grassGroundCol_3 = new StaticBody(this, grassGroundShape);
        SolidFixture grassGroundFixture_3 = new SolidFixture(grassGroundCol_3,grassGroundShape);

        //settings

        //offset for adjusting the ground image to the platform
        Vec2 groundTextureCoords = new Vec2(-2,12.1f);

        //level 1-1
        grassGroundFixture_1.setFriction(WORLD_FRICTION);
        grassGroundCol_1.setPosition(new Vec2(0f, -9));
        grassGroundCol_1.addImage(grassGround_1).setOffset(groundTextureCoords);
        //level 1-2
        grassGroundFixture_2.setFriction(WORLD_FRICTION);
        grassGroundCol_2.setPosition(new Vec2(95f, -9));
        grassGroundCol_2.addImage(grassGround_2).setOffset(groundTextureCoords);
        //level 1-3
        grassGroundFixture_3.setFriction(WORLD_FRICTION);
        grassGroundCol_3.setPosition(new Vec2(190f, -9));
        grassGroundCol_3.addImage(grassGround_3).setOffset(new Vec2(-2,12.1f));
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
