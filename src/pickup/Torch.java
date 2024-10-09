package pickup;

import city.cs.engine.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Torch extends StaticBody {
    private SoundClip w;
    private GhostlyFixture gf;
    //private Sensor s;
    private BodyImage image;
    private Pickups p;

    public Torch(World world) {
        super(world);

        BoxShape torchShape = new BoxShape(0.7f,0.7f);
        this.gf = new GhostlyFixture(this, torchShape);
        //this.s = new Sensor(this,torchShape);
        //SolidFixture s = new SolidFixture(this,torchShape);
        //s.setFriction(0);
        image = new BodyImage("data/misc/Torch.gif", 2.5f);
        addImage(image);
    }

    public void playTorchSound(String soundPath) {
        try {
            w = new SoundClip(soundPath);
            w.setVolume(0.5);
            w.play();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }
    public void destroyTorch() {
        this.destroy();
        //spawnTorchPickup();
    }
    /*
    public void spawnTorchPickup() {
        Food f = new Food(this.getWorld());
        f.addImage(new BodyImage("data/Misc/Food.png"));
        f.setPosition(this.getPosition());
    }
    */
}
