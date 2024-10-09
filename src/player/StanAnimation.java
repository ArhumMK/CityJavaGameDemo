package player;

import city.cs.engine.AttachedImage;
import city.cs.engine.BodyImage;
import org.jbox2d.common.Vec2;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The main animation handling class for the player's actions.
 */
public class StanAnimation {
    private Stan player;

    /**
     * Constructs a StanAnimation object for the given player.
     * @param player The player object to animate.
     */
    public StanAnimation(Stan player) {
        this.player = player;
    }

    /**
     * Updates the player object with a new instance. Used to get animations working on a level change.
     * @param newPlayer The new player instance.
     */
    public void updatePlayer(Stan newPlayer) {
        this.player = newPlayer;
    }

    /**
     * Updates the animation of the player based on the provided animation type. And is also used to update animation based on orientation
     * @param animationType The type of animation to update.
     */
    public void updateAnimation(String animationType) {
        BodyImage imageToAdd = getAnim(animationType);
        player.removeAllImages();
        if (!player.isFacingRight()) {
            player.addImage(imageToAdd).flipHorizontal();
        } else {
            player.addImage(imageToAdd);
        }
    }

    /**
     * Updates the attack animation (Whip) of the player based on the provided attacking standing or kneeling
     * @param attackAnimationType The type of attack animation to update.
     */
    public void updateAttackAnimation(String attackAnimationType) {
        int totalFrames = 4;
        int delay = 80;

        Timer timer = new Timer(delay, new ActionListener() {
            int frameCount = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (frameCount < totalFrames) {
                    switch (attackAnimationType) {
                        case "attacking":
                            updateAttackAnimationFrames(frameCount, player.stanAttack1, player.stanAttack2, player.stanAttack3, player.stanIdle);
                            break;
                        case "attackingKneeling":
                            updateAttackAnimationFrames(frameCount, player.stanKneelAttack1, player.stanKneelAttack2, player.stanKneelAttack3, player.stanKneel);
                            break;
                    }
                    frameCount++;
                } else {
                    ((Timer) e.getSource()).stop();
                }
            }
        });

        timer.start();
    }

    private void updateAttackAnimationFrames(int frameCount, BodyImage frame1, BodyImage frame2, BodyImage frame3, BodyImage idleImage) {
        switch (frameCount) {
            case 0:
                addAttackImage(frame1);
                break;
            case 1:
                addAttackImage(frame2);
                break;
            case 2:
                addAttackImage(frame3);
                break;
            case 3:
                player.removeAllImages();
                addIdleImage(idleImage);
                break;
        }
    }

    private void addIdleImage(BodyImage idleImage) {
        if (player.isKneeling()) {
            addKneelOrIdleImage(idleImage, player.stanKneel);
        } else {
            addKneelOrIdleImage(idleImage, player.stanIdle);
        }
    }

    private void addKneelOrIdleImage(BodyImage image, BodyImage kneelImage) {
        if (player.isFacingRight()) {
            player.addImage(kneelImage);
        } else {
            AttachedImage attachedImage = player.addImage(kneelImage);
            attachedImage.flipHorizontal();
        }
    }

    private void addAttackImage(BodyImage imageToAdd) {
        player.removeAllImages();

        AttachedImage attachedImage;
        if (player.isFacingRight()) {
            attachedImage = player.addImage(imageToAdd);
        } else {
            attachedImage = player.addImage(imageToAdd);
            attachedImage.flipHorizontal();
        }

        attachedImage.setOffset(new Vec2(2.4f, 0));
    }


    private BodyImage getAnim(String animationType) {
        return switch (animationType) {
            case "standing" -> player.stanIdle;
            case "walking" -> player.stanWalk;
            case "kneeling" -> player.stanKneel;
            case "readying" -> player.stanReady;
            case "hurting" -> player.stanHurt;
            case "corpse" -> player.stanDead;
            default ->
                    null;
        };
    }
}